namespace Des_Algorithm
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
            this.label6 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.tbDecriptedMessage = new System.Windows.Forms.TextBox();
            this.tbCipherText = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.tbDecriptionKey = new System.Windows.Forms.TextBox();
            this.tbDec_CipherText = new System.Windows.Forms.TextBox();
            this.btDecrypt = new System.Windows.Forms.Button();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.tbText = new System.Windows.Forms.TextBox();
            this.btEncrpyt = new System.Windows.Forms.Button();
            this.tbEncryptionKey = new System.Windows.Forms.TextBox();
            this.lbMesNr = new System.Windows.Forms.Label();
            this.lbKeyNr = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(364, 186);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(65, 17);
            this.label6.TabIndex = 12;
            this.label6.Text = "Message";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(364, 11);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(84, 17);
            this.label5.TabIndex = 11;
            this.label5.Text = "Ciphert Text";
            // 
            // tbDecriptedMessage
            // 
            this.tbDecriptedMessage.Location = new System.Drawing.Point(367, 205);
            this.tbDecriptedMessage.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.tbDecriptedMessage.Name = "tbDecriptedMessage";
            this.tbDecriptedMessage.ReadOnly = true;
            this.tbDecriptedMessage.Size = new System.Drawing.Size(245, 22);
            this.tbDecriptedMessage.TabIndex = 10;
            // 
            // tbCipherText
            // 
            this.tbCipherText.Location = new System.Drawing.Point(367, 30);
            this.tbCipherText.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.tbCipherText.Name = "tbCipherText";
            this.tbCipherText.ReadOnly = true;
            this.tbCipherText.Size = new System.Drawing.Size(245, 22);
            this.tbCipherText.TabIndex = 9;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(2, 235);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(32, 17);
            this.label4.TabIndex = 8;
            this.label4.Text = "Key";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(2, 191);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(80, 17);
            this.label3.TabIndex = 7;
            this.label3.Text = "Cipher Text";
            // 
            // tbDecriptionKey
            // 
            this.tbDecriptionKey.Location = new System.Drawing.Point(94, 232);
            this.tbDecriptionKey.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.tbDecriptionKey.Name = "tbDecriptionKey";
            this.tbDecriptionKey.Size = new System.Drawing.Size(225, 22);
            this.tbDecriptionKey.TabIndex = 6;
            // 
            // tbDec_CipherText
            // 
            this.tbDec_CipherText.Location = new System.Drawing.Point(94, 186);
            this.tbDec_CipherText.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.tbDec_CipherText.Name = "tbDec_CipherText";
            this.tbDec_CipherText.Size = new System.Drawing.Size(225, 22);
            this.tbDec_CipherText.TabIndex = 5;
            // 
            // btDecrypt
            // 
            this.btDecrypt.Location = new System.Drawing.Point(137, 271);
            this.btDecrypt.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.btDecrypt.Name = "btDecrypt";
            this.btDecrypt.Size = new System.Drawing.Size(75, 27);
            this.btDecrypt.TabIndex = 4;
            this.btDecrypt.Text = "Decrypt";
            this.btDecrypt.UseVisualStyleBackColor = true;
            this.btDecrypt.Click += new System.EventHandler(this.btDecrypt_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(2, 61);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(32, 17);
            this.label2.TabIndex = 4;
            this.label2.Text = "Key";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(2, 4);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(85, 34);
            this.label1.TabIndex = 3;
            this.label1.Text = "Message\r\nMax(2bytes)";
            // 
            // tbText
            // 
            this.tbText.Location = new System.Drawing.Point(94, 11);
            this.tbText.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.tbText.MaxLength = 16;
            this.tbText.Name = "tbText";
            this.tbText.Size = new System.Drawing.Size(225, 22);
            this.tbText.TabIndex = 0;
            this.tbText.Text = "0110001001110110";
            this.tbText.TextChanged += new System.EventHandler(this.tbText_TextChanged);
            // 
            // btEncrpyt
            // 
            this.btEncrpyt.Location = new System.Drawing.Point(137, 99);
            this.btEncrpyt.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.btEncrpyt.Name = "btEncrpyt";
            this.btEncrpyt.Size = new System.Drawing.Size(75, 27);
            this.btEncrpyt.TabIndex = 2;
            this.btEncrpyt.Text = "Encrypt";
            this.btEncrpyt.UseVisualStyleBackColor = true;
            this.btEncrpyt.Click += new System.EventHandler(this.btEncrpyt_Click);
            // 
            // tbEncryptionKey
            // 
            this.tbEncryptionKey.Location = new System.Drawing.Point(94, 58);
            this.tbEncryptionKey.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.tbEncryptionKey.Name = "tbEncryptionKey";
            this.tbEncryptionKey.Size = new System.Drawing.Size(225, 22);
            this.tbEncryptionKey.TabIndex = 1;
            this.tbEncryptionKey.Text = "1010000010110010";
            this.tbEncryptionKey.TextChanged += new System.EventHandler(this.tbEncryptionKey_TextChanged);
            // 
            // lbMesNr
            // 
            this.lbMesNr.AutoSize = true;
            this.lbMesNr.Location = new System.Drawing.Point(326, 15);
            this.lbMesNr.Name = "lbMesNr";
            this.lbMesNr.Size = new System.Drawing.Size(12, 17);
            this.lbMesNr.TabIndex = 13;
            this.lbMesNr.Text = ".";
            // 
            // lbKeyNr
            // 
            this.lbKeyNr.AutoSize = true;
            this.lbKeyNr.Location = new System.Drawing.Point(326, 63);
            this.lbKeyNr.Name = "lbKeyNr";
            this.lbKeyNr.Size = new System.Drawing.Size(12, 17);
            this.lbKeyNr.TabIndex = 14;
            this.lbKeyNr.Text = ".";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.SystemColors.ActiveCaption;
            this.ClientSize = new System.Drawing.Size(629, 308);
            this.Controls.Add(this.lbKeyNr);
            this.Controls.Add(this.lbMesNr);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.tbCipherText);
            this.Controls.Add(this.tbDecriptedMessage);
            this.Controls.Add(this.tbEncryptionKey);
            this.Controls.Add(this.btEncrpyt);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.tbText);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.tbDecriptionKey);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.tbDec_CipherText);
            this.Controls.Add(this.btDecrypt);
            this.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            this.Name = "Form1";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Form1";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox tbDecriptedMessage;
        private System.Windows.Forms.TextBox tbCipherText;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox tbDecriptionKey;
        private System.Windows.Forms.TextBox tbDec_CipherText;
        private System.Windows.Forms.Button btDecrypt;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox tbText;
        private System.Windows.Forms.Button btEncrpyt;
        private System.Windows.Forms.TextBox tbEncryptionKey;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label lbMesNr;
        private System.Windows.Forms.Label lbKeyNr;

    }
}

