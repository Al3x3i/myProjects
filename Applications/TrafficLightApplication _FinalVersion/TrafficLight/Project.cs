using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Reflection;


namespace TrafficLightApplication
{
    /// <summary>
    /// Not specified events//
    /// </summary>
    public class Project
    {
        //Simulator sim;
        
        List<Grid> grids;
      
        List<ICrossing> crossingsTypeA = new List<ICrossing>();
        string name;
        public int selectedGrid;
        int rowCount, columnCount;

        public Project (string name, int rows, int columns)
        {
            this.name = name;
            this.rowCount = rows;
            this.columnCount = columns;
            this.grids = new List<Grid>();
            this.grids.Add(new Grid(rows,columns,"default"));   //Add first grid with default settings.
        }

        void LoadProjectName() { }

        public Grid GetSelectedGrid()
        {
            return grids[selectedGrid];
        }

        /// <summary>
        /// This method makes clone of Crossing grid.
        /// </summary>
        /// <param name="name"></param>
        public void AddGrid(int gridNumber) 
        {
            Grid temp = new Grid(rowCount, columnCount, "ExtraCrossing");
            grids.Add(temp);
            selectedGrid = gridNumber;
            for (int i = 0; i < grids[0].GetAllCrossingsOnGrid().GetLength(0); i++)// First level of array
            {
                for (int y = 0; y < grids[0].GetAllCrossingsOnGrid().GetLength(1); y++) // Second level of array
                {
                    ICrossing crossing = grids[0].GetCrossing(i, y);
                    if (crossing !=null)
                    {
                        AddCrossing(i, y, crossing.GetType().FullName.ToString(), crossing.CrossingPictureBox);
                    }
                }
            }
        }
        /// <summary>
        /// Add crossings to grid
        /// </summary>
        /// <param name="row"></param>
        /// <param name="column"></param>
        /// <param name="crossingClass"></param>
        /// <param name="pb"></param>
        /// <returns></returns>
        public bool AddCrossing(int row, int column, string crossingClass, PictureBox pb)
        {
            try
            {
                string[] stringParts = crossingClass.Split('.');

                Assembly ass = Assembly.Load(stringParts[0] + "." + stringParts[1]);
                Type myType = ass.GetType(stringParts[0] + "." + stringParts[1] + "." + stringParts[2]);
                ICrossing newCrossing = (ICrossing)Activator.CreateInstance(myType, stringParts[2], pb);

                int added = 0;
                for (int i = 0; i < grids.Count; i++) // Add crossing for every grid.
                {
                    if (grids[i].AddCrossing(row, column, newCrossing))
                        added++;
                }
                if (added == grids.Count)
                    return true;
                else
                    return false;
            }
            catch (Exception e)
            {
                MessageBox.Show(e.Message);
                return false;
            }
        }

        public bool MoveCrossings(int fromRow, int fromColumn, int toRow, int toColumn)
        {
            int moved = 0;
            for (int i = 0; i < grids.Count; i++) // Add crossing for every grid.
            {
                if (grids[i].MoveCrossing(fromRow, fromColumn, toRow, toColumn))
                    moved++;
            }
            if (moved == grids.Count)
                return true;
            else
                return false;
        }

        /// <summary>
        /// This method is used to remove all crossings before loading project from file
        /// </summary>
        public void DeleteAllCrossings()
        {
            this.grids = new List<Grid>();
            this.grids.Add(new Grid(rowCount, columnCount, "default"));
            selectedGrid = 0;
        }

        public int GetAmountOfGrids()
        {
            return grids.Count();
        }

        public Grid GetSelectedGrid(int i)
        {
            return grids[i];
        }

        public void SetDefaultSettingForAllGrids()
        {
            for (int i = 0; i < grids.Count; i++)
            {
                grids[i].SetDefaultSettingsForGrid();
            }
        }
        public bool IsGridEmpty()
        {

            foreach (ICrossing item in GetSelectedGrid().GetAllCrossingsOnGrid())
            {
                if (item !=null)
                {
                    return true;
                }
            }
            return false;

        }
    }
}
