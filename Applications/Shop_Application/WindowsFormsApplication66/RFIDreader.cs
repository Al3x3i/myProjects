using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Phidgets;
using Phidgets.Events;

namespace WindowsFormsApplication66
{
    public class RFIDreader
    {
        RFID myRFIDReader;

        public RFIDreader(AttachEventHandler acctivate, DetachEventHandler deactivate, TagEventHandler process)
        {
                myRFIDReader = new RFID();
                myRFIDReader.Attach += acctivate;
                myRFIDReader.Detach += deactivate;
                myRFIDReader.Tag += process;
        }

        public string ReadCode()
        {
            try
            {
                    myRFIDReader.open();
                    myRFIDReader.waitForAttachment(3000);
                    myRFIDReader.Antenna = true;
                    myRFIDReader.LED = true;
                    return "an RFID-reader is found and opened.";
                
            }
            catch (PhidgetException) { return "no RFID-reader opened."; }  
        }

        public void RFIDturnOFF()
        {
            try
            {
                myRFIDReader.LED = false;
                myRFIDReader.Antenna = false;
                myRFIDReader.close();
            }
            catch (Exception)
            {
              
            }
           
        }
    }
}
