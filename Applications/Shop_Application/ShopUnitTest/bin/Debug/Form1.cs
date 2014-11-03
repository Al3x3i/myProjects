using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

using Phidgets;
using Phidgets.Events;

namespace WindowsFormsApplication66
{
    public partial class Form1 : Form
    {
        Client client;
        RFIDreader reader;
        SubmenuButtons submenuButton;

        public Form1()
        {
            InitializeComponent();
            try
            {
                client = new Client(new DatabaseHandler.PaymentProcessHandler(HandleDatabaseproblem),lbPrice, lbBalance,lbRemaining,lbInfo, lbIdReader);
                reader = new RFIDreader(new AttachEventHandler(ShowWhoIsAttached), new DetachEventHandler(ShowWhoIsDetached), new TagEventHandler(ProcessThisTag));
                submenuButton = new SubmenuButtons(gbSubMenu, new EventHandler(client.check_And_Add_Article), client.getListOfArticles);
                btContinue.Enabled = false;
                btAbort.Enabled = false;
                pbNo.Visible = false;
                pbYes.Visible = false;
                pbMinus.Visible = true;
            }

            catch (Exception)
            {
                lbIdReader.Items.Add("error at start-up.");
            }
        }

        private void btConfirmOK_Click(object sender, EventArgs e)
        {
            if (client.implementOrder())
            {
                client.ClientID = null;
                client.deleteClientOrder();
                pbYes.Visible = true;
            }
            else
            {
                pbYes.Visible = false;
                pbNo.Visible = true;
            }
        }

        private void btStartScanning_Click(object sender, EventArgs e)
        {
            lbInfo.Items.Clear();
            lbIdReader.Items.Clear();
            string report = reader.ReadCode();
            lbIdReader.Items.Add(report);

            btContinue.Enabled = true;
            btAbort.Enabled = true;
            pbYes.Visible = false;
            pbNo.Visible = false;
        }

        private void btContinue_Click(object sender, EventArgs e)
        {
            if (client.ClientID !="0")
            {
                client.getClientInfo();
                lbBalance.Text = client.getClientBalance().ToString();
                lbRemaining.Text = (client.getClientBalance() - client.GetFinalPrice).ToString();
                btContinue.Enabled = !btContinue.Enabled;
                reader.RFIDturnOFF();
            }
            else
            {
            client.ClientID = "0";
            lbIdReader.Items.Add("Please scan your ID");
            }
            
        }

        private void btAbort_Click(object sender, EventArgs e)
        {
            reader.RFIDturnOFF();

            client.deleteClientOrder();
            client.ClientID = "0";
            lbID.Text = "";
            lbPrice.Text = "0";
            lbBalance.Text = "0";
            lbRemaining.Text = "0";

            btAbort.Enabled = !btAbort.Enabled;
            btContinue.Enabled = false;
        }

        private void btDrinks_Click(object sender, EventArgs e)
        {
            submenuButton.ShowButtons("dranken");
            submenuButton.ChangeColorOfActiveButton(sender);
            
        }

        private void btSnakcs_Click(object sender, EventArgs e)
        {
            submenuButton.ShowButtons("snacks");
            submenuButton.ChangeColorOfActiveButton(sender);
        }

        private void btStuff_Click(object sender, EventArgs e)
        {
            submenuButton.ShowButtons("stuff");
            submenuButton.ChangeColorOfActiveButton(sender);
        }

        private void btSubBack_Click(object sender, EventArgs e)
        {
            submenuButton.ShowBackSubmenuButtons();
        }

        private void btSubNext_Click(object sender, EventArgs e)
        {
            submenuButton.ShowNextSubmenuButtons();
        }

        private void ShowWhoIsAttached(object sender, AttachEventArgs e)
        {
            lbIdReader.Items.Add("RFIDReader attached!, serial nr: " + e.Device.SerialNumber.ToString());
        }

        private void ShowWhoIsDetached(object sender, DetachEventArgs e)
        {
            lbIdReader.Items.Add("RFIDReader detached!, serial nr: " + e.Device.SerialNumber.ToString());
        }

        private void ProcessThisTag(object sender, TagEventArgs e)
        {
            lbIdReader.Items.Add("rfid has tag-nr: " + e.Tag);
            lbID.Text = e.Tag;
            client.ClientID = e.Tag;              //send chip code to database class
        }
        
        private void HandleDatabaseproblem(int caseSwitch)
        {
            switch (caseSwitch)
            {
                case 1: lbInfo.Items.Add("PAYMENT RECEIVED!"); break;
                case 2: lbIdReader.Items.Add("Cannot change client balance!"); break;
                case 3: MessageBox.Show("Client does not have enought money!", "CLINET ERROR"); break;
                case 4: lbIdReader.Items.Add("Connection error occured"); break;
                case 5: lbIdReader.Items.Add("Wrong ID code!"); break;
                default: MessageBox.Show("Error, some unnexpected error occured!!!", "Attenntion"); break;
            }
        }

        private void btRemoveOne_Click(object sender, EventArgs e)
        {
            string[] n = Convert.ToString(lbInfo.SelectedItem).Split();
            if (client.RemoverArticleFromOrder(n[0]))
            {
                lbInfo.Items.Clear();
                lbPrice.Text = client.GetFinalPrice.ToString();
                foreach (Articles item in client.getClientOrder)
                {
                    lbInfo.Items.Add(item.ToString());
                }
            }
        }

        private void btRemoveAll_Click(object sender, EventArgs e)
        {
            lbInfo.Items.Clear();
            client.deleteClientOrder();
            lbPrice.Text = "0";
            lbRemaining.Text = (client.getClientBalance() - client.GetFinalPrice).ToString();
            lbInfo.Items.Add("Order is empty");
        }
    }
}
