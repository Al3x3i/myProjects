using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.Web;
using Newtonsoft.Json.Linq;

namespace URA_WCF_SERVICE_
{
    [ServiceBehavior(ConfigurationName = "URA")]
    public class AppSevice : IAppService
    {
        private int keySize = 512;

        public string test()
        {
            return "Hello Message";
        }

        public string HelloMessage(string message, string phoneIMEI)
        {
            //set log file settings
            VanDoren.LogLite.Log.LogDirectory = @"C:\Logs\URA\";

            if (message == "URA" && phoneIMEI != null)
            {
                return LoginTask.SSL_HelloMessage(message, phoneIMEI, keySize);
            }
            return null;
        }

        public string KeyExchange(string message, string sessionID)
        {
            if (message != null && sessionID != null)
            {
                AppUserData tempUser = ClientServiceSession.GetUser(sessionID);

                if (tempUser != null)
                {
                    return LoginTask.SSL_KeyExchange(message, sessionID, keySize, tempUser);
                }
            }

            return null;
        }

        public Stream LoginMessage(Stream entity)
        {
            return LoginTask.SSL_Login(entity);
        }

        public Stream WorkedHoursPerWeek(string message, string sessionID)
        {

            string encryptedMessage = message;
            string returnEncryptedMessage = null;
            AppUserData tempUser = userRequestValidation(message, sessionID);

            if (tempUser != null)
            {
                try
                {
                    string decryptedMessage = AESEngine.Decrypt(encryptedMessage, tempUser.ClientAESPrivateKey);

                    dynamic json = JValue.Parse(decryptedMessage);

                    string phoneImei = json.phoneImei;
                    string searchWord = json.searchWord;

                    //TO DO ADD PHONE IMEI VALIDATION, ONLY ONE USER CAN USE SAME SESSION
                    string[] myParams = searchWord.Split(';');

                    //Should have 3 paramteres(year, week, employeeDBID)
                    if (myParams.Length == 3)
                    {
                        returnEncryptedMessage = WorkedHoursTask.GetProjectsPerWeek(myParams, tempUser.ClientAESPrivateKey);
                    }

                    if (returnEncryptedMessage != null)
                    {
                        return new System.IO.MemoryStream(UTF8Encoding.Default.GetBytes(returnEncryptedMessage));
                    }
                }
                //This exception occurs if the encypted message is wrong
                catch
                {
                    return null;
                }
            }
            return new System.IO.MemoryStream(ASCIIEncoding.Default.GetBytes("Session Error"));
        }

        public Stream AvailableProjects(string message, string sessionID)
        {

            string encryptedMessage = message;
            string returnEncryptedMessage = null;
            AppUserData tempUser = userRequestValidation(message, sessionID);

            if (tempUser != null)
            {
                try
                {
                    string decryptedMessage = AESEngine.Decrypt(encryptedMessage, tempUser.ClientAESPrivateKey);

                    dynamic json = JValue.Parse(decryptedMessage);

                    string phoneImei = json.phoneImei;

                    //TO DO ADD PHONE IMEI VALIDATION, ONLY ONE USER CAN USE SAME SESSION

                    returnEncryptedMessage = WorkedHoursTask.GetAvailableProjects(tempUser.ClientAESPrivateKey);

                    if (returnEncryptedMessage != null)
                    {
                        return new System.IO.MemoryStream(UTF8Encoding.Default.GetBytes(returnEncryptedMessage));
                    }
                }
                //This exception occurs if the encypted message is wrong
                catch (Exception ex)
                {
                    VanDoren.LogLite.Log.WriteInfo("AvailableProjects method error: " + ex.Message, "");
                }
            }
            return null;
        }

        public string AddNewTaskHours(string message, string sessionID)
        {
            string encryptedMessage = message;
            string returnEncryptedMessage = null;
            AppUserData tempUser = userRequestValidation(message, sessionID);

            if (tempUser != null)
            {
                try
                {
                    string decryptedMessage = AESEngine.Decrypt(encryptedMessage, tempUser.ClientAESPrivateKey);

                    dynamic json = JValue.Parse(decryptedMessage);

                    string phoneImei = json.phoneImei;
                    string searchWord = json.searchWord; // contains JSON object


                    JObject jOject = JObject.Parse(searchWord);
                    //TO DO ADD PHONE IMEI VALIDATION, ONLY ONE USER CAN USE SAME SESSION 
                    if (jOject.Count != 0)
                    {
                        returnEncryptedMessage = WorkedHoursTask.AddNewTaskToDatabase(jOject, tempUser.ClientAESPrivateKey);
                    }

                    if (returnEncryptedMessage != null)
                    {
                        return returnEncryptedMessage;
                    }
                }
                //This exception occurs if the encypted message is wrong
                catch (Exception ex)
                {
                    VanDoren.LogLite.Log.WriteInfo("AddNewTaskHours method error: " + ex.Message, "");
                }
            }
            return "Session Error";
        }


        public string UpdateTaskHours(string message, string sessionID)
        {
            string encryptedMessage = message;
            string returnEncryptedMessage = null;
            AppUserData tempUser = userRequestValidation(message, sessionID);
            if (tempUser != null)
            {
                try
                {
                    string decryptedMessage = AESEngine.Decrypt(encryptedMessage, tempUser.ClientAESPrivateKey);

                    dynamic json = JValue.Parse(decryptedMessage);

                    string phoneImei = json.phoneImei;
                    string searchWord = json.searchWord;


                    JObject jArray = JObject.Parse(searchWord);
                    //TO DO ADD PHONE IMEI VALIDATION, ONLY ONE USER CAN USE SAME SESSION 
                    if (jArray.Count != 0)
                    {
                        returnEncryptedMessage = WorkedHoursTask.UpdateTaskInDatabase(jArray, tempUser.ClientAESPrivateKey);
                    }

                    if (returnEncryptedMessage != null)
                    {
                        return returnEncryptedMessage;
                    }
                }
                //This exception occurs if the encypted message is wrong
                catch (Exception ex)
                {
                    VanDoren.LogLite.Log.WriteInfo("UpdateTaskHours method error: " + ex.Message, "");
                }
            }
            return "Session Error";
        }

        public string GetWeekPlanning(string message, string sessionID)
        {
            string encryptedMessage = message;
            string returnEncryptedMessage = null;
            AppUserData tempUser = userRequestValidation(message, sessionID);

            if (tempUser != null)
            {
                try
                {
                    string decryptedMessage = AESEngine.Decrypt(encryptedMessage, tempUser.ClientAESPrivateKey);

                    dynamic json = JValue.Parse(decryptedMessage);

                    string phoneImei = json.phoneImei;
                    string searchWord = json.searchWord;

                    //TO DO ADD PHONE IMEI VALIDATION, ONLY ONE USER CAN USE SAME SESSION
                    string[] myParams = searchWord.Split(';');

                    //Should have 3 paramteres(year, week, capDBID)
                    if (myParams.Length == 3)
                    {
                        returnEncryptedMessage = WeekPlanningTask.GetWeekPlanning(myParams, tempUser.ClientAESPrivateKey);
                    }

                    if (returnEncryptedMessage != null)
                    {
                        return returnEncryptedMessage;
                    }
                }
                //This exception occurs if the encypted message is wrong
                catch (Exception ex)
                {
                    VanDoren.LogLite.Log.WriteInfo("GetWeekPlanning method error: " + ex.Message, "");
                }
            }
            return "Session Error";
        }


        public string DeleteTaskHours(string message, string sessionID)
        {
            string encryptedMessage = message;
            string returnEncryptedMessage = null;
            AppUserData tempUser = userRequestValidation(message, sessionID);
            if (tempUser != null)
            {
                try
                {
                    string decryptedMessage = AESEngine.Decrypt(encryptedMessage, tempUser.ClientAESPrivateKey);

                    dynamic json = JValue.Parse(decryptedMessage);

                    string phoneImei = json.phoneImei;
                    string searchWord = json.searchWord;

                    JObject jArray = JObject.Parse(searchWord);
                    //TO DO ADD PHONE IMEI VALIDATION, ONLY ONE USER CAN USE SAME SESSION 
                    if (jArray.Count != 0)
                    {
                        returnEncryptedMessage = WorkedHoursTask.DeleteTaskInDatabase(jArray, tempUser.ClientAESPrivateKey);
                    }
                    if (returnEncryptedMessage != null)
                    {
                        return returnEncryptedMessage;
                    }
                }
                //This exception occurs if the encypted message is wrong
                catch (Exception ex)
                {
                    VanDoren.LogLite.Log.WriteInfo("UpdateTaskHours method error: " + ex.Message, "");
                }
            }
            return "Session Error";
        }


        /// <summary>
        /// Search user in session list by request session ID
        /// </summary>
        /// <param name="message">request parameters</param>
        /// <param name="sessionID"> session id</param>
        /// <returns>If session ID exist in session list then AppUserData object will be returned </returns>
        private AppUserData userRequestValidation(string message, string sessionID)
        {
            if (message != null && sessionID != null && sessionID.Length == 36)
            {
                AppUserData tempUser = ClientServiceSession.GetUser(sessionID);

                if (tempUser != null && tempUser.LoginHistoryRowID != null)
                {
                    tempUser.LastTimeCalledService = DateTime.Now;
                    return tempUser;
                }
            }
            return null;
        }
    }

}
