using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using MySql.Data.MySqlClient;

using Phidgets;
using Phidgets.Events;

namespace WindowsFormsApplication66
{
    public class DatabaseHandler
    {
        public  delegate void PaymentProcessHandler(int i);

        event PaymentProcessHandler PaymentReceived;
        event PaymentProcessHandler CantChangeBalance;
        event PaymentProcessHandler NoMoney;
        event PaymentProcessHandler DBproblem;
        event PaymentProcessHandler NoSuchID;

        private MySqlConnection connection;

        private string server;
        private string database;
        private string userID;
        private string dBPassWord;

        private string processLog;  //string for lbIdReader
        private decimal clientBalance = 0;
        private bool clientDOB = true; //False means client is older than 18 years.

        public DatabaseHandler(string server, string database, string userid, string dbpassword)
        {
            this.server = server;
            this.database = database;
            this.userID = userid;
            this.dBPassWord = dbpassword;
            String connectionInfo = "server=" + server +
                                    ";database=" + database +
                                    ";user id=" + userid +
                                    ";password=" + dbpassword +
                                    ";connect timeout=30;";
            connection = new MySqlConnection(connectionInfo);
        }

        public string getProcessLog
        {
            get{ return processLog; }
        }

        public decimal getClientBalance
        {
            get { return clientBalance; }
        }

        public bool getYoung
        {
            get { return clientDOB; }
        }

        public void setDatabaseRespond(PaymentProcessHandler temp)
        {
            this.PaymentReceived = temp;     //Eqals 1
            this.CantChangeBalance = temp;  //Eqals 2
            this.NoMoney = temp;            //Eqals 3
            this.DBproblem = temp;          //Eqals 4
            this.NoSuchID = temp;           //Eqals 5
        }

        public bool GetAllArticles(List<Articles> temp)
        {
            String sql = "SELECT * FROM article";
            MySqlCommand command = new MySqlCommand(sql, connection);
            try
            {
                connection.Open();
                MySqlDataReader reader = command.ExecuteReader();

                int articleId;
                int type_id;
                string articleName;
                string articleType;
                Boolean alcoholic;
                decimal articlePrice;
                int article_stock;
                
                while (reader.Read())
                {
                    articleId = Convert.ToInt32(reader["ARTICLE_ID"]);
                    type_id = Convert.ToInt32(reader["TYPE_ID"]);
                    articleName = Convert.ToString(reader["A_NAME"]);
                    articleType = Convert.ToString(reader["A_TYPE"]);
                    alcoholic = Convert.ToBoolean(reader["ALCOHOLIC"]); 
                    articlePrice = Convert.ToDecimal(reader["PRICE"]);
                    //articlePrice = Convert.ToString(reader["PRICE"]);
                    article_stock = Convert.ToInt32(reader["IN_STOCK"]);
                    
                    if (articleType == "Dranken")
                    {
                        temp.Add(new Drinks(articleId,type_id, articleName, articleType, alcoholic, articlePrice, article_stock));
                    }
                    else if (articleType == "Snacks")
                    {
                        temp.Add(new Snacks(articleId, type_id, articleName, articleType, alcoholic, articlePrice, article_stock));
                    }
                    else
                    {
                        temp.Add(new Stuff(articleId, type_id, articleName, articleType, alcoholic, articlePrice, article_stock));
                    }
                }
                return true;
            }
            catch
            {
                DBproblem(4);
                return false;
            }
            finally
            {
                connection.Close();
            }
        }

        public bool Start_operation(List<Articles> clientOrder, string clientID, decimal finalPrice)
        {
            int nrOfSales_orderRecordsChanged = 0;  // counter -  shows how many times "Sales Order" table was changed
            processLog = "";
            try
            {
                    List<string> statement = new List<string>();
                    connection.Open();
                    foreach (Articles temp in clientOrder)
                    {
                        MySqlCommand command = new MySqlCommand("make_purchase", connection);  //Read below content of procedure
                        command.CommandType = System.Data.CommandType.StoredProcedure;
                        command.Parameters.Add(new MySqlParameter("a_id", temp.GetArticleId));
                        command.Parameters.Add(new MySqlParameter("amount", 1));
                        command.Parameters.Add(new MySqlParameter("client_id", clientID));

                        command.ExecuteNonQuery();
                        nrOfSales_orderRecordsChanged += 1;
                    }
                    processLog = "Sales order table changed:" + nrOfSales_orderRecordsChanged +" time(s).";

                    //if (checkBalance(finalPrice, clientID)) I do not have to call this method, after For some security reason may check second time if user have enought money, but only before creating order list
                    //{
                        if (changeBalance(finalPrice, clientID))
                        {
                            PaymentReceived(1);             //return "Good!";
                            return true;
                        }
                        else
                        {
                            CantChangeBalance(2);   //return "Can't change balance in database";  // can't change balance
                            return false;
                        }
                    //}
                    //else
                    //{
                        //DBproblem(4);
                        //return false;
                    //}
            }
            catch
            {
                DBproblem(4);           //return "Database error!"; 
                return false;
            }
            finally
            {
                connection.Close();
            }
        }

        public void readClientInfo(string clientID)   //read from client his/her balance and date of birth/ By the date I can say is he young or not and store in boolean
        {
             try
            {
                String sql = "SELECT balance, dob FROM client WHERE chip_code ='" + clientID + "'";
                MySqlCommand command = new MySqlCommand(sql, connection);

                connection.Open();
                MySqlDataReader reader = command.ExecuteReader();
                reader.Read();
                clientBalance = Convert.ToDecimal(reader["BALANCE"]);
                DateTime date = Convert.ToDateTime(reader["DOB"]);

                if (date.AddYears(18) > DateTime.Now) { clientDOB = true; }
                else { clientDOB = false; }
            }
            catch
            {
                NoSuchID(5);
            }
            finally
            {
                connection.Close();
            }
        }

        //private bool checkBalance(decimal orderPrice, string clientID)
        //{
        //    try
        //    {
        //        String sql = "SELECT BALANCE FROM client WHERE chip_code ='" + clientID + "'";
        //        MySqlCommand command = new MySqlCommand(sql, connection);
        //        MySqlDataReader reader = command.ExecuteReader();
        //        reader.Read();
        //        decimal clientBalance = Convert.ToDecimal(reader["BALANCE"]);
        //        reader.Close();
        //        if (clientBalance >= orderPrice)
        //        {
        //            return true;
        //        }
        //        else
        //        {
        //            return false;
        //        }
        //    }
        //    catch
        //    {
        //        return false;
        //    }
        //}

        private bool changeBalance(decimal item, string clientID)
        {
            String sql = "UPDATE CLIENT SET BALANCE = BALANCE-" + item + " WHERE CHIP_CODE = '"+ clientID+"'";
            MySqlCommand command = new MySqlCommand(sql, connection);

            try
            {
                command.ExecuteNonQuery();
                processLog += " - Balance is changed";
                return true;
            }
            catch
            {
                processLog += " - Error, Client balance was not changed";
                return false;
            }
        }
    }
}
/*
drop procedure if exists make_purchase;
delimiter // 
create procedure make_purchase(in a_id int(3), in amount int(11), in client_id varchar(20))
begin
declare v_in_stock int(11);
select in_stock
into v_in_stock
from article
where article_id = a_id;
if (v_in_stock > amount) then
  update article
  set in_stock = in_stock - amount
  where article_id = a_id;
  insert into sales_order (`ARTICLE_ID`,`CHIP_CODE`,`Total`)
  values (a_id,client_id, amount);
  commit;
end if;
end;

// example of calling
call make_purchase('koffie','10','0000b3cde1');
 */
