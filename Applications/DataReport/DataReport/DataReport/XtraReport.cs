using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using DevExpress.XtraReports.UI;
using System.Data;
using System.Collections.Generic;

namespace DataReport
{
    public partial class XtraReport : DevExpress.XtraReports.UI.XtraReport
    {
        List<Tuple<string, decimal, decimal>> minMaxProperties;
        Color minColor;
        Color maxColor;

        public XtraReport()
        {
            InitializeComponent();
        }

        /// <summary>
        /// Create report
        /// </summary>
        /// <param name="myTable">Displayed information</param>
        /// <returns>Return true if report created properly</returns>
        public bool SetReportData(DataTable myTable, string imo, string shipName, List<Tuple<string, decimal, decimal>> minMaxProperties,Color minColor, Color maxColor)
        {
            try
            {
                this.DataSource = myTable;
                this.DataMember = myTable.TableName;

                this.xrLabelImo.Text ="IMO: " + imo;
                this.xrLabelShipName.Text ="Ship Name: " +  shipName;

                this.minMaxProperties = minMaxProperties;

                this.minColor = minColor;
                this.maxColor = maxColor;


                //go through all cell control`s and set databinding by cell.text (Note: only column "Time" has different cell.Name != cell.Text )
                foreach (var item in xrTableData.Controls)
                {
                    DevExpress.XtraReports.UI.XRTableRow row = item as DevExpress.XtraReports.UI.XRTableRow;
                    if (row != null)
                    {
                        foreach (var cells in row.Controls)
                        {
                            DevExpress.XtraReports.UI.XRTableCell cell = cells as DevExpress.XtraReports.UI.XRTableCell;
                            if (cell != null)
                            {
                                string columnName = cell.Text;
                                cell.DataBindings.Add("Text", myTable, columnName);
                            }
                        }
                    }
                }
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        /// <summary>
        /// Before print highlight min max cells
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void xrTableRow1_BeforePrint(object sender, System.Drawing.Printing.PrintEventArgs e)
        {
            DevExpress.XtraReports.UI.XRTableRow row = sender as DevExpress.XtraReports.UI.XRTableRow;
            if (row != null)
            {
                foreach (var cells in row.Controls)
                {
                    DevExpress.XtraReports.UI.XRTableCell cell = cells as DevExpress.XtraReports.UI.XRTableCell;
                    if (cell != null)
                    {
                        foreach (Tuple<string, decimal, decimal> property in minMaxProperties)
                        {
                            // Cell name match to the Min Max column property from minmax.csv
                            if (cell.Name == property.Item1)
                                {

                                string temp = cell.Text;
                                decimal cellValue;
                                
                                //Set min and max range colors and for default Color.White
                                if (Decimal.TryParse(temp, out cellValue))
                                {
                                    if (cellValue < property.Item2)
                                    {
                                        cell.BackColor = minColor;
                                    }
                                    else if (cellValue > property.Item3)
                                    {
                                        cell.BackColor = maxColor;
                                    }
                                    else
                                    {
                                        cell.BackColor = Color.White;
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
