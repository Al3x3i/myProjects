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
using System.Runtime.Serialization.Formatters.Binary;
using System.Runtime.Serialization;
using System.Reflection;
using System.Drawing.Printing;
using System.Windows.Forms.DataVisualization.Charting;


namespace TrafficLightApplication
{

    public partial class Form1 : Form
    {
        private List<ICrossing> crossingsTypeA; //List of Crossings Type A
        private List<ICrossing> crossingsTypeB; //List of Crossings Type B
        System.Windows.Forms.Panel crossingPanelTypeA; // Right panel of Crossing Type A
        System.Windows.Forms.Panel crossingPanelTypeB; // Right panel of Crossing Type B

        private HelpForm helpform;
        View view;
        Project project;
        Simulator simulator;
        ICrossing crossingSeleted;
        int selectedGrid = 0;
        int tabPagesAmount = 1; // amount of grid configurations
        int selectedTabPage = 0;
        int rowCount = 3; // the size of Array to store crossings
        int columnCount = 4; // the size of  Array to store crossings

        Thread playModeThread;

        public Form1()
        {
            InitializeComponent();
            this.simulationPanel.Location = new Point(10, 20);
            crossingsTypeA = new List<ICrossing>();
            crossingsTypeB = new List<ICrossing>();
            crossingPanelTypeA = new System.Windows.Forms.Panel();
            crossingPanelTypeB = new System.Windows.Forms.Panel();
            RightMouseClick.Enabled = true;
            FindAllCrossings(); //read crossings dll files

            project = new Project("Default poject", rowCount, columnCount);
            view = new View(project, new View.DisplaySelectedCrossing(DisplaySelectedCrossingInformation), new View.DisplayStatusStripMessage(DisplayStatusStripMessage)); //for MVC pattern
            simulator = new Simulator(view, simulationProgressBar);
            simulator.changeProgressBar += UpdateSimulationProgressBar; // shows how much time needs to finish simulation

            panel_View.Controls.Add(view); // Adding view class to Form controller
            CreateCrossingPanels(crossingPanelTypeA, crossingsTypeA);
            CreateCrossingPanels(crossingPanelTypeB, crossingsTypeB);
            timer1.Interval = 3000;
        }

        /// <summary>
        /// Method reads all crossing classe from folder. After that creates classe's instances 
        /// in order to crete dynamic list of all crossings in Toolbox
        /// </summary>
        public void FindAllCrossings()
        {
            string folder = System.AppDomain.CurrentDomain.BaseDirectory;
            string[] files = System.IO.Directory.GetFiles(folder, "*.dll");

            foreach (var file in files)
            {
                try
                {
                    Assembly assembly = Assembly.LoadFile(file);
                    Type[] types = assembly.GetTypes();
                    foreach (Type type in types)
                    {
                        Type iface = type.GetInterface("ICrossing");

                        if (iface != null)
                        {
                            ICrossing crossing = (ICrossing)Activator.CreateInstance(type);

                            string crossingNamespace = crossing.GetType().Namespace;
                            if (crossingNamespace.Equals("Crossing.TypeA"))
                                crossingsTypeA.Add(crossing);
                            else if (crossingNamespace.Equals("Crossing.TypeB"))
                                crossingsTypeB.Add(crossing);
                        }
                    }
                }
                catch (Exception e)
                {
                    MessageBox.Show(e.Message);
                }
            }
        }

        /// <summary>
        /// Simple method which responsilbe for drag and drop image from Toolbox
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void pb_Crossing_MouseDown(object sender, MouseEventArgs e)
        {
            ((PictureBox)sender).DoDragDrop((PictureBox)sender, DragDropEffects.Copy);
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
        /// Method is invoked through simulator event "updateSimulationStatus" when simulation duration is over. ==SimulationStatus.finished
        /// </summary>
        private void UpdateSimulaionStatus()
        {
            // To do!

        }

        #region //Load/Save project
        private void saveProjectToolStripMenuItem_Click(object sender, EventArgs e)
        {

            SaveFileDialog saveFile = new SaveFileDialog();

            if (saveFile.ShowDialog() == DialogResult.OK)
            {
                try
                {
                    BinaryFormatter formatter = new BinaryFormatter();
                    ICrossing[,] tempCrossings = project.GetSelectedGrid().GetAllCrossingsOnGrid();
                    using (Stream output = File.Create(saveFile.FileName))
                    {
                        for (int i = 0; i < tempCrossings.GetLength(0); i++)
                        {
                            for (int y = 0; y < tempCrossings.GetLength(1); y++) // 
                            {
                                if (tempCrossings[i, y] != null)
                                {
                                    formatter.Serialize(output, i); // save the position of crossing on the grid
                                    formatter.Serialize(output, y);
                                    formatter.Serialize(output, tempCrossings[i, y].Type);  //set crossing type

                                    formatter.Serialize(output, tempCrossings[i, y].ProbabilityEast);
                                    formatter.Serialize(output, tempCrossings[i, y].ProbabilityWest);
                                    formatter.Serialize(output, tempCrossings[i, y].ProbabilitySouth);
                                    formatter.Serialize(output, tempCrossings[i, y].ProbabilityNorth);

                                    foreach (var item in tempCrossings[i, y].incominglanes)
                                    {
                                        formatter.Serialize(output, item.LocalLight.DurationGreen);
                                        formatter.Serialize(output, item.BufferFromEast);
                                        formatter.Serialize(output, item.BufferFromWest);
                                        formatter.Serialize(output, item.BufferFromSouth);
                                        formatter.Serialize(output, item.BufferFromNorth);
                                    }
                                }
                            }
                        }
                    }
                }
                catch (SerializationException)
                {
                    MessageBox.Show("Cannot read file, please try again.");
                }
                catch (IOException x)
                {
                    MessageBox.Show(x.Message);
                }
            }
        }
        /// <summary>
        /// Loads project from file and updates tab in right panel
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void loadProjectToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            OpenFileDialog openFile = new OpenFileDialog();

            if (openFile.ShowDialog() == DialogResult.OK)
            {
                try
                {
                    using (Stream input = File.OpenRead(openFile.FileName))
                    {
                        BinaryFormatter bf = new BinaryFormatter();
                        project.DeleteAllCrossings();
                        if (tabPagesAmount!=1) // set default tab amount in right menu box
                        {
                            for (int i = 0; i < tabPagesAmount; i++)
                            {
                                TabPage p = (TabPage)tbGridConfiguration.GetControl(1);
                                tbGridConfiguration.TabPages.Remove(p);
                                tabPagesAmount -= 1;
                                if (i == 1)
                                {
                                    tbGridConfiguration.TabPages.Add(this.addNewTab);
                                    tabPagesAmount = 1;
                                }
                            }
                        }
                        view.ResetAllViewImages(rowCount, columnCount);

                        while (input.Position != input.Length)
                        {
                            int row = (int)bf.Deserialize(input);
                            int column = (int)bf.Deserialize(input);
                            string crossingType = (string)bf.Deserialize(input);

                            PictureBox pb = view.GetPictureBox(row, column); // get the right pictureBox from View class

                            if (crossingType == "A1")
                            {
                                foreach (var item in crossingsTypeA) // Get right Image to set to PictureBox
                                {

                                    if (item.GetType().Name == "Type" + crossingType)
                                    {
                                        pb.Image = item.GetCrossingImage();
                                        view.SetPictureBoxImage(row, column, pb.Image);
                                        break;
                                    }
                                }
                                project.AddCrossing(row, column, "Crossing.TypeA.Type" + crossingType, pb);
                                project.GetSelectedGrid().GetAllCrossingsOnGrid()[row, column].ProbabilityEast = (int)bf.Deserialize(input);
                                project.GetSelectedGrid().GetAllCrossingsOnGrid()[row, column].ProbabilityWest = (int)bf.Deserialize(input);
                                project.GetSelectedGrid().GetAllCrossingsOnGrid()[row, column].ProbabilitySouth = (int)bf.Deserialize(input);
                                project.GetSelectedGrid().GetAllCrossingsOnGrid()[row, column].ProbabilityNorth = (int)bf.Deserialize(input);

                                foreach (IncomingLane item in project.GetSelectedGrid().GetAllCrossingsOnGrid()[row, column].incominglanes)
                                {
                                    item.LocalLight.DurationGreen = (int)bf.Deserialize(input);
                                    item.BufferFromEast = (int)bf.Deserialize(input);
                                    item.BufferFromWest = (int)bf.Deserialize(input);
                                    item.BufferFromSouth = (int)bf.Deserialize(input);
                                    item.BufferFromNorth = (int)bf.Deserialize(input);
                                }
                            }
                            else if (crossingType == "A2")
                            {
                                //crossingsTypeB
                                project.AddCrossing(row, column, "Type" + crossingType, pb);
                            }
                        }
                    }
                }
                catch (SerializationException)
                {
                    MessageBox.Show("Make sure you opened the correct file,please try again.");
                }
                catch (IOException x)
                {
                    MessageBox.Show(x.Message);
                }
            }
        }
        #endregion


        /// <summary>
        /// Method is invoked by button "Stop".
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void bt_Stop_MouseClick(object sender, MouseEventArgs e)
        {
            timer1.Stop();
            bt_Stop.Image = Properties.Resources.stopButton_Common;
            simulator.simulationStatus = SimulationStatus.editting;

            DialogResult dr = MessageBox.Show("Close Simulation ?", "Traffic Simulation", MessageBoxButtons.YesNo,
                    MessageBoxIcon.Asterisk, MessageBoxDefaultButton.Button2);

            if (dr == DialogResult.Yes)
            {
                DisplayStatusStripMessage("Simulation stopped");
                simulator.simulationStatus = SimulationStatus.stopping;
                UnLockSimulation();
                this.generalCrossingsPanel.Visible = true;
                this.simulationPanel.Visible = false;
                this.tbGridConfiguration.Visible = true;
                this.bt_Pause.Enabled = false;
                this.bt_Play.Enabled = true;
                this.bt_Stop.Enabled = false;
                this.RightMouseClick.Enabled = true;
                this.bt_Play.Image = Properties.Resources.playButton_Common;
                bt_Stop.Image = Properties.Resources.stopButton_Disabled;
                bt_Pause.Image = Properties.Resources.pauseButton_Disabled;

                simulator.StopSimulation();
                project.SetDefaultSettingForAllGrids();
            }
            else
            {
                if (this.bt_Pause.Enabled != false) // occrus when while Pause mode the Stop button was clicked
                {
                    simulator.simulationStatus = SimulationStatus.running;
                    UnLockSimulation();
                }
            }
        }

        /// <summary>
        /// Main method which starts simulation. It has two different modes: 1) Initial situation when simulation runned first time
        /// (simulationStatus == running)
        /// 2) simulationStatus == edition -> the second one is used when user wants to pause simulation to make 
        /// not limited changes.
        /// current situation.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        /// 
        private void bt_Play_MouseClick(object sender, MouseEventArgs e)
        {
            this.bt_Play.Image = Properties.Resources.playButton_Common;
            if (project.IsGridEmpty())
            {
                timer1.Start();
                this.bt_Play.Image = Properties.Resources.playButton_Disabled;
                bt_Stop.Image = Properties.Resources.stopButton_Common;
                bt_Pause.Image = Properties.Resources.pauseButton_Common;
                this.generalCrossingsPanel.Visible = false;
                this.tbGridConfiguration.Visible = false;
                this.simulationPanel.Visible = true;
                this.bt_Play.Enabled = false;
                this.bt_Pause.Enabled = true;
                this.bt_Stop.Enabled = true;
                this.RightMouseClick.Enabled = false;
                DisplayStatusStripMessage("Simulation runned");

                SubmitMaxCarsAllowed();

                if (simulator.simulationStatus != SimulationStatus.editting)  // simulationStatus == running
                {
                    decimal simulationDuaration = this.numericDuration.Value;
                    decimal simulationSpeed = this.numericSpeed.Value;
                    simulationProgressBar.Maximum = (int)simulationDuaration * 600; // I am not sure about this number, should be one minut

                    simulator.simulationStatus = SimulationStatus.running;
                    if (playModeThread == null || !playModeThread.IsAlive)
                    {
                        playModeThread = new Thread(() => simulator.StartSimulation(project.GetSelectedGrid(), simulationDuaration, simulationSpeed, new Simulator.simulationOver(SimulationOverResult)));
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
            else
                toolStripStatusLabel.Text = "Grid is Empty!";
            
        }

        /// <summary>
        /// A method to submit the value that determines how many cars are on a lane can cause traffic jam.
        /// Currently this is universal for all lanes. Still makes sense that the user sets this. Allows
        /// him/her to play around with various city setups. Rome has narrow streets; LA has wide streets.
        /// </summary>
        private void SubmitMaxCarsAllowed()
        {
            ICrossing[,] crossings = project.GetSelectedGrid().GetAllCrossingsOnGrid();
            foreach (ICrossing c in crossings)
            {
                if (c != null)
                {
                    foreach (IncomingLane l in c.incominglanes)
                    {
                        l.MaxCarsAllowed = Convert.ToInt32(this.numericUpDown1.Value);
                    }
                }
            }
        }

        public void SubmitNrCars()
        {
            if (crossingSeleted.NorthNeighbour == null)
            {
                foreach (IncomingLane i in crossingSeleted.incominglanes)
                {
                    try
                    {
                        i.BufferFromNorth = Convert.ToInt32(this.textBox8.Text);
                    }
                    catch (Exception e)
                    {
                        MessageBox.Show(e.Message + "You probably inserted something other than a number",
                                            "NR of Cars/Probability Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    }
                }
            }
            if (crossingSeleted.SouthNeighbour == null)
            {
                foreach (IncomingLane i in crossingSeleted.incominglanes)
                {
                    try
                    {
                        i.BufferFromSouth = Convert.ToInt32(this.textBox7.Text);
                    }
                    catch (Exception e)
                    {
                        MessageBox.Show(e.Message + "You probably inserted something other than a number",
                                            "NR of Cars/Probability Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    }
                }
            }
            if (crossingSeleted.EastNeighbour == null)
            {
                foreach (IncomingLane i in crossingSeleted.incominglanes)
                {
                    try
                    {
                        i.BufferFromEast = Convert.ToInt32(this.textBox6.Text);
                    }
                    catch (Exception e)
                    {

                        MessageBox.Show(e.Message + "You probably inserted something other than a number",
                                            "NR of Cars/Probability Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    }
                }
            }
            if (crossingSeleted.WestNeighbour == null)
            {
                foreach (IncomingLane i in crossingSeleted.incominglanes)
                {
                    try
                    {
                        i.BufferFromWest = Convert.ToInt32(this.textBox5.Text);
                    }
                    catch (Exception e)
                    {

                        MessageBox.Show(e.Message + "You probably inserted something other than a number",
                                            "NR of Cars/Probability Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    }
                }
            }
        }

        public void SubmitNrCarsAll()
        {
            foreach (ICrossing c in project.GetSelectedGrid().GetAllCrossingsOnGrid())
            {
                if (c != null)
                {
                    if (c.NorthNeighbour == null)
                    {
                        foreach (IncomingLane i in c.incominglanes)
                            try
                            {
                                i.BufferFromNorth = Convert.ToInt32(this.textBox8.Text);
                            }
                            catch (Exception e)
                            {

                                MessageBox.Show(e.Message + "You probably inserted something other than a number",
                                                    "NR of Cars/Probability Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                            }
                    }
                    if (c.SouthNeighbour == null)
                    {
                        foreach (IncomingLane i in c.incominglanes)
                            try
                            {
                                i.BufferFromSouth = Convert.ToInt32(this.textBox7.Text);
                            }
                            catch (Exception e)
                            {

                                MessageBox.Show(e.Message + "You probably inserted something other than a number",
                                                    "NR of Cars/Probability Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                            }
                    }
                    if (c.EastNeighbour == null)
                    {
                        foreach (IncomingLane i in c.incominglanes)
                            try
                            {
                                i.BufferFromEast = Convert.ToInt32(this.textBox6.Text);
                            }
                            catch (Exception e)
                            {

                                MessageBox.Show(e.Message + "You probably inserted something other than a number",
                                                    "NR of Cars/Probability Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                            }
                    }
                    if (c.WestNeighbour == null)
                    {
                        foreach (IncomingLane i in c.incominglanes)
                            try
                            {
                                i.BufferFromWest = Convert.ToInt32(this.textBox5.Text);
                            }
                            catch (Exception e)
                            {

                                MessageBox.Show(e.Message + "You probably inserted something other than a number",
                                                    "NR of Cars/Probability Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                            }
                    }
                }
            }
        }

        public void SubmitProbility()
        {

            try
            {
                crossingSeleted.ProbabilityNorth = Convert.ToInt32(this.tbProbNorth.Text);
                crossingSeleted.ProbabilitySouth = Convert.ToInt32(this.tbProbSouth.Text);
                crossingSeleted.ProbabilityEast = Convert.ToInt32(this.tbProbEast.Text);
                crossingSeleted.ProbabilityWest = Convert.ToInt32(this.tbProbWest.Text);
            }
            catch (Exception e)
            {

                MessageBox.Show(e.Message + "You probably inserted something other than a number",
                                    "NR of Cars/Probability Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
            }
        }

        public void SubmitProbilityAll()
        {
            foreach (ICrossing c in project.GetSelectedGrid().GetAllCrossingsOnGrid())
            {
                if (c != null)
                {
                        try
                        {
                            c.ProbabilityNorth = Convert.ToInt32(this.tbProbNorth.Text);
                            c.ProbabilitySouth = Convert.ToInt32(this.tbProbSouth.Text);
                            c.ProbabilityEast = Convert.ToInt32(this.tbProbEast.Text);
                            c.ProbabilityWest = Convert.ToInt32(this.tbProbWest.Text);
                        }
                        catch (Exception e)
                        {

                            MessageBox.Show(e.Message + "You probably inserted something other than a number",
                                                "NR of Cars/Probability Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                        }
                }

            }

        }

        public void SubmitTrafficLightDuration()
        {
            if (crossingSeleted != null)
            {
                try
                {
                    crossingSeleted.incominglanes[0].LocalLight.DurationGreen = Convert.ToInt32(this.textBox4.Text);
                    crossingSeleted.incominglanes[1].LocalLight.DurationGreen = Convert.ToInt32(this.textBox3.Text);
                    crossingSeleted.incominglanes[2].LocalLight.DurationGreen = Convert.ToInt32(this.textBox2.Text);
                    crossingSeleted.incominglanes[3].LocalLight.DurationGreen = Convert.ToInt32(this.textBox1.Text);
                }
                catch (Exception fe)
                {
                    MessageBox.Show(fe.Message + "You probably inserted something other than a number",
                        "Traffic Light Duration Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                }
            }
        }

        public void SubmitTrafficLightDurationAll()
        {
            foreach (ICrossing c in project.GetSelectedGrid().GetAllCrossingsOnGrid())
            {
                if (c != null)
                {
                    try
                    {
                        
                            c.incominglanes[0].LocalLight.DurationGreen = Convert.ToInt32(this.textBox4.Text);
                            c.incominglanes[1].LocalLight.DurationGreen = Convert.ToInt32(this.textBox3.Text);
                            c.incominglanes[2].LocalLight.DurationGreen = Convert.ToInt32(this.textBox2.Text);
                            c.incominglanes[3].LocalLight.DurationGreen = Convert.ToInt32(this.textBox1.Text);
                      
                       
                    }
                    catch (Exception fe)
                    {
                        MessageBox.Show(fe.Message + "You probably inserted something other than a number", "Traffic Light Duration Error", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
                    }
                }
            }
        }
        /// <summary>
        /// Temporary suspends simulation to do limited changes in grid
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void bt_Pause_MouseClick(object sender, MouseEventArgs e)
        {
            timer1.Stop();
            simulator.simulationStatus = SimulationStatus.editting;
            this.simulationPanel.Visible = true;
            this.tbGridConfiguration.Visible = true;
            this.bt_Pause.Enabled = false;
            this.bt_Play.Enabled = true;
            this.bt_Stop.Enabled = true;
            this.bt_Play.Image = Properties.Resources.playButton_Common;
            bt_Stop.Image = Properties.Resources.stopButton_Common;
            bt_Pause.Image = Properties.Resources.pauseButton_Disabled;
            DisplayStatusStripMessage("Simulation paused");
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

        #region //Left panelButtons handler
        private void lbButtonCrossingType_A_MouseClick(object sender, MouseEventArgs e)
        {
            this.lbButtonCrossingType_B.BackColor = System.Drawing.SystemColors.Control;
            this.lbButtonCrossingType_A.BackColor = System.Drawing.SystemColors.ActiveCaption;

            this.crossingPanelTypeB.Visible = false;
            this.lbButtonCrossingType_B.Location = new System.Drawing.Point(lbButtonCrossingType_A.Location.X, crossingPanelTypeA.Height + 60);
            this.crossingPanelTypeA.Location = new System.Drawing.Point(lbButtonCrossingType_A.Location.X, lbButtonCrossingType_A.Location.Y + 45);
            this.crossingPanelTypeA.Visible = true;

        }

        private void lbButtonCrossingType_A_MouseDown(object sender, MouseEventArgs e)
        {

            this.lbButtonCrossingType_A.BackColor = System.Drawing.SystemColors.ControlDark;
        }

        private void lbButtonCrossingType_A_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            this.lbButtonCrossingType_A.BackColor = System.Drawing.SystemColors.ActiveCaption;
        }


        /// This method should be tested, because I hade one exception in this.CrossingsPanel.Visible = true; "The Object is used somwhere else"
        private void lbButtonCrossingType_B_MouseClick(object sender, MouseEventArgs e)
        {
            this.lbButtonCrossingType_A.BackColor = System.Drawing.SystemColors.Control; // change button A Color
            this.lbButtonCrossingType_B.BackColor = System.Drawing.SystemColors.ActiveCaption; // change button B Color

            this.crossingPanelTypeA.Visible = false;
            this.lbButtonCrossingType_B.Location = new System.Drawing.Point(lbButtonCrossingType_B.Location.X, lbButtonCrossingType_A.Location.Y + 45);
            this.crossingPanelTypeB.Location = new System.Drawing.Point(lbButtonCrossingType_A.Location.X, lbButtonCrossingType_B.Location.Y + 45);
            this.crossingPanelTypeB.Visible = true;
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

        private void Form1_FormClosing(object sender, FormClosingEventArgs e)
        {
            if (playModeThread != null) // if thread isn't created
            {
                playModeThread.Abort();
            }
        }

        /// <summary>
        /// Method controls simulation behaviour. Is invoked when Simulation mode buttons(Pause,Play,Stop) clicked
        /// </summary>
        /// 
        private void UnLockSimulation()
        {
            lock (simulator.threadLocker)
            {
                Monitor.Pulse(simulator.threadLocker);
            }
        }

        /// <summary>
        /// Dynamically add crossings to left crossing's panel
        /// </summary>
        private void CreateCrossingPanels(System.Windows.Forms.Panel crossingPanelType, List<ICrossing> crossingType)
        {
            crossingPanelType.BackColor = System.Drawing.SystemColors.HighlightText;
            crossingPanelType.Location = new System.Drawing.Point(13, 330);
            crossingPanelType.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
            crossingPanelType.Name = "CrossingsPanel";
            crossingPanelType.Size = new System.Drawing.Size(180, 80);
            crossingPanelType.TabIndex = 0;
            crossingPanelType.Visible = false;
            generalCrossingsPanel.Controls.Add(crossingPanelType);

            int X = 5; //x-coordinate
            int Y = 5; //y-coordinate

            foreach (ICrossing item in crossingType)
            {
                PictureBox pb = new PictureBox();
                pb.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
                pb.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
                pb.Name = item.GetType().ToString(); // This line helps to create right class after adding to view.
                pb.Size = new System.Drawing.Size(70, 70);
                pb.MouseDown += new System.Windows.Forms.MouseEventHandler(this.pb_Crossing_MouseDown);
                pb.Image = item.GetCrossingImage();
                pb.Location = new System.Drawing.Point(X, Y);
                X += 80;
                if (X > 166)
                {
                    X = 5;
                    Y += 80;
                    crossingPanelType.Size = new System.Drawing.Size(180, crossingPanelType.Size.Height + Y);
                }
                crossingPanelType.Controls.Add(pb);
            }
        }

        /// <summary>
        /// After selekting any crossing on View information about this crossing will be displayed on right panel
        /// </summary>
        /// <param name="crossing"></param>
        /// <param name="row"></param>
        /// <param name="column"></param>
        private void DisplaySelectedCrossingInformation(Image crossing, int row, int column)
        {
            crossingSeleted = project.GetSelectedGrid().GetCrossing(row, column);
            pbCrossingInfo.Image = crossing;
            int[] i = new int[3];
            int ii = i[1];
            if (crossing != null)
            {
                pbCrossingInfo.Image = crossing;

                int crossingType = 0;

                foreach (IncomingLane incom in crossingSeleted.incominglanes)
                {
                    
                    if (crossingSeleted.NorthNeighbour != null)
                    {
                        this.textBox8.Enabled = false;
                        this.tbProbNorth.Enabled = false;
                    }
                    else
                    {
                        this.textBox8.Text = incom.BufferFromNorthHistory.ToString();
                        this.tbProbNorth.Text = incom.ProbNorth.ToString();
                        this.textBox8.Enabled = true;
                        this.tbProbNorth.Enabled = true;
                    }

                    if (crossingSeleted.SouthNeighbour != null)
                    {

                        this.textBox7.Enabled = false;
                        this.tbProbSouth.Enabled = false;
                    }
                    else
                    {
                        this.textBox7.Text = incom.BufferFromSouthHistory.ToString();
                        this.tbProbSouth.Text = incom.ProbSouth.ToString();
                        this.textBox7.Enabled = true;
                        this.tbProbSouth.Enabled = true;
                    }
                    if (crossingSeleted.EastNeighbour != null)
                    {
                        this.textBox6.Enabled = false;
                        this.tbProbEast.Enabled = false;
                    }
                    else
                    {
                        this.textBox6.Text = incom.BufferFromEastHistory.ToString();
                        this.tbProbEast.Text = incom.ProbEast.ToString();
                        this.textBox6.Enabled = true;
                        this.tbProbEast.Enabled = true;
                    }

                    if (crossingSeleted.WestNeighbour != null)
                    {
                        this.textBox5.Enabled = false;
                        this.tbProbWest.Enabled = false;

                    }
                    else
                    {
                        this.textBox5.Text = incom.BufferFromWestHistory.ToString();
                        this.tbProbWest.Text = incom.ProbWest.ToString();
                        this.textBox5.Enabled = true;
                        this.tbProbWest.Enabled = true;
                    }
                    switch (crossingType)
                    {
                        case 0:
                            this.textBox4.Text = crossingSeleted.incominglanes[crossingType].LocalLight.DurationGreen.ToString();
                            break;
                        case 1:
                            this.textBox3.Text = crossingSeleted.incominglanes[crossingType].LocalLight.DurationGreen.ToString();
                            break;
                        case 2:
                            this.textBox2.Text = crossingSeleted.incominglanes[crossingType].LocalLight.DurationGreen.ToString();
                            break;
                        case 3:
                            this.textBox1.Text = crossingSeleted.incominglanes[crossingType].LocalLight.DurationGreen.ToString();
                            break;
                    }
                    crossingType++;
                }
            }


            //if the selected crossing has a neighbour, the textbox is disabled
            this.lbCN.Text = "Row: " + row.ToString() + ",Column: " + column.ToString();
        }

        private void helpToolStripMenuItem_Click(object sender, EventArgs e)
        {
            helpform = new HelpForm();
            helpform.Show();
        }

        private void DisplayStatusStripMessage(string message)
        {
            this.toolStripStatusLabel.Text = message;
        }

        private void moveToolStripMenuItem_Click_1(object sender, EventArgs e)
        {
            view.MovePictureBox();
        }

        private void removeToolStripMenuItem_Click(object sender, EventArgs e)
        {
            if (view.RemoveSelectedCrossing())
                this.toolStripStatusLabel.Text = "Crossing removed";
            else
                this.toolStripStatusLabel.Text = "Crossing is not removed";
        }

        private void btSubmit_Click(object sender, EventArgs e)
        {
            if (crossingSeleted != null)
            {
                this.SubmitNrCars();
                this.SubmitProbility();
                this.SubmitTrafficLightDuration();
                foreach (IncomingLane incomelane in crossingSeleted.incominglanes)
                {
                    incomelane.BufferFromNorthHistory = incomelane.BufferFromNorth;
                    incomelane.BufferFromSouthHistory = incomelane.BufferFromSouth;
                    incomelane.BufferFromEastHistory = incomelane.BufferFromEast;
                    incomelane.BufferFromWestHistory = incomelane.BufferFromWest;
                }
            }
            else
            {
                MessageBox.Show("You haven't choose a Crossing yet.");
            }

        }

        /// <summary>
        /// This method TotalCars() will calculate the total number s of cars on the the grid/Screen.
        /// I have created New Lable in memory. ANd when the cars seems on comes on the grid then, 
        /// it will store cars in it and display on the screen.
        /// Here i am going to display crossing[,] array, lane ID and total numbers of cars per lane. 
        /// And also the total number of cars on each crossing.
        /// </summary>
        public void CarsPerCrossing()
        {
            int[] CarsPerCrossing = new int[12];
            this.LanesPanel.Controls.Clear();
            int crossing = 0;
            int linesBreak = 0;
            foreach (ICrossing Icrossings in simulator.GetAllCrossingsOnGrid())
            {
                if (Icrossings != null)
                {
                    foreach (Lane L in Icrossings.Lanes)
                    {
                        Label label = new Label();
                        label.Location = new System.Drawing.Point(0, linesBreak + 17);
                        label.AutoSize = true;
                        label.Text = "Crossing : " + crossing.ToString() + ", Lane ID: " + L.LaneID.ToString() + ", Cars : " + L.countCars().ToString();
                        LanesPanel.Controls.Add(label);

                        linesBreak = label.Location.Y;

                        if (crossing == 0)
                        {
                            CarsPerCrossing[0] += L.countCars();
                            this.lb1.Text = Convert.ToString(CarsPerCrossing[0]);
                        }
                        else if (crossing == 1)
                        {
                            CarsPerCrossing[1] += L.countCars();
                            this.lb2.Text = Convert.ToString(CarsPerCrossing[1]);

                        }
                        else if (crossing == 2)
                        {
                            CarsPerCrossing[2] += L.countCars();
                            this.lb3.Text = Convert.ToString(CarsPerCrossing[2]);
                        }
                        else if (crossing == 3)
                        {
                            CarsPerCrossing[3] += L.countCars();
                            this.lb4.Text = Convert.ToString(CarsPerCrossing[3]);
                        }
                        else if (crossing == 4)
                        {
                            CarsPerCrossing[4] += L.countCars();
                            this.lb5.Text = Convert.ToString(CarsPerCrossing[4]);
                        }
                        else if (crossing == 5)
                        {
                            CarsPerCrossing[5] += L.countCars();
                            this.lb6.Text = Convert.ToString(CarsPerCrossing[5]);
                        }
                        else if (crossing == 6)
                        {
                            CarsPerCrossing[6] += L.countCars();
                            this.lb7.Text = Convert.ToString(CarsPerCrossing[6]);
                        }
                        else if (crossing == 7)
                        {
                            CarsPerCrossing[7] += L.countCars();
                            this.lb8.Text = Convert.ToString(CarsPerCrossing[7]);
                        }
                        else if (crossing == 8)
                        {
                            CarsPerCrossing[8] += L.countCars();
                            this.lb9.Text = Convert.ToString(CarsPerCrossing[8]);
                        }
                        else if (crossing == 9)
                        {
                            CarsPerCrossing[10] += L.countCars();
                            this.lb10.Text = Convert.ToString(CarsPerCrossing[9]);
                        }
                        else if (crossing == 10)
                        {
                            CarsPerCrossing[0] += L.countCars();
                            this.lb11.Text = Convert.ToString(CarsPerCrossing[10]);
                        }
                        else if (crossing == 11)
                        {
                            CarsPerCrossing[11] += L.countCars();
                            this.lb12.Text = Convert.ToString(CarsPerCrossing[11]);
                        }
                    }
                    crossing++;
                }
            }
        }

        /// <summary>
        /// This method TotalCars() will calculate the total number s of cars on the the grid/Screen.
        /// I have created New Lable in memory. ANd when the cars seems on comes on the grid then, 
        /// it will store cars in it and display on the screen.
        /// And at last the method will calculate the total numbers cars on the grid.
        /// </summary>
        private void TotalCars()
        {
            this.LanesPanel.Controls.Clear();
            int crossing = 0;
            int linesBreak = 0;
            int TotalCars = 0;
            foreach (ICrossing Icrossings in simulator.GetAllCrossingsOnGrid())
            {
                if (Icrossings != null)
                {
                    foreach (Lane L in Icrossings.Lanes)
                    {
                        Label label = new Label();
                        Label totalCars = new Label();
                        label.Location = new System.Drawing.Point(0, linesBreak + 17);
                        label.AutoSize = true;
                        label.Text = "Crossing : " + crossing.ToString() + " LaneID: " + L.LaneID.ToString() + " Cars : " + L.countCars().ToString();
                        totalCars.Text = L.countCars().ToString();
                        LanesPanel.Controls.Add(label);

                        TotalCars += Convert.ToInt32(totalCars.Text);

                        linesBreak = label.Location.Y;
                    }

                    this.lbTotalCarsOnAllLanes.Text = Convert.ToString(TotalCars);
                    crossing++;
                }
            }
        }

        private void btnPrintPreview_Click_1(object sender, EventArgs e)
        {
            PrintPreviewDialog preview = new PrintPreviewDialog();
            PrintDocument document = new PrintDocument();
            preview.Document = document;
            document.PrintPage += new PrintPageEventHandler(printScreen);
            preview.ShowDialog(this);
        }

        /// <summary>
        /// This methods printScreen() will helps us to fill the data in Print Preview section.
        /// Like; "Numbers of Cars on Crossing-1"... in first Column of X-axis, and amount value 
        /// as 'lb1.Text'....., in the second Column of X-axis.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        public void printScreen(object sender, PrintPageEventArgs e)
        {
            Graphics g = e.Graphics;

            int tableX = e.MarginBounds.X;
            int tableWidth = e.MarginBounds.X + e.MarginBounds.Width - tableX - 20;
            int firstColumnX = tableX + 2;
            int secondColumnX = tableX + (tableWidth / 2) + 5;
            int tableY = e.MarginBounds.Y;

            tableY = PrintTableRow(g, tableX, tableWidth, firstColumnX,
                secondColumnX, tableY, "Numbers of Cars on Crossing-1", lb1.Text);
            tableY = PrintTableRow(g, tableX, tableWidth, firstColumnX,
                 secondColumnX, tableY, "Numbers of Cars on Crossing-2", lb2.Text);
            tableY = PrintTableRow(g, tableX, tableWidth, firstColumnX,
                 secondColumnX, tableY, "Numbers of Cars on Crossing-3", lb3.Text);
            tableY = PrintTableRow(g, tableX, tableWidth, firstColumnX,
                  secondColumnX, tableY, "Numbers of Cars on Crossing-4", lb4.Text);
            tableY = PrintTableRow(g, tableX, tableWidth, firstColumnX,
                 secondColumnX, tableY, "Numbers of Cars on Crossing-5", lb5.Text);
            tableY = PrintTableRow(g, tableX, tableWidth, firstColumnX,
                 secondColumnX, tableY, "Numbers of Cars on Crossing-6", lb6.Text);
            tableY = PrintTableRow(g, tableX, tableWidth, firstColumnX,
                 secondColumnX, tableY, "Numbers of Cars on Crossing-7", lb7.Text);
            tableY = PrintTableRow(g, tableX, tableWidth, firstColumnX,
                secondColumnX, tableY, "Numbers of Cars on Crossing-8", lb8.Text);
            tableY = PrintTableRow(g, tableX, tableWidth, firstColumnX,
                secondColumnX, tableY, "Numbers of Cars on Crossing-9", lb9.Text);
            tableY = PrintTableRow(g, tableX, tableWidth, firstColumnX,
                secondColumnX, tableY, "Numbers of Cars on Crossing-10", lb10.Text);
            tableY = PrintTableRow(g, tableX, tableWidth, firstColumnX,
                secondColumnX, tableY, "Numbers of Cars on Crossing-11", lb11.Text);
            tableY = PrintTableRow(g, tableX, tableWidth, firstColumnX,
                secondColumnX, tableY, "Numbers of Cars on Crossing-12", lb12.Text);
            tableY = PrintTableRow(g, tableX, tableWidth, firstColumnX,
                secondColumnX, tableY, "", "");
            tableY = PrintTableRow(g, tableX, tableWidth, firstColumnX,
                secondColumnX, tableY, "Numbers of Cars on Screen ", this.lbTotalCarsOnAllLanes.Text);

            g.DrawRectangle(Pens.Black, tableX, e.MarginBounds.Y,
                 tableWidth, tableY - e.MarginBounds.Y);
            g.DrawLine(Pens.Black, secondColumnX, e.MarginBounds.Y,
                 secondColumnX, tableY);
        }

        /// <summary>
        /// THis methods is responsible for Prient Preview section.
        /// This methods PrintTableRow() wil draw all the row on the table.
        /// And this methods has several perameter. 
        /// </summary>
        /// <param name="printGraphics">This is graphic</param>
        /// <param name="tableX">int veriable on the table at X-axis</param>
        /// <param name="tableWidth">int veriable for the width of the table</param>
        /// <param name="firstColumnX">int veriable for the first column at X-axis</param>
        /// <param name="secondColumnX">int veriable for the second column at X-axis</param>
        /// <param name="tableY">int veriable on the table at Y-axis</param>
        /// <param name="firstColumn">string veriable on the first column 'For name of the crossing'.</param>
        /// <param name="secondColumn">string veriable on the second column 'For numbers of cars'.</param>
        /// <returns>returns the whole table at Y-axis </returns>
        private int PrintTableRow(Graphics printGraphics, int tableX,
               int tableWidth, int firstColumnX, int secondColumnX,
               int tableY, string firstColumn, string secondColumn)
        {
            Font arial12 = new Font("Arial", 12);
            Size stringSize = Size.Ceiling(printGraphics.MeasureString(firstColumn, arial12));
            tableY += 6;
            printGraphics.DrawString(firstColumn, arial12, Brushes.Black,
                            firstColumnX, tableY);
            printGraphics.DrawString(secondColumn, arial12, Brushes.Black,
                            secondColumnX, tableY);
            tableY += (int)stringSize.Height + 2;
            printGraphics.DrawLine(Pens.Black, tableX, tableY, tableX + tableWidth, tableY);
            arial12.Dispose();
            return tableY;
        }

        /// <summary>
        /// This methods  Chart() convert the total nummbers of cars per crossing and total numbers of cars on the grid.
        /// </summary>
        public void Chart()
        {
            this.chart1.Series.Clear();
            this.chart1.ResetAutoValues();

            int c1 = Convert.ToInt32(this.lb1.Text);
            int c2 = Convert.ToInt32(this.lb2.Text);
            int c3 = Convert.ToInt32(this.lb3.Text);
            int c4 = Convert.ToInt32(this.lb4.Text);
            int c5 = Convert.ToInt32(this.lb5.Text);
            int c6 = Convert.ToInt32(this.lb6.Text);
            int c7 = Convert.ToInt32(this.lb7.Text);
            int c8 = Convert.ToInt32(this.lb8.Text);
            int c9 = Convert.ToInt32(this.lb9.Text);
            int c10 = Convert.ToInt32(this.lb10.Text);
            int c11 = Convert.ToInt32(this.lb11.Text);
            int c12 = Convert.ToInt32(this.lb12.Text);
            int total = Convert.ToInt32(this.lbTotalCarsOnAllLanes.Text);

            string[] crossingArray = { "C-1", "C-2", "C-3", "C-4", "C-5", "C-6", "C-7", "C-8", "C-9", "C-10", "C-11", "C-12", "Total" };
            int[] carsNumberArray = { c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, total };

            // Set Color Palette.
            this.chart1.Palette = ChartColorPalette.SeaGreen;

            // Add crossings.
            for (int i = 0; i < crossingArray.Length; i++)
            {
                // Add crossings.
                Series series = this.chart1.Series.Add(crossingArray[i]);

                // Add numbers of cars.
                series.Points.Add(carsNumberArray[i]);
            }
        }

        private void btSubmitAll_Click(object sender, EventArgs e)
        {
            this.SubmitNrCarsAll();
            this.SubmitProbilityAll();
            this.SubmitTrafficLightDurationAll();
            foreach (ICrossing c in project.GetSelectedGrid().GetAllCrossingsOnGrid()) 
            {
                if (c != null)
                {
                    foreach (IncomingLane incomelane in c.incominglanes)
                    {
                        incomelane.BufferFromNorthHistory = incomelane.BufferFromNorth;
                        incomelane.BufferFromSouthHistory = incomelane.BufferFromSouth;
                        incomelane.BufferFromEastHistory = incomelane.BufferFromEast;
                        incomelane.BufferFromWestHistory = incomelane.BufferFromWest;
                        incomelane.ProbNorth = c.ProbabilityNorth;
                        incomelane.ProbSouth = c.ProbabilitySouth;
                        incomelane.ProbEast = c.ProbabilityEast;
                        incomelane.ProbWest = c.ProbabilityWest;
                    }
                }
            }

        }

        /// <summary>
        /// This method timer1_Tick(), will tick in every after 3 seconds, only if there are grids.
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void timer1_Tick(object sender, EventArgs e)
        {
            if (this.simulator.grid != null)
            {
                this.Chart();
                this.TotalCars();
                this.CarsPerCrossing();
            }
        }

        private void btSubmit2_Click(object sender, EventArgs e)
        {

            this.SubmitNrCars();
            this.SubmitProbility();
            this.SubmitTrafficLightDuration();
            foreach (IncomingLane incomelane in crossingSeleted.incominglanes)
            {
                incomelane.BufferFromNorthHistory = incomelane.BufferFromNorth;
                incomelane.BufferFromSouthHistory = incomelane.BufferFromSouth;
                incomelane.BufferFromEastHistory = incomelane.BufferFromEast;
                incomelane.BufferFromWestHistory = incomelane.BufferFromWest;
            }
        }

        private void btSubmitAll2_Click(object sender, EventArgs e)
        {
            this.SubmitNrCarsAll();
            this.SubmitProbilityAll();
            this.SubmitTrafficLightDurationAll();
            foreach (ICrossing c in project.GetSelectedGrid().GetAllCrossingsOnGrid())
            {
                if (c != null)
                {
                    foreach (IncomingLane incomelane in c.incominglanes)
                    {
                        incomelane.BufferFromNorthHistory = incomelane.BufferFromNorth;
                        incomelane.BufferFromSouthHistory = incomelane.BufferFromSouth;
                        incomelane.BufferFromEastHistory = incomelane.BufferFromEast;
                        incomelane.BufferFromWestHistory = incomelane.BufferFromWest;
                        incomelane.ProbNorth = c.ProbabilityNorth;
                        incomelane.ProbSouth = c.ProbabilitySouth;
                        incomelane.ProbEast = c.ProbabilityEast;
                        incomelane.ProbWest = c.ProbabilityWest;
                    }
                }
            }
        }

        private void btSubmit3_Click(object sender, EventArgs e)
        {
            this.SubmitNrCars();
            this.SubmitProbility();
            this.SubmitTrafficLightDuration();
            foreach (IncomingLane incomelane in crossingSeleted.incominglanes)
            {
                incomelane.BufferFromNorthHistory = incomelane.BufferFromNorth;
                incomelane.BufferFromSouthHistory = incomelane.BufferFromSouth;
                incomelane.BufferFromEastHistory = incomelane.BufferFromEast;
                incomelane.BufferFromWestHistory = incomelane.BufferFromWest;
            }
        }

        private void tbSubmitAll3_Click(object sender, EventArgs e)
        {
            this.SubmitNrCarsAll();
            this.SubmitProbilityAll();
            this.SubmitTrafficLightDurationAll();
            foreach (ICrossing c in project.GetSelectedGrid().GetAllCrossingsOnGrid())
            {
                if (c != null)
                {
                    foreach (IncomingLane incomelane in c.incominglanes)
                    {
                        incomelane.BufferFromNorthHistory = incomelane.BufferFromNorth;
                        incomelane.BufferFromSouthHistory = incomelane.BufferFromSouth;
                        incomelane.BufferFromEastHistory = incomelane.BufferFromEast;
                        incomelane.BufferFromWestHistory = incomelane.BufferFromWest;
                        incomelane.ProbNorth = c.ProbabilityNorth;
                        incomelane.ProbSouth = c.ProbabilitySouth;
                        incomelane.ProbEast = c.ProbabilityEast;
                        incomelane.ProbWest = c.ProbabilityWest;
                    }
                }
            }
        }

        private void exitToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void tbGridConfiguration_Selected(object sender, TabControlEventArgs e)
        {
            project.selectedGrid = tbGridConfiguration.SelectedIndex;
            TabPage current_tab = tbGridConfiguration.SelectedTab;

            string tagIndex = (string)tbGridConfiguration.SelectedTab.Tag;
            if (tagIndex != null && tagIndex.Equals("+") && tabPagesAmount <= 4) // If I want to add new tab
            {
                tabPagesAmount++;
                TabPage newPage = new TabPage();
                tbGridConfiguration.TabPages.Remove(tbGridConfiguration.SelectedTab);  // after removing occurs recursive implementation of SelectedIndexChanged 
                tbGridConfiguration.TabPages.Add(newPage);
                newPage.Text = "(" + tabPagesAmount.ToString() + ")";
                project.AddGrid(tabPagesAmount - 1);
                if (tabPagesAmount != 3)
                {
                    tbGridConfiguration.TabPages.Add(current_tab);
                }
            }
            else if (tbGridConfiguration.SelectedTab != null)
            {
                TabPage newPage = tbGridConfiguration.SelectedTab;

                newPage.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(255)))), ((int)(((byte)(128)))));

                newPage.Controls.Add(this.btSubmitAll);
                newPage.Controls.Add(this.btSubmit);
                newPage.Controls.Add(this.tbProbWest);
                newPage.Controls.Add(this.tbProbEast);
                newPage.Controls.Add(this.tbProbSouth);
                newPage.Controls.Add(this.tbProbNorth);
                newPage.Controls.Add(this.textBox1);
                newPage.Controls.Add(this.textBox2);
                newPage.Controls.Add(this.textBox3);
                newPage.Controls.Add(this.textBox4);
                newPage.Controls.Add(this.textBox5);
                newPage.Controls.Add(this.textBox6);
                newPage.Controls.Add(this.textBox7);
                newPage.Controls.Add(this.textBox8);

                newPage.Controls.Add(this.label38);
                newPage.Controls.Add(this.label5);
                newPage.Controls.Add(this.label6);
                newPage.Controls.Add(this.label7);
                newPage.Controls.Add(this.label8);
                newPage.Controls.Add(this.label9);
                newPage.Controls.Add(this.label10);
                newPage.Controls.Add(this.label11);
                newPage.Controls.Add(this.label12);
                newPage.Controls.Add(this.label13);
                newPage.Controls.Add(this.label14);
                newPage.ImeMode = System.Windows.Forms.ImeMode.On;
                newPage.Location = new System.Drawing.Point(4, 25);
                newPage.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
                newPage.Name = "tabPage1";
                newPage.Padding = new System.Windows.Forms.Padding(3, 2, 3, 2);
                newPage.Size = new System.Drawing.Size(389, 371);
                newPage.TabIndex = 0;
            }
        }

        private void helpToolStripMenuItem_Click_2(object sender, EventArgs e)
        {
            helpform = new HelpForm();
            helpform.Show();
        }

        private void saveResultToolStripMenuItem_Click(object sender, EventArgs e)
        {
            SaveFileDialog saveResult = new SaveFileDialog();

            if (saveResult.ShowDialog() == DialogResult.OK)
            {
                FileStream fs = null;


                fs = new FileStream(saveResult.FileName + ".txt", FileMode.Create, FileAccess.Write);
                StreamWriter m_streamWriter = new StreamWriter(fs);
                m_streamWriter.Flush();
                m_streamWriter.BaseStream.Seek(0, SeekOrigin.Begin);

                string text = "Numbers of Cars on Crossing-1" + "\r\n" + lb1.Text + "\r\n" + "Numbers of Cars on Crossing-2" + "\r\n" + lb2.Text + "\r\n" + "Numbers of Cars on Crossing-3" + "\r\n" + lb3.Text
                    + "\r\n" + "Numbers of Cars on Crossing-4" + "\r\n" + lb4.Text + "\r\n" + "Numbers of Cars on Crossing-5" + "\r\n" + lb5.Text + "\r\n" + "Numbers of Cars on Crossing-6" + "\r\n" + lb6.Text
                    + "\r\n" + "Numbers of Cars on Crossing-7" + "\r\n" + lb7.Text + "\r\n" + "Numbers of Cars on Crossing-8" + "\r\n" + lb8.Text + "\r\n" + "Numbers of Cars on Crossing-9" + "\r\n" + lb9.Text
                    + "\r\n" + "Numbers of Cars on Crossing-10" + "\r\n" + lb10.Text + "\r\n" + "Numbers of Cars on Crossing-11" + "\r\n" + lb11.Text + "\r\n" + "Numbers of Cars on Crossing-12" + "\r\n" + lb12.Text
                    + "\r\n" + "Numbers of Cars on Screen " + "\r\n" + this.lbTotalCarsOnAllLanes.Text;
                m_streamWriter.Write(text);

                m_streamWriter.Close();
                MessageBox.Show("Your Result has been saved.");
            }
        }
        /// <summary>
        /// Start new project in Main Menu
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void startNewProjectToolStripMenuItem_Click(object sender, EventArgs e)
        {
            project.DeleteAllCrossings();
            view.ResetAllViewImages(rowCount, columnCount);
            if (tabPagesAmount != 1) // set default tab amount in right menu box
            {
                for (int i = 0; i < tabPagesAmount; i++)
                {
                    TabPage p = (TabPage)tbGridConfiguration.GetControl(1);
                    tbGridConfiguration.TabPages.Remove(p);
                    tabPagesAmount -= 1;
                    if (i == 1)
                    {
                        tbGridConfiguration.TabPages.Add(this.addNewTab);
                        tabPagesAmount = 1;
                    }
                }
            }
        }
        /// <summary>
        /// Used by simulator when simulation time is over.
        /// </summary>
        public void SimulationOverResult()
        {
            project.SetDefaultSettingForAllGrids();
        }
    }
}
