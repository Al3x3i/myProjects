package nl.vandoren.app.uraandroid.Model;

import java.util.ArrayList;

/**
 * Created by Alex on 6/11/2015.
 */
public class Project implements Comparable<Project>,Cloneable {

    //After adding new day to the workedList the list should be sorted
    @Override
    public int compareTo(Project project) {
        return this.projectDayNameNumber<project.projectDayNameNumber? -1:
                this.projectDayNameNumber>project.projectDayNameNumber? 1 : 0;
    }

    public Project clone() {
        try {
            return (Project)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String tableUrenDbid;  //DBID in tabelUren
    public String projectHours; // Worked hours
    public int taskStatus; // status of task, commitment status
    public String projectTaskDbid; //for spinner and worked hours constructor
    public String projectTaskName; //The task name from tabelTask
    public String projectDayName; //Zondag,Maandag, Dinsdag...Zaterdag
    public String projectID;  // EX. A1219B1.01
    public int projectDayNameNumber; //0,1,2,3...6
    public String projectDate; //Right format of date = 2015-06-10 .Send to the WCF service
    public ArrayList<Pair> taskList; //available task for this project

    public ArrayList<Pair> getTaskList(){return taskList;}

    /**
     * Project stores tasks
     */
    public static class Pair{
        public String taskDBID;
        public String taskName;
        public Pair(String id, String name){
            taskDBID = id;
            taskName = name;
        }
    }
}
