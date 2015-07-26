namespace DataReport
{
    partial class ReportForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(ReportForm));
            this.recentlyUsedItemsComboBox1 = new DevExpress.XtraReports.UserDesigner.RecentlyUsedItemsComboBox();
            this.designRepositoryItemComboBox1 = new DevExpress.XtraReports.UserDesigner.DesignRepositoryItemComboBox();
            this.GridDB = new DevExpress.XtraGrid.GridControl();
            this.gridViewReport = new DevExpress.XtraGrid.Views.Grid.GridView();
            this.btShow = new System.Windows.Forms.Button();
            this.gbControl = new System.Windows.Forms.GroupBox();
            this.lbShipName = new System.Windows.Forms.Label();
            this.lbImo = new System.Windows.Forms.Label();
            this.btSelectDB = new System.Windows.Forms.Button();
            this.btPDF = new System.Windows.Forms.Button();
            this.lbTill = new System.Windows.Forms.Label();
            this.lbFrom = new System.Windows.Forms.Label();
            this.timePickerTill = new System.Windows.Forms.DateTimePicker();
            this.timePickerFrom = new System.Windows.Forms.DateTimePicker();
            this.openDBFileDialog = new System.Windows.Forms.OpenFileDialog();
            this.savePDFFileDialog = new System.Windows.Forms.SaveFileDialog();
            this.gridSplitContainer1 = new DevExpress.XtraGrid.GridSplitContainer();
            this.pbVDELogo = new System.Windows.Forms.PictureBox();
            ((System.ComponentModel.ISupportInitialize)(this.recentlyUsedItemsComboBox1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.designRepositoryItemComboBox1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.GridDB)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.gridViewReport)).BeginInit();
            this.gbControl.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.gridSplitContainer1)).BeginInit();
            this.gridSplitContainer1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pbVDELogo)).BeginInit();
            this.SuspendLayout();
            // 
            // recentlyUsedItemsComboBox1
            // 
            this.recentlyUsedItemsComboBox1.AppearanceDropDown.Font = new System.Drawing.Font("Tahoma", 11.25F);
            this.recentlyUsedItemsComboBox1.AppearanceDropDown.Options.UseFont = true;
            this.recentlyUsedItemsComboBox1.AutoHeight = false;
            this.recentlyUsedItemsComboBox1.Buttons.AddRange(new DevExpress.XtraEditors.Controls.EditorButton[] {
            new DevExpress.XtraEditors.Controls.EditorButton(DevExpress.XtraEditors.Controls.ButtonPredefines.Combo)});
            this.recentlyUsedItemsComboBox1.DropDownRows = 12;
            this.recentlyUsedItemsComboBox1.Name = "recentlyUsedItemsComboBox1";
            // 
            // designRepositoryItemComboBox1
            // 
            this.designRepositoryItemComboBox1.AutoHeight = false;
            this.designRepositoryItemComboBox1.Buttons.AddRange(new DevExpress.XtraEditors.Controls.EditorButton[] {
            new DevExpress.XtraEditors.Controls.EditorButton(DevExpress.XtraEditors.Controls.ButtonPredefines.Combo)});
            this.designRepositoryItemComboBox1.Name = "designRepositoryItemComboBox1";
            // 
            // GridDB
            // 
            this.GridDB.Dock = System.Windows.Forms.DockStyle.Fill;
            this.GridDB.Location = new System.Drawing.Point(0, 0);
            this.GridDB.MainView = this.gridViewReport;
            this.GridDB.Name = "GridDB";
            this.GridDB.Size = new System.Drawing.Size(825, 456);
            this.GridDB.TabIndex = 0;
            this.GridDB.ViewCollection.AddRange(new DevExpress.XtraGrid.Views.Base.BaseView[] {
            this.gridViewReport});
            // 
            // gridViewReport
            // 
            this.gridViewReport.AppearancePrint.HeaderPanel.Font = new System.Drawing.Font("Times New Roman", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.gridViewReport.AppearancePrint.HeaderPanel.Options.UseFont = true;
            this.gridViewReport.GridControl = this.GridDB;
            this.gridViewReport.Name = "gridViewReport";
            this.gridViewReport.OptionsBehavior.AllowAddRows = DevExpress.Utils.DefaultBoolean.False;
            this.gridViewReport.OptionsBehavior.AllowDeleteRows = DevExpress.Utils.DefaultBoolean.False;
            this.gridViewReport.OptionsBehavior.AllowFixedGroups = DevExpress.Utils.DefaultBoolean.False;
            this.gridViewReport.OptionsBehavior.AllowPartialRedrawOnScrolling = false;
            this.gridViewReport.OptionsBehavior.Editable = false;
            this.gridViewReport.OptionsCustomization.AllowFilter = false;
            this.gridViewReport.OptionsMenu.EnableColumnMenu = false;
            this.gridViewReport.OptionsMenu.EnableFooterMenu = false;
            this.gridViewReport.OptionsMenu.EnableGroupPanelMenu = false;
            this.gridViewReport.OptionsPrint.AutoWidth = false;
            this.gridViewReport.OptionsView.ColumnAutoWidth = false;
            this.gridViewReport.OptionsView.ShowGroupPanel = false;
            // 
            // btShow
            // 
            this.btShow.Enabled = false;
            this.btShow.Location = new System.Drawing.Point(394, 19);
            this.btShow.Name = "btShow";
            this.btShow.Size = new System.Drawing.Size(75, 54);
            this.btShow.TabIndex = 1;
            this.btShow.Text = "Show";
            this.btShow.UseVisualStyleBackColor = true;
            this.btShow.Click += new System.EventHandler(this.btShow_Click);
            // 
            // gbControl
            // 
            this.gbControl.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.gbControl.Controls.Add(this.lbShipName);
            this.gbControl.Controls.Add(this.lbImo);
            this.gbControl.Controls.Add(this.btSelectDB);
            this.gbControl.Controls.Add(this.btPDF);
            this.gbControl.Controls.Add(this.lbTill);
            this.gbControl.Controls.Add(this.lbFrom);
            this.gbControl.Controls.Add(this.timePickerTill);
            this.gbControl.Controls.Add(this.timePickerFrom);
            this.gbControl.Controls.Add(this.btShow);
            this.gbControl.Location = new System.Drawing.Point(12, 13);
            this.gbControl.Name = "gbControl";
            this.gbControl.Size = new System.Drawing.Size(825, 86);
            this.gbControl.TabIndex = 2;
            this.gbControl.TabStop = false;
            this.gbControl.Text = "Control";
            // 
            // lbShipName
            // 
            this.lbShipName.AutoSize = true;
            this.lbShipName.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbShipName.Location = new System.Drawing.Point(616, 53);
            this.lbShipName.Name = "lbShipName";
            this.lbShipName.Size = new System.Drawing.Size(91, 20);
            this.lbShipName.TabIndex = 9;
            this.lbShipName.Text = "Ship Name:";
            // 
            // lbImo
            // 
            this.lbImo.AutoSize = true;
            this.lbImo.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbImo.Location = new System.Drawing.Point(617, 21);
            this.lbImo.Name = "lbImo";
            this.lbImo.Size = new System.Drawing.Size(43, 20);
            this.lbImo.TabIndex = 8;
            this.lbImo.Text = "IMO:";
            // 
            // btSelectDB
            // 
            this.btSelectDB.Location = new System.Drawing.Point(16, 21);
            this.btSelectDB.Name = "btSelectDB";
            this.btSelectDB.Size = new System.Drawing.Size(75, 52);
            this.btSelectDB.TabIndex = 7;
            this.btSelectDB.Text = "Select\r\nDatabase\r\n";
            this.btSelectDB.UseVisualStyleBackColor = true;
            this.btSelectDB.Click += new System.EventHandler(this.btSelectDB_Click);
            // 
            // btPDF
            // 
            this.btPDF.Enabled = false;
            this.btPDF.Location = new System.Drawing.Point(506, 19);
            this.btPDF.Name = "btPDF";
            this.btPDF.Size = new System.Drawing.Size(75, 53);
            this.btPDF.TabIndex = 6;
            this.btPDF.Text = "PDF";
            this.btPDF.UseVisualStyleBackColor = true;
            this.btPDF.Click += new System.EventHandler(this.btPDF_Click);
            // 
            // lbTill
            // 
            this.lbTill.AutoSize = true;
            this.lbTill.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbTill.Location = new System.Drawing.Point(111, 52);
            this.lbTill.Name = "lbTill";
            this.lbTill.Size = new System.Drawing.Size(27, 20);
            this.lbTill.TabIndex = 5;
            this.lbTill.Text = "Till";
            // 
            // lbFrom
            // 
            this.lbFrom.AutoSize = true;
            this.lbFrom.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbFrom.Location = new System.Drawing.Point(111, 18);
            this.lbFrom.Name = "lbFrom";
            this.lbFrom.Size = new System.Drawing.Size(46, 20);
            this.lbFrom.TabIndex = 4;
            this.lbFrom.Text = "From";
            // 
            // timePickerTill
            // 
            this.timePickerTill.Location = new System.Drawing.Point(163, 52);
            this.timePickerTill.Name = "timePickerTill";
            this.timePickerTill.Size = new System.Drawing.Size(200, 20);
            this.timePickerTill.TabIndex = 3;
            // 
            // timePickerFrom
            // 
            this.timePickerFrom.Location = new System.Drawing.Point(163, 19);
            this.timePickerFrom.Name = "timePickerFrom";
            this.timePickerFrom.Size = new System.Drawing.Size(199, 20);
            this.timePickerFrom.TabIndex = 2;
            // 
            // openDBFileDialog
            // 
            this.openDBFileDialog.Filter = "Database Files (*.sdf)|*.sdf";
            this.openDBFileDialog.InitialDirectory = "C:\\";
            // 
            // savePDFFileDialog
            // 
            this.savePDFFileDialog.Filter = "PDF Files(*.pdf)|*.pdf";
            this.savePDFFileDialog.InitialDirectory = "C:\\";
            // 
            // gridSplitContainer1
            // 
            this.gridSplitContainer1.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.gridSplitContainer1.Grid = this.GridDB;
            this.gridSplitContainer1.Location = new System.Drawing.Point(12, 105);
            this.gridSplitContainer1.Name = "gridSplitContainer1";
            this.gridSplitContainer1.Panel1.Controls.Add(this.pbVDELogo);
            this.gridSplitContainer1.Panel1.Controls.Add(this.GridDB);
            this.gridSplitContainer1.Size = new System.Drawing.Size(825, 456);
            this.gridSplitContainer1.TabIndex = 4;
            // 
            // pbVDELogo
            // 
            this.pbVDELogo.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.pbVDELogo.BackColor = System.Drawing.SystemColors.Window;
            this.pbVDELogo.Image = global::DataReport.Properties.Resources.Alfa_Logo;
            this.pbVDELogo.Location = new System.Drawing.Point(7, 24);
            this.pbVDELogo.Margin = new System.Windows.Forms.Padding(10);
            this.pbVDELogo.Name = "pbVDELogo";
            this.pbVDELogo.Size = new System.Drawing.Size(808, 425);
            this.pbVDELogo.SizeMode = System.Windows.Forms.PictureBoxSizeMode.CenterImage;
            this.pbVDELogo.TabIndex = 3;
            this.pbVDELogo.TabStop = false;
            // 
            // ReportForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(849, 573);
            this.Controls.Add(this.gbControl);
            this.Controls.Add(this.gridSplitContainer1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "ReportForm";
            this.Text = "DataReport";
            this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.ReportForm_FormClosed);
            ((System.ComponentModel.ISupportInitialize)(this.recentlyUsedItemsComboBox1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.designRepositoryItemComboBox1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.GridDB)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.gridViewReport)).EndInit();
            this.gbControl.ResumeLayout(false);
            this.gbControl.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.gridSplitContainer1)).EndInit();
            this.gridSplitContainer1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.pbVDELogo)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button btShow;
        private System.Windows.Forms.GroupBox gbControl;
        private System.Windows.Forms.DateTimePicker timePickerFrom;
        private System.Windows.Forms.Button btPDF;
        private System.Windows.Forms.Label lbTill;
        private System.Windows.Forms.Label lbFrom;
        private System.Windows.Forms.DateTimePicker timePickerTill;
        private System.Windows.Forms.OpenFileDialog openDBFileDialog;
        private System.Windows.Forms.Button btSelectDB;
        private System.Windows.Forms.SaveFileDialog savePDFFileDialog;
        private DevExpress.XtraGrid.GridControl GridDB;
        private DevExpress.XtraGrid.GridSplitContainer gridSplitContainer1;
        private DevExpress.XtraReports.UserDesigner.RecentlyUsedItemsComboBox recentlyUsedItemsComboBox1;
        private DevExpress.XtraReports.UserDesigner.DesignRepositoryItemComboBox designRepositoryItemComboBox1;
        private DevExpress.XtraGrid.Views.Grid.GridView gridViewReport;
        private System.Windows.Forms.PictureBox pbVDELogo;
        private System.Windows.Forms.Label lbShipName;
        private System.Windows.Forms.Label lbImo;
    }
}

