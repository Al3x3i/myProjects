using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;

namespace TrafficLightApplication
{
    public class OutgoingLane : Lane
    {
        public Road NeighbourCrossingRoad { get; set; }
        //public List<Point> Path { get; set; }//this is probably not necessary since the boundaries can be used to determine carsOnlane.

        //constructor
        public OutgoingLane(string laneID, double boundaryEntry, double boundaryExit, Road parent) :
            base(laneID, boundaryEntry, boundaryExit)
        {
            this.LaneID = laneID;
            this.BoundaryEntry = boundaryEntry;
            this.BoundaryExit = boundaryExit;
            this.RoadParent = parent;
        }

        /// <summary>
        /// Car has a path.
        /// Inspect the car's pathid. It should tell the destination of the car.
        /// get the neighbour crossing and its road, lanes, and path according to the destination.
        /// </summary>
        /// <param name="car"></param>
        public void SendCarToNeighbour(Car car)
        {

            //set neighbour crossing Road and get the destination neighbour.
            ICrossing destNeighbour = SetNeighbourCrossingRoad(car.Path);

            if (destNeighbour != null)
            {
                //the counter corresponds to the exact position of the desired road on the crossing.
                int indexcounter = 0;
                foreach (Road r in destNeighbour.Roads)
                {
                    if (NeighbourCrossingRoad.RoadID == r.RoadID)
                    {
                        destNeighbour.Roads[indexcounter].IncomingLanes[0].ReceiveCar(this, car);
                    }
                    indexcounter++;
                }

            }
            car.ExitBoundaryReached -= SendCarToNeighbour;
            CarGraveyard.Add(car);
            UpdateCarList();
        }

        private void UpdateCarList()
        {
            List<Car> temp = new List<Car>();
            foreach (Car car in CarsOnLane)
            {
                if (!CarGraveyard.Contains(car))
                    temp.Add(car);
            }
            CarGraveyard = null;
            CarGraveyard = new List<Car>(); // clears memory
            CarsOnLane = temp;
        }

        public void AddCarFromIncomingLane(Car car)
        {
            car.localLane = null;
            car.localOutgoingLane = this;
            CarsOnLane.Add(car);
            car.ExitBoundaryReached += SendCarToNeighbour;
        }

        /// <summary>
        /// This method set the destination neighbour road and also returns the destination crossing.
        /// This information is used to send car to neighbour.
        /// Just use path instead of incoming lane as per class diagram.
        /// </summary>
        /// <param name="inlane"></param>
        private ICrossing SetNeighbourCrossingRoad(Path path)
        {

            char source = path.PathID[1];
            char direction = path.PathID[3];

            if (source == 'N')
            {
                switch (direction)
                {

                    case 'L':
                        if (RoadParent.crossingParent.EastNeighbour != null)
                        {
                            NeighbourCrossingRoad = RoadParent.crossingParent.EastNeighbour.WestRoad;
                        }
                        return RoadParent.crossingParent.EastNeighbour;
                    case 'S':
                        if (RoadParent.crossingParent.SouthNeighbour != null)
                        {
                            NeighbourCrossingRoad = RoadParent.crossingParent.SouthNeighbour.NorthRoad;
                        }
                        return RoadParent.crossingParent.SouthNeighbour;
                    case 'R':
                        if (RoadParent.crossingParent.WestNeighbour!=null)
                        {
                        NeighbourCrossingRoad = RoadParent.crossingParent.WestNeighbour.EastRoad;
                        }
                        return RoadParent.crossingParent.WestNeighbour;
                        
                    default:
                        break;
                }

            }

            if (source == 'S')
            {
                switch (direction)
                {

                    case 'L':
                        if (RoadParent.crossingParent.WestNeighbour != null)
                        {
                            NeighbourCrossingRoad = RoadParent.crossingParent.WestNeighbour.EastRoad;
                        }
                        return RoadParent.crossingParent.WestNeighbour;
                    case 'S':
                        if (RoadParent.crossingParent.NorthNeighbour != null)
                        {
                            NeighbourCrossingRoad = RoadParent.crossingParent.NorthNeighbour.SouthRoad;
                        }
                        return RoadParent.crossingParent.NorthNeighbour;
                    case 'R':
                        if (RoadParent.crossingParent.EastNeighbour != null)
                        {
                            NeighbourCrossingRoad = RoadParent.crossingParent.EastNeighbour.WestRoad;
                        }
                        return RoadParent.crossingParent.EastNeighbour;
                    default:
                        break;
                }

            }

            if (source == 'W')
            {
                switch (direction)
                {

                    case 'L':
                        if (RoadParent.crossingParent.NorthNeighbour != null)
                        {
                            NeighbourCrossingRoad = RoadParent.crossingParent.NorthNeighbour.SouthRoad;
                        }
                        return RoadParent.crossingParent.NorthNeighbour;
                    case 'S':
                        if (RoadParent.crossingParent.EastNeighbour != null)
                        {
                            NeighbourCrossingRoad = RoadParent.crossingParent.EastNeighbour.WestRoad;
                        }
                        return RoadParent.crossingParent.EastNeighbour;
                    case 'R':
                        if (RoadParent.crossingParent.SouthNeighbour != null)
                        {
                            NeighbourCrossingRoad = RoadParent.crossingParent.SouthNeighbour.NorthRoad;
                        }
                        return RoadParent.crossingParent.SouthNeighbour;
                    default:
                        break;
                }

            }

            if (source == 'E')
            {
                switch (direction)
                {

                    case 'L':
                        if (RoadParent.crossingParent.SouthNeighbour != null)
                        {
                            NeighbourCrossingRoad = RoadParent.crossingParent.SouthNeighbour.NorthRoad;
                        }
                        return RoadParent.crossingParent.SouthNeighbour;
                    case 'S':
                        if (RoadParent.crossingParent.WestNeighbour != null)
                        {
                            NeighbourCrossingRoad = RoadParent.crossingParent.WestNeighbour.EastRoad;
                        }
                        return RoadParent.crossingParent.WestNeighbour;
                    case 'R':
                        if (RoadParent.crossingParent.NorthNeighbour!=null)
                        {
                        NeighbourCrossingRoad = RoadParent.crossingParent.NorthNeighbour.SouthRoad;
                        }
                        return RoadParent.crossingParent.NorthNeighbour;
                    default:
                        break;
                }

            }

            return null;

        }
    }//class
}//namespace
