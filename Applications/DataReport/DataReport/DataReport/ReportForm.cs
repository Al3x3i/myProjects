using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.OleDb;
using System.Data.SqlServerCe;
using System.IO;
using DevExpress.XtraPrinting;
using DevExpress.XtraGrid;
using DevExpress.XtraGrid.Views.Grid;
using DevExpress.XtraGrid.Views.Grid.ViewInfo;
using System.Collections;
using System.Diagnostics;



namespace DataReport
{
    public partial class ReportForm : Form
    {
        string dbCopyFilePath;
        List<Tuple<string, decimal, decimal>> minMaxProperties;
        string[] reportHeaderData; // IMO and ShipNumber

        Color minColor;
        Color maxColor;

        public ReportForm()
        {
            InitializeComponent();

            timePickerTill.Value = DateTime.Now;
            timePickerFrom.Value = DateTime.Now;

            minColor = System.Drawing.ColorTranslator.FromHtml("#B0D1FF");
            maxColor = System.Drawing.ColorTranslator.FromHtml("#F17373");

            if (!DisplayDataRecord("shipinfo.csv"))
            {
                timePickerTill.Enabled = false;
                timePickerFrom.Enabled = false;
                btSelectDB.Enabled = false;
            }

            ReadMinMaxTableProperties("minmax.csv"); // read columns maximum and minimum values
            if (minMaxProperties.Count >0)
            {
                gridViewReport.RowCellStyle += gridViewReport_RowCellStyle;  // highlight cells if their value range is more or less. See minMax values
            }
        }

        /// <summary>
        /// Style cell by minMax properties = item1 = column name, item2 = min value, item2 = max value
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        void gridViewReport_RowCellStyle(object sender, RowCellStyleEventArgs e)
        {
            foreach (Tuple<string, decimal, decimal> property in minMaxProperties)
            {
                if (e.Column.FieldName == property.Item1)
                {
                    GridView View = sender as GridView;
                    string temp = View.GetRowCellDisplayText(e.RowHandle, View.Columns[property.Item1]);
                    decimal cellValue;
                    if (Decimal.TryParse(temp, out cellValue))
                    {
                        if (cellValue < property.Item2)
                        {
                            e.Appearance.BackColor = minColor;
                        }
                        else if (cellValue > property.Item3)
                        {
                            e.Appearance.BackColor = maxColor;
                        }
                    }
                }
            }
        }

        /// <summary>
        /// Displays Dialog Window for selecting database.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btSelectDB_Click(object sender, EventArgs e)
        {
            try
            {
                DialogResult result = openDBFileDialog.ShowDialog();
                if (result == DialogResult.OK)
                {
                    string tempPath = openDBFileDialog.FileName;
                    if (tempPath != null)
                    {
                        dbCopyFilePath = System.IO.Path.GetTempPath() + "DataReportDB";
                        File.Copy(tempPath, dbCopyFilePath, true);
                        btShow.Enabled = true;
                        btPDF.Enabled = false;
                        openDBFileDialog.FileName = "";
                    }
                }
            }
            catch (Exception)
            {
                MessageBox.Show("Unexpected error occurred while saving file", "Error Message");
            }
        }

        /// <summary>
        /// Displays a report from selected database. 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btShow_Click(object sender, EventArgs e)
        {

            int date = timePickerTill.Value.Date.Subtract(timePickerFrom.Value.Date).Days;
            if (date >= 15)
            {
                MessageBox.Show("The date range between From and Till cannot be more than 15 days", "Warning", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return;
            }
            else if (date < 0)
            {
                MessageBox.Show("Wrong date range", "Warning", MessageBoxButtons.OK, MessageBoxIcon.Warning);
                return;
            }

            string connectionString = string.Format("Data Source={0};Max Database Size=256;Persist Security Info=False;", dbCopyFilePath);

            try
            {
                using (SqlCeConnection connection = new SqlCeConnection(connectionString))
                {
                    if (OpenConnection(connection))
                    {
                        string command = @"SELECT * 
                                            FROM 
                                                DataLogger_MECP 
                                            WHERE 
                                                Time 
                                            BETWEEN 
                                                @from 
                                            AND 
                                                @till";
                        using (SqlCeDataAdapter adapter = new SqlCeDataAdapter(command, connection))
                        {
                            adapter.SelectCommand.Parameters.Add(new SqlCeParameter("@from", timePickerFrom.Value.Date.ToShortDateString()));
                            adapter.SelectCommand.Parameters.Add(new SqlCeParameter("@till", timePickerTill.Value.Date.AddHours(24).ToShortDateString()));
                            using (DataSet dataSet = new DataSet())
                            {
                                adapter.Fill(dataSet);
                                GridDB.DataSource = dataSet.Tables[0];

                                // Set full format for time
                                gridViewReport.Columns["Time"].DisplayFormat.FormatString = "MMM/d/yyyy hh:mm tt";
                                pbVDELogo.Visible = false;
                                btPDF.Enabled = true;
                                gridViewReport.BestFitColumns();
                            }
                        }
                    }
                    else
                    {
                        MessageBox.Show("Unable to open the database connection.");
                    }
                }
            }
            // This error will be invoked if minMax value contains wrong data(See gridViewReport.RowCellStyle event handler)
            catch(FormatException)
            {
                MessageBox.Show("Unexpected error occurred while displaying cell properties", "Error Message");
            }
            catch (Exception)
            {
                MessageBox.Show("Unexpected error occurred while data reading", "Error Message");
            }
        }

        /// <summary>
        /// Opens cpnnection
        /// </summary>
        /// <param name="con">Connection String</param>
        /// <returns>Returns true if connection is opened or return false</returns>
        private bool OpenConnection(SqlCeConnection con)
        {
            try
            {
                con.Open();
                return true;
            }
            catch (Exception ex)
            {
                //Check on "Database old version" error. Exception number "2147467259"
                if (ex.HResult == -2147467259)
                {
                    SqlCeEngine engine = new SqlCeEngine(con.ConnectionString);
                    engine.Upgrade();
                    engine.Dispose();
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }

        /// <summary>
        /// Save report into PDF extension
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void btPDF_Click(object sender, EventArgs e)
        {
            try
            {
                using (XtraReport report = new XtraReport())
                {
                    DataTable table = ((DataView)(gridViewReport.DataSource)).Table;

                    if (report.SetReportData(table, reportHeaderData[0], reportHeaderData[1],minMaxProperties,minColor,maxColor))
                    {
                        DialogResult result = savePDFFileDialog.ShowDialog();
                        if (result == DialogResult.OK)
                        {
                            report.ExportToPdf(savePDFFileDialog.FileName);
                            Process.Start(savePDFFileDialog.FileName);
                            savePDFFileDialog.FileName = "";

                        }
                    }
                    else
                    {
                        MessageBox.Show("Unexpected error occurred while PDF saving", "Error Message");
                    }

                }
            }
            catch (Exception)
            {
                MessageBox.Show("Unexpected error occurred while PDF saving", "Error Message");
            }

        }
        /// <summary>
        /// Read cell properties from minMax document and store into Tuple list. Item1 = Column Name; Item2 = min Value, Item2 = Max Value
        /// </summary>
        /// <param name="filePath">File Path</param>
        /// <returns></returns>
        private void ReadMinMaxTableProperties(string filePath)
        {
            try
            {
                minMaxProperties = new List<Tuple<string,decimal,decimal>>();
                using(StreamReader reader = new StreamReader(filePath))
                {
                    string line;
                    while ((line = reader.ReadLine())!=null)
                    {
                        string[] temp = line.Split(';');
                        minMaxProperties.Add(new Tuple<string, decimal, decimal>(temp[0], Convert.ToDecimal(temp[1]), Convert.ToDecimal(temp[2])));
                    }
                }
            }
            catch (Exception)
            {
                MessageBox.Show("Unexpected error occurred while reading min, max properties", "Error Message");
            }

        }


        /// <summary>
        /// Retrieves data from file and displays records in labels
        /// </summary>
        /// <param name="filePath">File path</param>
        /// <returns>Returns true if data rerived successfully and labels updated</returns>
        private bool DisplayDataRecord(string filePath)
        {
            try
            {
                using (StreamReader stream = new StreamReader(filePath))
                {
                    string line;
                    if ((line = stream.ReadLine()) != null)
                    {
                        reportHeaderData = line.Split(';');
                        if (reportHeaderData.Length == 2)
                        {
                            lbImo.Text = "IMO: " + reportHeaderData[0];
                            lbShipName.Text = "Ship Name: " + reportHeaderData[1];
                            return true;
                        }
                        else
                        {
                            MessageBox.Show("Unexpected error occurred while reading \"IMO\" and \"Ship Name\"", "Error Message");
                            return false;
                        }
                    }
                    MessageBox.Show("Unexpected error occurred while reading \"IMO\" and \"Ship Name\"", "Error Message");
                    return false;
                }
            }
            catch
            {
                MessageBox.Show("The \"IMO\" and \"Ship Name\" data is corrupted", "Error Message");
                return false;
            }
        }

        /// <summary>
        /// After closing application temporary database file will be deleted
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ReportForm_FormClosed(object sender, FormClosedEventArgs e)
        {
            try
            {
                if (dbCopyFilePath != null && File.Exists(dbCopyFilePath))
                    File.Delete(dbCopyFilePath);
            }
            catch (Exception)
            { }
        }
    }
}

