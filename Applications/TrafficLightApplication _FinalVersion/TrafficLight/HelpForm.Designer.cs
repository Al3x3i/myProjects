namespace TrafficLightApplication
{
    partial class HelpForm
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
            this.tabControl1 = new System.Windows.Forms.TabControl();
            this.tabIntersection = new System.Windows.Forms.TabPage();
            this.tabSimulation = new System.Windows.Forms.TabPage();
            this.tabFAQ = new System.Windows.Forms.TabPage();
            this.labIntersection = new System.Windows.Forms.Label();
            this.labSimulation = new System.Windows.Forms.Label();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.checkBox1 = new System.Windows.Forms.CheckBox();
            this.checkBox2 = new System.Windows.Forms.CheckBox();
            this.checkBox3 = new System.Windows.Forms.CheckBox();
            this.checkBox4 = new System.Windows.Forms.CheckBox();
            this.checkBox5 = new System.Windows.Forms.CheckBox();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.tabControl1.SuspendLayout();
            this.tabIntersection.SuspendLayout();
            this.tabSimulation.SuspendLayout();
            this.tabFAQ.SuspendLayout();
            this.groupBox1.SuspendLayout();
            this.SuspendLayout();
            // 
            // tabControl1
            // 
            this.tabControl1.Controls.Add(this.tabIntersection);
            this.tabControl1.Controls.Add(this.tabSimulation);
            this.tabControl1.Controls.Add(this.tabFAQ);
            this.tabControl1.Location = new System.Drawing.Point(12, 12);
            this.tabControl1.Name = "tabControl1";
            this.tabControl1.SelectedIndex = 0;
            this.tabControl1.Size = new System.Drawing.Size(570, 385);
            this.tabControl1.TabIndex = 1;
            // 
            // tabIntersection
            // 
            this.tabIntersection.BackColor = System.Drawing.Color.Silver;
            this.tabIntersection.Controls.Add(this.labIntersection);
            this.tabIntersection.Location = new System.Drawing.Point(4, 22);
            this.tabIntersection.Name = "tabIntersection";
            this.tabIntersection.Padding = new System.Windows.Forms.Padding(3);
            this.tabIntersection.Size = new System.Drawing.Size(379, 267);
            this.tabIntersection.TabIndex = 0;
            this.tabIntersection.Text = "Intersection";
            // 
            // tabSimulation
            // 
            this.tabSimulation.BackColor = System.Drawing.Color.Silver;
            this.tabSimulation.Controls.Add(this.labSimulation);
            this.tabSimulation.Location = new System.Drawing.Point(4, 22);
            this.tabSimulation.Name = "tabSimulation";
            this.tabSimulation.Padding = new System.Windows.Forms.Padding(3);
            this.tabSimulation.Size = new System.Drawing.Size(379, 359);
            this.tabSimulation.TabIndex = 1;
            this.tabSimulation.Text = "Simulation";
            // 
            // tabFAQ
            // 
            this.tabFAQ.BackColor = System.Drawing.Color.Silver;
            this.tabFAQ.Controls.Add(this.textBox1);
            this.tabFAQ.Controls.Add(this.groupBox1);
            this.tabFAQ.Location = new System.Drawing.Point(4, 22);
            this.tabFAQ.Name = "tabFAQ";
            this.tabFAQ.Size = new System.Drawing.Size(562, 359);
            this.tabFAQ.TabIndex = 2;
            this.tabFAQ.Text = "FAQ";
            // 
            // labIntersection
            // 
            this.labIntersection.AutoSize = true;
            this.labIntersection.Location = new System.Drawing.Point(6, 13);
            this.labIntersection.Name = "labIntersection";
            this.labIntersection.Size = new System.Drawing.Size(0, 13);
            this.labIntersection.TabIndex = 0;
            // 
            // labSimulation
            // 
            this.labSimulation.AutoSize = true;
            this.labSimulation.Location = new System.Drawing.Point(6, 3);
            this.labSimulation.Name = "labSimulation";
            this.labSimulation.Size = new System.Drawing.Size(0, 13);
            this.labSimulation.TabIndex = 0;
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.checkBox5);
            this.groupBox1.Controls.Add(this.checkBox4);
            this.groupBox1.Controls.Add(this.checkBox3);
            this.groupBox1.Controls.Add(this.checkBox2);
            this.groupBox1.Controls.Add(this.checkBox1);
            this.groupBox1.Location = new System.Drawing.Point(13, 13);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(482, 155);
            this.groupBox1.TabIndex = 0;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Looking for answers?";
            // 
            // checkBox1
            // 
            this.checkBox1.AutoSize = true;
            this.checkBox1.Location = new System.Drawing.Point(6, 19);
            this.checkBox1.Name = "checkBox1";
            this.checkBox1.Size = new System.Drawing.Size(194, 17);
            this.checkBox1.TabIndex = 2;
            this.checkBox1.Text = "How long does a simulation run for?";
            this.checkBox1.UseVisualStyleBackColor = true;
            this.checkBox1.CheckedChanged += new System.EventHandler(this.checkBox1_CheckedChanged);
            // 
            // checkBox2
            // 
            this.checkBox2.AutoSize = true;
            this.checkBox2.Location = new System.Drawing.Point(6, 42);
            this.checkBox2.Name = "checkBox2";
            this.checkBox2.Size = new System.Drawing.Size(242, 17);
            this.checkBox2.TabIndex = 4;
            this.checkBox2.Text = "Can I place a crossing over another crossing?";
            this.checkBox2.UseVisualStyleBackColor = true;
            this.checkBox2.CheckedChanged += new System.EventHandler(this.checkBox2_CheckedChanged);
            // 
            // checkBox3
            // 
            this.checkBox3.AutoSize = true;
            this.checkBox3.Location = new System.Drawing.Point(6, 65);
            this.checkBox3.Name = "checkBox3";
            this.checkBox3.Size = new System.Drawing.Size(233, 17);
            this.checkBox3.TabIndex = 5;
            this.checkBox3.Text = "How do I load a saved grid from my system?";
            this.checkBox3.UseVisualStyleBackColor = true;
            this.checkBox3.CheckedChanged += new System.EventHandler(this.checkBox3_CheckedChanged);
            // 
            // checkBox4
            // 
            this.checkBox4.AutoSize = true;
            this.checkBox4.Location = new System.Drawing.Point(6, 88);
            this.checkBox4.Name = "checkBox4";
            this.checkBox4.Size = new System.Drawing.Size(195, 17);
            this.checkBox4.TabIndex = 6;
            this.checkBox4.Text = "How do I add a crossing to my grid?";
            this.checkBox4.UseVisualStyleBackColor = true;
            this.checkBox4.CheckedChanged += new System.EventHandler(this.checkBox4_CheckedChanged);
            // 
            // checkBox5
            // 
            this.checkBox5.AutoSize = true;
            this.checkBox5.Location = new System.Drawing.Point(6, 111);
            this.checkBox5.Name = "checkBox5";
            this.checkBox5.Size = new System.Drawing.Size(262, 17);
            this.checkBox5.TabIndex = 7;
            this.checkBox5.Text = "Must the cell Im moving my crossing to, be empty?";
            this.checkBox5.UseVisualStyleBackColor = true;
            this.checkBox5.CheckedChanged += new System.EventHandler(this.checkBox5_CheckedChanged);
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(13, 191);
            this.textBox1.Multiline = true;
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(493, 145);
            this.textBox1.TabIndex = 7;
            // 
            // HelpForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(594, 398);
            this.Controls.Add(this.tabControl1);
            this.Name = "HelpForm";
            this.Text = "HelpForm";
            this.Load += new System.EventHandler(this.HelpForm_Load);
            this.tabControl1.ResumeLayout(false);
            this.tabIntersection.ResumeLayout(false);
            this.tabIntersection.PerformLayout();
            this.tabSimulation.ResumeLayout(false);
            this.tabSimulation.PerformLayout();
            this.tabFAQ.ResumeLayout(false);
            this.tabFAQ.PerformLayout();
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TabControl tabControl1;
        private System.Windows.Forms.TabPage tabIntersection;
        private System.Windows.Forms.TabPage tabSimulation;
        private System.Windows.Forms.TabPage tabFAQ;
        private System.Windows.Forms.Label labIntersection;
        private System.Windows.Forms.Label labSimulation;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.CheckBox checkBox5;
        private System.Windows.Forms.CheckBox checkBox4;
        private System.Windows.Forms.CheckBox checkBox3;
        private System.Windows.Forms.CheckBox checkBox2;
        private System.Windows.Forms.CheckBox checkBox1;
    }
}