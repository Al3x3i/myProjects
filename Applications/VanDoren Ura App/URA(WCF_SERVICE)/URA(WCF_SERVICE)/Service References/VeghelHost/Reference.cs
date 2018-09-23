﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.34209
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace URA_WCF_SERVICE_.VeghelHost {
    
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.ServiceContractAttribute(ConfigurationName="VeghelHost.IUraService")]
    public interface IUraService {
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IUraService/WorkedHoursPerWeek", ReplyAction="http://tempuri.org/IUraService/WorkedHoursPerWeekResponse")]
        string WorkedHoursPerWeek(string[] myParams);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IUraService/WorkedHoursPerWeek", ReplyAction="http://tempuri.org/IUraService/WorkedHoursPerWeekResponse")]
        System.Threading.Tasks.Task<string> WorkedHoursPerWeekAsync(string[] myParams);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IUraService/AvailableProjects", ReplyAction="http://tempuri.org/IUraService/AvailableProjectsResponse")]
        string AvailableProjects();
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IUraService/AvailableProjects", ReplyAction="http://tempuri.org/IUraService/AvailableProjectsResponse")]
        System.Threading.Tasks.Task<string> AvailableProjectsAsync();
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IUraService/AddNewTaskHours", ReplyAction="http://tempuri.org/IUraService/AddNewTaskHoursResponse")]
        string AddNewTaskHours(System.Collections.Generic.Dictionary<string, string> myParams);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IUraService/AddNewTaskHours", ReplyAction="http://tempuri.org/IUraService/AddNewTaskHoursResponse")]
        System.Threading.Tasks.Task<string> AddNewTaskHoursAsync(System.Collections.Generic.Dictionary<string, string> myParams);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IUraService/UpdateTaskHours", ReplyAction="http://tempuri.org/IUraService/UpdateTaskHoursResponse")]
        string UpdateTaskHours(System.Collections.Generic.Dictionary<string, string> myParams);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IUraService/UpdateTaskHours", ReplyAction="http://tempuri.org/IUraService/UpdateTaskHoursResponse")]
        System.Threading.Tasks.Task<string> UpdateTaskHoursAsync(System.Collections.Generic.Dictionary<string, string> myParams);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IUraService/GetWeekPlanning", ReplyAction="http://tempuri.org/IUraService/GetWeekPlanningResponse")]
        string GetWeekPlanning(string[] myParams);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IUraService/GetWeekPlanning", ReplyAction="http://tempuri.org/IUraService/GetWeekPlanningResponse")]
        System.Threading.Tasks.Task<string> GetWeekPlanningAsync(string[] myParams);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IUraService/DeleteTaskHours", ReplyAction="http://tempuri.org/IUraService/DeleteTaskHoursResponse")]
        string DeleteTaskHours(System.Collections.Generic.Dictionary<string, string> myParams);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IUraService/DeleteTaskHours", ReplyAction="http://tempuri.org/IUraService/DeleteTaskHoursResponse")]
        System.Threading.Tasks.Task<string> DeleteTaskHoursAsync(System.Collections.Generic.Dictionary<string, string> myParams);
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IUraService/Test", ReplyAction="http://tempuri.org/IUraService/TestResponse")]
        string Test();
        
        [System.ServiceModel.OperationContractAttribute(Action="http://tempuri.org/IUraService/Test", ReplyAction="http://tempuri.org/IUraService/TestResponse")]
        System.Threading.Tasks.Task<string> TestAsync();
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public interface IUraServiceChannel : URA_WCF_SERVICE_.VeghelHost.IUraService, System.ServiceModel.IClientChannel {
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    public partial class UraServiceClient : System.ServiceModel.ClientBase<URA_WCF_SERVICE_.VeghelHost.IUraService>, URA_WCF_SERVICE_.VeghelHost.IUraService {
        
        public UraServiceClient() {
        }
        
        public UraServiceClient(string endpointConfigurationName) : 
                base(endpointConfigurationName) {
        }
        
        public UraServiceClient(string endpointConfigurationName, string remoteAddress) : 
                base(endpointConfigurationName, remoteAddress) {
        }
        
        public UraServiceClient(string endpointConfigurationName, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(endpointConfigurationName, remoteAddress) {
        }
        
        public UraServiceClient(System.ServiceModel.Channels.Binding binding, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(binding, remoteAddress) {
        }
        
        public string WorkedHoursPerWeek(string[] myParams) {
            return base.Channel.WorkedHoursPerWeek(myParams);
        }
        
        public System.Threading.Tasks.Task<string> WorkedHoursPerWeekAsync(string[] myParams) {
            return base.Channel.WorkedHoursPerWeekAsync(myParams);
        }
        
        public string AvailableProjects() {
            return base.Channel.AvailableProjects();
        }
        
        public System.Threading.Tasks.Task<string> AvailableProjectsAsync() {
            return base.Channel.AvailableProjectsAsync();
        }
        
        public string AddNewTaskHours(System.Collections.Generic.Dictionary<string, string> myParams) {
            return base.Channel.AddNewTaskHours(myParams);
        }
        
        public System.Threading.Tasks.Task<string> AddNewTaskHoursAsync(System.Collections.Generic.Dictionary<string, string> myParams) {
            return base.Channel.AddNewTaskHoursAsync(myParams);
        }
        
        public string UpdateTaskHours(System.Collections.Generic.Dictionary<string, string> myParams) {
            return base.Channel.UpdateTaskHours(myParams);
        }
        
        public System.Threading.Tasks.Task<string> UpdateTaskHoursAsync(System.Collections.Generic.Dictionary<string, string> myParams) {
            return base.Channel.UpdateTaskHoursAsync(myParams);
        }
        
        public string GetWeekPlanning(string[] myParams) {
            return base.Channel.GetWeekPlanning(myParams);
        }
        
        public System.Threading.Tasks.Task<string> GetWeekPlanningAsync(string[] myParams) {
            return base.Channel.GetWeekPlanningAsync(myParams);
        }
        
        public string DeleteTaskHours(System.Collections.Generic.Dictionary<string, string> myParams) {
            return base.Channel.DeleteTaskHours(myParams);
        }
        
        public System.Threading.Tasks.Task<string> DeleteTaskHoursAsync(System.Collections.Generic.Dictionary<string, string> myParams) {
            return base.Channel.DeleteTaskHoursAsync(myParams);
        }
        
        public string Test() {
            return base.Channel.Test();
        }
        
        public System.Threading.Tasks.Task<string> TestAsync() {
            return base.Channel.TestAsync();
        }
    }
}