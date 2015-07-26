package nl.vandoren.app.uraandroid.Fragment.WorkedHours;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nl.vandoren.app.uraandroid.Model.Project;
import nl.vandoren.app.uraandroid.Model.ProjectController;

/**
 * Created by Alex on 6/11/2015.
 */
public class WorkedHours_helper {

    //parins worked projects/task
    final String TABLE_ROW_DBID = "DBID";
    final String DATE = "Date";
    final String DAY_NAME = "Day";
    final String WORKEDHOURS = "UrenWorked";
    final String TASK_DBID = "tabelTakenDBID";
    final String TASK_NAME = "TaakNaam";
    final String PROJECT_NAME = "ProjectID";
    final String STATUS_HOUR="StatusHour";

    //Parsing available projects
    final String TASK_ID = "task_id";
    final String NAME = "task_name";


    /**
     *
     * @param message JSON string which should be parsed
     * @param workedProjectsList List of workedHour
     */
    public void parseWorkedHoursFromJsonMessage(String message, ArrayList<Project> workedProjectsList)
    {
        try {
            JSONArray myList = new JSONArray(message);
            Project tempProject = null;

            for(int x = 0; x<myList.length();x++) {
                JSONObject o = myList.getJSONObject(x);
                tempProject = ProjectController.createWorkedProjectHours(o.getString(TABLE_ROW_DBID),
                        o.getString(DATE), o.getString(DAY_NAME), o.getString(WORKEDHOURS),
                        o.getString(TASK_DBID), o.getString(TASK_NAME), o.getString(PROJECT_NAME),
                        o.getInt(STATUS_HOUR));
                workedProjectsList.add(tempProject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Fill in available projects to the list
     * @param message Json document
     * @param availableProjectsList list to which is added available projects
     */
    public void parseAvailableProjectsFromJsonMessage(String message, ArrayList<Project> availableProjectsList)
    {
        try {

            Project tempProject = null;
            JSONArray myTasks = null;
            JSONArray myList = new JSONArray(message);

            for(int i =0;i<myList.length();i+=2){
                tempProject =ProjectController.createAvailableProject(myList.get(i).toString());
                myTasks = new JSONArray(myList.get(i+1).toString());
                for(int y=0;y<myTasks.length();y++){
                    JSONObject o = myTasks.getJSONObject(y);
                    ProjectController.addTaskToProject(tempProject, o.getString(TASK_ID), o.getString(NAME));
                }
                availableProjectsList.add(tempProject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
