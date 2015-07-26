using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;

namespace TrafficLightApplication
{        
    
    public class TrafficLight
    {
            private int lightID { get; set; }//this is set once through the constructor.
        
            //constructor
            public TrafficLight(int lightID)
            {
                this.lightID = lightID;
                this.DurationGreen = 5;
                
            }

            // -- Properties --

            /// <summary>
            /// Holds green light.
            /// </summary>
            public Light Greenlight { get; set; }

            /// <summary>
            /// Holds orange light.
            /// </summary>
            public Light Orangelight { get; set; }

            /// <summary>
            /// Holds red light.
            /// </summary>
            public Light Redlight { get; set; }

            /// <summary>
            /// Holds color.
            /// </summary>
            public Color Color { get; set; }

            /// <summary>
            /// Holds duration of green time.
            /// </summary>
            public int DurationGreen { get; set; }

            // -- Methods

            /// <summary>
            /// Method for assigning the traffic lights with colors.
            /// </summary>
            /// <param name="green">Position of green light.</param>
            /// <param name="orange">Position of orange light.</param>
            /// <param name="red">Position of red light.</param>
            public void setLights(Point green, Point orange, Point red)
            {
                Greenlight = new Light(green);
                Redlight = new Light(red);
                Orangelight = new Light(orange);
            }

            /// <summary>
            /// Set the color of the traffic light green.
            /// </summary>
            public void setGreen()
            {
                Color = Color.Green;
                this.Greenlight.StatusColor = Color;
                this.Redlight.StatusColor = Color.Black;
            }
            /// <summary>
            /// Set the color of the traffic light red.
            /// </summary>
            public void setRed()
            {
                Color = Color.Red;
                this.Greenlight.StatusColor = Color.Black;
                this.Redlight.StatusColor = Color;
            }
          
    }

    /// <summary>
    /// This is the <b>Light</b> class.
    /// </summary>
    public class Light
    {
        /// <summary>
        /// Constructor of Light object.
        /// </summary>
        /// <param name="p">Position of light.</param>
        public Light(Point p)
        {
            this.Location = p;
            this.StatusColor = Color.Black;
        }

        // -- Properties --

        /// <summary>
        /// Holds location of light.
        /// </summary>
        public Point Location { get; set; }

        /// <summary>
        /// Holds current color of light.
        /// </summary>
        public Color StatusColor { get; set; }
    }


 }//namespace

