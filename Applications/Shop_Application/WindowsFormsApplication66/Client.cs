using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace WindowsFormsApplication66
{
    public class Client
    {
        public DatabaseHandler DBConnection;
        private List<Articles> article;
        private List<Articles> clientOrder = null;
        private string clientID = null;                    ///Use for testing "0000b3cde1"
        private decimal clientBalance;
        private bool clientDOB = false;
        private decimal FinalOrderPrice = 0;
        Label lbPrice;
        Label lbBalance;
        Label lbRemaining;
        ListBox lbInfo;

        public Client(DatabaseHandler.PaymentProcessHandler temp, Label lbPrice, Label lbBalance, Label lbRemaining, ListBox lbInfo, ListBox lbIdReader)
        {
            DBConnection = new DatabaseHandler("athena01.fhict.local", "dbi289674", "dbi289674", "rxLHDTP75c");
            DBConnection.setDatabaseRespond(temp); //Set database exceptions
            
            this.lbPrice = lbPrice;
            this.lbBalance = lbBalance;
            this.lbRemaining = lbRemaining;
            this.lbInfo = lbInfo;

            clientOrder = new List<Articles>();
            article = new List<Articles>();
            if (DBConnection.GetAllArticles(article))  //Load articles from database
            {
                lbIdReader.Items.Add("Database is loaded");
            }
            else
            {
                lbIdReader.Items.Add("Database error");
            }
        }

        public decimal GetFinalPrice 
        { 
            get { return FinalOrderPrice; } 
        }

        public List<Articles> getListOfArticles
        {
            get { return article; }
        }

        public List<Articles> getClientOrder
        {
            get { return clientOrder; }
        }

        public void deleteClientOrder()
        {
            FinalOrderPrice = 0;
            clientOrder = new List<Articles>();
        }

        public bool RemoverArticleFromOrder(string del)
        {
            foreach (Articles temp in clientOrder)
            {
                if (temp.GetID() == del)
                {
                    FinalOrderPrice = FinalOrderPrice - temp.GetArticlePrice;
                    lbRemaining.Text = (getClientBalance() - GetFinalPrice).ToString();
                    clientOrder.Remove(temp);
                    return true;
                }
            }
            return false;
        }

        public string ClientID
        {
            get { return clientID; }
            set { clientID = value; }
        }

        public void getClientInfo()
        {
            DBConnection.readClientInfo(clientID);
            clientBalance = DBConnection.getClientBalance;
            clientDOB = DBConnection.getYoung;
        }

        public decimal getClientBalance()
        {
            return clientBalance;
        }

        private Articles getArticle(string i)
        {
            foreach (Articles temp in article)
            {
                if (temp.GetID() == i )
                {
                    return temp; 
                }
            }
            return null;
        }

        public bool AddArticleIntoOrder(Articles temp)
        {
            if (clientDOB == temp.Alcoholic && clientDOB)    // check if client young(true), system doesn't allow get article
            {
                return false;
            }
            else
	        {
                FinalOrderPrice += temp.GetArticlePrice;
                clientOrder.Add(temp);
                return true;
	        }
        }

        public void check_And_Add_Article(object sender, EventArgs e)
        {
            Articles tempArticle = getArticle(((Button)sender).Tag.ToString());
            decimal currentBalance = getClientBalance() - GetFinalPrice;

            if (clientID == null )
            {
                lbInfo.Items.Add("Please scan your ID");
            }
            else if (currentBalance - tempArticle.GetArticlePrice >= 0)       //for testing without rfid reader remove if statement
            {
                if (AddArticleIntoOrder(tempArticle))
                {
                    lbPrice.Text = GetFinalPrice.ToString();
                    lbBalance.Text = getClientBalance().ToString();
                    lbRemaining.Text = (getClientBalance() - GetFinalPrice).ToString();
                    lbInfo.Items.Clear();
                    foreach (Articles item in getClientOrder)
                    {
                        lbInfo.Items.Add(item.ToString());
                    }
                }
                else
                {
                    lbInfo.Items.Add("Sorry, you are young!");
                }
                
            }
            else
            {
                lbRemaining.ForeColor = System.Drawing.Color.Red;
                lbRemaining.Text = currentBalance.ToString();
                lbInfo.Items.Add("Not Enought money");
            }
        }

        public bool implementOrder()
        {
            if (clientOrder.Count != 0 && DBConnection.Start_operation(clientOrder, clientID, FinalOrderPrice))
            {
                lbInfo.Items.Add(DBConnection.getProcessLog);
                return true;
            }
            else if (clientOrder.Count == 0)
            {
                lbInfo.Items.Add("Client Order is empty!");
                return false;
            }
            else
            {
                return false;
            }
        }
    }
}
