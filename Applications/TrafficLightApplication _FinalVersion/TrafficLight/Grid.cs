using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;
using System.Windows.Forms; // I used this to add new crossing with predifined Picture box

namespace TrafficLightApplication
{
    /// <summary>
    /// Not specified events
    /// </summary>
    public class Grid
    {
        private ICrossing[,] crossings;

        public Grid()
        { }

        public Grid(int rows, int columns, string confName)
        {
            crossings = new ICrossing[rows, columns];
        }

        public bool AddCrossing(int row, int column, ICrossing crossing)
        {

            if (crossings[row, column] == null)
            {
                crossings[row, column] = crossing;
                SetCrossingNeighbours(row, column);
                
                return true;
            }
            else
            {
                return false;
            }
        }
        /// <summary>
        /// Method implements the logic to place crossing neighbours.
        /// </summary>
        /// <param name="row"></param>
        /// <param name="column"></param>
        private void SetCrossingNeighbours(int row, int column)
        {
            if ((row - 1) >= 0) // checks possibility of crossing position
            {
                if (crossings[row - 1, column] != null) // if someone can be there, check
                {
                    crossings[row - 1, column].SouthNeighbour = crossings[row, column];
                    
                }
                if (crossings[row, column] != null)
                    crossings[row, column].NorthNeighbour = crossings[row - 1, column];
            }
            else if(crossings[row, column] !=null)
                crossings[row, column].NorthNeighbour = null;

            if ((row + 1) < crossings.GetLength(0))
            {
                
                if (crossings[row + 1, column] != null)
                {
                    crossings[row + 1, column].NorthNeighbour = crossings[row, column];
                   
                }
                if (crossings[row, column] != null)
                    crossings[row, column].SouthNeighbour = crossings[row + 1, column];
            }
            else if (crossings[row, column] !=null)
                crossings[row, column].SouthNeighbour = null;

            if ((column - 1) >= 0)
            {
                if (crossings[row, column - 1] != null)
                {
                    crossings[row, column - 1].EastNeighbour = crossings[row, column];
                  

                }
                if (crossings[row, column] != null)
                    crossings[row, column].WestNeighbour = crossings[row, column - 1];
            }
            else if (crossings[row, column] !=null)
                crossings[row, column].WestNeighbour = null;

            if ((column + 1) < crossings.GetLength(1))
            {
                if (crossings[row, column + 1] != null)
                {
                    crossings[row, column + 1].WestNeighbour = crossings[row, column];
                    
                }
                if (crossings[row, column] != null)
                    crossings[row, column].EastNeighbour = crossings[row, column + 1];
            }
            else if(crossings[row, column] !=null)
                crossings[row, column].EastNeighbour = null;
        }

        public bool MoveCrossing(int fromRow, int fromColumn, int toRow, int toColumn)
        {
            if (crossings[toRow, toColumn] == null && crossings[fromRow, fromColumn] != null)
            {
                crossings[toRow, toColumn] = crossings[fromRow, fromColumn];
                crossings[fromRow, fromColumn] = null;

                // updating neighbours
                SetCrossingNeighbours(toRow, toColumn); 
                SetCrossingNeighbours(fromRow, fromColumn); 
                return true;
            }
            return false;
        }

        public bool DeleteCrossing(int row, int column)
        {
            if (crossings[row, column] != null)
            {
                crossings[row, column] = null;
                SetCrossingNeighbours(row, column); 
                return true;
            }
            return false;
        }

        public void MoveCarsOnCrossing()
        {
            foreach (ICrossing crossing in crossings)
            {
                if (crossing != null)
                {
                    crossing.MoveCarsOnRoads();
                }
            }
        }

        public ICrossing[,] GetAllCrossingsOnGrid() // this method is not in class diagram
        {
            return crossings;
        }
        

        public ICrossing GetCrossing(int row, int column)
        {
            return crossings[row, column];
        }

        public void SetDefaultSettingsForGrid()
        {
            foreach (ICrossing crossing in crossings)
            {
                if (crossing != null)
                {
                    crossing.ProbabilityEast = 25;
                    crossing.ProbabilityWest = 25;
                    crossing.ProbabilitySouth = 25;
                    crossing.ProbabilityNorth = 25;

                    foreach (var item in crossing.incominglanes)
                    {
                        item.LocalLight.DurationGreen = 5;
                        item.BufferFromEast = 10;
                        item.BufferFromWest = 10;
                        item.BufferFromSouth = 10;
                        item.BufferFromNorth = 10;
                    }
                    foreach (Lane laneItem in crossing.Lanes)
                    {
                        laneItem.SetDefaultSettingForLane();
                    }
                }
            }
        }
    }

}
