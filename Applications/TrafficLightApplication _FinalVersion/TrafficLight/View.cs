using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Threading;
using System.Windows.Forms;
using System.Reflection;

namespace TrafficLightApplication
{
    public partial class View : UserControl
    {

        public delegate void DisplaySelectedCrossing(Image crossing, int row, int column);
        private DisplaySelectedCrossing displayCrossingInfo;

        public delegate void DisplayStatusStripMessage(string message);
        public DisplayStatusStripMessage displayStatusStripMessage;

        Project project; //To add new crossing into crossings list

        Image[,] pictureBoxesImages;

        private PictureBox selectedPictureBox;
        private Point mouseClickedPosition;  // is used to hande right mouse click to move or remove crossing
        private int moveFromRow;
        private int moveFromCell;
        private bool moveCrossing = false;

        public View(Project project, DisplaySelectedCrossing displayCrossingInfo, DisplayStatusStripMessage displayStatusStripMessage)
        {
            InitializeComponent();
            this.project = project;
            this.displayCrossingInfo += displayCrossingInfo;
            this.displayStatusStripMessage += displayStatusStripMessage;
            pictureBoxesImages = new Image[3, 4];
            SetEventHandlerForPictureBoxes();
        }
        /// <summary>
        /// Subscribing to common events handlers for all pictureboxes on View
        /// </summar
        public void SetEventHandlerForPictureBoxes()
        {
            foreach (Control control in this.Controls)
            {
                foreach (Control pictureBox in control.Controls)
                {
                    pictureBox.AllowDrop = true;
                    pictureBox.DragEnter += pictureBoxCustom_DragEnter;
                    pictureBox.DragDrop += pictureBoxCustom_DragDrop;
                    pictureBox.Click += new System.EventHandler(this.PictureBox_Click);
                    pictureBox.MouseDown += new System.Windows.Forms.MouseEventHandler(this.pb_Crossing_MouseDown);
                }
            }
        }
        
        /// <summary>
        /// Displays cars on crossings
        /// </summary>
        /// <param name="crossings"></param>
        public void drawOnCrossing(ICrossing[,] crossings)
        {
            moveCrossing = false;
                for (int i = 0; i < crossings.GetLength(0); i++)// First level of array
                {
                    for (int y = 0; y < crossings.GetLength(1); y++) // Second level of array
                    {
                        try
                        {

                        if (crossings[i, y] != null)
                        {
                            ICrossing thecrossing = crossings[i, y];

                            Bitmap original_image = new Bitmap(pictureBoxesImages[i, y]); // read picturebox images from array
                            Graphics g = Graphics.FromImage(original_image);
                            Pen p = new Pen(Color.SandyBrown, 5);
                            SolidBrush sb = new SolidBrush(Color.Aqua);
                            SolidBrush mypen = new SolidBrush(Color.Black);
                            crossings[i, y].CrossingPictureBox.Invalidate();

                           //draw the traffic lights
                            foreach (IncomingLane l in crossings[i, y].incominglanes)
                            {
                                mypen.Color = l.LocalLight.Greenlight.StatusColor;
                                g.FillRectangle(mypen, l.LocalLight.Greenlight.Location.X, l.LocalLight.Greenlight.Location.Y, 14, 14);
                                mypen.Color = l.LocalLight.Orangelight.StatusColor;
                                g.FillRectangle(mypen, l.LocalLight.Orangelight.Location.X, l.LocalLight.Orangelight.Location.Y, 14, 14);
                                mypen.Color = l.LocalLight.Redlight.StatusColor;
                                g.FillRectangle(mypen, l.LocalLight.Redlight.Location.X, l.LocalLight.Redlight.Location.Y, 14, 14);

                                //jam indication code
                                if (l.Jam == true)
                                {
                                    char direction = l.LaneID[0];
                                    Pen redPen = new Pen(Color.IndianRed, 5);
                                    if (direction == 'S')
                                    {
                                        g.DrawLine(redPen, new Point(Convert.ToInt32(crossings[i, y].BoundaryEastInner - 5), Convert.ToInt32(l.BoundaryExit)),
                                                        new Point((Convert.ToInt32(crossings[i, y].BoundaryEastInner - 5)), Convert.ToInt32(l.BoundaryEntry)));
                                    }
                                    if (direction == 'N')
                                    {
                                        g.DrawLine(redPen, new Point(Convert.ToInt32(crossings[i, y].BoundaryWestInner + 5), Convert.ToInt32(l.BoundaryExit)),
                                                        new Point((Convert.ToInt32(crossings[i, y].BoundaryWestInner + 5)), Convert.ToInt32(l.BoundaryEntry)));
                                    }
                                    if (direction == 'W')
                                    {
                                        g.DrawLine(redPen, new Point(Convert.ToInt32(l.BoundaryExit), Convert.ToInt32(crossings[i, y].BoundarySouthInner - 5)),
                                                        new Point(Convert.ToInt32(l.BoundaryEntry), Convert.ToInt32(crossings[i, y].BoundarySouthInner - 5)));
                                    }
                                    if (direction == 'E')
                                    {
                                        g.DrawLine(redPen, new Point(Convert.ToInt32(l.BoundaryExit), Convert.ToInt32(crossings[i, y].BoundaryNorthInner)),
                                                        new Point(Convert.ToInt32(l.BoundaryEntry), Convert.ToInt32(crossings[i, y].BoundaryNorthInner)));
                                    }
                                }

                                //pedestrian code

                                Rectangle pedestrian1 = new Rectangle(crossings[i, y].PedestrianLocation, new Size(5, 5));
                                Rectangle pedestrian2 = new Rectangle(crossings[i, y].PedestrianLocation2, new Size(5, 5));
                                Point temp = crossings[i, y].PedestrianLocation;
                                Point temp2 = crossings[i, y].PedestrianLocation2;
                                if (crossings[i, y].Type == "A2" && crossings[i, y].AllRed == true)
                                {
                                    g.DrawEllipse(p, pedestrian1);
                                    g.DrawEllipse(p, pedestrian2);
                                    temp.X -= 1;//the pedestrian takes 1 giant steps.
                                    temp2.X += 1;
                                    crossings[i, y].PedestrianLocation = temp; //somehow required because direct setting is not working.
                                    crossings[i, y].PedestrianLocation2 = temp2;
                                }
                                else
                                {
                                    //reset the pedestrian location for the next round.
                                    temp.X = Convert.ToInt32(crossings[i, y].BoundaryEastInner);
                                    temp2.X = Convert.ToInt32(crossings[i, y].BoundaryWestInner);
                                    crossings[i, y].PedestrianLocation = temp;
                                    crossings[i, y].PedestrianLocation2 = temp2;
                                }
                            }
                            foreach (Lane laneItem in crossings[i, y].Lanes)
                            {
                                foreach (Car carItem in laneItem.CarsOnLane)
                                {
                                    g.FillEllipse(sb, carItem.Position.X -5 , carItem.Position.Y -5 , 10, 10); // Change function. I will call . Maybe create new list and send 
                                }
                            }
                            lock (thecrossing.CrossingPictureBox)
                            {
                                thecrossing.CrossingPictureBox.Image = original_image;
                            }

                        }
                        }catch(InvalidOperationException e){
                        MessageBox.Show(e.Message);
                        }
                    }
                }
        }

        #region //Custom Crossing Adding
        private void pictureBoxCustom_DragEnter(object sender, DragEventArgs e)
        {
            e.Effect = DragDropEffects.Copy;
        }

        private void pictureBoxCustom_DragDrop(object sender, DragEventArgs e)
        {
            Point p = this.tbLayoutPanel_View.PointToClient(Control.MousePosition); // Get Mouse hover position

            PictureBox pb = (PictureBox)tbLayoutPanel_View.GetChildAtPoint(p);
            PictureBox c = (PictureBox)e.Data.GetData(e.Data.GetFormats()[0]);
            string crossingClass = c.Name;

            Control test = tbLayoutPanel_View.GetChildAtPoint(p); // Get Control object from tile at Point p
            int row = tbLayoutPanel_View.GetRow(test);  // Get tile Row Number
            int column = tbLayoutPanel_View.GetColumn(test); // Get tile Column number
            

            if (pictureBoxesImages[row, column] ==null)
            {
                pb.Image = c.Image;
                project.AddCrossing(row, column, crossingClass, pb);
                pictureBoxesImages[row, column] = pb.Image;
                displayCrossingInfo(pb.Image, row, column);
                displayStatusStripMessage("Updated");
            }
            else
            {
                //pb.Image = null;
                displayStatusStripMessage("Error, Crossing already exists");
            }
        }
        #endregion

        // General class for all Pictureboxes on View
        private void PictureBox_Click(object sender, EventArgs e)
        {
            try
            {
                Point p = this.tbLayoutPanel_View.PointToClient(Control.MousePosition);
                Control temp = tbLayoutPanel_View.GetChildAtPoint(p);
                int row = tbLayoutPanel_View.GetRow(temp);
                int column = tbLayoutPanel_View.GetColumn(temp);

                if (!moveCrossing)
                {
                    displayCrossingInfo(pictureBoxesImages[row, column], row, column);
                }
                else if (moveCrossing == true)
                {

                    if (project.MoveCrossings(moveFromRow, moveFromCell, row, column)) // if cell is occupied then return false;
                    {
                        selectedPictureBox.Image = null;
                        selectedPictureBox = null;
                        PictureBox pb = (PictureBox)tbLayoutPanel_View.GetChildAtPoint(p);
                        pb.Image = pictureBoxesImages[moveFromRow, moveFromCell];

                        for (int i = 0; i < project.GetAmountOfGrids(); i++)
                        {
                            project.GetSelectedGrid(i).GetCrossing(row, column).CrossingPictureBox = pb;
                        }

                        
                        pictureBoxesImages[row, column] = pictureBoxesImages[moveFromRow, moveFromCell];
                        pictureBoxesImages[moveFromRow, moveFromCell] = null;
                        moveCrossing = false;
                        displayStatusStripMessage("Updated");
                    }
                    else
                    {
                        displayStatusStripMessage("Cannot update crossing position, please try again");
                        moveCrossing = false;
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void pb_Crossing_MouseDown(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Right)
            {
                mouseClickedPosition = this.tbLayoutPanel_View.PointToClient(Control.MousePosition);
            }
        }

        /// <summary>
        /// Move crossing from one cell to another in tho mouse click
        /// </summary>
        /// <param name="displayStatusStripMessage"></param>
        public void MovePictureBox()
        {
            try
            {
                Control temp = tbLayoutPanel_View.GetChildAtPoint(mouseClickedPosition);
                moveFromRow = tbLayoutPanel_View.GetRow(temp);
                moveFromCell = tbLayoutPanel_View.GetColumn(temp);
                selectedPictureBox = (PictureBox)tbLayoutPanel_View.GetChildAtPoint(mouseClickedPosition);
                selectedPictureBox.Image = null;
                moveCrossing = true;
                this.displayStatusStripMessage("Crossing selected");
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        public bool RemoveSelectedCrossing()
        {
            try
            {
                Control temp = tbLayoutPanel_View.GetChildAtPoint(mouseClickedPosition);
                int row = tbLayoutPanel_View.GetRow(temp);
                int column = tbLayoutPanel_View.GetColumn(temp);
                if (project.GetSelectedGrid().DeleteCrossing(row, column))
                {
                    PictureBox pb = (PictureBox)tbLayoutPanel_View.GetChildAtPoint(mouseClickedPosition);
                    pictureBoxesImages[row, column] = null;
                    pb.Image = null;
                    return true;
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                return false;
            }
            return false;
        }

        private void View_MouseDown(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Right)
            {
                mouseClickedPosition.X = e.X;
                mouseClickedPosition.Y = e.Y;
            }
        }

        /// <summary>
        /// After loading this method will be invoked to set image to array. This array is used to draw properly changes on the screen
        /// </summary>
        /// <param name="row"></param>
        /// <param name="column"></param>
        /// <param name="img"></param>
        public void SetPictureBoxImage(int row, int column, Image img)
        {
            pictureBoxesImages[row, column] = img;
        }

        /// <summary>
        /// Method invoked while loading crossings from file. 
        /// </summary>
        /// <param name="row"></param>
        /// <param name="column"></param>
        /// <returns></returns>
        public PictureBox GetPictureBox(int row, int column)
        {
            return (PictureBox)this.tbLayoutPanel_View.GetControlFromPosition(column, row);
        }

        public void ResetAllViewImages(int row, int column)
        {
            pictureBoxesImages = new Image[row, column];
            for (int i = 0; i < row; i++)
            {
                for (int y = 0; y < column; y++)
                {
                    pictureBoxesImages[i, y] = null;
                    ((PictureBox)this.tbLayoutPanel_View.GetControlFromPosition(y, i)).Image = null;
                }
            }
        }
    
    }
}