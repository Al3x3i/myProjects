using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Threading.Tasks;
using System.Threading;
using System.Data.SqlClient;

namespace URA_WCF_SERVICE_
{
    public class ClientServiceSession
    {
        private static Dictionary<Guid, AppUserData> sessionContainer = new Dictionary<Guid, AppUserData>();

        //private static int temporaryUserTimeOut = 30;
        private static int loggedUserSessionWaitingTime = 20; // session timeout in minutes
        private static int sessionCleanerWaitingTime = 5;  //thread will run every 3 minutes, to check whether "sessionWaitingTime" ran out of time or not.

        public static Thread sessionCleaner;
        private static List<Guid> closeConnectionList;
        /// <summary>
        /// Add new user to session container
        /// </summary>
        /// <param name="Imei">phone Imei</param>
        /// <param name="rsaPublicKey">RSA public key</param>
        /// <param name="rsaPrivateKey">RSA private key</param>
        /// <returns></returns>
        public static string AddUserToSessionContainer(string Imei, string rsaPublicKey, string rsaPrivateKey)
        {
            if (sessionCleaner == null)
            {
                CreteServiceCleaner();
            }
            Guid guid = Guid.NewGuid();
            AppUserData temp = new AppUserData { PhoneImei = Imei, RSApublicKey = rsaPublicKey, RSAprivateKey = rsaPrivateKey, LastTimeCalledService = DateTime.Now };
            sessionContainer.Add(guid, temp);
            return guid.ToString();

        }

        /// <summary>
        /// Provide user search by user ID
        /// </summary>
        /// <param name="clientGuidID">User GUID</param>
        /// <returns>reurn AppUserData object</returns>
        public static AppUserData GetUser(string clientGuidID)
        {
            try
            {
                Guid tempGuid = new Guid(clientGuidID);
                foreach (KeyValuePair<Guid, AppUserData> pair in sessionContainer)
                {
                    if (pair.Key.CompareTo(tempGuid) == 0)
                    {
                        return pair.Value;
                    }
                }
                return null;
            }
            catch
            {
                return null;
            }
        }
    

        private static void CreteServiceCleaner()
        {
            closeConnectionList = new List<Guid>();
            sessionCleaner = new Thread(CleanSessions);
            sessionCleaner.Priority = ThreadPriority.BelowNormal;
            sessionCleaner.Start();
        }

        /// <summary>
        /// If loggedUserSessionWaitingTime is exceeded then connection session will be removed from container and updated Table "ConnectionHistories" -> Set IsOnline='False'
        /// </summary>
        private static void CleanSessions()
        {
            while (true)
            {
                try
                {
                    VanDoren.LogLite.Log.WriteInfo("Thread sleep", "");
                    Thread.Sleep(TimeSpan.FromMinutes(sessionCleanerWaitingTime));
                    for (int i = 0; i < sessionContainer.Count; i++)
                    {
                        var item = sessionContainer.ElementAt(i);
                        if (item.Value.LastTimeCalledService.AddMinutes(loggedUserSessionWaitingTime) < DateTime.Now)
                        {
                            closeConnectionList.Add(item.Value.LoginHistoryRowID);

                            //Remove connection Session from Container
                            DeleteUserFromSessionContainer(item.Key.ToString());
                        }
                    }
                    VanDoren.LogLite.Log.WriteInfo("closeConnectionList.Count()", "");
                    if (closeConnectionList.Count() == 0)
                        continue;

                    //Update ConnectionHistories Table
                    string connectionString = System.Configuration.ConfigurationManager.ConnectionStrings["VanDorenURA_APP"].ConnectionString;
                    using (SqlConnection connection = new SqlConnection(connectionString))
                    {

                        string updateHistoryQuery = "(";
                        for (int s = 0; s < closeConnectionList.Count; s++)
                        {
                            updateHistoryQuery += s + 1 != closeConnectionList.Count ? "'" + closeConnectionList[s].ToString() + "'," : "'" + closeConnectionList[s].ToString() + "'";
                        }
                        VanDoren.LogLite.Log.WriteInfo("SQL query:" + updateHistoryQuery, "");
                        updateHistoryQuery += ")";
                        using (SqlCommand command = new SqlCommand(@"UPDATE AccountInfo
                                                              SET IsOnline = 'False'
                                                              UPDATE LoginHistory
	                                                          SET LoggedOut =GETDATE()
                                                              FROM 
	                                                            LoginHistory lh
	                                                            INNER JOIN AccountInfo ai 
	                                                            ON ai.AccountID = lh.AccountID
	                                                            WHERE lh.ID IN" + updateHistoryQuery, connection))
                        {
                            VanDoren.LogLite.Log.WriteInfo("Execute query", "");
                            command.Connection.Open();
                            command.ExecuteNonQuery();
                            closeConnectionList.Clear();
                        }
                    }

                }
                catch (Exception ex)
                {
                    VanDoren.LogLite.Log.WriteInfo("Thread cleaner Exception"+ ": " + ex.Message, "");
                }
            }
        }
        /// <summary>
        /// Removes user from session container
        /// </summary>
        /// <param name="clientGuidID">User GUID</param>
        /// <returns></returns>
        public static bool DeleteUserFromSessionContainer(string clientGuidID)
        {
            try
            {
                if (sessionContainer.Remove(new Guid(clientGuidID)))
                {
                    return true;
                }
                return false;
            }
            catch
            {
                return false;
            }
        }
    }



    /// <summary>
    /// User model class
    /// </summary>
    public class AppUserData
    {
        public string PhoneImei { get; set; }
        public string EmployeeDBID { get; set; }
        public Guid LoginHistoryRowID { get; set; }
        public string RSApublicKey { get; set; }
        public string RSAprivateKey { get; set; }
        public string ClientAESPrivateKey { get; set; }
        public DateTime LastTimeCalledService { get; set; }

    }
}