using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Drawing;
using System.Windows.Forms;

namespace TrafficLightApplication
{
    public delegate void PedestrianHandler();

    public interface ICrossing
    {

        string Type { get; set; } //needed for pedestrian logic discrimination.
        bool AllRed { get; set; }
        Point PedestrianLocation { get; set; }
        Point PedestrianLocation2 { get; set; }

        string ID { get; set; }

        Image GetCrossingImage();

        List<Lane> Lanes { get; set; }
        List<IncomingLane> incominglanes { get; set; }
        List<Road> Roads { get; set; }
        List<Path> Paths { get; set; }
        ICrossing NorthNeighbour { get; set; }//Elton - the grid will set the neighbours.
        ICrossing SouthNeighbour { get; set; }
        ICrossing WestNeighbour { get; set; }
        ICrossing EastNeighbour { get; set; }
        int NrOfCarsToGenerate { get; set; }
        int ProbabilityNorth { get; set; }
        int ProbabilitySouth { get; set; }
        int ProbabilityWest { get; set; }
        int ProbabilityEast { get; set; }
        int NrOfCarsFromNorth { get; set; }
        int NrOfCarsFromSouth { get; set; }
        int NrOfCarsFromWest { get; set; }
        int NrOfCarsFromEast { get; set; }
        
        double BoundaryWestInner { get;}
        double BoundaryEastInner { get;}
        double BoundarySouthInner { get;}
        double BoundaryNorthInner { get;}
        PictureBox CrossingPictureBox { get; set; }

        Road NorthRoad { get; set; }
        Road SouthRoad { get; set; }
        Road EastRoad { get; set; }
        Road WestRoad { get; set; }

        void AddPathsToLane();
        void CreatePaths();
        void CreateLanes();
        void GenerateCarsOnRoad();
        void SendCarToNeighbour(Car car);
        void ReceiveCar(ICrossing neighbour, Car car);
        void MoveCarsOnRoads();
        void StartTrafficLightTimer();
        void MonitorTrafficJamOnRoads();
        void StopTrafficLightTimer();
    }
}
