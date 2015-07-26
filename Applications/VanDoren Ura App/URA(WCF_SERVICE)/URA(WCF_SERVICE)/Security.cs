using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Security.Cryptography;
using System.Text;

namespace URA_WCF_SERVICE_
{

    public static class RSAEngine
    {
        //If true = Random number will be added at the beginning and at the end of the message to incriese security. 
        //This function not used because some problem with Java encryption.In java it is turned on(RSA/ECB/PKCS1PADDING), C# decrypt with Padding=false
        private static bool _optimalAsymmetricEncryptionPadding = false;

        /// <summary>
        /// Gerenate random keys
        /// </summary>
        /// <param name="keySize">size of the key, 512,1024,2048</param>
        /// <param name="publicKey">key to encrypt message</param>
        /// <param name="publicAndPrivateKey">key to decrypt message</param>
        public static void GenerateKeys(int keySize, out string publicKey, out string publicAndPrivateKey)
        {
            using (var provider = new RSACryptoServiceProvider(keySize))
            {
                publicKey = provider.ToXmlString(false);
                publicAndPrivateKey = provider.ToXmlString(true);
            }
        }

        /// <summary>
        /// encrypt plain text
        /// </summary>
        /// <param name="text">plain text</param>
        /// <param name="keySize">size of the key, same size for decryption</param>
        /// <param name="publicKeyXml">public key in encapsulated in XML format</param>
        /// <returns> encypted text</returns>
        public static string EncryptText(string text, int keySize, string publicKeyXml)
        {
            var encrypted = Encrypt(Encoding.UTF8.GetBytes(text), keySize, publicKeyXml);
            return Convert.ToBase64String(encrypted);
        }

        /// <summary>
        /// Encrypt message in bytes
        /// </summary>
        /// <param name="data"></param>
        /// <param name="keySize"></param>
        /// <param name="publicKeyXml"></param>
        /// <returns></returns>
        private static byte[] Encrypt(byte[] data, int keySize, string publicKeyXml)
        {
            if (data == null || data.Length == 0) throw new ArgumentException("Data are empty", "data");
            int maxLength = GetMaxDataLength(keySize);
            if (data.Length > maxLength) throw new ArgumentException(String.Format("Maximum data length is {0}", maxLength), "data");
            if (!IsKeySizeValid(keySize)) throw new ArgumentException("Key size is not valid", "keySize");
            if (String.IsNullOrEmpty(publicKeyXml)) throw new ArgumentException("Key is null or empty", "publicKeyXml");

            using (var provider = new RSACryptoServiceProvider(keySize))
            {
                provider.FromXmlString(publicKeyXml);
                return provider.Encrypt(data, _optimalAsymmetricEncryptionPadding);
            }
        }

        /// <summary>
        /// Decrypt message in string
        /// </summary>
        /// <param name="text">encrypted mesage</param>
        /// <param name="keySize">size of the key</param>
        /// <param name="publicAndPrivateKeyXml">private key. </param>
        /// <returns>decrypted message</returns>
        public static string DecryptText(string text, int keySize, string publicAndPrivateKeyXml)
        {
            var decrypted = Decrypt(Convert.FromBase64String(text), keySize, publicAndPrivateKeyXml);
            return Encoding.UTF8.GetString(decrypted);
        }

        /// <summary>
        ///  Decrypt message in bytes
        /// </summary>
        /// <param name="data">message in binary </param>
        /// <param name="keySize">size of the key</param>
        /// <param name="publicAndPrivateKeyXml">private key</param>
        /// <returns>bytesMessage </returns>
        private static byte[] Decrypt(byte[] data, int keySize, string publicAndPrivateKeyXml)
        {
            if (data == null || data.Length == 0) throw new ArgumentException("Data are empty", "data");
            if (!IsKeySizeValid(keySize)) throw new ArgumentException("Key size is not valid", "keySize");
            if (String.IsNullOrEmpty(publicAndPrivateKeyXml)) throw new ArgumentException("Key is null or empty", "publicAndPrivateKeyXml");

            using (var provider = new RSACryptoServiceProvider(keySize))
            {
                provider.FromXmlString(publicAndPrivateKeyXml);
                return provider.Decrypt(data, _optimalAsymmetricEncryptionPadding);
            }
        }

        /// <summary>
        /// Changes key size according calculations
        /// </summary>
        /// <param name="keySize"></param>
        /// <returns></returns>
        private static int GetMaxDataLength(int keySize)
        {
            if (_optimalAsymmetricEncryptionPadding)
            {
                return ((keySize - 384) / 8) + 7;
            }
            return ((keySize - 384) / 8) + 37;
        }

        /// <summary>
        /// Key size validation
        /// </summary>
        /// <param name="keySize"></param>
        /// <returns></returns>
        private static bool IsKeySizeValid(int keySize)
        {
            return keySize >= 384 &&
                    keySize <= 16384 &&
                    keySize % 8 == 0;
        }
    }


    public static class AESEngine
    {
        /// <summary>
        /// Initializing Rijndael's symmetric encryption algorithm (.NET Framework 4.5)
        /// </summary>
        /// <param name="secretKey"></param>
        /// <returns></returns>
        private static RijndaelManaged GetRijndaelManaged(String secretKey)
        {
            var keyBytes = new byte[16];
            var secretKeyBytes = Encoding.UTF8.GetBytes(secretKey);

            //if size of secretKey bigger that 128 bytes(16*8) then extra chars will be removed.
            Array.Copy(secretKeyBytes, keyBytes, Math.Min(keyBytes.Length, secretKeyBytes.Length));
            return new RijndaelManaged
            {
                Mode = CipherMode.CBC,
                Padding = PaddingMode.PKCS7,
                KeySize = 128,
                BlockSize = 128,
                Key = keyBytes,
                IV = keyBytes
            };
        }

        private static byte[] Encrypt(byte[] plainBytes, RijndaelManaged rijndaelManaged)
        {
            return rijndaelManaged.CreateEncryptor()
                .TransformFinalBlock(plainBytes, 0, plainBytes.Length);
        }

        private static byte[] Decrypt(byte[] encryptedData, RijndaelManaged rijndaelManaged)
        {
            return rijndaelManaged.CreateDecryptor()
                .TransformFinalBlock(encryptedData, 0, encryptedData.Length);
        }

        /// <summary>
        /// Encrypts plaintext using AES 128bit key and a Chain Block Cipher and returns a base64 encoded string
        /// </summary>
        /// <param name="plainText">Plain text to encrypt</param>
        /// <param name="masterKey">Secret key</param>
        /// <returns>Encoded string</returns>
        public static String Encrypt(String plainText, String masterKey)
        {
            var plainBytes = Encoding.UTF8.GetBytes(plainText);
            return Convert.ToBase64String(Encrypt(plainBytes, GetRijndaelManaged(masterKey)));
        }

        /// <summary>
        /// Decrypts a base64 encoded string using the given key (AES 128bit key and a Chain Block Cipher)
        /// </summary>
        /// <param name="encryptedText">Base64 Encoded String</param>
        /// <param name="masterKey">Secret key</param>
        /// <returns>Decrypted String</returns>
        public static String Decrypt(String encryptedText, String masterKey)
        {
            var encryptedBytes = Convert.FromBase64String(encryptedText);
            return Encoding.UTF8.GetString(Decrypt(encryptedBytes, GetRijndaelManaged(masterKey)));
        }

        static byte[] GetBytes(string str)
        {
            byte[] bytes = new byte[str.Length * sizeof(char)];
            System.Buffer.BlockCopy(str.ToCharArray(), 0, bytes, 0, bytes.Length);
            return bytes;
        }
    }
}
