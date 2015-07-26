package nl.vandoren.app.uraandroid.Fragment.WorkedHours;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Locale;

import nl.vandoren.app.uraandroid.Connection.WorkedHoursTask;
import nl.vandoren.app.uraandroid.Model.Project;

/**
 * Created by Alex on 25-6-2015.
 */
public class WorkedHours_fragment_controller {
    private final String TAG = "myLogs";
    private Handler myHandler; //handle connection return messages
    private WorkedHours_fragment myFragment;
    private ProgressDialog pDialog;
    private WorkedHoursTask workedHoursTask;
    private WorkedHours_helper helper; // parse Json documents from service

    public ArrayList<Project> workedHoursProjectsList; // per week
    public ArrayList<Project> availableProjectsList; // is used in buttonProjects

    private Project tempProject;
    private Project updateProject;



    public WorkedHours_fragment_controller(WorkedHours_fragment fragment){
        myHandler = new IncomingHandler();
        myFragment = fragment;

        availableProjectsList = new ArrayList<>();
        workedHoursProjectsList = new ArrayList<>();

        workedHoursTask = new WorkedHoursTask(myHandler);
        helper = new WorkedHours_helper();
    }

    /**
     * Reads current week worked tasks, Invoked for the first start of the screen
     */
    public void loadFirstWeekWorkedProjects(){
        Calendar c = (GregorianCalendar) Calendar.getInstance().clone();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        c.set(Calendar.DAY_OF_WEEK,c.getFirstDayOfWeek());
        String firstDayWeek = df.format(c.getTime());
        c.add(Calendar.DAY_OF_WEEK, 6);
        String lastDayWeek = df.format(c.getTime());

        workedHoursTask.getWorkedHourPerWeek(firstDayWeek, lastDayWeek);
    }

    /**
     * Reads available projects
     */
    public void getAvailableProjects(){
        workedHoursTask.getAvailableProjects();
    }

    /**
     * Reads worked hours per week
     * @param from From date
     * @param till Toll date
     */
    public void getWorkedHours(String from,String till){
        workedHoursTask.getWorkedHourPerWeek(from, till);
    }

    /**
     * Methods implements project updating or inserting functionality
     * @param p project which holds parameters of update/insert project
     */
    public void saveTask(Project p){
        this.tempProject = p;
        updateProject = findProjectInWorkedHoursList(tempProject);

        showProgressDialog();
        if(updateProject !=null){
            workedHoursTask.updateTask(updateProject, tempProject.projectHours);
        }else{
            //it need for situation when project task was selected from previous week.
            p.taskStatus = 1;
            workedHoursTask.addNewTask(p);
        }
    }


    /**
     * Remove itom from task list and delete message to the service
     * @param p project which holds parameters of update/insert project
     */
    public void deleteTask(Project p){
        this.tempProject = p;

        showProgressDialog();
        workedHoursTask.deleteTask(tempProject);

    }

    /**
     * Searches projects in worked hours list
     * @param p - project which is created or selected/updated
     * @return The project task was already created, then return existed object, this project has
     * tabelUren row ID (called "DBID")
     */
    public Project findProjectInWorkedHoursList(Project p){
        for(Project temp:workedHoursProjectsList) {
            if (temp.projectDayNameNumber == p.projectDayNameNumber) {
                if (temp.projectID.equals(p.projectID)) {
                    if (temp.projectTaskDbid.equals(p.projectTaskDbid)) {
                        return temp;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Show dialog box of progress
     */
    private void showProgressDialog(){
        if(myFragment.isVisible()) {
            pDialog = new ProgressDialog(myFragment.getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
    }

    /**
     * Disable progress/loading DialogBox
     */
    public void disableProgressDialog(){
        if(pDialog !=null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    /**
     * Class handles call backs after HTTP request
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            try {
                switch (msg.what) {
                    case 0:
                        //loaded worked hours
                        workedHoursProjectsList.clear();
                        helper.parseWorkedHoursFromJsonMessage((String) msg.obj, workedHoursProjectsList);

                        myFragment.displayWorkedHoursList(workedHoursProjectsList);

                        break;
                    case 1:
                        //error while loading worked hours
                        Toast.makeText(myFragment.getActivity(), "Error while loading worked hours", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        //loaded available projects
                        availableProjectsList.clear();
                        helper.parseAvailableProjectsFromJsonMessage((String) msg.obj, availableProjectsList);
                        if (availableProjectsList.size() != 0) {
                            for (int i = 0; i < availableProjectsList.size(); i++)
                                myFragment.projectListDialog.addProjectName(availableProjectsList.get(i).projectID);
                        }
                        break;
                    case 3:
                        //error while loading available projects
                        Toast.makeText(myFragment.getActivity(), "Error while loading available projects", Toast.LENGTH_LONG).show();
                        break;
                    case 4:
                        //inserted new task
                        tempProject.tableUrenDbid = msg.obj.toString();

                        workedHoursProjectsList.add((Project) tempProject.clone());

                        //Sort worked hours list
                        Collections.sort(workedHoursProjectsList);
                        myFragment.displayWorkedHoursList(workedHoursProjectsList);

                        Toast.makeText(myFragment.getActivity(), "Added new task", Toast.LENGTH_LONG).show();
                        break;
                    case 5:
                        //error while adding new task
                        Toast.makeText(myFragment.getActivity(), "Error while adding", Toast.LENGTH_LONG).show();
                        break;
                    case 6:
                        //updated  task
                        updateProject.taskList = tempProject.taskList;
                        updateProject.projectHours = tempProject.projectHours;
                        myFragment.displayWorkedHoursList(workedHoursProjectsList);
                        Toast.makeText(myFragment.getActivity(), "Task updated", Toast.LENGTH_LONG).show();
                        break;
                    case 7:
                        //Error occurred while updating
                        Toast.makeText(myFragment.getActivity(), "Error while updating", Toast.LENGTH_LONG).show();
                        break;
                    case 8:
                        //delete task
                        workedHoursProjectsList.remove(tempProject);
                        myFragment.displayWorkedHoursList(workedHoursProjectsList);
                        Toast.makeText(myFragment.getActivity(), "Task deleted", Toast.LENGTH_LONG).show();
                        break;
                    case 9:
                        //Error occurred while deleting
                        Toast.makeText(myFragment.getActivity(), "Error while deleting", Toast.LENGTH_LONG).show();
                        break;
                }
            }
            catch (Exception ex){
                Log.i(TAG, "WorkedHoursController Handler Exception: " + ex.getMessage());
            }

            if(msg.what >= 4) {
                disableProgressDialog();
            }
        }
    };
}
