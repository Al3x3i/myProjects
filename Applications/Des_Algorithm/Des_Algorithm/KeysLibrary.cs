using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Des_Algorithm
{
    class KeysLibrary
    {
        public static byte[] PC1_subKey = new byte[] { 12, 5, 14, 1, 10, 2, 6, 9, 15, 4, 13, 7, 11, 3 }; //PC1 permutation matrix
        public static byte[] PC2_subKey = new byte[] { 6, 11, 4, 8, 13, 3, 12, 5, 1, 10, 2, 9 }; //PC2 permutation matrix

        public static byte[] IP = new byte[] { 10, 6, 14, 2, 8, 16, 12, 4, 1, 13, 7, 9, 5, 11, 3, 15 }; //Initial permutation matrix
        public static byte[] IP_1 = new byte[] { 9, 4, 15, 8, 13, 2, 11, 5, 12, 1, 14, 7, 10, 3, 16, 6 }; //Inverse of initial permutation matrix
        public static byte[] EP = new byte[] { 8, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 1 }; // Permutation matrix expansion
        public static byte[] P = new byte[] { 6, 4, 7, 3, 5, 1, 8, 2 }; //Fixed permutation matrix
        public static byte[,] SB5 = new byte[4, 16] { { 2, 12,  4,  1,  7, 10, 11,  6,  8,  5,  3, 15, 13,  0, 14,  9 },  //S-BOXnr5 matrix
                                        { 14, 11,  2, 12,  4,  7, 13,  1,  5,  0, 15, 10,  3,  9,  8,  6 },
                                        { 4,  2,  1, 11, 10, 13,  7,  8, 15,  9, 12,  5,  6,  3,  0, 14 },
                                        { 11,  8, 12,  7,  1, 14,  2, 13,  6, 15,  0,  9, 10,  4,  5,  3 }};

        public static byte[,] SB6 = new byte[4, 16] { { 12,  1, 10, 15,  9,  2,  6,  8,  0, 13,  3,  4, 14,  7,  5, 11 }, //S-BOXnr6 matrix
                                        { 10, 15,  4,  2,  7, 12,  9,  5,  6,  1, 13, 14,  0, 11,  3,  8 },
                                        { 9, 14, 15,  5,  2,  8, 12,  3,  7,  0,  4, 10,  1, 13, 11,  6 },
                                        { 4,  3,  2, 12,  9,  5, 15, 10, 11, 14,  1,  7,  6,  0,  8, 13 }};
    }
}
