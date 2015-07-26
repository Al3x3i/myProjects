using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data.SqlClient;
using System.Data;
using Newtonsoft.Json.Linq;
using System.Globalization;

namespace URA_WCF_SERVICE_
{
    public class WorkedHoursTask
    {

        /// <summary>
        /// Gets the worked hours task list and ecrypt by session AES master key
        /// </summary>
        /// <param name="myParams">contains from, till dates and URA DBID</param>
        /// <param name="clientAESPrivateKey">Session AES private key</param>
        /// <returns>Returns task list from to till date</returns>
        public static string GetProjectsPerWeek(string[] myParams, string clientAESPrivateKey)
        {
            VeghelHost.UraServiceClient proxy = new VeghelHost.UraServiceClient();
            String respond = proxy.WorkedHoursPerWeek(myParams);

            CloseProxy("GetWeekPlanning proxy closing error", proxy);

            String message = AESEngine.Encrypt(respond, clientAESPrivateKey);
            return message;
        }

        /// <summary>
        /// Get all available projects with task and encypt by Session AES mater key
        /// </summary>
        /// <param name="clientAESPrivateKey">Session AES master key</param>
        /// <returns> return encrypted project list</returns>
        public static string GetAvailableProjects(string clientAESPrivateKey)
        {
            String respond = null;
            VeghelHost.UraServiceClient proxy = null;
            proxy = new VeghelHost.UraServiceClient();
            respond = proxy.AvailableProjects();

            CloseProxy("GetAvailableProjects proxy closing error", proxy);

            String message = AESEngine.Encrypt(respond, clientAESPrivateKey);
            return message;
        }

        /// <summary>
        /// Add new task to the database
        /// </summary>
        /// <param name="myParams">SQL query parameters: Date, Hours, TaskDBID, Syntus, EmployeeID</param>
        /// <param name="clientAESPrivateKey">Session encryption key</param>
        /// <returns>Return encypted message</returns>
        public static string AddNewTaskToDatabase(JObject myParams, string clientAESPrivateKey)
        {
            Dictionary<String, String> temp = new Dictionary<string, string>()
            {
                {"Date",myParams["Date"].ToString()},
                {"Hours",myParams["Hours"].ToString()},
                {"TaskDbid",myParams["TaskDbid"].ToString()},
                {"Syntus",myParams["Syntus"].ToString()},
                {"EmployeesDBID",myParams["EmployeesDBID"].ToString()}
            };

            VeghelHost.UraServiceClient proxy = new VeghelHost.UraServiceClient();
            String respond = proxy.AddNewTaskHours(temp);

            CloseProxy("AddNewTaskToDatabase proxy closing error", proxy);

            String message = AESEngine.Encrypt(respond, clientAESPrivateKey);
            return message;

        }

        /// <summary>
        /// Update Task working hours
        /// </summary>
        /// <param name="myParams">contains id, date, hours, taskDBID, employeeID</param>
        /// <param name="clientAESPrivateKey">session private AES key</param>
        /// <returns>after update returns message 'UPDATED' or 'NOTUPDATED'</returns>
        public static string UpdateTaskInDatabase(JObject myParams, string clientAESPrivateKey)
        {
            Dictionary<String, String> temp = new Dictionary<string, string>()
            {
                {"DBID",myParams["DBID"].ToString()},
                {"Date",myParams["Date"].ToString()},
                {"Hours",myParams["Hours"].ToString()},
                {"TaskDbid",myParams["TaskDbid"].ToString()},
                {"EmployeesDBID",myParams["EmployeesDBID"].ToString()}
            };

            VeghelHost.UraServiceClient proxy = new VeghelHost.UraServiceClient();
            String respond = proxy.UpdateTaskHours(temp);

            CloseProxy("UpdateTaskInDatabase proxy closing error", proxy);

            String message = AESEngine.Encrypt(respond, clientAESPrivateKey);
            return message;
        }

        /// <summary>
        /// Prase Json object and invoke proxy to delete rowID in database. For validation send whole data from the row.
        /// </summary>
        /// <param name="myParams"></param>
        /// <param name="clientAESPrivateKey"></param>
        /// <returns></returns>
        public static string DeleteTaskInDatabase(JObject myParams, string clientAESPrivateKey)
        {
            Dictionary<String, String> temp = new Dictionary<string, string>()
            {
                {"DBID",myParams["DBID"].ToString()},
                {"Date",myParams["Date"].ToString()},
                {"Hours",myParams["Hours"].ToString()},
                {"TaskDbid",myParams["TaskDbid"].ToString()},
                {"EmployeesDBID",myParams["EmployeesDBID"].ToString()}
            };

            VeghelHost.UraServiceClient proxy = new VeghelHost.UraServiceClient();
            String respond = proxy.DeleteTaskHours(temp);

            CloseProxy("UpdateTaskInDatabase proxy closing error", proxy);

            String message = AESEngine.Encrypt(respond, clientAESPrivateKey);
            return message;
        }
        /// <summary>
        /// Close opened proxy, in case error, Log function will be invoked with exception message
        /// </summary>
        /// <param name="excaptionMessage">Exception message</param>
        /// <param name="proxy">Opened proxy</param>
        private static void CloseProxy(string excaptionMessage, VeghelHost.UraServiceClient proxy)
        {
            try
            {
                proxy.Close();
            }
            catch (Exception ex)
            {
                VanDoren.LogLite.Log.WriteInfo(excaptionMessage + ": " + ex.Message, "");
                proxy.Abort();
            }
        }
    }
}