using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Des_Algorithm
{
    class DesAlgorithm
    {
        KeysLibrary kyesLibrary;
        
        byte[] permutated_SubKey0; // subkey, which will be formed after first Key Generation, it uses for matrix K1 permutation.
        byte[] subkeyK1; //Subkey K1
        byte[] subkeyK2; //Subkey K1
        byte[] cipherText; //encrypted text
        public byte[] intialSubkey; //The user's subkey
        public byte[] plainText; //The user's message 

        public DesAlgorithm()
        {
            kyesLibrary = new KeysLibrary(); // object contains all data of keys

            subkeyK1 = new byte[KeysLibrary.PC2_subKey.Length];
            subkeyK2 = new byte[KeysLibrary.PC2_subKey.Length];
            plainText = new byte[16];
            intialSubkey = new byte[16];
            cipherText = new byte[16];
        }
        /// <summary>
        /// Main method to ecrypt message. It uses 2 iteration K1 and K2
        /// </summary>
        /// <param name="message"></param>
        /// <param name="key"></param>
        /// <returns></returns>
        public string Encryption(byte[] message, byte[] key)
        {
            KeysLibrary k = new KeysLibrary();

            plainText = message;
            intialSubkey = key;
            byte[] f_FunctionResult =new byte[8];  // f-function result
            byte[] desCicleResult = new byte[16]; // iteration result

            SubkeyCalculation(); // Find Subkeys

            //Chiphertext first inverse permutation
            byte[] IPplaintext = new byte[KeysLibrary.IP.Length]; // create temporal array to store Initial permutation key
            Permutation(plainText, KeysLibrary.IP, IPplaintext, IPplaintext.Length);  // changes IPplaintext - message IP

            //IP message splited into two parts
            byte[] IPplaintextRightSide = new byte[8];
            byte[] IPplaintextLeftSide = new byte[8];
            Array.Copy(IPplaintext, 8, IPplaintextRightSide, 0, 8);
            Array.Copy(IPplaintext, 0, IPplaintextLeftSide, 0, 8);

            //First iteration of F function
            f_FunctionResult = DesF_function(IPplaintextLeftSide, IPplaintextRightSide, subkeyK1);  // DES Cicle
            desCicleResult = SwapLeftToRight(f_FunctionResult, IPplaintextRightSide);  //Swap numbers from left to the right. End of 

            //Second Interation
            Array.Copy(desCicleResult, 8, IPplaintextRightSide, 0, 8);
            Array.Copy(desCicleResult, 0, IPplaintextLeftSide, 0, 8);
            f_FunctionResult = DesF_function(IPplaintextLeftSide, IPplaintextRightSide, subkeyK2); //

            //Combine Left and Right part after second iteration to provide invesre permutation

            Array.Copy(f_FunctionResult, 0, desCicleResult, 8, 8);
            Array.Copy(IPplaintextRightSide, 0, desCicleResult, 0, 8);

            Permutation(desCicleResult, KeysLibrary.IP_1, cipherText, cipherText.Length); //Inverse Initial permutation

            //generate string file with cipher text.
            string outcome = ""; 
            for (int i = 0; i < cipherText.Length; i++)
            {
                outcome += cipherText[i].ToString();
            }
            return outcome;
        }
        /// <summary>
        /// Main logic of decrypting occures below. Method implement only 2 steps for permutation. To extend to 16 permutations, needs to update to 16 subkeys.
        /// </summary>
        /// <param name="message"></param>
        /// <param name="key"></param>
        /// <returns></returns>
        public string Decryption(byte[] message, byte[] key)
        {

            cipherText = message;
            intialSubkey = key;

            SubkeyCalculation();

            byte[] desCicleInput = new byte[16];
            Permutation(cipherText, KeysLibrary.IP, desCicleInput, desCicleInput.Length); //Inverse Initial permutation

            byte[] IPplaintextRightSide = new byte[8];
            byte[] IPplaintextLeftSide = new byte[8];
            Array.Copy(desCicleInput, 0, IPplaintextRightSide, 0, 8);
            Array.Copy(desCicleInput, 8, IPplaintextLeftSide, 0, 8);


            IPplaintextLeftSide = InverseDesF_Function(IPplaintextLeftSide, IPplaintextRightSide, subkeyK2);
            desCicleInput = SwapLeftToRight(IPplaintextLeftSide, IPplaintextRightSide);

            Array.Copy(desCicleInput, 8, IPplaintextRightSide, 0, 8);
            Array.Copy(desCicleInput, 0, IPplaintextLeftSide, 0, 8);

            IPplaintextLeftSide = InverseDesF_Function(IPplaintextLeftSide, IPplaintextRightSide, subkeyK1);

            Array.Copy(IPplaintextRightSide, 0, desCicleInput, 8, 8);
            Array.Copy(IPplaintextLeftSide, 0, desCicleInput, 0, 8);


            byte[] PlainText = new byte[KeysLibrary.IP.Length];
            Permutation(desCicleInput, KeysLibrary.IP_1, PlainText, PlainText.Length);
            string outcome = "";
            for (int i = 0; i < PlainText.Length; i++)
            {
                outcome += PlainText[i].ToString();
            }

            return outcome;
        }

        /// <summary>
        /// To get subkey is used PC1 and PC2 matrices and used key Feed back shift at 3 positions. (Note: in 64 bit des is used addition table for key generation)
        /// </summary>
        private void SubkeyCalculation()
        {
            //Perform first subKey permutation
            permutated_SubKey0 = new byte[KeysLibrary.PC1_subKey.Length];
            Permutation(intialSubkey, KeysLibrary.PC1_subKey, permutated_SubKey0, KeysLibrary.PC1_subKey.Length); // method does permutation and returns generated subkey - permutated_SubKey0 is output

            //#K1 - K1 determination 
            SubkeyFeedBackShift(permutated_SubKey0);
            Permutation(permutated_SubKey0, KeysLibrary.PC2_subKey, subkeyK1, KeysLibrary.PC2_subKey.Length); 

            //#K2 - K2 determination
            SubkeyFeedBackShift(permutated_SubKey0);
            Permutation(permutated_SubKey0, KeysLibrary.PC2_subKey, subkeyK2, KeysLibrary.PC2_subKey.Length);
        }

        private byte[] DesF_function(byte[] IPplaintextLeftSide, byte[] IPplaintextRightSide, byte[] subKey)
        {

            byte[] cycleText;

            byte[] EPoutput = new byte[12];
            Permutation(IPplaintextRightSide, KeysLibrary.EP, EPoutput, EPoutput.Length);

            cycleText = XOR_function(EPoutput, subKey);

            string item = S_BoxFunction(cycleText);

            byte[] SboxesOutput = new byte[item.Length];
            for (int i = 0; i < item.Length; i++)
            {
                SboxesOutput[i] = Convert.ToByte(item[i].ToString());
            }

            byte[] p_key = new byte[8];
            Permutation(SboxesOutput, KeysLibrary.P, p_key, p_key.Length);

            byte[] p_output = XOR_function(IPplaintextLeftSide, p_key);  // level 7

            return p_output;
        }

        private byte[] InverseDesF_Function(byte[] IPplaintextLeftSide, byte[] IPplaintextRightSide, byte[] subKey)
        {
            byte[] EPoutput = new byte[12];
            Permutation(IPplaintextRightSide, KeysLibrary.EP, EPoutput, EPoutput.Length);

            byte[] cycleText;
            cycleText = XOR_function(EPoutput, subKey);

            string item = S_BoxFunction(cycleText);  // get outcome from S- Boxes

            byte[] SboxesOutput = new byte[item.Length];
            for (int i = 0; i < item.Length; i++)
            {
                SboxesOutput[i] = Convert.ToByte(item[i].ToString());
            }

            byte[] p_key = new byte[8];
            Permutation(SboxesOutput, KeysLibrary.P, p_key, p_key.Length);

            IPplaintextLeftSide = XOR_function(IPplaintextLeftSide, p_key);

            return IPplaintextLeftSide;
        }

        /// <summary>
        /// Method swaps cipher text into two parts. Left part goes to the Right part and Right to the Left part.
        /// </summary>
        /// <param name="f_FunctionResult"></param>
        /// <param name="IPplaintextRightSide"></param>
        /// <returns></returns>
        private byte[] SwapLeftToRight(byte[] f_FunctionResult, byte[] IPplaintextRightSide)
        {
            byte[] temp = new byte[16];
            Array.Copy(IPplaintextRightSide, 0, temp, 0, 8);
            Array.Copy(f_FunctionResult, 0, temp, 8, 8);
            return temp;
        }

        /// <summary>
        /// Method splits recived text into equal part(6 bits), then every s-box performs substitution 
        /// uses table and combines outcome.
        /// </summary>
        /// <param name="inputText"></param>
        /// <returns></returns>
        private string S_BoxFunction(byte[] inputText)
        {
            byte[] left = new byte[6]; //left side
            byte[] right = new byte[6]; //right side

            for (int counter = 0; counter < inputText.Length; counter++)
            {
                if (counter < 6)
                {
                    left[counter] = inputText[counter];
                }
                else
                {
                    right[-6 + counter] = inputText[counter];
                }
            }
            string SB5output = SelectedSboxOutput(left, KeysLibrary.SB5); // substitution uses SB5 table
            string SB6output = SelectedSboxOutput(right, KeysLibrary.SB6); // substitution uses SB6 table
            return SB5output + SB6output;
        }

        /// <summary>
        /// First is selected B0 and B5 bytes of array to find row number. Rest B1,B2,B3,B4 bytes is used to find column number
        /// </summary>
        /// <param name="S_BoxInput"></param>
        /// <param name="SBoxMatrixNumber"></param>
        /// <returns></returns>
        private string SelectedSboxOutput(byte[] S_BoxInput, byte[,] SBoxMatrixNumber)
        {
            string temp;
            temp = Convert.ToString(S_BoxInput.First(), 2) + Convert.ToString(S_BoxInput.Last(), 2);

            int row = Convert.ToInt32(temp, 2);

            temp = Convert.ToString(S_BoxInput[1], 2) + Convert.ToString(S_BoxInput[2], 2) +
                    Convert.ToString(S_BoxInput[3], 2) + Convert.ToString(S_BoxInput[4], 2);

            int column = Convert.ToInt32(temp, 2);

            string output = Convert.ToString(SBoxMatrixNumber[row, column], 2);

            while (output.Length < 4) // the result should have 4 bytes in ASCII
            {
                output = "0" + output;
            }
            return output;
        }

        /// <summary>
        /// Method concantinate two array uses XOR function
        /// </summary>
        /// <param name="EPoutput"></param>
        /// <param name="keyK1"></param>
        /// <returns></returns>
        private byte[] XOR_function(byte[] EPoutput, byte[] keyK1)
        {
            byte[] temp = new byte[EPoutput.Length];
            for (int i = 0; i < EPoutput.Length; i++)
            {
                if (EPoutput[i] != keyK1[i])
                {
                    temp[i] = 1;
                }
                else
                {
                    temp[i] = 0;
                }
            }
            return temp;
        }

        /// <summary>
        /// Main role of this methotd split subKey into two parts and call SubkeySwap() method to provide key shifting
        /// </summary>
        private void SubkeyFeedBackShift(byte[] SubKey)
        {
            byte[] C = new byte[7];
            byte[] D = new byte[7];

            for (int counter = 0; counter < permutated_SubKey0.Length; counter++)
            {
                if (counter < 7)
                {
                    C[counter] = permutated_SubKey0[counter];
                }
                else
                {
                    D[-7 + counter] = permutated_SubKey0[counter];
                }
            }
            C = SubkeySwap(C, 3);  //call method to shift byte positions in subkey
            D = SubkeySwap(D, 3);

            C.CopyTo(SubKey, 0);
            D.CopyTo(SubKey, 7);
        }

        /// <summary>
        /// Method receives key array of values and length at how much they should be shifted
        /// </summary>
        /// <param name="key"></param>
        /// <param name="feed_back_shift"></param>
        /// <returns></returns>
        private byte[] SubkeySwap(byte[] key, int length)
        {
            byte[] temp = new byte[length];

            for (int i = 0; i < key.Length; i++)
            {
                if (i == 0 || i == 1 || i == 2)
                {
                    temp[i] = key[i];
                }
                else
                {
                    key[i - 3] = key[i];
                }
            }
            temp.CopyTo(key, 4);  //This function adds first thee bytes to the end.
            return key;
        }

        /// <summary>
        /// General method to provide data permutation uses for this permutation matrixes, which depends on where it is used.
        /// </summary>
        /// <param name="input"></param>
        /// <param name="permutation">permutation matrix</param>
        /// <param name="output"></param>
        /// <param name="keyLength"></param>
        private void Permutation(byte[] input, byte[] permutation, byte[] output, int keyLength)
        {
            int number;
            for (int i = 0; i < keyLength; i++)
            {
                number = permutation[i];
                output[i] = input[number - 1];
            }
        }
    }
}
