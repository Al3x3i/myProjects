using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
//using System.Threading.Tasks;
using System.Collections;
using System.Threading;
using System.Drawing;
//using CrossingControls;

namespace TrafficLightApplication
{
    public class IncomingLane:Lane
    {
        public int DirectionProbability { get; set; }
        Random myrand = new Random();
        int buffer;
        TrafficLight localLight;
        public Boolean jammed;
        int probNorth;
        int probEast;
        int probSouth;
        int probWest;

        public int ProbNorth { get; set; }
        public int ProbSouth { get; set; }
        public int ProbEast { get; set; }
        public int ProbWest { get; set; }

        public int BufferFromNorth { get; set; }
        public int BufferFromSouth { get; set; }
        public int BufferFromEast { get; set; }
        public int BufferFromWest { get; set; }

        public int BufferFromNorthHistory { get; set; }
        public int BufferFromSouthHistory { get; set; }
        public int BufferFromEastHistory { get; set; }
        public int BufferFromWestHistory { get; set; }

        //an event declaration
        //The simulator subscribes to this event to show where a traffic has occured.
        //Currently not used but will leave it in there for future extensibility.
        public event TrafficJamDelegate trafficJamOccurred;
        public bool Jam { get; set; }

        

        //constructor
        public IncomingLane(string laneID, double boundaryEntry, double boundaryExit, Road parent)
            : base(laneID, boundaryEntry, boundaryExit)
        {
            
            RoadParent = parent;
            localLight = new TrafficLight(0); // temp
            jammed = (this.CarsOnLane.Count >= this.MaxCarsAllowed);
            this.probNorth = 25;
            this.probEast = 25;
            this.probSouth = 25;
            this.probWest = 25;

            this.ProbNorth = 25;
            this.ProbSouth = 25;
            this.ProbEast = 25;
            this.ProbWest = 25;

            BufferFromNorth = 10;
            BufferFromSouth = 10;
            BufferFromEast = 10;
            BufferFromWest = 10;

            BufferFromNorthHistory = 10;
            BufferFromSouthHistory = 10;
            BufferFromEastHistory = 10;
            BufferFromWestHistory = 10;

            
        }
        
        public TrafficLight LocalLight
        {
            get { return this.localLight; }
            set { this.localLight = value; }
        }
        public Path GetPath(string pathID)
        {
            foreach (Path path in RoadParent.crossingParent.Paths)
            {
                if (pathID[1] == path.PathID[1] && pathID[3] == path.PathID[3])
                {
                    return path;
                }
            }
            return null;
        }
        /// <summary>
        /// A method to Generate cars.
        /// Uses the dice to determine probability based on given probabilities.
        /// </summary>
        public override void GenerateCars(int probEast, int probNorth, int probSouth, int probWest)
        {
            //this generates a car on all crossings.
            Random rand = new Random();
            int dice = 0;
            this.probNorth = probNorth;
            this.probEast = probEast;
            this.probSouth = probSouth;
            this.probWest = probWest;

            int totalDice = probNorth + probEast + probSouth + probWest;
            string pathString = "";
            Car car;

            if (LaneID == "NIA" && BufferFromNorth > 0)
            {
                dice = rand.Next(1, (totalDice - probNorth));
                if (dice <= probEast)
                {
                    pathString = "ENTL";
                }
                else if (dice > probEast && dice <= probEast + probSouth)
                {
                    pathString = "ENGS";
                }
                else if (dice > probEast + probSouth && dice <= probEast + probSouth + probWest)
                {
                    pathString = "ENTR";
                }

                car = new Car(GetPath(pathString), BoundaryExit, this);
                if (!Collision(car, 'N'))
                {
                    car.InnerBoundaryPassed += SendCarToOutgoingLane;
                    //car.IncomingLaneJammed += RaiseTrafficJamAlert;
                    CarsOnLane.Add(car);
                    BufferFromNorth--;
                    
                }

            }
            else if (LaneID == "EIA" && BufferFromEast > 0)
            {
                dice = rand.Next(1, (totalDice - probEast));
                if (dice <= probSouth)
                {
                    pathString = "EETL";
                }
                else if (dice > probSouth && dice <= probWest + probSouth)
                {
                    pathString = "EEGS";
                }
                else if (dice > probSouth + probWest && dice <= probNorth + probSouth + probWest)
                {
                    pathString = "EETR";
                }
                car = new Car(GetPath(pathString), BoundaryExit, this);
                if (!Collision(car, 'E'))
                {
                    car.InnerBoundaryPassed += SendCarToOutgoingLane;
                    CarsOnLane.Add(car);
                    BufferFromEast--;
                }
            }
            else if (LaneID == "SIA" && BufferFromSouth > 0)
            {
                dice = rand.Next(1, (totalDice - probSouth));
                if (dice <= probWest)
                {
                    pathString = "ESTL";
                }
                else if (dice > probWest && dice <= probWest + probNorth)
                {
                    pathString = "ESGS";
                }
                else if (dice > probWest + probNorth && dice <= probEast + probNorth + probWest)
                {
                    pathString = "ESTR";
                }
                car = new Car(GetPath(pathString), BoundaryExit, this);
                if (!Collision(car, 'S'))
                {
                    car.InnerBoundaryPassed += SendCarToOutgoingLane;
                    CarsOnLane.Add(car);
                    BufferFromSouth--;
                }
            }
            else if (LaneID == "WIA" && BufferFromWest > 0)
            {
                dice = rand.Next(1, (totalDice - probWest));
                if (dice <= probNorth)
                {
                    pathString = "EWTL";
                }
                else if (dice > probNorth && dice <= probEast + probNorth)
                {
                    pathString = "EWGS";
                }
                else if (dice > probEast + probNorth && dice <= probEast + probNorth + probSouth)
                {
                    pathString = "EWTR";
                }
                car = new Car(GetPath(pathString), BoundaryExit, this);
                if (!Collision(car, 'W'))
                {
                    car.InnerBoundaryPassed += SendCarToOutgoingLane;
                    CarsOnLane.Add(car);
                    BufferFromWest--;
                }
            }
        }
        public Boolean Collision(Car car, char incomingDirection)
        {
            int stepSize;
            if (incomingDirection == 'W' || incomingDirection == 'E')
            {
                foreach (Car otherCar in CarsOnLane)
                {
                    if (car != otherCar)
                    {
                        stepSize = Math.Abs(car.Path.pathpoints[0].X - car.Path.pathpoints[1].X);
                        if (Math.Abs(car.Position.X - otherCar.Position.X) < stepSize + 8)
                        {
                            return true;
                        }
                    }
                }
            }
            else if (incomingDirection == 'N' || incomingDirection == 'S')
            {
                foreach (Car otherCar in CarsOnLane)
                {
                    if (car != otherCar)
                    {
                        stepSize = Math.Abs(car.Path.pathpoints[0].Y - car.Path.pathpoints[1].Y);
                        if (Math.Abs(car.Position.Y - otherCar.Position.Y) < stepSize + 8)
                        {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        private void SendCarToOutgoingLane(Car sender)
        {
            if (sender.Path.pathpoints[sender.Path.pathpoints.Count - 1].X < RoadParent.crossingParent.BoundaryWestInner)
            {
                RoadParent.crossingParent.WestRoad.OutgoingLanes[0].AddCarFromIncomingLane(sender);

            }
            else if (sender.Path.pathpoints[sender.Path.pathpoints.Count - 1].X > RoadParent.crossingParent.BoundaryEastInner)
            {
                RoadParent.crossingParent.EastRoad.OutgoingLanes[0].AddCarFromIncomingLane(sender);
            }
            else if (sender.Path.pathpoints[sender.Path.pathpoints.Count - 1].Y < RoadParent.crossingParent.BoundaryNorthInner)
            {
                RoadParent.crossingParent.NorthRoad.OutgoingLanes[0].AddCarFromIncomingLane(sender);
            }
            else if (sender.Path.pathpoints[sender.Path.pathpoints.Count - 1].Y > RoadParent.crossingParent.BoundarySouthInner)
            {
                RoadParent.crossingParent.SouthRoad.OutgoingLanes[0].AddCarFromIncomingLane(sender);
            }
            sender.InnerBoundaryPassed -= SendCarToOutgoingLane;
            CarGraveyard.Add(sender);
            UpdateCarList();
        }
       
        /// <summary>
        /// Once A car enters a new lane it must be added to that count.
        /// </summary>
        private void UpdateCarList()
        {
            List<Car> temp = new List<Car>();
            foreach (Car car in CarsOnLane)
            {
                if (!CarGraveyard.Contains(car))
                    temp.Add(car);
            }
            CarGraveyard = null;
            CarGraveyard = new List<Car>();  // clears memory
            CarsOnLane = temp;
        }

        public void ReceiveCar(Lane neighbour, Car car)
        {
            car.tracker = 0;
            int dice = 0;
            Random rand = new Random();
            int totalDice = probNorth + probEast + probSouth + probWest;
            if (neighbour.RoadParent.crossingParent == RoadParent.crossingParent.NorthNeighbour)
            {
                dice = rand.Next(1, (totalDice - probNorth));
                if (dice <= probEast)
                {
                    car = new Car(GetPath("ENTL"), BoundaryExit, this);
                }
                else if (dice > probEast && dice <= probEast + probSouth)
                {
                    car = new Car(GetPath("ENGS"), BoundaryExit, this);
                }
                else if (dice > probEast + probSouth && dice <= probEast + probSouth + probWest)
                {
                    car = new Car(GetPath("ENTR"), BoundaryExit, this);
                }
                if (!Collision(car, 'N'))
                {
                    car.InnerBoundaryPassed += SendCarToOutgoingLane;
                    CarsOnLane.Add(car);
                }
                
            }
            else if (neighbour.RoadParent.crossingParent == RoadParent.crossingParent.EastNeighbour)
            {
                dice = rand.Next(1, (totalDice - probEast));
                if (dice <= probSouth)
                {
                    car = new Car(GetPath("EETL"), BoundaryExit, this);
                }
                else if (dice > probSouth && dice <= probWest + probSouth)
                {
                    car = new Car(GetPath("EEGS"), BoundaryExit, this);
                }
                else if (dice > probSouth + probWest && dice <= probNorth + probSouth + probWest)
                {
                    car = new Car(GetPath("EEGS"), BoundaryExit, this);
                }
                if (!Collision(car, 'E'))
                {
                    car.InnerBoundaryPassed += SendCarToOutgoingLane;
                    CarsOnLane.Add(car);
                }
                
            }
            else if (neighbour.RoadParent.crossingParent == RoadParent.crossingParent.SouthNeighbour)
            {
                dice = rand.Next(1, (totalDice - probSouth));
                if (dice <= probWest)
                {

                    car = new Car(GetPath("ESTL"), BoundaryExit, this);
                }
                else if (dice > probWest && dice <= probWest + probNorth)
                {
                    car = new Car(GetPath("ESGS"), BoundaryExit, this);
                }
                else if (dice > probWest + probNorth && dice <= probEast + probNorth + probWest)
                {
                    car = new Car(GetPath("ESTR"), BoundaryExit, this);
                }
                if (!Collision(car, 'S'))
                {
                    car.InnerBoundaryPassed += SendCarToOutgoingLane;
                    CarsOnLane.Add(car);
                }
                
            }
            else if (neighbour.RoadParent.crossingParent == RoadParent.crossingParent.WestNeighbour)
            {
                dice = rand.Next(1, (totalDice - probWest));
                if (dice <= probNorth)
                {
                    car = new Car(GetPath("EWTL"), BoundaryExit, this);
                }
                else if (dice > probNorth && dice <= probEast + probNorth)
                {
                    car = new Car(GetPath("EWGS"), BoundaryExit, this);
                }
                else if (dice > probEast + probNorth && dice <= probEast + probNorth + probSouth)
                {
                    car = new Car(GetPath("EWTR"), BoundaryExit, this);
                }
                if (!Collision(car, 'W'))
                {
                    car.InnerBoundaryPassed += SendCarToOutgoingLane;
                    CarsOnLane.Add(car);
                }
                
            }
        }

        public void MonitorTraffic()
        {
            //1. where do I get the max cars allowed from? From the form per crossing.
            if (this.CarsOnLane.Count >= this.MaxCarsAllowed)
            {
                //who is supposed to listen to this event?
                if (trafficJamOccurred != null)
                {
                    this.trafficJamOccurred(this, this.CarsOnLane.Count);
                    this.Jam = true;
                }
            }
            else Jam = false;
        }

    }//class

    }//namespace

