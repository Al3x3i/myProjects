using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace TrafficLightApplication
{
    public partial class HelpForm : Form
    {
        String[] myanswers;

        public HelpForm()
        {
            InitializeComponent();
            myanswers = new String[5];
     
            myanswers[0] = "The simulation runs the duration of time you set to it. You can set the simulation to run to any duration";
            myanswers[1] = "It is not possible to place a crossing over another crossing";
            myanswers[2] = "You can find an open button at the top left corner of the circuit form, when you click on this button, your system will open up a window where you can select a saved grid on your system. Opening the file, loads the crossings on the grid";
            myanswers[3] = "To add a crossing to yor grid, drag on a particular crossing and drop it on a empty cell on the grid.";
            myanswers[4] = "Yes, this cell has to be an empty cell, if not the crossing won't be placed.";
            
        }

        private void HelpForm_Load(object sender, EventArgs e)
        {
            string holderIntersections = null;

            holderIntersections += "Add a Crossing:\r\n";
            holderIntersections += "\r\n";
            holderIntersections += "1. Select the type of crossing you want to add.";
            holderIntersections += "\r\n2. Drag and drop the crossing on an empty cell.";
            holderIntersections += "\r\n3. Crossing added.";
            holderIntersections += "\r\n\r\n";
            holderIntersections += "Remove a crossing:\r\n";
            holderIntersections += "\r\n";
            holderIntersections += "1. Right click on a crossing and click remove.";
            holderIntersections += "\r\n2. Crossing Removed.";
            holderIntersections += "\r\n";
            holderIntersections += "\r\n\r\n";
            holderIntersections += "Move an intersection:\r\n";
            holderIntersections += "\r\n";
            holderIntersections += "1. Select the move button. ";
            holderIntersections += "\r\n2. Select the intersection on the grid that is to be moved.";
            holderIntersections += "\r\n3. Drag the crossing to a new empty position on the grid";
            holderIntersections += "\r\n4. Crossing moved.";
            holderIntersections += "\r\n\r\n";
            holderIntersections += "Edit settings / parameters:\r\n";
            holderIntersections += "\r\n";
            holderIntersections += "1. Move to the right pane where traffic settings are.";
            holderIntersections += "\r\n2. Change and enter the desired settings.";
            holderIntersections += "\r\n3. Editing completed.";
            holderIntersections += "\r\n\r\n";

            // display
            this.labIntersection.Text = holderIntersections;

            // string for holding the whole descriptions and explanations
            // of the simulation.
            string holderSimulation = null;

            holderSimulation += "Start a simulation:\r\n";
            holderSimulation += "\r\n";
            holderSimulation += "1. Place one or more intersections on the grid.";
            holderSimulation += "\r\n2. Select the play button.";
            holderSimulation += "\r\n3. Simulation started.";
            holderSimulation += "\r\n\r\n";
            holderSimulation += "Pause a simulation:\r\n";
            holderSimulation += "\r\n";
            holderSimulation += "1. Select the pause button on a running simulation.";
            holderSimulation += "\r\n2. Simulation stopped.";
            holderSimulation += "\r\n\r\n";
            holderSimulation += "Stop a simulation:\r\n";
            holderSimulation += "\r\n";
            holderSimulation += "1. Select the stop button on a running simulation.";
            holderSimulation += "\r\n2. Simulation stopped.";
            holderSimulation += "\r\n\r\n";

            // display
            this.labSimulation.Text = holderSimulation;

           

          
        }

        private void checkBox1_CheckedChanged(object sender, EventArgs e)
        {
            if (checkBox1.Checked == true)
            {
                checkBox2.Checked = false;
                checkBox3.Checked = false;
                checkBox4.Checked = false;
                checkBox5.Checked = false;
                
                textBox1.Text = myanswers[0];
            }
            else
            {
                textBox1.Text = "";
            }
        }

        private void checkBox2_CheckedChanged(object sender, EventArgs e)
        {
            if (checkBox2.Checked == true)
            {
                checkBox1.Checked = false;
                checkBox3.Checked = false;
                checkBox4.Checked = false;
                checkBox5.Checked = false;
               
                textBox1.Text = myanswers[1];

            }
            else
            {
                textBox1.Text = "";
            }
        }

        private void checkBox3_CheckedChanged(object sender, EventArgs e)
        {
            if (checkBox3.Checked == true)
            {
                checkBox1.Checked = false;
                checkBox2.Checked = false;
                checkBox4.Checked = false;
                checkBox5.Checked = false;
                
                textBox1.Text = myanswers[2];

            }
            else
            {
                textBox1.Text = "";
            }
        }

        private void checkBox4_CheckedChanged(object sender, EventArgs e)
        {
            if (checkBox4.Checked == true)
            {
                checkBox1.Checked = false;
                checkBox2.Checked = false;
                checkBox3.Checked = false;
                checkBox5.Checked = false;
               
                textBox1.Text = myanswers[3];

            }
            else
            {
                textBox1.Text = "";
            }
        }

        private void checkBox5_CheckedChanged(object sender, EventArgs e)
        {
            if (checkBox5.Checked == true)
            {
                checkBox1.Checked = false;
                checkBox2.Checked = false;
                checkBox3.Checked = false;
                checkBox4.Checked = false;
               
                textBox1.Text = myanswers[4];

            }
            else
            {
                textBox1.Text = "";
            }
        }

    }
}
