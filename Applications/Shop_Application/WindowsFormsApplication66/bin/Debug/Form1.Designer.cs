namespace WindowsFormsApplication66
{
    partial class Form1
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
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            this.lbInfo = new System.Windows.Forms.ListBox();
            this.btRemoveOne = new System.Windows.Forms.Button();
            this.btConfirmOK = new System.Windows.Forms.Button();
            this.btRemoveAll = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.btDrinks = new System.Windows.Forms.Button();
            this.btSnakcs = new System.Windows.Forms.Button();
            this.btStuff = new System.Windows.Forms.Button();
            this.gbSubMenu = new System.Windows.Forms.GroupBox();
            this.btSubNext = new System.Windows.Forms.Button();
            this.btSubBack = new System.Windows.Forms.Button();
            this.lbPrice = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.lbBalance = new System.Windows.Forms.Label();
            this.lbRemaining = new System.Windows.Forms.Label();
            this.gbReader = new System.Windows.Forms.GroupBox();
            this.btStartScanning = new System.Windows.Forms.Button();
            this.lbIdReader = new System.Windows.Forms.ListBox();
            this.lbID = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.btAbort = new System.Windows.Forms.Button();
            this.btContinue = new System.Windows.Forms.Button();
            this.SubMenuImages = new System.Windows.Forms.ImageList(this.components);
            this.groupBox4 = new System.Windows.Forms.GroupBox();
            this.pbMinus = new System.Windows.Forms.PictureBox();
            this.pbNo = new System.Windows.Forms.PictureBox();
            this.pbYes = new System.Windows.Forms.PictureBox();
            this.groupBox1.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.gbReader.SuspendLayout();
            this.groupBox4.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pbMinus)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pbNo)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pbYes)).BeginInit();
            this.SuspendLayout();
            // 
            // lbInfo
            // 
            this.lbInfo.FormattingEnabled = true;
            this.lbInfo.ItemHeight = 16;
            this.lbInfo.Location = new System.Drawing.Point(12, 12);
            this.lbInfo.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.lbInfo.Name = "lbInfo";
            this.lbInfo.Size = new System.Drawing.Size(477, 180);
            this.lbInfo.TabIndex = 0;
            // 
            // btRemoveOne
            // 
            this.btRemoveOne.Location = new System.Drawing.Point(21, 78);
            this.btRemoveOne.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.btRemoveOne.Name = "btRemoveOne";
            this.btRemoveOne.Size = new System.Drawing.Size(135, 50);
            this.btRemoveOne.TabIndex = 3;
            this.btRemoveOne.Text = "REMOVE ONE";
            this.btRemoveOne.UseVisualStyleBackColor = true;
            this.btRemoveOne.Click += new System.EventHandler(this.btRemoveOne_Click);
            // 
            // btConfirmOK
            // 
            this.btConfirmOK.Location = new System.Drawing.Point(21, 21);
            this.btConfirmOK.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.btConfirmOK.Name = "btConfirmOK";
            this.btConfirmOK.Size = new System.Drawing.Size(135, 50);
            this.btConfirmOK.TabIndex = 4;
            this.btConfirmOK.Text = "OK";
            this.btConfirmOK.UseVisualStyleBackColor = true;
            this.btConfirmOK.Click += new System.EventHandler(this.btConfirmOK_Click);
            // 
            // btRemoveAll
            // 
            this.btRemoveAll.Location = new System.Drawing.Point(21, 142);
            this.btRemoveAll.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.btRemoveAll.Name = "btRemoveAll";
            this.btRemoveAll.Size = new System.Drawing.Size(135, 50);
            this.btRemoveAll.TabIndex = 5;
            this.btRemoveAll.Text = "REMOVE ALL";
            this.btRemoveAll.UseVisualStyleBackColor = true;
            this.btRemoveAll.Click += new System.EventHandler(this.btRemoveAll_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 16.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(505, 38);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(159, 32);
            this.label1.TabIndex = 6;
            this.label1.Text = "Total Price:";
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.btConfirmOK);
            this.groupBox1.Controls.Add(this.btRemoveAll);
            this.groupBox1.Controls.Add(this.btRemoveOne);
            this.groupBox1.Location = new System.Drawing.Point(769, 12);
            this.groupBox1.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Padding = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.groupBox1.Size = new System.Drawing.Size(176, 222);
            this.groupBox1.TabIndex = 7;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Confirm";
            // 
            // groupBox2
            // 
            this.groupBox2.BackColor = System.Drawing.SystemColors.Control;
            this.groupBox2.Controls.Add(this.btDrinks);
            this.groupBox2.Controls.Add(this.btSnakcs);
            this.groupBox2.Controls.Add(this.btStuff);
            this.groupBox2.Location = new System.Drawing.Point(33, 230);
            this.groupBox2.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Padding = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.groupBox2.Size = new System.Drawing.Size(168, 205);
            this.groupBox2.TabIndex = 8;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "MAIN MENU";
            // 
            // btDrinks
            // 
            this.btDrinks.Location = new System.Drawing.Point(15, 25);
            this.btDrinks.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.btDrinks.Name = "btDrinks";
            this.btDrinks.Size = new System.Drawing.Size(135, 50);
            this.btDrinks.TabIndex = 4;
            this.btDrinks.Text = "DRINKS (1)";
            this.btDrinks.UseVisualStyleBackColor = true;
            this.btDrinks.Click += new System.EventHandler(this.btDrinks_Click);
            // 
            // btSnakcs
            // 
            this.btSnakcs.Location = new System.Drawing.Point(15, 85);
            this.btSnakcs.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.btSnakcs.Name = "btSnakcs";
            this.btSnakcs.Size = new System.Drawing.Size(135, 50);
            this.btSnakcs.TabIndex = 5;
            this.btSnakcs.Text = "SNACKS (2)";
            this.btSnakcs.UseVisualStyleBackColor = true;
            this.btSnakcs.Click += new System.EventHandler(this.btSnakcs_Click);
            // 
            // btStuff
            // 
            this.btStuff.Location = new System.Drawing.Point(15, 145);
            this.btStuff.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.btStuff.Name = "btStuff";
            this.btStuff.Size = new System.Drawing.Size(135, 50);
            this.btStuff.TabIndex = 3;
            this.btStuff.Text = "STUFF (3)";
            this.btStuff.UseVisualStyleBackColor = true;
            this.btStuff.Click += new System.EventHandler(this.btStuff_Click);
            // 
            // gbSubMenu
            // 
            this.gbSubMenu.BackColor = System.Drawing.SystemColors.Control;
            this.gbSubMenu.Location = new System.Drawing.Point(207, 230);
            this.gbSubMenu.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.gbSubMenu.Name = "gbSubMenu";
            this.gbSubMenu.Padding = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.gbSubMenu.Size = new System.Drawing.Size(457, 226);
            this.gbSubMenu.TabIndex = 9;
            this.gbSubMenu.TabStop = false;
            this.gbSubMenu.Text = "SUB MENU";
            // 
            // btSubNext
            // 
            this.btSubNext.Location = new System.Drawing.Point(677, 315);
            this.btSubNext.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.btSubNext.Name = "btSubNext";
            this.btSubNext.Size = new System.Drawing.Size(80, 50);
            this.btSubNext.TabIndex = 8;
            this.btSubNext.Text = ">>";
            this.btSubNext.UseVisualStyleBackColor = true;
            this.btSubNext.Click += new System.EventHandler(this.btSubNext_Click);
            // 
            // btSubBack
            // 
            this.btSubBack.Location = new System.Drawing.Point(677, 375);
            this.btSubBack.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.btSubBack.Name = "btSubBack";
            this.btSubBack.Size = new System.Drawing.Size(80, 50);
            this.btSubBack.TabIndex = 10;
            this.btSubBack.Text = "<<";
            this.btSubBack.UseVisualStyleBackColor = true;
            this.btSubBack.Click += new System.EventHandler(this.btSubBack_Click);
            // 
            // lbPrice
            // 
            this.lbPrice.AutoSize = true;
            this.lbPrice.Font = new System.Drawing.Font("Microsoft Sans Serif", 16.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbPrice.Location = new System.Drawing.Point(670, 38);
            this.lbPrice.Name = "lbPrice";
            this.lbPrice.Size = new System.Drawing.Size(31, 32);
            this.lbPrice.TabIndex = 13;
            this.lbPrice.Text = "0";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 16.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(505, 140);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(160, 32);
            this.label3.TabIndex = 15;
            this.label3.Text = "Remaining:";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 16.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(505, 90);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(127, 32);
            this.label2.TabIndex = 16;
            this.label2.Text = "Balance:";
            // 
            // lbBalance
            // 
            this.lbBalance.AutoSize = true;
            this.lbBalance.Font = new System.Drawing.Font("Microsoft Sans Serif", 16.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbBalance.Location = new System.Drawing.Point(634, 90);
            this.lbBalance.Name = "lbBalance";
            this.lbBalance.Size = new System.Drawing.Size(31, 32);
            this.lbBalance.TabIndex = 17;
            this.lbBalance.Text = "0";
            // 
            // lbRemaining
            // 
            this.lbRemaining.AutoSize = true;
            this.lbRemaining.Font = new System.Drawing.Font("Microsoft Sans Serif", 16.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbRemaining.Location = new System.Drawing.Point(671, 140);
            this.lbRemaining.Name = "lbRemaining";
            this.lbRemaining.Size = new System.Drawing.Size(31, 32);
            this.lbRemaining.TabIndex = 18;
            this.lbRemaining.Text = "0";
            // 
            // gbReader
            // 
            this.gbReader.Controls.Add(this.btStartScanning);
            this.gbReader.Controls.Add(this.lbIdReader);
            this.gbReader.Controls.Add(this.lbID);
            this.gbReader.Controls.Add(this.label4);
            this.gbReader.Controls.Add(this.btAbort);
            this.gbReader.Controls.Add(this.btContinue);
            this.gbReader.Location = new System.Drawing.Point(976, 12);
            this.gbReader.Name = "gbReader";
            this.gbReader.Size = new System.Drawing.Size(416, 452);
            this.gbReader.TabIndex = 20;
            this.gbReader.TabStop = false;
            this.gbReader.Text = "RFID Reader";
            // 
            // btStartScanning
            // 
            this.btStartScanning.Location = new System.Drawing.Point(138, 100);
            this.btStartScanning.Name = "btStartScanning";
            this.btStartScanning.Size = new System.Drawing.Size(135, 60);
            this.btStartScanning.TabIndex = 13;
            this.btStartScanning.Text = "Start Scanning";
            this.btStartScanning.UseVisualStyleBackColor = true;
            this.btStartScanning.Click += new System.EventHandler(this.btStartScanning_Click);
            // 
            // lbIdReader
            // 
            this.lbIdReader.FormattingEnabled = true;
            this.lbIdReader.ItemHeight = 16;
            this.lbIdReader.Location = new System.Drawing.Point(14, 266);
            this.lbIdReader.Name = "lbIdReader";
            this.lbIdReader.Size = new System.Drawing.Size(381, 180);
            this.lbIdReader.TabIndex = 12;
            // 
            // lbID
            // 
            this.lbID.AutoSize = true;
            this.lbID.Font = new System.Drawing.Font("Microsoft Sans Serif", 16.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lbID.ForeColor = System.Drawing.Color.Blue;
            this.lbID.Location = new System.Drawing.Point(238, 39);
            this.lbID.Name = "lbID";
            this.lbID.Size = new System.Drawing.Size(42, 32);
            this.lbID.TabIndex = 11;
            this.lbID.Text = "ID";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 16.2F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(8, 39);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(228, 32);
            this.label4.TabIndex = 10;
            this.label4.Text = "Client ID code is:";
            // 
            // btAbort
            // 
            this.btAbort.Location = new System.Drawing.Point(14, 187);
            this.btAbort.Name = "btAbort";
            this.btAbort.Size = new System.Drawing.Size(135, 60);
            this.btAbort.TabIndex = 9;
            this.btAbort.Text = "Abort";
            this.btAbort.UseVisualStyleBackColor = true;
            this.btAbort.Click += new System.EventHandler(this.btAbort_Click);
            // 
            // btContinue
            // 
            this.btContinue.Location = new System.Drawing.Point(269, 187);
            this.btContinue.Name = "btContinue";
            this.btContinue.Size = new System.Drawing.Size(135, 60);
            this.btContinue.TabIndex = 8;
            this.btContinue.Text = "Continue";
            this.btContinue.UseVisualStyleBackColor = true;
            this.btContinue.Click += new System.EventHandler(this.btContinue_Click);
            // 
            // SubMenuImages
            // 
            this.SubMenuImages.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("SubMenuImages.ImageStream")));
            this.SubMenuImages.TransparentColor = System.Drawing.SystemColors.Control;
            this.SubMenuImages.Images.SetKeyName(0, "id0.png");
            this.SubMenuImages.Images.SetKeyName(1, "id1.png");
            this.SubMenuImages.Images.SetKeyName(2, "id2.png");
            this.SubMenuImages.Images.SetKeyName(3, "id3.png");
            this.SubMenuImages.Images.SetKeyName(4, "id4.png");
            this.SubMenuImages.Images.SetKeyName(5, "id5.png");
            this.SubMenuImages.Images.SetKeyName(6, "id6.png");
            this.SubMenuImages.Images.SetKeyName(7, "id7.png");
            this.SubMenuImages.Images.SetKeyName(8, "id8.png");
            this.SubMenuImages.Images.SetKeyName(9, "Thee(1,1).png");
            this.SubMenuImages.Images.SetKeyName(10, "VeseMint(1,2).png");
            // 
            // groupBox4
            // 
            this.groupBox4.Controls.Add(this.pbMinus);
            this.groupBox4.Controls.Add(this.pbNo);
            this.groupBox4.Controls.Add(this.pbYes);
            this.groupBox4.Location = new System.Drawing.Point(802, 251);
            this.groupBox4.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.groupBox4.Name = "groupBox4";
            this.groupBox4.Padding = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.groupBox4.Size = new System.Drawing.Size(113, 207);
            this.groupBox4.TabIndex = 11;
            this.groupBox4.TabStop = false;
            this.groupBox4.Text = "TRANSFER:";
            // 
            // pbMinus
            // 
            this.pbMinus.Image = global::WindowsFormsApplication66.Properties.Resources.minus;
            this.pbMinus.Location = new System.Drawing.Point(18, 100);
            this.pbMinus.Name = "pbMinus";
            this.pbMinus.Size = new System.Drawing.Size(80, 25);
            this.pbMinus.TabIndex = 21;
            this.pbMinus.TabStop = false;
            // 
            // pbNo
            // 
            this.pbNo.Image = global::WindowsFormsApplication66.Properties.Resources.no_icon_hi;
            this.pbNo.Location = new System.Drawing.Point(21, 124);
            this.pbNo.Name = "pbNo";
            this.pbNo.Size = new System.Drawing.Size(75, 75);
            this.pbNo.SizeMode = System.Windows.Forms.PictureBoxSizeMode.AutoSize;
            this.pbNo.TabIndex = 22;
            this.pbNo.TabStop = false;
            // 
            // pbYes
            // 
            this.pbYes.Image = global::WindowsFormsApplication66.Properties.Resources.ok_icon_hi;
            this.pbYes.Location = new System.Drawing.Point(21, 27);
            this.pbYes.Name = "pbYes";
            this.pbYes.Size = new System.Drawing.Size(75, 75);
            this.pbYes.SizeMode = System.Windows.Forms.PictureBoxSizeMode.AutoSize;
            this.pbYes.TabIndex = 21;
            this.pbYes.TabStop = false;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1411, 476);
            this.Controls.Add(this.gbReader);
            this.Controls.Add(this.lbRemaining);
            this.Controls.Add(this.lbBalance);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.lbPrice);
            this.Controls.Add(this.groupBox4);
            this.Controls.Add(this.btSubBack);
            this.Controls.Add(this.btSubNext);
            this.Controls.Add(this.gbSubMenu);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.lbInfo);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.Name = "Form1";
            this.Text = "OddFruit Shop Application";
            this.groupBox1.ResumeLayout(false);
            this.groupBox2.ResumeLayout(false);
            this.gbReader.ResumeLayout(false);
            this.gbReader.PerformLayout();
            this.groupBox4.ResumeLayout(false);
            this.groupBox4.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.pbMinus)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pbNo)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pbYes)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ListBox lbInfo;
        private System.Windows.Forms.Button btRemoveOne;
        private System.Windows.Forms.Button btConfirmOK;
        private System.Windows.Forms.Button btRemoveAll;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.Button btDrinks;
        private System.Windows.Forms.Button btSnakcs;
        private System.Windows.Forms.Button btStuff;
        private System.Windows.Forms.GroupBox gbSubMenu;
        private System.Windows.Forms.Button btSubNext;
        private System.Windows.Forms.Button btSubBack;
        private System.Windows.Forms.Label lbPrice;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label lbBalance;
        private System.Windows.Forms.Label lbRemaining;
        private System.Windows.Forms.GroupBox gbReader;
        private System.Windows.Forms.Button btStartScanning;
        private System.Windows.Forms.ListBox lbIdReader;
        private System.Windows.Forms.Label lbID;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Button btAbort;
        private System.Windows.Forms.Button btContinue;
        internal System.Windows.Forms.ImageList SubMenuImages;
        private System.Windows.Forms.GroupBox groupBox4;
        private System.Windows.Forms.PictureBox pbNo;
        private System.Windows.Forms.PictureBox pbYes;
        private System.Windows.Forms.PictureBox pbMinus;
    }
}

