using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;

namespace TrafficLightApplication
{
    public class Path
    {
        public string PathID { get; set; }
        public List<Point> pathpoints { get; set; }
        public Point TurningPoint { get; set; }

        //constructor
        public Path(string ID)
        {
            this.PathID = ID;
            this.pathpoints = new List<Point>();
        }

        //methods
        /// <summary>
        /// A method to add points to the path
        /// </summary>
        /// <param name="startPoint">required to know the start point of the path</param>
        /// <param name="entryDirection">North(N),South(S),West(W),East(E)</param>
        /// <param name="roadWidth">the width of the road</param>
        /// <param name="destinationDirection">required to know in which direction we must add points after a
        /// turning point has been reached</param>
        /// <param name="laneInnerBorder">The lane's border</param>
        public void setPath(Point startPoint, string entryDirection,
                            double roadWidth, string destinationDirection,
                            int laneInnerBorder, double destinationOuterBoundary)
        {

            //Add startingPoint to path already
            this.pathpoints.Add(startPoint);
            //Determine TurningPoints until which we must generate points.
            Point turningPoint = DetermineTurningPoint(entryDirection, destinationDirection, startPoint, laneInnerBorder, roadWidth);


            if (entryDirection == "S")
            {
                while (pathpoints[pathpoints.Count - 1].Y > turningPoint.Y)
                {
                    pathpoints.Add(new Point(startPoint.X, (startPoint.Y - 4)));//going up requires subtracting
                    startPoint = pathpoints[pathpoints.Count - 1];
                }
            }
            if (entryDirection == "N")
            {
                while (pathpoints[pathpoints.Count - 1].Y < turningPoint.Y)
                {
                    pathpoints.Add(new Point(startPoint.X, (startPoint.Y + 4)));
                    startPoint = pathpoints[pathpoints.Count - 1];
                }
            }
            if (entryDirection == "W")
            {
                while (pathpoints[pathpoints.Count - 1].X < turningPoint.X)
                {
                    pathpoints.Add(new Point(startPoint.X + 4, startPoint.Y));
                    startPoint = pathpoints[pathpoints.Count - 1];
                }
            }
            if (entryDirection == "E")
            {
                while (pathpoints[pathpoints.Count - 1].X > turningPoint.X)
                {
                    pathpoints.Add(new Point(startPoint.X - 4, startPoint.Y));
                    startPoint = pathpoints[pathpoints.Count - 1]; //Elton - this causes us to add on the last array entry.
                }
            }


            //if you've reached the turning point start adding points in the turned direction
            if (entryDirection == "S")
            {
                if (destinationDirection == "R")
                {
                    while (pathpoints[pathpoints.Count - 1].X < destinationOuterBoundary)
                    {
                        startPoint = pathpoints[pathpoints.Count - 1]; //take the last point in the array and add a point to it in the -X direction
                        pathpoints.Add(new Point((startPoint.X + 5), startPoint.Y)); //at intervals of 5 pixels. I hope this is smooth enough.
                    }
                }
                if (destinationDirection == "L")
                {
                    while (pathpoints[pathpoints.Count - 1].X > destinationOuterBoundary)
                    {
                        startPoint = pathpoints[pathpoints.Count - 1]; //take the last point in the array and add a point to it in the -X direction
                        pathpoints.Add(new Point((startPoint.X - 5), startPoint.Y)); //at intervals of 5 pixels. I hope this is smooth enough.
                    }
                }
                if (destinationDirection == "S")
                {
                    while (pathpoints[pathpoints.Count - 1].Y > destinationOuterBoundary)
                    {
                        startPoint = pathpoints[pathpoints.Count - 1]; //take the last point in the array and add a point to it in the -X direction
                        pathpoints.Add(new Point(startPoint.X, startPoint.Y - 5)); //at intervals of 5 pixels. I hope this is smooth enough.
                    }
                }
            }

            if (entryDirection == "N")
            {
                if (destinationDirection == "R")
                {
                    while (pathpoints[pathpoints.Count - 1].X > destinationOuterBoundary)
                    {
                        startPoint = pathpoints[pathpoints.Count - 1]; //take the last point in the array and add a point to it in the -X direction
                        pathpoints.Add(new Point((startPoint.X - 5), startPoint.Y)); //at intervals of 5 pixels. I hope this is smooth enough.
                    }
                }
                if (destinationDirection == "L")
                {
                    while (pathpoints[pathpoints.Count - 1].X < destinationOuterBoundary)
                    {
                        startPoint = pathpoints[pathpoints.Count - 1]; //take the last point in the array and add a point to it in the -X direction
                        pathpoints.Add(new Point((startPoint.X + 5), startPoint.Y)); //at intervals of 5 pixels. I hope this is smooth enough.
                    }
                }
                if (destinationDirection == "S")
                {
                    while (pathpoints[pathpoints.Count - 1].Y < destinationOuterBoundary)
                    {
                        startPoint = pathpoints[pathpoints.Count - 1]; //take the last point in the array and add a point to it in the -X direction
                        pathpoints.Add(new Point(startPoint.X, startPoint.Y + 5)); //at intervals of 5 pixels. I hope this is smooth enough.
                    }
                }
            }

            if (entryDirection == "W")
            {
                if (destinationDirection == "R")
                {
                    while (pathpoints[pathpoints.Count - 1].Y < destinationOuterBoundary)
                    {
                        startPoint = pathpoints[pathpoints.Count - 1]; //take the last point in the array and add a point to it in the Y direction
                        pathpoints.Add(new Point(startPoint.X, (startPoint.Y + 5))); //at intervals of 5 pixels. I hope this is smooth enough.
                    }
                }
                if (destinationDirection == "L")
                {
                    while (pathpoints[pathpoints.Count - 1].Y > destinationOuterBoundary)
                    {
                        startPoint = pathpoints[pathpoints.Count - 1]; //take the last point in the array and add a point to it in the -Y direction
                        pathpoints.Add(new Point(startPoint.X, (startPoint.Y - 5))); //at intervals of 5 pixels. I hope this is smooth enough.
                    }
                }
                if (destinationDirection == "S")
                {
                    while (pathpoints[pathpoints.Count - 1].X < destinationOuterBoundary)
                    {
                        startPoint = pathpoints[pathpoints.Count - 1]; //take the last point in the array and add a point to it in the -X direction
                        pathpoints.Add(new Point((startPoint.X + 5), startPoint.Y)); //at intervals of 5 pixels. I hope this is smooth enough.
                    }
                }
            }

            if (entryDirection == "E")
            {
                if (destinationDirection == "R")
                {
                    while (pathpoints[pathpoints.Count - 1].Y > destinationOuterBoundary)
                    {
                        startPoint = pathpoints[pathpoints.Count - 1]; //take the last point in the array and add a point to it in the Y direction
                        pathpoints.Add(new Point(startPoint.X, (startPoint.Y - 5))); //at intervals of 5 pixels. I hope this is smooth enough.
                    }
                }
                if (destinationDirection == "L")
                {
                    while (pathpoints[pathpoints.Count - 1].Y < destinationOuterBoundary)
                    {
                        startPoint = pathpoints[pathpoints.Count - 1]; //take the last point in the array and add a point to it in the -Y direction
                        pathpoints.Add(new Point(startPoint.X, (startPoint.Y + 5))); //at intervals of 5 pixels. I hope this is smooth enough.
                    }
                }
                if (destinationDirection == "S")
                {
                    while (pathpoints[pathpoints.Count - 1].X > destinationOuterBoundary)
                    {
                        startPoint = pathpoints[pathpoints.Count - 1]; //take the last point in the array and add a point to it in the -X direction
                        pathpoints.Add(new Point((startPoint.X - 5), startPoint.Y)); //at intervals of 5 pixels. I hope this is smooth enough.
                    }
                }
            }
        }

        /// <summary>
        /// Just a utility Method to determine turningPoint.
        /// </summary>
        /// <param name="entryDirection"></param>
        /// <param name="destinationDirection"></param>
        /// <param name="startPoint"></param>
        /// <param name="laneInnerBorder"></param>
        /// <param name="roadWidth"></param>
        /// <returns></returns>
        private Point DetermineTurningPoint(string entryDirection, string destinationDirection, Point startPoint, int laneInnerBorder, double roadWidth)
        {

            //Point turningPoint;

            if (entryDirection == "S")
            {
                if (destinationDirection == "R")
                {//turning right
                    return TurningPoint = new Point(startPoint.X, (laneInnerBorder - (int)(roadWidth / 3)));//a third of the width of a road.
                }
                if (destinationDirection == "S")
                {//going straight
                    return TurningPoint = new Point(startPoint.X, (laneInnerBorder - (int)(roadWidth - 1)));//just 1 pixel from the next lane.
                }
                if (destinationDirection == "L")
                {//turning left
                    return TurningPoint = new Point(startPoint.X, (laneInnerBorder - (int)(roadWidth * 0.67)));// 2/3 of a road.
                }

            }

            if (entryDirection == "N")
            {
                if (destinationDirection == "R")
                {//turning right
                    return TurningPoint = new Point(startPoint.X, (laneInnerBorder + (int)(roadWidth / 3)));//a third of the width of a road.
                }
                if (destinationDirection == "S")
                {//going straight
                    return TurningPoint = new Point(startPoint.X, (laneInnerBorder + (int)(roadWidth - 1)));//just 1 pixel from the next lane.
                }
                if (destinationDirection == "L")
                {//turning left
                    return TurningPoint = new Point(startPoint.X, (laneInnerBorder + (int)(roadWidth * 0.67)));// 2/3 of a road.
                }

            }
            if (entryDirection == "E")
            {
                if (destinationDirection == "R")
                {//turning right
                    return TurningPoint = new Point((laneInnerBorder - (int)(roadWidth / 3)), startPoint.Y);//a third of the width of a road.
                }
                if (destinationDirection == "S")
                {//going straight
                    return TurningPoint = new Point((laneInnerBorder - (int)(roadWidth - 1)), startPoint.Y);//just 1 pixel from the next lane.
                }
                if (destinationDirection == "L")
                {//turning left
                    return TurningPoint = new Point((laneInnerBorder - (int)(roadWidth * 0.67)), startPoint.Y);// 2/3 of a road.
                }

            }
            if (entryDirection == "W")
            {
                if (destinationDirection == "R")
                {//turning right
                    return TurningPoint = new Point((laneInnerBorder + (int)(roadWidth / 3)), startPoint.Y);//a third of the width of a road.
                }
                if (destinationDirection == "S")
                {//going straight
                    return TurningPoint = new Point((laneInnerBorder + (int)(roadWidth - 1)), startPoint.Y);//just 1 pixel from the next lane.
                }
                if (destinationDirection == "L")
                {//turning left
                    return TurningPoint = new Point((laneInnerBorder + (int)(roadWidth * 0.67)), startPoint.Y);// 2/3 of a road.
                }

            }
            return new Point(0, 0);
        }
    }//class
}//namespace
