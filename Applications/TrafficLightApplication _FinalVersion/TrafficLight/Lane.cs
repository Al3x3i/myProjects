using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
//using System.Threading.Tasks;
using System.Windows.Forms;
using System.Drawing;
//using System.Threading;

namespace TrafficLightApplication
{
    //declare a delegate type for traffic jam event.
    //It will tell you on which lane and how many cars in traffic jam.
    //not used now but left in for future extensibility.
    public delegate void TrafficJamDelegate(Lane lane, int numOfCars);

    /// <summary>
    /// Elton - This class really serves the purpose of associating traffic lights with cars.
    /// Crucially it has a list of lights and a list of cars. That's it's purpose!!
    /// </summary>
    public class Lane
    {
        public string LaneID { get; set; }
        public List<Car> CarsOnLane { get; set; }
        public List<Car> CarGraveyard { get; set; }
        public TrafficLight TrafficLight { get; set; }
        public double BoundaryEntry { get; set; }
        public double BoundaryExit { get; set; }
        public int MaxCarsAllowed { get; set; }
        public List<string> Lanepaths; //just store the pathID's instead of the entire paths/list of points. This may be an unnecessary in the end.
        public Road RoadParent { get; set; }//needed for a backward call



        //constructor
        /// <summary>
        /// Elton - the constructor
        /// I will not set the maxcars here because they get set by the 
        /// user after a crossing has been added to the grid.
        /// </summary>
        /// <param name="laneID"></param>
        /// <param name="boundaryEntry">Can be retrieved from the crossing</param>
        public Lane(string laneID, double boundaryEntry, double boundaryExit)
        {
            this.LaneID = laneID;//strict naming e.g. SIA for SouthIn-A
            this.BoundaryEntry = boundaryEntry;
            this.BoundaryExit = boundaryExit;
            SetDefaultSettingForLane();
        }

        /// <summary>
        /// Method is used to set default settings when simulation is over adn when project is created
        /// </summary>
        public void SetDefaultSettingForLane()
        {
            this.CarsOnLane = new List<Car>();
            this.CarGraveyard = new List<Car>();
            this.Lanepaths = new List<string>();
            this.MaxCarsAllowed = 2;
        }

        /// <summary>
        /// countCars() will count the total numbers of cars on each lane.
        /// </summary>
        /// <returns>Returns integer value</returns>
        public int countCars()
        {
            return CarsOnLane.Count();
        }


        public virtual void GenerateCars(int probEast, int probNorth, int probSouth, int probWest){}
      
        public void MoveCars()
        {
            foreach (Car car in CarsOnLane)
            {
                car.Run();
            }
        }


        /// <summary>
        ///the crossing will pass a crossingview to the lane.
        ///use the boundaryEntry and boundaryExit variables to determine which paths belong to which path.
        ///If the startingPoint of a path(CrossingControls.BasicCrossing.Paths.startingPoint) 
        ///is on a determinable XY coordinate then that path belongs  to this lane. We will use directions to
        ///filter the coordinates. May be overriden by the outgoing lane. He will use inner boundaries
        /// </summary>
        public virtual void SetPaths(ICrossing crossing, char direction)
        {

            foreach (Path p in crossing.Paths)
            {
                char[] temp = p.PathID.ToCharArray();//convert the string to a char array
                if (temp[1] == direction)
                {//Elton - if the 2nd character is the same as the entry direction then that lane belongs to this path.
                    this.Lanepaths.Add(p.PathID);
                }
            }
        }



    }
}

