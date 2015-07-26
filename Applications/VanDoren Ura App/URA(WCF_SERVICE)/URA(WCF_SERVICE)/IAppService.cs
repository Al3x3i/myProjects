using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.Text;
using System.ServiceModel.Web;
using System.IO;

namespace URA_WCF_SERVICE_
{
    [ServiceContract]
    public interface IAppService
    {
        [OperationContract]
        [WebGet(BodyStyle = WebMessageBodyStyle.Bare)]
        string test();

        [OperationContract]
        [WebGet(BodyStyle = WebMessageBodyStyle.Bare)]
        string HelloMessage(string message, string phoneIMEI);

        [OperationContract]
        [WebGet(BodyStyle = WebMessageBodyStyle.Bare)]
        string KeyExchange(string message, string sessionID);

        [OperationContract]
        [WebInvoke(Method = "POST")]
        Stream LoginMessage(Stream entity);

        [OperationContract]
        [WebGet(UriTemplate = "WorkedHours?0={message}&1={sessionID}", BodyStyle = WebMessageBodyStyle.Bare)]
        Stream WorkedHoursPerWeek(string message, string sessionID);

        [OperationContract]
        [WebGet(UriTemplate = "REMOVED", BodyStyle = WebMessageBodyStyle.Wrapped)]
        Stream AvailableProjects(string message, string sessionID);

        [OperationContract]
        [WebInvoke(UriTemplate = "REMOVED", Method = "PUT", RequestFormat = WebMessageFormat.Json)]
        string AddNewTaskHours(string message, string sessionID);

        [OperationContract]
        [WebInvoke(UriTemplate = "REMOVED", Method = "PUT", RequestFormat = WebMessageFormat.Json)]
        string UpdateTaskHours(string message, string sessionID);

        [OperationContract]
        [WebInvoke(UriTemplate = "REMOVED", Method = "GET", RequestFormat = WebMessageFormat.Json)]
        string GetWeekPlanning(string message, string sessionID);

        [OperationContract]
        [WebInvoke(UriTemplate = "REMOVED", Method = "DELETE", RequestFormat = WebMessageFormat.Json)]
        string DeleteTaskHours(string message, string sessionID);
    }
}
