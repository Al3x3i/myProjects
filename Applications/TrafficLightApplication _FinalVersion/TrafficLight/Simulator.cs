using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Windows.Forms;

namespace TrafficLightApplication
{
    class Simulator
    {
        public delegate void progressBar(int i); // change progress bar status
        public event progressBar changeProgressBar; // change progress bar status

        public delegate void simulationOver();
        public event simulationOver updateSimulationStatus;

        public object threadLocker = new object();

        public Grid grid { get; set; }
        private View view;
        public SimulationStatus simulationStatus;
  
        private decimal simulationDuaration; //Simulation End Time
        public decimal simulationDuarationTimer; //Simulation Current Time
        private int simulationSpeed = 100;// Default value for Thread.Sleep()

        public Simulator(View view, ProgressBar progressBar)
        {
            this.view = view;
        }
        /// <summary>
        /// Method responsible for running simulation
        /// </summary>
        /// <param name="grid">Active Grid</param>
        /// <param name="simulationDuaration"></param>
        /// <param name="simulationSpeed"></param>
        public void StartSimulation(Grid grid, decimal simulationDuaration, decimal simulationSpeed, simulationOver updateSimulationStatus)
        {

            this.simulationDuaration = simulationDuaration * 600;
            this.simulationSpeed = this.simulationSpeed / (int)simulationSpeed;
            this.grid = grid;
            this.updateSimulationStatus += updateSimulationStatus;
            foreach (ICrossing crossing in grid.GetAllCrossingsOnGrid())
            {

                if (crossing != null)
                {
                    crossing.StartTrafficLightTimer();
                }
            }

            lock (threadLocker)
            {
                int x = 1;
                while (simulationDuarationTimer <= this.simulationDuaration && simulationStatus == SimulationStatus.running || simulationStatus == SimulationStatus.editting)
                {
                    if (x % 7 == 0)
                    {
                        foreach (ICrossing crossing in grid.GetAllCrossingsOnGrid())
                        {
                            if (crossing != null)
                            {
                                crossing.GenerateCarsOnRoad();
                                crossing.MonitorTrafficJamOnRoads();
                            }
                        }
                    }
                    x++;

                    grid.MoveCarsOnCrossing();

                    drawCars();

                    changeProgressBar((int)simulationDuarationTimer);
                    simulationDuarationTimer++;

                    Thread.Sleep(this.simulationSpeed);

                    if (simulationStatus == SimulationStatus.editting)
                    {
                        Monitor.Wait(threadLocker);
                    }
                }

                if (simulationStatus == SimulationStatus.running) // If simulation time is over then show simulation result
                {
                    if (updateSimulationStatus != null)
                    {
                        updateSimulationStatus();
                    }
                }
            }
        }

        /// <summary>
        ///Metohd stops simulation process and resets cars from the grid
        /// </summary>
        public void StopSimulation()
        {
            foreach (ICrossing crossing in grid.GetAllCrossingsOnGrid())
            {
                if (crossing != null)
                {
                    crossing.StopTrafficLightTimer();
                }
            }
            simulationDuarationTimer = 0;
            changeProgressBar(0);
            
            //drawCars();
        }

        public void drawCars()
        {
            view.drawOnCrossing(grid.GetAllCrossingsOnGrid());
        }

        /// <summary>
        /// ICrossing[,] GetAllCrossingsOnGrid() get the all crossing on the grid. 
        /// </summary>
        /// <returns> Return array[,] of the crossing</returns>
        public ICrossing[,] GetAllCrossingsOnGrid()
        {
            return grid.GetAllCrossingsOnGrid();
        }
    }
}
