using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;


namespace TrafficLightApplication
{
    public class Car
    {
        private int carID;
        
        private Path path;
        private Point position;
        private int size;
        public int tracker;    // tracks where player is on path/waypoints
        List<Car> otherCars;    // needed for collision-detection
        public IncomingLane  localLane;
        public OutgoingLane localOutgoingLane;

        public int innerBoundaryPoint;
        public char incomingDirection;

        // events
        public delegate void ExitBoundaryReachedHandler(Car sender);
        public event ExitBoundaryReachedHandler ExitBoundaryReached;
        public delegate void InnerBoundaryPassedHandler(Car sender);
        public event InnerBoundaryPassedHandler InnerBoundaryPassed;

        public Car(Path path, double innerBoundary)
        {
            this.carID = 00;
            this.path = path;
            tracker = 0;
            this.position = path.pathpoints[tracker];
            this.innerBoundaryPoint = (int)innerBoundary;

            incomingDirection = path.PathID[1];
            localLane = null;
            localOutgoingLane = null;
        }
        public Car(Path path, double innerBoundary, IncomingLane lane)
        {
            this.carID = 00;
            this.path = path;
            tracker = 0;
            this.position = path.pathpoints[tracker];
            this.innerBoundaryPoint = (int)innerBoundary;

            incomingDirection = path.PathID[1];
            localLane = lane;
        }

        public Point Position
        {
            get { return this.position; }
            set { this.position = value; }

        }

        public Path Path
        {
            get { return this.path; }
            set { this.path = value; }
        }

        /// <summary>
        /// elton's move method
        /// </summary>
        public void Run()//loop draw from a point to another point according to the path
        {
            if (tracker < (path.pathpoints.Count - 1))
            {
                tracker++;
                this.position = path.pathpoints[tracker];
                CollissionTest();
                if (incomingDirection == 'W')
                {
                    if (this.position.X > innerBoundaryPoint) // traveled somewhat east
                        LightTest();
                }
                else if (incomingDirection == 'N')
                {
                    if (this.position.Y > innerBoundaryPoint) // traveled somewhat south
                        LightTest();
                }
                else if (incomingDirection == 'E')
                {
                    if (this.position.X < innerBoundaryPoint) // traveled somewhat west
                        LightTest();
                }
                else if (incomingDirection == 'S')
                {
                    if (this.position.Y < innerBoundaryPoint) // traveled somewhat north
                        LightTest();
                }

                if (tracker == (path.pathpoints.Count - 1))
                {
                    if (ExitBoundaryReached != null)
                        ExitBoundaryReached(this);
                }
            }
        }
        private void CollissionTest()
        {
            if (localLane != null)
            {
                if (localLane.Collision(this, incomingDirection))
                {
                    tracker--;
                    this.position = path.pathpoints[tracker];
                }
            }
        }
        private void LightTest()
        {
            if (localLane != null)
            {
                Boolean sentBack = false;
                if (localLane.LocalLight.Color == Color.Red) // red light !!
                {
                    tracker--;
                    this.position = path.pathpoints[tracker];
                    sentBack = true;
                }

                if (InnerBoundaryPassed != null && !sentBack)
                    InnerBoundaryPassed(this);
            }
        }
    }
}
