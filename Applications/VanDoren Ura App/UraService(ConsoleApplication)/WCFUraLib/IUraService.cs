using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.ServiceModel;

namespace WCFUraLib
{
    [ServiceContract]
    public interface IUraService
    {
        [OperationContract]
        string WorkedHoursPerWeek(string[] myParams);

        [OperationContract]
        string AvailableProjects();

        [OperationContract]
        string AddNewTaskHours(Dictionary<string, string> myParams);

        [OperationContract]
        string UpdateTaskHours(Dictionary<string, string> myParams);

        [OperationContract]
        string GetWeekPlanning(string[] myParams);

        [OperationContract]
        string DeleteTaskHours(Dictionary<string, string> myParams);

        [OperationContract]
        string Test();
    }
}
