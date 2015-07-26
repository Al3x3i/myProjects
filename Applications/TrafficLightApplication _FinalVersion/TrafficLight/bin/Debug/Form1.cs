using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Threading;
using System.IO;
using Crossing.TypeA;
using System.Runtime.Serialization.Formatters.Binary;
using System.Runtime.Serialization;


namespace TrafficLightApplication
{
    public partial class Form1 : Form
    {
        View view;
        Project project;
        Simulator simulator;
        TypeA1 typeA;
        Grid grid;

        Thread playModeThread;
        decimal simulationDuration;

        public Form1()
        {
            
            InitializeComponent();

            project = new Project("Default poject", 3, 4);
            view = new View(project, new View.DisplaySelectedCrossing(DisplaySelectedCrossing)); //for MVC pattern
            simulator = new Simulator(view, simulationProgressBar);
            simulator.changeProgressBar += UpdateSimulationProgressBar; // shows how much time needs to finish simulation

            typeA = new TypeA1();
            this.pc_Crossint.Image = typeA.GetCrossingImage();
            panel_View.Controls.Add(view);
            
        }


        private void pc_Crossint_MouseDown(object sender, MouseEventArgs e)
        {
            pc_Crossint.DoDragDrop(pc_Crossint.Image, DragDropEffects.Copy);
        }
        /// <summary>
        /// Updates simulation progress bar, used by Simulator class, is invoked through changeProgressBar event.
        /// </summary>
        /// <param name="i"></param>
        private void UpdateSimulationProgressBar(int i)
        {
            simulationProgressBar.Invoke(
                new Action(() =>
                {
                    simulationProgressBar.Value = i;
                }
            ));
        }
        /// <summary>
        /// Method is invoked through simulator event updateSimulationStatus when simulation duration is over.
        /// </summary>
        private void UpdateSimulaionStatus()
        {


        }
        #region //Load/Save project
        private void saveProjectToolStripMenuItem_Click(object sender, EventArgs e)
        {
            SaveFileDialog saveFile = new SaveFileDialog();

            if (saveFile.ShowDialog() == DialogResult.OK)
            {
                FileStream myFS = null;
                BinaryFormatter myBF = null;

                try
                {
                    myFS = new FileStream(saveFile.FileName + ".dc", FileMode.Create, FileAccess.Write);
                    myBF = new BinaryFormatter();
                    myBF.Serialize(myFS, grid);

                    MessageBox.Show("Your file has been saved.");
                }
                catch (SerializationException)
                {
                    MessageBox.Show("A problem happend, please try again.");
                }
                catch (IOException x)
                {
                    MessageBox.Show(x.Message);
                }
                finally
                {
                    if (myFS != null)
                        myFS.Close();
                }
            }
        }

        private void loadProjectToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            OpenFileDialog openFile = new OpenFileDialog();

            if (openFile.ShowDialog() == DialogResult.OK)
            {
                FileStream myFS = null;
                BinaryFormatter myBF = null;

                try
                {
                    myFS = new FileStream(openFile.FileName, FileMode.Open, FileAccess.Read);
                    myBF = new BinaryFormatter();
                    grid = (Grid)(myBF.Deserialize(myFS));
                }
                catch (SerializationException)
                {
                    MessageBox.Show("Make sure you opened the correct file,please try again.");
                }
                catch (IOException x)
                {
                    MessageBox.Show(x.Message);
                }
                finally
                {
                    if (myFS != null)
                        myFS.Close();
                    Invalidate();
                }
            }
        }
        #endregion

        #region Simulation mode buttons
        /// <summary>
        /// Main method which start simulation. It has two different modes: 1) Initial situation when simulation runned first time simulationStatus == running
        /// 2) simulationStatus == edition - the second one is used when user want pause simulation to make not essentional changes or just check
        /// current situation.
        /// To pause method is used "lock" same in Simulation class in method "StartSimulation()"
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        /// 

        private void bt_Stop_MouseClick(object sender, MouseEventArgs e)
        {
            bt_Stop.Image = Properties.Resources.stopButton_Common;
            simulator.simulationStatus = SimulationStatus.editting;

            DialogResult dr = MessageBox.Show("Close Simulation ?", "Traffic Simulation", MessageBoxButtons.YesNo,
                    MessageBoxIcon.Asterisk, MessageBoxDefaultButton.Button2);

            if (dr == DialogResult.Yes)
            {
                simulator.simulationStatus = SimulationStatus.stopping;
                UnLockSimulation();
                this.bt_Pause.Enabled = false;
                this.bt_Play.Enabled = true;
                this.bt_Stop.Enabled = false;
                this.bt_Play.Image = Properties.Resources.playButton_Common;
                bt_Stop.Image = Properties.Resources.stopButton_Disabled;
                bt_Pause.Image = Properties.Resources.pauseButton_Disabled;

                simulator.StopSimulation();
            }
            else
            {
                if (this.bt_Pause.Enabled != false) // while pause mode the stop button was clicked
                {
                    simulator.simulationStatus = SimulationStatus.running;
                    UnLockSimulation();
                }
            }
        }

        private void bt_Play_MouseClick(object sender, MouseEventArgs e)
        {
            this.bt_Play.Image = Properties.Resources.playButton_Disabled;
            bt_Stop.Image = Properties.Resources.stopButton_Common;
            bt_Pause.Image = Properties.Resources.pauseButton_Common;
            this.bt_Play.Enabled = false;
            this.bt_Pause.Enabled = true;
            this.bt_Stop.Enabled = true;

            if (simulator.simulationStatus != SimulationStatus.editting)  // simulationStatus == running
            {
                decimal simulationDuaration = this.numericDuration.Value;
                decimal simulationSpeed = this.numericSpeed.Value;
                simulationProgressBar.Maximum = (int)simulationDuaration * 600; // I am not sure about this number, should be one minut

                simulator.simulationStatus = SimulationStatus.running;
                if (playModeThread == null || !playModeThread.IsAlive)
                {
                    playModeThread = new Thread(() => simulator.StartSimulation(project.GetGrid(0), simulationDuaration, simulationSpeed));
                    playModeThread.Name = "Simulation thread";
                    playModeThread.Start();
                }
            }
            else if (simulator.simulationStatus == SimulationStatus.editting) // simulationStatus == editing
            {
                lock (simulator.threadLocker)
                {
                    simulator.simulationStatus = SimulationStatus.running;
                    UnLockSimulation();
                }
            }
        }

        private void bt_Pause_MouseClick(object sender, MouseEventArgs e)
        {
            simulator.simulationStatus = SimulationStatus.editting;

            this.bt_Pause.Enabled = false;
            this.bt_Play.Enabled = true;
            this.bt_Stop.Enabled = true;
            this.bt_Play.Image = Properties.Resources.playButton_Common;
            bt_Stop.Image = Properties.Resources.stopButton_Common;
            bt_Pause.Image = Properties.Resources.pauseButton_Disabled;
        }

        #region Simulation mode Hover/Double click
        private void bt_Pause_MouseDown(object sender, MouseEventArgs e)
        {
            this.bt_Pause.Image = Properties.Resources.pauseButton_Click;
        }

        private void bt_Pause_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            this.bt_Pause.Image = Properties.Resources.pauseButton_Common;
        }

        private void bt_Play_MouseDown(object sender, MouseEventArgs e)
        {
            this.bt_Play.Image = Properties.Resources.playButton_Click;
        }

        private void bt_Play_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            this.bt_Play.Image = Properties.Resources.playButton_Common;
        }

        private void bt_Stop_MouseDown(object sender, MouseEventArgs e)
        {

            this.bt_Stop.Image = Properties.Resources.stopButton_Click;
        }

        private void bt_Stop_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            this.bt_Stop.Image = Properties.Resources.stopButton_Common;
        }
        #endregion

        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (playModeThread !=null) // if thread isn't created
            {
                playModeThread.Abort();
            }
        }

        private void UnLockSimulation()
        {
            lock (simulator.threadLocker)
            {
                Monitor.Pulse(simulator.threadLocker);
            }
        }
#endregion


        #region //Left panelButtons handler
        private void lbButtonCrossingType_A_MouseClick(object sender, MouseEventArgs e)
        {
            this.lbButtonCrossingType_B.BackColor = System.Drawing.SystemColors.Control;
            this.lbButtonCrossingType_A.BackColor = System.Drawing.SystemColors.ActiveCaption;

            this.CrossingPanel.Visible = false;
            this.lbButtonCrossingType_B.Location = new System.Drawing.Point(lbButtonCrossingType_A.Location.X, CrossingPanel.Height + 60);
            this.CrossingPanel.Location = new System.Drawing.Point(lbButtonCrossingType_A.Location.X, lbButtonCrossingType_A.Location.Y+45);
            this.CrossingPanel.Visible = true;
            


        }

        private void lbButtonCrossingType_A_MouseDown(object sender, MouseEventArgs e)
        {
 
            this.lbButtonCrossingType_A.BackColor = System.Drawing.SystemColors.ControlDark;
        }

        private void lbButtonCrossingType_A_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            this.lbButtonCrossingType_A.BackColor = System.Drawing.SystemColors.ActiveCaption;
        }

        private void lbButtonCrossingType_B_MouseClick(object sender, MouseEventArgs e)
        {
            this.lbButtonCrossingType_A.BackColor = System.Drawing.SystemColors.Control;
            this.lbButtonCrossingType_B.BackColor = System.Drawing.SystemColors.ActiveCaption;

            this.CrossingPanel.Visible = false;
            this.lbButtonCrossingType_B.Location = new System.Drawing.Point(lbButtonCrossingType_A.Location.X, lbButtonCrossingType_A.Location.Y + 45);
            this.CrossingPanel.Location = new System.Drawing.Point(lbButtonCrossingType_B.Location.X, lbButtonCrossingType_B.Location.Y + 45);
            this.CrossingPanel.Visible = true;
        }

        private void lbButtonCrossingType_B_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            this.lbButtonCrossingType_B.BackColor = System.Drawing.SystemColors.ActiveCaption;
        }

        private void lbButtonCrossingType_B_MouseDown(object sender, MouseEventArgs e)
        {
            this.lbButtonCrossingType_B.BackColor = System.Drawing.SystemColors.ControlDark;
        }
        #endregion

        private void DisplaySelectedCrossing(Image crossing)
        {
            ///pbCrossingInfo.Image = crossing.CrossingPictureBox.Image;
            pbCrossingInfo.Image = crossing;
        }

        private void ReadAllCrossingsTypesFromFolder()
        {

        }










        /// <summary> // Alex
        /// Later make it more dynamic, automaticaly add eventhandlers. Related with view class.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        //private void pc_Crossint_MouseDown(object sender, MouseEventArgs e)
        //{
        //    Point mousePoint = new Point(e.X, e.Y);
            
            
        //    DoDragDrop(sender,DragDropEffects.Copy);
        //    //((Control)sender).DoDragDrop(sender, DragDropEffects.Copy);
        //}
    }
}
