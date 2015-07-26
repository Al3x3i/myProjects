using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;
using System.Windows.Forms;
using TrafficLightApplication;

namespace Crossing.TypeB
{
    public class TypeB1 //: ICrossing
    {
        public Image img;

        public TypeB1()
        {
            img = Resource.crossing_type_B1;
            ID = "Crossing B1";
        }

        public Image GetCrossingImage()
        {
            return img;
        }

        public string ID { get; set; }

    }
}
