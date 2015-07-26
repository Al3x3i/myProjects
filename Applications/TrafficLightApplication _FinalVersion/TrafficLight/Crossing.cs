using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
//using System.Threading.Tasks;
using System.Drawing;
using System.Collections;
using System.Windows.Forms;


namespace TrafficLightApplication
{
    public class Crossing
    {
        public string ID { get; set; }
        //public CrossingControls.BasicCrossingView CrossingView { get; set; }
        public List<Lane> Lanes { get; set; }
        public List<Road> Roads { get; set; }
        public List<Path> Paths { get; set; }
        public Crossing NorthNeighbour { get; set; }//Elton - the grid will set the neighbours.
        public Crossing SouthNeighbour { get; set; }
        public Crossing WestNeighbour { get; set; }
        public Crossing EastNeighbour { get; set; }
        public int NrOfCarsToGenerate { get; set; }
        public int ProbabilityNorth { get; set; }
        public int ProbabilitySouth { get; set; }
        public int ProbabilityWest { get; set; }
        public int ProbabilityEast { get; set; }
        public PictureBox CrossingPictureBox { get; set; }

        double roadWidth;
        double boundaryWest;
        double boundaryWestInner;
        double boundaryEastInner;
        double boundaryEast;
        double boundarySouth;
        double boundarySouthInner;
        double boundaryNorth;
        double boundaryNorthInner;
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
        public Crossing(string id, PictureBox pb)
        {
            this.Roads = new List<Road>();
            this.ID = id;
            //this.CrossingView = crossingView;
            this.CrossingPictureBox = pb;

            ////Elton - just testing lanes
            //IncomingLane lane = new IncomingLane("A1", 43, 43);
            //lane.SetPaths(this.CrossingView, 'S');

            //Elton - Setup Boundaries
           roadWidth = pb.Image.Height / 3;
           boundaryWest = 0;//Elton - an X coordinate
           boundaryWestInner = (pb.Image.Width / 2) - (roadWidth / 2);//Elton - an X coordinate
           boundaryEastInner = (pb.Image.Width / 2) + (roadWidth / 2);//Elton - an coordinate
           boundaryEast = pb.Image.Width;
           boundarySouth = pb.Image.Height;
           boundarySouthInner = (pb.Image.Height / 2) + (roadWidth / 2);//Elton - A Y coordinate
           boundaryNorthInner = (pb.Image.Height / 2) - (roadWidth / 2);
           boundaryNorth = 0;

            // roads
           //northRoad = new Road('N', this);
           //Roads.Add(northRoad);
           //southRoad = new Road('S', this);
           //Roads.Add(southRoad);
           //eastRoad = new Road('E', this);
           //Roads.Add(eastRoad);
           //westRoad = new Road('W', this);
           //Roads.Add(westRoad);

           //Path Section
           Paths = new List<Path>();
           CreatePaths();

            //Lane Section
           Lanes = new List<Lane>();
            CreateLanes();

            
           

            //Add Paths to respective Lanes
           AddPathsToLane();

        }

        private void AddPathsToLane()
        {
            //foreach(Lane l in Lanes){
            //    char[] temp = l.LaneID.ToCharArray();//explode the laneID
            //    l.SetPaths(this, temp[0]);
            //}
        }

        /// <summary>
        /// A method to create paths
        /// </summary>
        private void CreatePaths()
        {
            Path ESTR = new Path("ESTR");//Enter South Turn Right
            Point ESTRStartPoint = new Point((int)(boundaryEastInner - 3), (int)boundarySouth);
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
            Point ENTRStartPoint = new Point((int)(boundaryWestInner + 3), (int)boundaryNorth);
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
            Point EWTRStartPoint = new Point((int)boundaryWest, (int)(boundarySouthInner - 3));
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
            Point EETRStartPoint = new Point((int)boundaryEast, (int)(boundaryNorthInner + 3));
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
        private void CreateLanes()
        {
            IncomingLane SIA = new IncomingLane("SIA", boundarySouth, boundarySouthInner, southRoad); 
            Lanes.Add(SIA);
            southRoad.AddIncomingLane(SIA);
            Lane SOA = new Lane("SOA", boundarySouth, boundarySouthInner);
            Lanes.Add(SOA);
            southRoad.AddOutcomingLane(SOA);

            IncomingLane NIA = new IncomingLane("NIA", boundaryNorth, boundaryNorthInner, northRoad); 
            Lanes.Add(NIA);
            northRoad.AddIncomingLane(NIA);
            Lane NOA = new Lane("NOA", boundaryNorth, boundaryNorthInner);
            Lanes.Add(NOA);
            northRoad.AddOutcomingLane(NOA);

            IncomingLane EIA = new IncomingLane("EIA", boundaryEast, boundaryEastInner,eastRoad); 
            Lanes.Add(EIA);
            eastRoad.AddIncomingLane(EIA);
            Lane EOA = new Lane("EOA", boundaryEast, boundaryEastInner);
            Lanes.Add(EOA);
            eastRoad.AddOutcomingLane(EOA);

            IncomingLane WIA = new IncomingLane("WIA", boundaryWest, boundaryWestInner,westRoad);
            Lanes.Add(WIA);
            westRoad.AddIncomingLane(WIA);
            Lane WOA = new Lane("WOA", boundarySouth, boundarySouthInner);
            Lanes.Add(WOA);
            westRoad.AddOutcomingLane(WOA);
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
            List<Road> generatingRoads = new List<Road>();
            //generatingRoads = getGeneratingRoads();

            int carsperRoad = 10;
            //now ask the roads to generate cars.
            foreach (Road r in Roads)
            {
                r.GenerateCarsOnLane(carsperRoad, ProbabilityEast, ProbabilityNorth, ProbabilitySouth, ProbabilityWest);
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
        private void SendCarToNeighbour(Car car)
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
        public void ReceiveCar(Crossing neighbour, Car car)
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

        public void MoveCarsOnRoads()
        {
            foreach (Road road in Roads)
            {
                road.MoveCarsOnLanes();
            }
        }
    }
}
