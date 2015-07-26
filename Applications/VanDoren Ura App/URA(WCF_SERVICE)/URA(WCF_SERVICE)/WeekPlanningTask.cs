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
    public class WeekPlanningTask
    {
        /// <summary>
        /// Reads week planning from database
        /// </summary>
        /// <param name="myParams">contains sql parametes: from and till date, session AES key for encrypting message</param>
        /// <param name="clientAESPrivateKey"></param>
        /// <returns></returns>
        public static string GetWeekPlanning(string[] myParams, string clientAESPrivateKey)
        {
            VeghelHost.UraServiceClient proxy = new VeghelHost.UraServiceClient();
            String respond = proxy.GetWeekPlanning(myParams);

            try
            {
                proxy.Close();
            }
            catch (Exception ex)
            {
                VanDoren.LogLite.Log.WriteInfo("GetWeekPlanning proxy closing error: " + ex.Message, "");
                proxy.Abort();
            }

            return AESEngine.Encrypt(respond, clientAESPrivateKey);
        }
    }
}