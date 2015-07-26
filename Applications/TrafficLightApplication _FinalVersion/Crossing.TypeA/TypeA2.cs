using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Threading;
using System.Drawing;
using System.Timers;
using System.Windows.Forms;
using TrafficLightApplication;

namespace Crossing.TypeA
{
    
   
    [Serializable]
    public class TypeA2 : ICrossing
    {
        //pedestrian code
        public bool AllRed { get;set;}
        public Point PedestrianLocation { get; set; }
        public Point PedestrianLocation2 { get; set; }

        public string Type { get; set; }

        public Image img = Resource.crossingA2;
        public string ID { get; set; }
        //public CrossingControls.BasicCrossingView CrossingView { get; set; }
        public List<Lane> Lanes { get; set; }
        /// <summary>
        /// Holds list of IncomingLane objects.
        /// </summary>
        public List<IncomingLane> incominglanes { get; set; }
        /// <summary>
        /// timer for traffic lights.
        /// </summary>
        public System.Timers.Timer timerTrafficLight;
        public List<Road> Roads { get; set; }
        public List<Path> Paths { get; set; }
        public ICrossing NorthNeighbour { get; set; }//Elton - the grid will set the neighbours.
        public ICrossing SouthNeighbour { get; set; }
        public ICrossing WestNeighbour { get; set; }
        public ICrossing EastNeighbour { get; set; }
        public int NrOfCarsToGenerate { get; set; }
        public int ProbabilityNorth { get; set; }
        public int ProbabilitySouth { get; set; }
        public int ProbabilityWest { get; set; }
        public int ProbabilityEast { get; set; }
        public PictureBox CrossingPictureBox { get; set; }
        public int NrOfCarsFromNorth { get; set; }
        public int NrOfCarsFromSouth { get; set; }
        public int NrOfCarsFromWest { get; set; }
        public int NrOfCarsFromEast { get; set; }
        
        double roadWidth;
        double boundaryWest;
        public double boundaryWestInner;
        public double boundaryEastInner;
        double boundaryEast;
        double boundarySouth;
        public double boundarySouthInner;
        double boundaryNorth;
        public double boundaryNorthInner;
        private Road northRoad;
        public Road NorthRoad
        {
            get { return this.northRoad; }
            set { this.northRoad = value; }
        }
        private Road southRoad;
        public Road SouthRoad
        {
            get { return this.southRoad; }
            set { this.southRoad = value; }
        }


        private Road eastRoad;
        public Road EastRoad
        {
            get { return this.eastRoad; }
            set { this.eastRoad = value; }
        }
        private Road westRoad;
        public Road WestRoad
        {
            get { return this.westRoad; }
            set { this.westRoad = value; }
        }
        /// <summary>
        /// This is used to while loading application to create in Form panel of crossings
        /// </summary>
        public TypeA2()
        {
        }

        public TypeA2(string id, PictureBox pb)
        {
           

            this.Roads = new List<Road>();
            this.ID = id;
            //this.CrossingView = crossingView;
            this.CrossingPictureBox = pb;
            this.Status = 1; //TrafficLight Status
            incominglanes = new List<IncomingLane>(4);
          

            //Elton - Setup Boundaries
            roadWidth = pb.Image.Height / 3;
            boundaryWest = 0;//Elton - an X coordinate
            boundaryWestInner = (pb.Image.Width / 2) - (roadWidth / 2);//Elton - an X coordinate
            boundaryEastInner = (pb.Image.Width / 2) + (roadWidth / 2);//Elton - an coordinate
            boundaryEast = pb.Image.Width;
            boundarySouth = pb.Image.Height;
            boundarySouthInner = (pb.Image.Height / 2) + (roadWidth / 2);//Elton - A Y coordinate
            boundaryNorthInner = ((pb.Image.Height / 2) - (roadWidth / 2)) - 6;
            boundaryNorth = 0;

            //needed for pedestrian code
            this.Type = "A2"; 
            PedestrianLocation = new Point(Convert.ToInt32(BoundaryEastInner), Convert.ToInt32(BoundarySouthInner - 10));
            PedestrianLocation2 = new Point(Convert.ToInt32(BoundaryWestInner), Convert.ToInt32(BoundaryNorthInner + 3));

            //trafficlight timer
            this.timerTrafficLight = new System.Timers.Timer();
            timerTrafficLight.Elapsed += new ElapsedEventHandler(timerTrafficLight_Tick);
            timerTrafficLight.Interval = 1;

            //roads
            northRoad = new Road('N', this);
            Roads.Add(northRoad);
            southRoad = new Road('S', this);
            Roads.Add(southRoad);
            eastRoad = new Road('E', this);
            Roads.Add(eastRoad);
            westRoad = new Road('W', this);
            Roads.Add(westRoad);

            //Path Section
            Paths = new List<Path>();
            CreatePaths();

            //Lane Section
            Lanes = new List<Lane>();
            CreateLanes();

            // traffic lights
            // CreateTrafficLights();

            //Add Paths to respective Lanes
            AddPathsToLane();
            ProbabilityNorth = 25;
            ProbabilityEast = 25;
            ProbabilitySouth = 25;
            ProbabilityWest = 25;

            NrOfCarsFromNorth = 10;
            NrOfCarsFromSouth = 10;
            NrOfCarsFromEast = 10;
            NrOfCarsFromWest = 10;
        }


        /// <summary>
        /// Holds status of t-light.
        /// </summary>
        public int Status
        {
            get;
            set;
        }

       
        public void AddPathsToLane()
        {
            foreach (Lane l in Lanes)
            {
                char[] temp = l.LaneID.ToCharArray();//explode the laneID
                l.SetPaths(this, temp[0]);
            }
        }

        /// <summary>
        /// A method to create paths
        /// </summary>
        public void CreatePaths()
        {
            Path ESTR = new Path("ESTR");//Enter South Turn Right
            Point ESTRStartPoint = new Point((int)(boundaryEastInner - roadWidth / 4), (int)boundarySouth);
            ESTR.setPath(ESTRStartPoint, "S", roadWidth, "R", Convert.ToInt32(boundarySouthInner), boundaryEast);
            Paths.Add(ESTR);
            Path ESGS = new Path("ESGS");//Enter South Go Straight
            Point ESGSStartPoint = new Point((ESTRStartPoint.X - 2), ESTRStartPoint.Y);//simply use the previous startpoint and vary te coordinate -2 pixels.
            ESGS.setPath(ESGSStartPoint, "S", roadWidth, "S", Convert.ToInt32(boundarySouthInner), boundaryNorth);
            Paths.Add(ESGS);
            Path ESTL = new Path("ESTL");//Enter South Turn Left
            Point ESTLStartPoint = new Point((ESGSStartPoint.X - 2), ESTRStartPoint.Y);//simply use the previous startpoint and vary te coordinate -2 pixels.
            ESTL.setPath(ESTLStartPoint, "S", roadWidth, "L", Convert.ToInt32(boundarySouthInner), boundaryWest);
            Paths.Add(ESTL);
            Path ENTR = new Path("ENTR");//Enter North Turn Right
            Point ENTRStartPoint = new Point((int)(boundaryWestInner + roadWidth / 4), (int)boundaryNorth);
            ENTR.setPath(ENTRStartPoint, "N", roadWidth, "R", Convert.ToInt32(boundaryNorthInner), boundaryWest);
            Paths.Add(ENTR);
            Path ENGS = new Path("ENGS");//Enter North GO Straight
            Point ENGSStartPoint = new Point((ENTRStartPoint.X + 2), (int)boundaryNorth);
            ENGS.setPath(ENGSStartPoint, "N", roadWidth, "S", Convert.ToInt32(boundaryNorthInner), boundarySouth);
            Paths.Add(ENGS);
            Path ENTL = new Path("ENTL");//Enter North Turn Left
            Point ENTLStartPoint = new Point((ENGSStartPoint.X + 2), (int)boundaryNorth);
            ENTL.setPath(ENTLStartPoint, "N", roadWidth, "L", Convert.ToInt32(boundaryNorthInner), boundaryEast);
            Paths.Add(ENTL);
            Path EWTR = new Path("EWTR");//Enter West Turn Right
            Point EWTRStartPoint = new Point((int)boundaryWest, (int)(boundarySouthInner - roadWidth / 4));
            EWTR.setPath(EWTRStartPoint, "W", roadWidth, "R", Convert.ToInt32(boundaryWestInner), boundarySouth);
            Paths.Add(EWTR);
            Path EWGS = new Path("EWGS");//Enter West GO Straight
            Point EWGSStartPoint = new Point((int)boundaryWest, (int)(EWTRStartPoint.Y - 2));
            EWGS.setPath(EWGSStartPoint, "W", roadWidth, "S", Convert.ToInt32(boundaryWestInner), boundaryEast);
            Paths.Add(EWGS);
            Path EWTL = new Path("EWTL");//Enter West Turn Left
            Point EWTLStartPoint = new Point((int)boundaryWest, (int)(EWGSStartPoint.Y - 2));
            EWTL.setPath(EWTLStartPoint, "W", roadWidth, "L", Convert.ToInt32(boundaryWestInner), boundaryNorth);
            Paths.Add(EWTL);
            Path EETR = new Path("EETR");//Enter East Turn Right
            Point EETRStartPoint = new Point((int)boundaryEast, (int)(boundaryNorthInner + roadWidth / 4));
            EETR.setPath(EETRStartPoint, "E", roadWidth, "R", Convert.ToInt32(boundaryEastInner), boundaryNorth);
            Paths.Add(EETR);
            Path EEGS = new Path("EEGS");//Enter East GO Straight
            Point EEGSStartPoint = new Point((int)boundaryEast, (int)(EETRStartPoint.Y + 2));
            EEGS.setPath(EEGSStartPoint, "E", roadWidth, "S", Convert.ToInt32(boundaryEastInner), boundaryWest);
            Paths.Add(EEGS);
            Path EETL = new Path("EETL");//Enter East Turn Left
            Point EETLStartPoint = new Point((int)boundaryEast, (int)(EEGSStartPoint.Y + 2));
            EETL.setPath(EETLStartPoint, "E", roadWidth, "L", Convert.ToInt32(boundaryEastInner), boundarySouth);
            Paths.Add(EETL);
        }

        /// <summary>
        /// A method to create Lanes
        /// </summary>
        public void CreateLanes()
        {
            IncomingLane NIA = new IncomingLane("NIA", boundaryNorth, boundaryNorthInner, northRoad);
            Lanes.Add(NIA);
            incominglanes.Add(NIA);
            NIA.trafficJamOccurred += trafficJamOccurred;
            northRoad.AddIncomingLane(NIA);
            OutgoingLane NOA = new OutgoingLane("NOA", boundaryNorth, boundaryNorthInner, northRoad);
            Lanes.Add(NOA);
            northRoad.AddOutcomingLane(NOA);

            IncomingLane EIA = new IncomingLane("EIA", boundaryEast, boundaryEastInner, eastRoad);
            Lanes.Add(EIA);
            incominglanes.Add(EIA);
            EIA.trafficJamOccurred += trafficJamOccurred;
            eastRoad.AddIncomingLane(EIA);
            OutgoingLane EOA = new OutgoingLane("EOA", boundaryEast, boundaryEastInner, eastRoad);
            Lanes.Add(EOA);
            eastRoad.AddOutcomingLane(EOA);

            IncomingLane SIA = new IncomingLane("SIA", boundarySouth, boundarySouthInner, southRoad);
            Lanes.Add(SIA);
            incominglanes.Add(SIA);
            SIA.trafficJamOccurred += trafficJamOccurred;
            southRoad.AddIncomingLane(SIA);
            OutgoingLane SOA = new OutgoingLane("SOA", boundarySouth, boundarySouthInner, southRoad);
            Lanes.Add(SOA);
            southRoad.AddOutcomingLane(SOA);


            IncomingLane WIA = new IncomingLane("WIA", boundaryWest, boundaryWestInner, westRoad);
            Lanes.Add(WIA);
            incominglanes.Add(WIA);
            WIA.trafficJamOccurred += trafficJamOccurred;
            westRoad.AddIncomingLane(WIA);
            OutgoingLane WOA = new OutgoingLane("WOA", boundarySouth, boundarySouthInner, westRoad);
            Lanes.Add(WOA);
            westRoad.AddOutcomingLane(WOA);


            for (int i = 0; i < 4; i++)
            {
                this.CreateTrafficlights(i);
            }
        }

        void trafficJamOccurred(Lane lane, int numOfCars)
        {
            //this.CrossingPictureBox.Hide();
        }

        /// <summary>
        /// This method is to generate the number of cars on the roads.
        /// If, foreample, a crossing should generate 100 cars, then it must
        /// distribute the load equally among the roads where it does not have neighbors.
        /// E.g. south=50, west=50.
        /// </summary>
        /// <param name=""></param>
        public void GenerateCarsOnRoad()
        {
            GenerateCarsOnLanes(ProbabilityEast, ProbabilityNorth, ProbabilitySouth, ProbabilityWest);
        }
        /// <summary>
        ///Gerard - A stolen method to generate Cars on lane 
        /// </summary>
        private void GenerateCarsOnLanes(int probEast, int probNorth, int probSouth, int probWest)
        {
            List<Lane> generatingLanes = new List<Lane>();
            foreach (Lane l in Lanes)
            {
                if (l.LaneID == "NIA")
                {
                    if (NorthNeighbour == null)
                    {
                        generatingLanes.Add(l);
                    }
                }
                else if (l.LaneID == "EIA")
                {
                    if (EastNeighbour == null)
                    {
                        generatingLanes.Add(l);
                    }
                }
                else if (l.LaneID == "SIA")
                {
                    if (SouthNeighbour == null)
                    {
                        generatingLanes.Add(l);
                    }
                }
                else if (l.LaneID == "WIA")
                {
                    if (WestNeighbour == null)
                    {
                        generatingLanes.Add(l);
                    }
                }
            }
            foreach (Lane lane in generatingLanes)
            {
                lane.GenerateCars(probEast, probNorth, probSouth, probWest);
            }

        }

        /// <summary>
        /// Method for giving the location of bulbs of trafficlights.
        /// </summary>
        /// <param name="x">Determines lane to create traffic lights in type 1.</param>
        private void CreateTrafficlights(int x)
        {
            Point green = new Point();
            Point orange = new Point();
            Point red = new Point();
            switch (x)
            {
                case 0:
                    green.X = (int)boundaryWestInner - 16;
                    green.Y = (int)boundaryNorthInner - 44;
                    orange.X = (int)boundaryWestInner - 16;
                    orange.Y = (int)boundaryNorthInner - 30;
                    red.X = (int)boundaryWestInner - 16;
                    red.Y = (int)boundaryNorthInner - 16;
                    this.incominglanes[x].LocalLight.setLights(green, orange, red);
                    break;
                case 1:
                    green.X = (int)boundaryEastInner + 30;
                    green.Y = (int)boundaryNorthInner - 14;
                    orange.X = (int)boundaryEastInner + 16;
                    orange.Y = (int)boundaryNorthInner - 14;
                    red.X = (int)boundaryEastInner + 2;
                    red.Y = (int)boundaryNorthInner - 14;
                    this.incominglanes[x].LocalLight.setLights(green, orange, red);
                    break;
                case 2:
                    green.X = (int)boundaryEastInner + 2;
                    green.Y = (int)boundarySouthInner + 22;
                    orange.X = (int)boundaryEastInner + 2;
                    orange.Y = (int)boundarySouthInner + 11;
                    red.X = (int)boundaryEastInner + 2;
                    red.Y = (int)boundarySouthInner + 1;
                    this.incominglanes[x].LocalLight.setLights(green, orange, red);
                    break;
                case 3:
                    green.X = (int)boundaryWestInner - 44;
                    green.Y = (int)boundarySouthInner + 1;
                    orange.X = (int)boundaryWestInner - 30;
                    orange.Y = (int)boundarySouthInner + 1;
                    red.X = (int)boundaryWestInner - 16;
                    red.Y = (int)boundarySouthInner + 1;
                    this.incominglanes[x].LocalLight.setLights(green, orange, red);
                    break;

            }
        }

       

        public void StartTrafficLightTimer()
        {
            timerTrafficLight.Start();
        }

        public void StopTrafficLightTimer()
        {
            timerTrafficLight.Stop();
        }
        /// <summary>
        /// Change status to control trafficlights in the crossing.
        /// </summary>
        public void ChangeStatus()
        {
            if (Status == 1)
                Status = 2;
            else if (Status == 2)
                Status = 3;
            else if (Status == 3)
                Status = 4;
            else if (Status == 4)
                Status = 5;
            else if (Status == 5)
                Status = 1;
        }

        /// <summary>
        /// Resets all the traffic light to red.
        /// </summary>
        public void ResetTrafficLights()
        {
            foreach (IncomingLane l in incominglanes)
            {
                l.LocalLight.setRed();
            }

        }

        /// <summary>
        /// Apply the colors to the trafficlights depending on the status of the crossing.
        /// </summary>
        private void ApplyStatus()
        {
            switch (Status)
            {
                case 1:
                    this.incominglanes[0].LocalLight.setGreen();
                    //set AllRed to False
                    this.AllRed = false;
                    break;
                case 2:

                    this.incominglanes[1].LocalLight.setGreen();

                    break;
                case 3:

                    this.incominglanes[2].LocalLight.setGreen();

                    break;
                case 4:

                    this.incominglanes[3].LocalLight.setGreen();
                    break;
                    //case 5 for pedestrians to move
                case 5:
                    this.incominglanes[0].LocalLight.setRed();
                    this.incominglanes[1].LocalLight.setRed();
                    this.incominglanes[2].LocalLight.setRed();
                    this.incominglanes[3].LocalLight.setRed();
                    //set AllRead to true.
                    this.AllRed = true;
                    break;

            }


        }




        /// <summary>
        /// Timer for traffic light, it takes corresponding duration of greentime dynamically according to the status.
        /// </summary>
        /// <param name="sender">Sender object.</param>
        /// <param name="e">EventArgs.</param>
        private void timerTrafficLight_Tick(object sender, EventArgs e)
        {
            timerTrafficLight.Stop();

            SetInterval();

            ResetTrafficLights();

            System.Threading.Thread.Sleep(2000);

            //Apply the colors of the trafficlights depending on Status.
            ApplyStatus();

            ChangeStatus();// Change status of the intersection

            timerTrafficLight.Start();
        }

        /// <summary>
        /// Method for setting the interval.
        /// </summary>
        private void SetInterval()
        {
            switch (this.Status)
            {
                case 1:
                    timerTrafficLight.Interval = incominglanes[0].LocalLight.DurationGreen * 1000;
                    break;
                case 2:
                    timerTrafficLight.Interval = incominglanes[1].LocalLight.DurationGreen * 1000;
                    break;
                case 3:
                    timerTrafficLight.Interval = incominglanes[2].LocalLight.DurationGreen * 1000;
                    break;
                case 4:
                    timerTrafficLight.Interval = incominglanes[3].LocalLight.DurationGreen * 1000;
                    break;
                case 5:
                    timerTrafficLight.Interval = 5000;
                    timerTrafficLight.Interval = 5000;
                    timerTrafficLight.Interval = 5000;
                    timerTrafficLight.Interval = 5000;
                    break;

            }
        }

        /// <summary>
        /// this method requires knowing where the car is..
        /// this is determined by knowing what its position corresponds to
        /// regarding the waypoints/paths
        /// in order for this to work you must subscribe to the cars'
        /// ExitBoundaryReached event. this is done upon generating and receiving cars.
        /// This method unsubscribes this crossing from the car.
        /// </summary>
        /// <param name="car"></param>
        public void SendCarToNeighbour(Car car)
        {
            /*
            #region to the north
            if (car.Position == northOut[1])
            {
                if (northNeighbour != null)
                {
                    northNeighbour.ReceiveCar(this, car);
                }
                northOutGrave.Add(car);
                UpdateNorthOutLane();
            }
            #endregion
            #region to the east
            else if (car.Position == eastOut[1])
            {
                if (eastNeighbour != null)
                {
                    eastNeighbour.ReceiveCar(this, car);
                }
                eastOutGrave.Add(car);
                UpdateEastOutLane();
            }
            #endregion
            #region to the south
            else if (car.Position == southOut[1])
            {
                if (southNeighbour != null)
                {
                    southNeighbour.ReceiveCar(this, car);
                }
                southOutGrave.Add(car);
                UpdateSouthOutLane();
            }
            #endregion
            #region to the west
            else if (car.Position == westOut[1])
            {
                if (westNeighbour != null)
                {
                    westNeighbour.ReceiveCar(this, car);
                }
                westOutGrave.Add(car);
                UpdateWestOutLane();
            }
            #endregion
            car.ExitBoundaryReached -= SendCarToNeighbour;
            toYourScatteredBodiesGo.Add(car);
            UpdateCarList();
             */
        }

        /// <summary>
        /// called by the neighbour who wishes to send a car to this crossing
        /// </summary>
        /// <param name="neighbour"></param>
        /// <param name="car"></param>
        public void ReceiveCar(ICrossing neighbour, Car car)
        {
            /*
            List<Point> wpList = new List<Point>();
            int dice = rand.Next(1, 3);
            #region from the north
            if (neighbour == northNeighbour)
            {
                wpList.AddRange(northInc);

                if (dice == 1)
                    wpList.AddRange(eastOut);
                else if (dice == 2)
                    wpList.AddRange(southOut);
                else if (dice == 3)
                    wpList.AddRange(westOut);

                car.ReplaceWaypoints(wpList, northIncCars);

                northIncCars.Add(car);
            }
            #endregion
            #region from the east
            else if (neighbour == eastNeighbour)
            {
                wpList.AddRange(eastInc);

                if (dice == 1)
                    wpList.AddRange(southOut);
                else if (dice == 2)
                    wpList.AddRange(westOut);
                else if (dice == 3)
                    wpList.AddRange(northOut);

                car.ReplaceWaypoints(wpList, eastIncCars);
                eastIncCars.Add(car);
            }
            #endregion
            #region from the south
            else if (neighbour == southNeighbour)
            {
                wpList.AddRange(southInc);

                if (dice == 1)
                    wpList.AddRange(westOut);
                else if (dice == 2)
                    wpList.AddRange(northOut);
                else if (dice == 3)
                    wpList.AddRange(eastOut);

                car.ReplaceWaypoints(wpList, southIncCars);
                southIncCars.Add(car);
            }
            #endregion
            #region from the west
            else if (neighbour == westNeighbour)
            {
                wpList.AddRange(westInc);

                if (dice == 1)
                    wpList.AddRange(northOut);
                else if (dice == 2)
                    wpList.AddRange(eastOut);
                else if (dice == 3)
                    wpList.AddRange(southOut);

                car.ReplaceWaypoints(wpList, westIncCars);
                westIncCars.Add(car);
            }
            #endregion
            car.ExitBoundaryReached += SendCarToNeighbour;
            cars.Add(car);

            if (CarCountScream != null)
                CarCountScream(cars.Count);
            */
        }

        public void MonitorTrafficJamOnRoads()
        {
            foreach (Road r in Roads)
            {
                r.MonitorTrafficJamOnIncomingLanes();
            }
        }
        public void MoveCarsOnRoads()
        {
            foreach (Lane lane in Lanes)
            {
                lane.MoveCars();
            }
        }


        public Image GetCrossingImage()
        {
            return img;
        }


        public double BoundaryWestInner
        {
            get
            {
                return boundaryWestInner;
            }
        }

        public double BoundaryEastInner
        {
            get
            {
                return this.boundaryEastInner;
            }
        }

        public double BoundarySouthInner
        {
            get
            {
                return this.boundarySouthInner;
            }
        }

        public double BoundaryNorthInner
        {
            get
            {
                return this.boundaryNorthInner;
            }
        }


       
    }

}
