using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Des_Algorithm
{
    public partial class Form1 : Form
    {
        DesAlgorithm Des;

        public Form1()
        {
            InitializeComponent();
            Des = new DesAlgorithm();
            lbMesNr.Text = (16-tbText.Text.Length).ToString();
            lbKeyNr.Text = (16-tbEncryptionKey.Text.Length).ToString();
        }

        private void btEncrpyt_Click(object sender, EventArgs e)
        {
            string cipherText = "";
            byte[] messageToBinary = new byte[16];
            
            byte[] keyToBinary = new byte[16];
            
            try
            {
                string message = tbText.Text;
                string key = tbEncryptionKey.Text;
                

                if (message.Length <= 16 && message.Length != 0 &&
                    key.Length <= 16 && key.Length != 0)
                {
                    for (int i = 0; i < message.Length; i++)
                    {
                        messageToBinary[i] = Convert.ToByte(message[i].ToString());
                    }
                    for (int i = 0; i < key.Length; i++)
                    {
                        keyToBinary[i] = Convert.ToByte(key[i].ToString());
                    }

                    bool MessageAllDigits = message.All(char.IsDigit);
                    bool KeyAllDigits = key.All(char.IsDigit);

                    if (MessageAllDigits && KeyAllDigits && messageToBinary.Max() <= 1 &&
                        messageToBinary.Min() >= 0 && keyToBinary.Max() <= 1 && keyToBinary.Min() >= 0)
                    {
                        cipherText = Des.Encryption(messageToBinary, keyToBinary); // send data to create cipher text
                    }
                    else
                        MessageBox.Show("Sorry, The message and key are allowed to be in binary format: 0's and 1's");
                }
                else
                    MessageBox.Show("Sorry, The max size of text may be 16 digits");

                tbCipherText.Text = cipherText;
            }
            catch
            {
                MessageBox.Show("Sorry, Some unnexpected error occured");
            }
        }

        private void btDecrypt_Click(object sender, EventArgs e)
        {
            byte[] cipherTextToBinary = new byte[16];
            byte[] keyToBinary = new byte[16];
            string message = "";
            try
            {
                string cipherText = tbDec_CipherText.Text;
                string key = tbDecriptionKey.Text;

                if (cipherText.Length <= 16 && cipherText.Length != 0 &&
                    key.Length <= 16 && key.Length != 0)
                {
                    for (int i = 0; i < cipherText.Length; i++)
                    {
                        cipherTextToBinary[i] = Convert.ToByte(cipherText[i].ToString());
                    }
                    for (int i = 0; i < key.Length; i++)
                    {
                        keyToBinary[i] = Convert.ToByte(key[i].ToString());
                    }

                    bool cipherTextAllDigits = cipherText.All(char.IsDigit);
                    bool KeyAllDigits = key.All(char.IsDigit);

                    if (cipherTextAllDigits && KeyAllDigits && cipherTextToBinary.Max() <= 1 &&
                        cipherTextToBinary.Min() >= 0 && keyToBinary.Max() <= 1 && keyToBinary.Min() >= 0)
                    {
                        message = Des.Decryption(cipherTextToBinary, keyToBinary); // send data to to decrypt cypher text
                    }
                    else
                        MessageBox.Show("Sorry, The cipher and key are allowed to be in binary format: 0's and 1's");
                }
                else
                    MessageBox.Show("Sorry, The max size of text may be 16 digits");

                tbDecriptedMessage.Text = message;
            }
            catch
            {
                MessageBox.Show("Sorry, Some unnexpected error occured");
            }
        }

        private void tbText_TextChanged(object sender, EventArgs e)
        {

            lbMesNr.Text = (16- tbText.Text.Length).ToString();
   
        }

        private void tbEncryptionKey_TextChanged(object sender, EventArgs e)
        {
            lbKeyNr.Text = (16 - tbEncryptionKey.Text.Length).ToString();
        }
    }
}
