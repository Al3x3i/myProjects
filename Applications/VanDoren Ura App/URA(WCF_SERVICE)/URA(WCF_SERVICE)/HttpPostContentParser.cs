using System;
using System.Collections.Generic;
using System.IO;
using System.Text;

namespace URA_WCF_SERVICE_
{
    class HttpPostContentParser
    {
        public HttpPostContentParser(Stream stream)
        {
            this.Parse(stream, Encoding.UTF8);
        }

        public HttpPostContentParser(Stream stream, Encoding encoding)
        {
            this.Parse(stream, encoding);
        }


        private void Parse(Stream stream, Encoding encoding)
        {
            this.Success = false;

            // Read the stream into a byte array

            byte[] data = Misc.ToByteArray(stream);

            // Copy to a string for header parsing
            string content = encoding.GetString(data);

            string name = string.Empty;
            string value = string.Empty;
            bool lookForValue = false;
            int charCount = 0;

            foreach (var c in content)
            {
                if (c == '=')
                {
                    lookForValue = true;
                }
                else if (c == '&')
                {
                    lookForValue = false;
                    AddParameter(name, value);
                    name = string.Empty;
                    value = string.Empty;
                }
                else if (!lookForValue)
                {
                    name += c;
                }
                else
                {
                    value += c;
                }

                if (++charCount == content.Length)
                {
                    AddParameter(name, value);
                    break;
                }
            }

            // Get the start & end indexes of the file contents
            //int startIndex = nameMatch.Index + nameMatch.Length + "\r\n\r\n".Length;
            //Parameters.Add(name, s.Substring(startIndex).TrimEnd(new char[] { '\r', '\n' }).Trim());

            // If some data has been successfully received, set success to true
            if (Parameters.Count != 0)
                this.Success = true;
        }

        private void AddParameter(string name, string value)
        {
            if (!string.IsNullOrWhiteSpace(name) && !string.IsNullOrWhiteSpace(value))
                Parameters.Add(name.Trim(), value.Trim());
        }

        public IDictionary<string, string> Parameters = new Dictionary<string, string>();

        public bool Success
        {
            get;
            private set;
        }
    }

    public static class Misc
    {
        public static int IndexOf(byte[] searchWithin, byte[] serachFor, int startIndex)
        {
            int index = 0;
            int startPos = Array.IndexOf(searchWithin, serachFor[0], startIndex);

            if (startPos != -1)
            {
                while ((startPos + index) < searchWithin.Length)
                {
                    if (searchWithin[startPos + index] == serachFor[index])
                    {
                        index++;
                        if (index == serachFor.Length)
                        {
                            return startPos;
                        }
                    }
                    else
                    {
                        startPos = Array.IndexOf<byte>(searchWithin, serachFor[0], startPos + index);
                        if (startPos == -1)
                        {
                            return -1;
                        }
                        index = 0;
                    }
                }
            }

            return -1;
        }

        public static byte[] ToByteArray(Stream stream)
        {
            byte[] buffer = new byte[32768];
            using (MemoryStream ms = new MemoryStream())
            {
                while (true)
                {
                    int read = stream.Read(buffer, 0, buffer.Length);
                    if (read <= 0)
                        return ms.ToArray();
                    ms.Write(buffer, 0, read);
                }
            }
        }
    }

}
