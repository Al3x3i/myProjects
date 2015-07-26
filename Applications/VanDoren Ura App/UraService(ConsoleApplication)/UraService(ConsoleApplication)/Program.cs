using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WCFUraLib;

namespace UraService_ConsoleApplication_
{
    class Program
    {
        static void Main(string[] args)
        {
            UraService uraLib = new UraService();
            uraLib.StartService();
            Console.WriteLine("Listening...");
            Console.ReadLine();

             //Close the service.
            uraLib.StopService();
        }
    }
}
