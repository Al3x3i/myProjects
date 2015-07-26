package nl.vandoren.app.uraandroid.Model;

import java.util.ArrayList;

/**
 * Created by Aleksei on 6/20/2015.
 */
public class ProjectController {

    /**
     * Constructor is used to create available projects
     * @param projectID Id of project
     * @return returns created project
     */
    public static Project createAvailableProject(String projectID){
        Project p = new Project();
        p.projectID = projectID;
        p.taskList = new ArrayList<>();
        return p;
    }

    /**
     * Constructor is used to create worked projects which includes in past or current
     *
     * @param tableUrenDbid - table tableUren row ID
     * @param date - the date of worked task
     * @param dayName - name of task, Monday, Tuesday etc..
     * @param worked_hours - worked hours
     * @param taskDbid - task id
     * @param taskName - name of task
     * @param projectName - project name/ID
     * @return return new Project
     */
    public static Project createWorkedProjectHours(String tableUrenDbid,String date, String dayName,
                                                   String worked_hours, String taskDbid,
                                                   String taskName, String projectName, int status){
        Project p = new Project();
        p.tableUrenDbid = tableUrenDbid;
        p.projectDate = date;
        setDayNumberByName(p,dayName);
        p.projectHours = worked_hours;
        p.projectTaskDbid = taskDbid;
        p.projectTaskName = taskName;
        p.projectID = projectName;
        p.taskStatus = status;
        return p;
    }


    /**
     * After selecting item from worked list the existed tasks of project will added to
     * transferred  project
     * @param p - project to which is added new task
     * @param taskID - task id
     * @param taskName - task name
     */
    public static void addTaskToProject(Project p, String taskID, String taskName) {
        p.taskList.add(new Project.Pair(taskID,taskName));
    }

    /**
     * Method set project day number. Late this number is needed for day sorting in list
     * @param p project
     * @param dayName name of day
     */
    public static void setDayNumberByName(Project p, String dayName){
        p.projectDayName = dayName;
        switch(dayName){
            case "maandag":p.projectDayNameNumber=0;break;
            case "dinsdag":p.projectDayNameNumber=1;break;
            case "woensdag":p.projectDayNameNumber=2;break;
            case "donderdag":p.projectDayNameNumber=3;break;
            case "vrijdag":p.projectDayNameNumber=4;break;
            case "zaterdag":p.projectDayNameNumber=5;break;
            case "zondag":p.projectDayNameNumber=6;break;
        }
    }

    /**
     * Method sets day name to transferred project. The method is needed for creating new project
     * and adding to the worked hours
     * @param p
     */
    public static void setDayNameByNumber(Project p){
        int dayNumber = p.projectDayNameNumber;
        switch(dayNumber){
            case 0 :p.projectDayName="maandag";break;
            case 1 :p.projectDayName="dinsdag";break;
            case 2 :p.projectDayName="woensdag";break;
            case 3 :p.projectDayName="donderdag";break;
            case 4 :p.projectDayName="vrijdag";break;
            case 5 :p.projectDayName="zaterdag";break;
            case 6 :p.projectDayName="zondag";break;
        }
    }

    public static void setNewTime(Project p, int timerHours, int timerMinutes){

        Float timerTime = 0f;

        timerTime = (float)timerHours + timerMinutes/60;
        timerTime += ((float)timerMinutes % 60)/100;

        String t = String.format("%.2f", timerTime);
        p.projectHours = t;
    }

    public static int[] getProjectTimeFromString(Project p){
        String [] time = p.projectHours.split("\\.");
        int[] time2 = new int[2];
        time2[0] = Integer.parseInt(time[0]); // hours
        time2[1] = Integer.parseInt(time[1]); // minutes
        if(time2[1]<6 && time[1].charAt(0)!='0'){
            time2[1] = time2[1] *10;
        }
        return time2;
    }

    public static String getTotalHours(ArrayList<Project> projectList){

        int [] tempHours;
        Float timerTime = 0f;
        int [] totalTime = new int[2];

        for (Project p :projectList){
            tempHours = getProjectTimeFromString (p);
            totalTime[0] += tempHours[0]; //hours
            totalTime[1] += tempHours[1]; //minutes
        }

        timerTime = (float)totalTime[0] + totalTime[1]/60;
        timerTime += ((float)totalTime[1] % 60)/100;

        String t = String.format("%.2f", timerTime);

        return t;
    }

}
