using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Linq;
using System.ServiceProcess;
using System.Text;
using System.Threading.Tasks;
using WCFUraLib;

namespace Ura_windowsService_
{
    public partial class Service1 : ServiceBase
    {
        UraService uraLib;
        public Service1()
        {
            InitializeComponent();
            uraLib = new UraService();
        }

        protected override void OnStart(string[] args)
        {
            uraLib.StartService();
        }

        protected override void OnStop()
        {
            uraLib.StopService();
        }
    }
}
