using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TrafficLightApplication
{
    public class Road
    {
        public char RoadID { get; set; }//Elton - Strict naming, e.g. South
        public bool Neighbour { get; set; }//Elton - parameter to determine if a road has a neighbor. The crossing will set this.
        private List<IncomingLane> incomingLanes;
        public List<IncomingLane> IncomingLanes
        {
            get { return this.incomingLanes; }
            set { this.incomingLanes = value; }
        }
        public List<OutgoingLane> OutgoingLanes { get; set; }
        public ICrossing crossingParent { get; set; } //needed for backward call

        //constructor
        public Road(char roadID, ICrossing parent)
        {

            this.RoadID = roadID;
            this.IncomingLanes = new List<IncomingLane>();
            this.OutgoingLanes = new List<OutgoingLane>();
            this.crossingParent = parent;
        }
        public void AddIncomingLane(IncomingLane lane)
        {
            lane.SetPaths(crossingParent, RoadID);
            IncomingLanes.Add(lane);
        }
        public void AddOutcomingLane(OutgoingLane lane)
        {
            OutgoingLanes.Add(lane);
        }

        /// <summary>
        ///Elton - A method to generate Cars on lane 
        /// </summary>
        public void GenerateCarsOnLane(int probEast, int probNorth, int probSouth, int probWest)
        {

            foreach (IncomingLane l in IncomingLanes)
            {
                l.GenerateCars(probEast, probNorth, probSouth, probWest);
            }
        }
        public void MoveCarsOnLanes()
        {
            foreach (Lane lane in IncomingLanes)
            {
                lane.MoveCars();
            }
            foreach (Lane lane in OutgoingLanes)
            {
                lane.MoveCars();
            }
        }

        public void MonitorTrafficJamOnIncomingLanes()
        {
            foreach (IncomingLane l in IncomingLanes)
            {
                l.MonitorTraffic();
            }
        }

    }//class
}
