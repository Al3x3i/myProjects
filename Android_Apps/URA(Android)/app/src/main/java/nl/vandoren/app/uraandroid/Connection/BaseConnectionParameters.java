package nl.vandoren.app.uraandroid.Connection;

import nl.vandoren.app.uraandroid.Model.User;

/**Class is used as storage of all connection and login settings
 * Created by Alex on 6/2/2015.
 */


public class BaseConnectionParameters {
    protected User user;

    protected String defaultAddressURL = //removed
    //protected String defaultAddressURL = "http://192.168.1.138:49309/AppService.svc/";
    protected String userSessionID[] = new String[1]; //is used in service request
    public static String phoneImei;

    private final static String workedHoursCommand = "WorkedHours";
    private final static String availableProjectsCommand = "AvailableProjects";
    private final static String addNewTaskCommand = "AddNewTaskHours";
    private final static String updateTaskCommand = "UpdateTaskHours";
    private final static String deleteTaskCommand = "DeleteTaskHours";
    private final static String weekPlanningCommand = "WeekPlanning";

    public BaseConnectionParameters()
    {
        user = User.getInstance();
    }

    public String getWorkedHoursURL()
    {
        return defaultAddressURL + workedHoursCommand;
    }
    public String addNewTaskURL()
    {
        return defaultAddressURL + addNewTaskCommand;
    }
    public String updateTaskURL()
    {
        return defaultAddressURL + updateTaskCommand;
    }
    public String deleteTaskURL()
    {
        return defaultAddressURL + deleteTaskCommand;
    }
    public String getWeekPlanningURL()
    {
        return defaultAddressURL + weekPlanningCommand;
    }
    public String getAvailableProjectsURL()
    {
        return defaultAddressURL + availableProjectsCommand;
    }

    public int getUraAccount_DBID(){return user.uraAccount_Dbid;}
    public String getURA_syntus(){return user.ura_syntus;}
    public String getCapa_DBID(){return user.capa_Dbid;}
}
