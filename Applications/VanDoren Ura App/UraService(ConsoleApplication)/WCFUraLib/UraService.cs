using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Globalization;
using System.Linq;
using System.ServiceModel;
using System.ServiceModel.Description;
using System.Text;
using System.Threading.Tasks;
using Newtonsoft.Json.Linq;

namespace WCFUraLib
{
    public class UraService : IUraService
    {
        private ServiceHost myServiceHost = null;

        private readonly string urenRegistratie = @"";
        private readonly string VDE_Generic = @"";

        /// <summary>
        /// Start Service
        /// </summary>
        public void StartService()
        {
            if (myServiceHost != null)
            {
                myServiceHost.Close();
            }

            try
            {
                //Set log file settings
                VanDoren.LogLite.Log.LogDirectory = @"C:\Logs\URA\";

                // Create the binding.
                NetTcpBinding binding = new NetTcpBinding();
                binding.Security.Mode = SecurityMode.None;
                binding.Security.Transport.ClientCredentialType = TcpClientCredentialType.None;

                // Create the URI for the endpoint.
                Uri netTcpUri = new Uri("net.tcp://192.168.30.112:8008");

                // Create the service host and add an endpoint.
                myServiceHost = new ServiceHost(typeof(UraService), netTcpUri);

                ServiceMetadataBehavior mBehave = new ServiceMetadataBehavior();
                myServiceHost.Description.Behaviors.Add(mBehave);

                myServiceHost.AddServiceEndpoint(typeof(IUraService), binding, "");
                myServiceHost.AddServiceEndpoint(typeof(IMetadataExchange), MetadataExchangeBindings.CreateMexTcpBinding(), "mex");

                // Open the service.
                myServiceHost.Open();

            }
            catch (Exception ex)
            {
                VanDoren.LogLite.Log.WriteInfo("Error while starting UraHost service: " + ex.Message, "");
            }
        }

        /// <summary>
        /// Stop Service
        /// </summary>
        public void StopService()
        {
            if (myServiceHost != null)
            {
                myServiceHost.Close();
                myServiceHost = null;
            }
        }


        /// <summary>
        /// To test service.
        /// </summary>
        /// <returns></returns>
        public string Test()
        {
            return "hello World";
        }

        /// <summary>
        /// Read worked hours list from database
        /// </summary>
        /// <param name="myParams">Contains startDate, endDate and employeeID</param>
        /// <returns>Returns list of worked hours. Message is packed to JSON extension</returns>
        public string WorkedHoursPerWeek(string[] myParams)
        {
            try
            {
                using (SqlConnection myConnection = new SqlConnection(urenRegistratie))
                {
                    myConnection.Open();

                    using (SqlCommand myCommand = new SqlCommand())
                    {
                        myCommand.CommandText = @"SET 
                                                LANGUAGE DUTCH
                                            SELECT
                                                u.DBID,
                                                LEFT(CONVERT(VARCHAR, DateHour, 120), 10) AS Date,
                                                DATENAME(dw,u.DateHour) as day,  
                                                u.UrenWorked,
                                                u.StatusHour, 
                                                u.tabelTakenDBID,
                                                t.TaakNaam,
                                                p.ProjectID
                                            FROM
                                                tabelUren as u
                                            LEFT JOIN 
                                                tabelTaken as t
                                            ON 
                                                u.tabelTakenDBID = t.DBID
                                            LEFT JOIN 
                                                tabelProjecten as p
                                            ON 
                                                p.DBID = t.ProjectDBID
                                            WHERE 
                                                u.DateHour BETWEEN @startDate AND @endDate AND u.tabelEmployeesDBID = @employee
                                            ORDER BY 2";

                        myCommand.CommandType = CommandType.Text;
                        myCommand.Parameters.AddWithValue("@startDate", DateTime.ParseExact(myParams[0].ToString(), "yyyy-MM-dd", CultureInfo.InvariantCulture));
                        myCommand.Parameters.AddWithValue("@endDate", DateTime.ParseExact(myParams[1].ToString(), "yyyy-MM-dd", CultureInfo.InvariantCulture));
                        myCommand.Parameters.AddWithValue("@employee", myParams[2]);
                        myCommand.Connection = myConnection;
                        using (SqlDataReader myReader = myCommand.ExecuteReader())
                        {
                            if (myReader.HasRows)
                            {
                                JArray jArray = new JArray();
                                while (myReader.Read())
                                {
                                    jArray.Add(new JObject(
                                                new JProperty("DBID", myReader["DBID"]),
                                                new JProperty("Date", myReader["Date"]),
                                                new JProperty("Day", myReader["Day"]),
                                                new JProperty("UrenWorked", myReader["UrenWorked"]),
                                                new JProperty("StatusHour", myReader["StatusHour"]),
                                                new JProperty("tabelTakenDBID", myReader["tabelTakenDBID"]),
                                                new JProperty("TaakNaam", myReader["TaakNaam"]),
                                                new JProperty("ProjectID", myReader["ProjectID"])));

                                }
                                return jArray.ToString();
                            }
                            //SQL query returned empty result
                            else
                            {
                                return "";
                            }
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                VanDoren.LogLite.Log.WriteInfo("WorkedHoursPerWeek method error: " + ex.Message, "");
                return "";
            }
        }

        /// <summary>
        /// Read all available projects from database
        /// </summary>
        /// <returns>Returns list of projects. Each project contains name, id, and list of task. Message format is JSON</returns>
        public string AvailableProjects()
        {
            try
            {
                using (SqlConnection myConnection = new SqlConnection(urenRegistratie))
                {
                    myConnection.Open();

                    using (SqlCommand myCommand = new SqlCommand())
                    {
                        myCommand.CommandText = @"SELECT 
                                                ProjectID,
                                                t.DBID as takenDBID,
                                                t.TaakNaam
                                                        
                                              FROM 
                                                tabelProjecten AS p
                                              JOIN 
                                                tabelTaken AS t
                                              ON 
                                                p.DBID = t.ProjectDBID
                                              WHERE ProjectVoltooid = 0
                                              ORDER BY 1";
                        myCommand.CommandType = CommandType.Text;

                        myCommand.Connection = myConnection;
                        using (SqlDataReader myReader = myCommand.ExecuteReader())
                        {
                            if (myReader.HasRows)
                            {
                                JArray jArray = new JArray();
                                JArray tempObject = new JArray();
                                string projectID = "";
                                string nextProjectID = "";
                                myReader.Read();

                                if (myReader.Read())
                                {
                                    projectID = myReader["ProjectID"].ToString();
                                    new JObject(new JProperty("id", 12), new JProperty("name", 12));
                                    tempObject.Add(new JObject(new JProperty("task_id", myReader["takenDBID"].ToString()),
                                                                new JProperty("task_name", myReader["TaakNaam"].ToString())));
                                    jArray.Add(projectID);

                                    while (myReader.Read())
                                    {
                                        nextProjectID = myReader["ProjectID"].ToString();
                                        if (projectID == nextProjectID)
                                        {
                                            projectID = nextProjectID;
                                            tempObject.Add(new JObject(new JProperty("task_id", myReader["takenDBID"].ToString()),
                                                                        new JProperty("task_name", myReader["TaakNaam"].ToString())));
                                        }
                                        else
                                        {
                                            projectID = nextProjectID;
                                            jArray.Add(tempObject);
                                            tempObject = new JArray();
                                            jArray.Add(projectID);
                                            tempObject.Add(new JObject(new JProperty("task_id", myReader["takenDBID"].ToString()),
                                                                        new JProperty("task_name", myReader["TaakNaam"].ToString())));
                                        }
                                    }
                                    //add last project's tasks
                                    jArray.Add(tempObject);
                                    return jArray.ToString();
                                }
                            }
                            return "";
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                VanDoren.LogLite.Log.WriteInfo("AvailableProjects method error: " + ex.Message, "");
                return "";
            }

        }

        /// <summary>
        /// Add new working hours to the 'urenregistratie'
        /// </summary>
        /// <param name="myParams">Contains Date, Worked Hours, TaskDBID, Syntus, EmployeeDBID</param>
        /// <returns>In case positive outcome returns 'INSERTED' and added row id. Otherwise returns just 'NOTINSERTED'</returns>
        public string AddNewTaskHours(Dictionary<string, string> myParams)
        {
            using (SqlConnection myConnection = new SqlConnection(urenRegistratie))
            {
                myConnection.Open();

                using (SqlCommand myCommand = new SqlCommand())
                {
                    try
                    {
                        String tabelUren_DateHour = myParams["Date"].ToString();
                        double tabelUren_UrenWorked = Convert.ToDouble(myParams["Hours"], CultureInfo.InvariantCulture);
                        String tabelUren_TakenDBID = myParams["TaskDbid"].ToString();
                        String tabelUren_FunctionHour = myParams["Syntus"].ToString();
                        int tabelUren_EmployeesDBID = Convert.ToInt32(myParams["EmployeesDBID"]);
                        myCommand.CommandText = @"BEGIN TRAN
                                                    BEGIN TRY
	                                                INSERT tabelUren 
                                                        (DateHour, 
                                                        UrenWorked,
                                                        StatusHour, 
                                                        tabelTakenDBID,
                                                        functionHour, 
                                                        tabelEmployeesDBID,
                                                        commandHour) 
	                                                VALUES
                                                        (@DateHour,
                                                        @UrenWorked, 
                                                        1, 
                                                        @TakenDBID, 
                                                        @FunctionHour,
                                                        @EmployeesDBID,
                                                        ' ')
                                                    SELECT SCOPE_IDENTITY()
	                                                COMMIT TRAN
                                                END TRY
	                                            BEGIN CATCH
	                                                ROLLBACK TRAN
                                                END CATCH";
                        myCommand.CommandType = CommandType.Text;
                        myCommand.Connection = myConnection;
                        myCommand.Parameters.AddWithValue("@DateHour", DateTime.ParseExact(tabelUren_DateHour, "yyyy-MM-dd", CultureInfo.InvariantCulture));
                        myCommand.Parameters.AddWithValue("@UrenWorked", tabelUren_UrenWorked);
                        myCommand.Parameters.AddWithValue("@TakenDBID", tabelUren_TakenDBID);
                        myCommand.Parameters.AddWithValue("@FunctionHour", tabelUren_FunctionHour);
                        myCommand.Parameters.AddWithValue("@EmployeesDBID", tabelUren_EmployeesDBID);
                        //read new row id
                        int scalar = Convert.ToInt32(myCommand.ExecuteScalar());
                        if (scalar != 0)
                        {
                            return "INSERTED;" + scalar;
                        }
                    }
                    catch (Exception ex)
                    {
                        VanDoren.LogLite.Log.WriteInfo("AddNewTaskHours method error: " + ex.Message, "");
                    }
                    return "NOTINSERTED";
                }
            }
        }
        /// <summary>
        /// Update working hours in existed task
        /// </summary>
        /// <param name="myParams">Contains rowDBID, Date, TaskDBID, EmployeeeDBID</param>
        /// <returns>Returns 'Updated' if task's hours were updated successfully</returns>
        public string UpdateTaskHours(Dictionary<string, string> myParams)
        {
            using (SqlConnection myConnection = new SqlConnection(urenRegistratie))
            {
                myConnection.Open();

                using (SqlCommand myCommand = new SqlCommand())
                {
                    try
                    {
                        String tabelUren_DBID = myParams["DBID"].ToString();
                        String tabelUren_DateHour = myParams["Date"].ToString();
                        Double tabelUren_UrenWorked = Convert.ToDouble(myParams["Hours"], CultureInfo.InvariantCulture);
                        String tabelUren_TakenDBID = myParams["TaskDbid"].ToString();
                        int tabelUren_EmployeesDBID = Convert.ToInt32(myParams["EmployeesDBID"]);

                        myCommand.CommandText = @"BEGIN TRAN
                                                BEGIN TRY
	                                                UPDATE 
                                                        tabelUren 
                                                    SET
                                                        UrenWorked=@UrenWorked
	                                                WHERE 
                                                        DBID = @DBID
                                                    AND 
                                                        DateHour = @DateHour 
                                                    AND 
                                                        StatusHour = 1 
                                                    AND 
                                                        tabelTakenDBID = @TakenDBID 
                                                    AND 
                                                        tabelEmployeesDBID = @EmployeesDBID
	                                                COMMIT TRAN
                                                END TRY
                                                BEGIN CATCH
	                                                ROLLBACK TRAN
                                                END CATCH";

                        myCommand.Parameters.AddWithValue("@DBID", tabelUren_DBID);
                        myCommand.Parameters.AddWithValue("@DateHour", DateTime.ParseExact(tabelUren_DateHour, "yyyy-MM-dd", CultureInfo.InvariantCulture));
                        myCommand.Parameters.AddWithValue("@UrenWorked", tabelUren_UrenWorked);
                        myCommand.Parameters.AddWithValue("@TakenDBID", tabelUren_TakenDBID);
                        myCommand.Parameters.AddWithValue("@EmployeesDBID", tabelUren_EmployeesDBID);
                        myCommand.CommandType = CommandType.Text;
                        myCommand.Connection = myConnection;
                        int counterAffectedRows = myCommand.ExecuteNonQuery();
                        if (counterAffectedRows == 1)
                        {
                            return "UPDATED";
                        }
                    }
                    catch (Exception ex)
                    {
                        VanDoren.LogLite.Log.WriteInfo("UpdateTaskHours method error: " + ex.Message, "");
                    }
                    return "NOTUPDATED";
                }
            }
        }
        /// <summary>
        /// Reads from database week plaaninng per week
        /// </summary>
        /// <param name="myParams">Contains from and till date, employeeDBID</param>
        /// <returns>returns week planning</returns>
        public string GetWeekPlanning(string[] myParams)
        {
            try
            {
                using (SqlConnection myConnection = new SqlConnection(VDE_Generic))
                {
                    myConnection.Open();

                    using (SqlCommand myCommand = new SqlCommand())
                    {
                        myCommand.CommandText = @"SELECT 
                                                P.Code as Project, 
                                                T.Name as Task,
                                                SD.Hours, 
                                                SD.Date
                                            FROM 
                                                Project P
                                            LEFT JOIN 
                                                Task T 
                                            ON 
                                                P.Dbid = T.ProjectDbid
                                            LEFT JOIN 
                                                TaskAssignment TA 
                                            ON 
                                                T.Dbid = TA.TaskDbid
                                            LEFT JOIN 
                                                Employee E 
                                            ON 
                                                E.DBid = TA.EmployeeDbid
                                            LEFT JOIN 
                                                ScheduleDay SD
                                            ON 
                                                TA.Dbid = SD.TaskAssignmentDbid
                                            WHERE 
                                                E.Dbid = @EmployeeDbid
                                            AND 
                                                SD.Date >= @BeginWeekDate 
                                            AND 
                                                SD.Date <= @EndWeekDate
                                            ORDER BY SD.Date ASC, P.Code ASC";

                        myCommand.CommandType = CommandType.Text;
                        myCommand.Parameters.AddWithValue("@BeginWeekDate", DateTime.ParseExact(myParams[0].ToString(), "yyyy-MM-dd", CultureInfo.InvariantCulture));
                        myCommand.Parameters.AddWithValue("@EndWeekDate", DateTime.ParseExact(myParams[1].ToString(), "yyyy-MM-dd", CultureInfo.InvariantCulture));
                        myCommand.Parameters.AddWithValue("@EmployeeDbid", myParams[2].ToString());
                        myCommand.Connection = myConnection;

                        using (SqlDataReader myReader = myCommand.ExecuteReader())
                        {
                            if (myReader.HasRows)
                            {
                                JArray jArray = new JArray();
                                while (myReader.Read())
                                {
                                    jArray.Add(new JObject(
                                                new JProperty("Project", myReader["Project"]),
                                                new JProperty("Task", myReader["Task"]),
                                                new JProperty("Hours", myReader["Hours"]),
                                                new JProperty("Date", myReader["Date"])));
                                }
                                return jArray.ToString();
                            }
                            //SQL query returned empty result, week planning is empty
                            else
                            {
                                return "";
                            }
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                VanDoren.LogLite.Log.WriteInfo("GetWeekPlanning method error: " + ex.Message, "");
                return null;
            }
        }

        /// <summary>
        /// Remove row id from database, for where statement are used: rowID, Date, hours, status, taskID and employeeID
        /// </summary>
        /// <param name="myParams">Parameteres for where statement</param>
        /// <returns> in case deleted row will be returned 'DELETED', otherwise 'NOTDELETED'</returns>
        public string DeleteTaskHours(Dictionary<string, string> myParams)
        {
            using (SqlConnection myConnection = new SqlConnection(urenRegistratie))
            {
                myConnection.Open();

                using (SqlCommand myCommand = new SqlCommand())
                {
                    try
                    {
                        String tabelUren_DBID = myParams["DBID"].ToString();
                        String tabelUren_DateHour = myParams["Date"].ToString();
                        Double tabelUren_UrenWorked = Convert.ToDouble(myParams["Hours"], CultureInfo.InvariantCulture);
                        String tabelUren_TakenDBID = myParams["TaskDbid"].ToString();
                        int tabelUren_EmployeesDBID = Convert.ToInt32(myParams["EmployeesDBID"]);

                        myCommand.CommandText = @"BEGIN TRAN
	                                                BEGIN TRY
		                                                DELETE 
			                                                tabelUren 
		                                                WHERE 
			                                                DBID = @DBID
		                                                AND 
			                                                DateHour = @DateHour
                                                        AND
                                                            UrenWorked = @UrenWorked
		                                                AND 
			                                                StatusHour = 1 
		                                                AND 
			                                                tabelTakenDBID = @TakenDBID 
		                                                AND 
			                                                tabelEmployeesDBID = @EmployeesDBID
		                                                COMMIT TRAN
	                                                END TRY
	                                                BEGIN CATCH
		                                                ROLLBACK TRAN
	                                                END CATCH";

                        myCommand.Parameters.AddWithValue("@DBID", tabelUren_DBID);
                        myCommand.Parameters.AddWithValue("@DateHour", DateTime.ParseExact(tabelUren_DateHour, "yyyy-MM-dd", CultureInfo.InvariantCulture));
                        myCommand.Parameters.AddWithValue("@UrenWorked", tabelUren_UrenWorked).SqlDbType = SqlDbType.Real;
                        myCommand.Parameters.AddWithValue("@TakenDBID", tabelUren_TakenDBID);
                        myCommand.Parameters.AddWithValue("@EmployeesDBID", tabelUren_EmployeesDBID);
                        myCommand.CommandType = CommandType.Text;
                        myCommand.Connection = myConnection;
                        int counterAffectedRows = myCommand.ExecuteNonQuery();
                        if (counterAffectedRows == 1)
                        {
                            return "DELETED";
                        }
                    }
                    catch (Exception ex)
                    {
                        VanDoren.LogLite.Log.WriteInfo("UpdateTaskHours method error: " + ex.Message, "");
                    }
                    return "NOTDELETED";
                }
            }
        }
    }
}