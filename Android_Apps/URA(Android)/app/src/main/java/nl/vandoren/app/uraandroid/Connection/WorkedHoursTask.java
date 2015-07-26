package nl.vandoren.app.uraandroid.Connection;

import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;

import nl.vandoren.app.uraandroid.Model.Project;

/**Class implements tasks of Worked Hours fragment
 * Created by Alex on 6/2/2015.
 */
public class WorkedHoursTask extends BaseConnectionParameters{

    //TODO ADD MOBILE PHONE VALIDATION, BECAUSE ONLY ONE CAN USE SAME LOGIN
    ConnectionManager connectionManager = ConnectionManager.getInstance();
    JSONObject plainJsonObject;
    Message handlerMsg;
    Handler myHandler;

    //New task
    final String DATE = "Date";
    final String HOURS = "Hours";
    final String TASK_DBID = "TaskDbid";
    final String SYNTUS = "Syntus";
    final String EMPLOYEE_DBID = "EmployeesDBID";

    // extra for update task
    final String UREN_DBID = "DBID";

    public WorkedHoursTask(Handler h){
        myHandler=h;
    }

    /**
     * Reads available projects from service, For this creates request parameters and invoke http request
     * in new Thread, is used GET method. Through handler returns back the message from service.
     */
    public void getAvailableProjects(){
        try {
            plainJsonObject = new JSONObject();
            plainJsonObject.put("phoneImei", super.phoneImei);
            final String url = super.getAvailableProjectsURL();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    String respond = connectionManager.executeHTTPRequest(plainJsonObject, url, "GET");
                    if (respond!=null)
                    {
                        handlerMsg = myHandler.obtainMessage(2,respond);
                        myHandler.sendMessage(handlerMsg);
                    }else{
                        myHandler.sendEmptyMessage(3);
                    }
                }
            }).start();

        }
        catch (Exception ex)
        {
            myHandler.sendEmptyMessage(3);
        }

    }

    /**
     * Reads worked hours/tasks per week, For this creates request parameters and invoke http request
     * in new Thread, is used GET method. Through handler returns back the message from service.
     * @param firstDay from date
     * @param lastDay till date
     */
    public void getWorkedHourPerWeek(String firstDay, String lastDay)
    {
        try {
            String searchWord =  firstDay + ";" +lastDay + ";" + super.getUraAccount_DBID();
            plainJsonObject = new JSONObject();  // Will include message and digital signature
            plainJsonObject.put("phoneImei", super.phoneImei);
            plainJsonObject.put("searchWord", searchWord);
            final String url = this.getWorkedHoursURL();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String respond = connectionManager.executeHTTPRequest(plainJsonObject, url, "GET");
                    if (respond!=null)
                    {
                        handlerMsg = myHandler.obtainMessage(0,respond);
                        myHandler.sendMessage(handlerMsg);
                    }else{
                        myHandler.sendEmptyMessage(1);
                    }
                }
            }).start();
        }
        catch (Exception ex)
        {
            myHandler.sendEmptyMessage(1);
        }
    }

    /**
     *  Update worked hours of existed task. Creates new Thread and invoke http request with
     *  PUT method
     * @param project Project/Task parameters
     * @param newTime new worked hours
     */
    public void updateTask(Project project, String newTime){

        try {

            JSONObject tempArray = new JSONObject();
            tempArray.put(UREN_DBID,project.tableUrenDbid);  //tableUren_DBID
            tempArray.put(DATE,project.projectDate);  //Date
            tempArray.put(TASK_DBID,project.projectTaskDbid);  //Task
            tempArray.put(HOURS,newTime);  //Hours
            tempArray.put(EMPLOYEE_DBID,super.getUraAccount_DBID()); //URA DBID

            String searchWord = tempArray.toString();
            plainJsonObject = new JSONObject();  // Will include message and digital signature
            plainJsonObject.put("phoneImei", super.phoneImei);
            plainJsonObject.put("searchWord", searchWord);
            final String url = this.updateTaskURL();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String respond = connectionManager.executeHTTPRequest(plainJsonObject, url, "PUT");
                    if (respond!=null && respond.equals("UPDATED"))
                    {
                        handlerMsg = myHandler.obtainMessage(6,respond);
                        myHandler.sendMessage(handlerMsg);
                    }
                    else{
                        myHandler.sendEmptyMessage(7);
                    }
                }
            }).start();
        }
        catch (Exception ex)
        {
            myHandler.sendEmptyMessage(7);
        }
    }

    /**
     * Send to the service new project/task. If project is inserted, then will return tabelUren's
     * row ID, handler handles respond
     * @param p
     */
    public void addNewTask(Project p)
    {
        try {
            // check constraints

            JSONObject tempArray = new JSONObject();
            tempArray.put(DATE,p.projectDate);  //Date
            tempArray.put(HOURS,p.projectHours);  //Hours
            tempArray.put(TASK_DBID,p.projectTaskDbid);  //TaskDBID


            String respond = checkNewTaskConstraints(p);
            if(respond ==null) {
                tempArray.put(SYNTUS,super.getURA_syntus()); //syntus
            }else{
                tempArray.put(SYNTUS,respond); //syntus
            }
            tempArray.put(EMPLOYEE_DBID, super.getUraAccount_DBID()); //EmployeeID
            String searchWord = tempArray.toString();
            plainJsonObject = new JSONObject();  // Will include message and digital signature
            plainJsonObject.put("phoneImei", super.phoneImei);
            plainJsonObject.put("searchWord", searchWord);
            final String url = this.addNewTaskURL();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    //The respond can be INSERTED;newRowDBID or NOTINSERTED
                    String[] respond = connectionManager.executeHTTPRequest(plainJsonObject, url, "PUT").split(";");
                    if (respond!=null && respond[0].equals("INSERTED"))
                    {
                        handlerMsg = myHandler.obtainMessage(4,respond[1]);
                        myHandler.sendMessage(handlerMsg);
                    }else{
                        myHandler.sendEmptyMessage(5);
                    }

                }
            });
            t.start();
            t.join();
        }
        catch (Exception ex)
        {
            myHandler.sendEmptyMessage(5);
        }
    }
    /**
     * Call service method to delete task from database
     * @param project
     */

    public void deleteTask(Project project)
    {
        try {

            JSONObject tempArray = new JSONObject();
            tempArray.put(UREN_DBID,project.tableUrenDbid);  //tableUren_DBID
            tempArray.put(DATE,project.projectDate);  //Date
            tempArray.put(TASK_DBID,project.projectTaskDbid);  //Task
            tempArray.put(HOURS,project.projectHours);  //Hours
            tempArray.put(EMPLOYEE_DBID,super.getUraAccount_DBID()); //URA_DBID

            String searchWord = tempArray.toString();
            plainJsonObject = new JSONObject();  // Will include message and digital signature
            plainJsonObject.put("phoneImei", super.phoneImei);
            plainJsonObject.put("searchWord", searchWord);
            final String url = this.deleteTaskURL();
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    //The respond can be DELETED or NOTDELETED
                    String respond = connectionManager.executeHTTPRequest(plainJsonObject,
                            url, "DELETE");
                    if (respond!=null && respond.equals("DELETED"))
                    {
                        handlerMsg = myHandler.obtainMessage(8,respond);
                        myHandler.sendMessage(handlerMsg);
                    }else{
                        myHandler.sendEmptyMessage(9);
                    }
                }
            });
            t.start();//Don't use t.join - because I have dialog message.
        }
        catch (Exception ex)
        {
            myHandler.sendEmptyMessage(9);
        }
    }
    /**
     * Check new task constraints
     * In case "overige" task then ura_syntus should be ="overig"
     */
    private String checkNewTaskConstraints(Project project){

        if(project.projectID.equals("Overige")){
            return "Overig";
        }
        return null;
    }
}
