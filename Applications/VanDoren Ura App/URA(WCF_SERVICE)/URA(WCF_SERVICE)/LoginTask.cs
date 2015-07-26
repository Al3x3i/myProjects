using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.IO;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Web;

namespace URA_WCF_SERVICE_
{
    public class LoginTask
    {
        public static string SSL_HelloMessage(string message, string phoneIMEI, int keySize)
        {
            try
            {
                string publicKey = String.Empty;
                string publicAndPrivateKey = String.Empty;

                RSAEngine.GenerateKeys(keySize, out publicKey, out publicAndPrivateKey);

                // For every new session generates random public and private key and stores to Account object
                RSAEngine.GenerateKeys(keySize, out publicKey, out publicAndPrivateKey);

                //Create personal session
                string guid = ClientServiceSession.AddUserToSessionContainer(phoneIMEI, publicKey, publicAndPrivateKey);

                //Return generated public key
                return publicKey + "<id>" + guid + "</id>";
            }
            catch
            { 
                return null; 
            }
        }

        public static string SSL_KeyExchange(string message, string sessionID, int keySize, AppUserData tempUser)
        {
            try
            {
                string clientAESPrivateKey = RSAEngine.DecryptText(message, keySize, tempUser.RSAprivateKey);
                if (clientAESPrivateKey != null)
                {
                    tempUser.ClientAESPrivateKey = clientAESPrivateKey;
                }
                string confirmationMessage = AESEngine.Encrypt("SSLConfirmed", clientAESPrivateKey);

                return confirmationMessage;
            }
            catch
            {
                return null;
            }
        }

        public static Stream SSL_Login(Stream stream)
        {
            string encryptedLogin = null;
            string encryptedPassword = null;
            string userSessionID = null;
            string sessionId = OperationContext.Current.SessionId;

            HttpPostContentParser parser = new HttpPostContentParser(stream);
            if (parser.Success)
            {
                string a = parser.Parameters["0"];
                //SessionID(0) is not encrypted
                userSessionID = HttpUtility.UrlDecode(parser.Parameters["0"]);
                encryptedLogin = HttpUtility.UrlDecode(parser.Parameters["1"]);
                encryptedPassword = HttpUtility.UrlDecode(parser.Parameters["2"]);

                if (encryptedLogin != null && encryptedPassword != null && userSessionID != null)
                {
                    // Retrived Session account from list and then this data will be used to decrypt a message. If no Session then will return null
                    AppUserData sessionUser = ClientServiceSession.GetUser(userSessionID);
                    if (sessionUser != null)
                    {
                        
                        //string connection_VanDorenURA_Original = System.Configuration.ConfigurationManager.ConnectionStrings["VanDorenURA_Original"].ConnectionString;
                        //Read connection settings from app.config
                        string connection_VanDorenURA_APP = System.Configuration.ConfigurationManager.ConnectionStrings["VanDorenURA_APP"].ConnectionString;

                        string aesDecryptedLogin = AESEngine.Decrypt(encryptedLogin, sessionUser.ClientAESPrivateKey);
                        string aesDecryptedPassword = AESEngine.Decrypt(encryptedPassword, sessionUser.ClientAESPrivateKey);

                        try
                        {
                            using (SqlConnection myConnection = new SqlConnection(connection_VanDorenURA_APP))
                            {
                                myConnection.Open();

                                SqlCommand myCommand = new SqlCommand(@"DECLARE 
                                                                        @PHONE_ID VARCHAR(20)
                                                                    SELECT    
	                                                                    @PHONE_ID = PhoneImei
                                                                    FROM 
	                                                                    BlockedPhoneImei
                                                                    WHERE
	                                                                    PhoneImei = @phoneImei
                                                                    IF @PHONE_ID IS NULL
                                                                    SELECT *
                                                                    FROM 
                                                                        AccountInfo 
                                                                    WHERE 
                                                                        login=@myLogin AND IsActive='True'", myConnection);

                                myCommand.Parameters.Add(new SqlParameter("@myLogin", aesDecryptedLogin));
                                myCommand.Parameters.Add(new SqlParameter("@phoneImei", sessionUser.PhoneImei));
                                
                                using (SqlDataReader myReader = myCommand.ExecuteReader())
                                {

                                    //In case if user doesn't exist or phone is blocked then return value is 0 
                                    if (myReader.HasRows)
                                    {
                                        myReader.Read();
                                        //Get password from config to decrypt password from database
                                        string dbRsaPublicAndPrivateKey = System.Configuration.ConfigurationManager.AppSettings["dbRsaPublicAndPrivateKey"];
                                        string decryptedPasswordFromDatabase = RSAEngine.DecryptText((String)myReader["Password"], 512, dbRsaPublicAndPrivateKey);

                                        if (decryptedPasswordFromDatabase == aesDecryptedPassword)
                                        {
                                            String fullName = Convert.ToString(myReader["firstName"]) + " ";
                                            fullName += myReader["middleName"] != DBNull.Value ? Convert.ToString(myReader["middleName"]) + " " : "";
                                            fullName += Convert.ToString(myReader["secondName"]);

                                            String URA_DBID = Convert.ToString(myReader["URA_DBID"]);
                                            String syntus = Convert.ToString(myReader["SyntusFunction"]);
                                            String capa_DBID = Convert.ToString(myReader["Capa_DBID"]);
                                            SqlCommand sqlCommand = new SqlCommand(@"BEGIN TRAN
                                                                    DECLARE 
                                                                        @Account_ID uniqueidentifier,
                                                                        @Hisotry_ID uniqueidentifier
                                                                    SELECT
                                                                        @Account_ID = AccountID
                                                                    FROM 
                                                                        AccountInfo
                                                                    WHERE 
                                                                        Login =@myLogin AND IsActive='True' 
                                                                    IF @Account_ID IS NOT NULL
                                                                    UPDATE 
                                                                        AccountInfo SET IsOnline= 1 WHERE AccountID = @Account_ID
                                                                    BEGIN TRY
                                                                        SET 
                                                                            @Hisotry_ID =NEWID()
                                                                        INSERT INTO 
                                                                            LoginHistory (ID,LoggedIn,PhoneImei, AccountID)
                                                                        VALUES 
                                                                            (@Hisotry_ID, GETDATE(), @phoneImei ,@Account_ID)
                                                                        SELECT 
                                                                            @Hisotry_ID
                                                                        COMMIT
                                                                    END TRY
                                                                    BEGIN CATCH
	                                                                    ROLLBACK
                                                                    END CATCH", myConnection);

                                            sqlCommand.Parameters.Add(new SqlParameter("@myLogin", aesDecryptedLogin));

                                            sqlCommand.Parameters.Add(new SqlParameter("@phoneImei", sessionUser.PhoneImei));

                                            using (SqlDataReader myReader2 = sqlCommand.ExecuteReader())
                                            {
                                                // Affected row should be two = Updated IsOnline and Insert to LoginHistories
                                                if (myReader2.RecordsAffected == 2)
                                                {
                                                    //I need to save rowID in user object. Later this ID will be used by session cleaner(Thread). See ClientServiceSession
                                                    myReader2.Read();

                                                    //This value will is check while getting data and to remove from database
                                                    sessionUser.LoginHistoryRowID = (Guid)myReader2[0];

                                                    JObject jObject = new JObject() { 
                                                        new JProperty("Confirmation", "LoginAccepted"),
                                                        new JProperty("fullName",fullName),
                                                        new JProperty("URA_DBID", URA_DBID),
                                                        new JProperty("syntus", syntus),
                                                        new JProperty("capa_DBID",capa_DBID)};
                                                    string returnMessage = AESEngine.Encrypt(jObject.ToString(), sessionUser.ClientAESPrivateKey);
                                                    return new System.IO.MemoryStream(ASCIIEncoding.Default.GetBytes(returnMessage));
                                                }
                                                else if (myReader2.RecordsAffected == 1)
                                                {
//CAN OCCURE ERORR message, probably uniq ID exception
                                                }
                                            }
                                        }
                                    }
                                    return null;
                                }
                            }
                        }
                        catch (Exception ex)
                        {
                            // Writes the error message to a log file.
                            VanDoren.LogLite.Log.WriteInfo("SSL_Login method error" + ex.Message, "");
                            ClientServiceSession.DeleteUserFromSessionContainer(userSessionID);
                            return null;
                        }
                    }
                }
            }
            //While parsing occurs error(the data was corupted or wrong format sended to the service) or SessionID is not available, 
            ClientServiceSession.DeleteUserFromSessionContainer(userSessionID);
            return null;
        }

    }
}