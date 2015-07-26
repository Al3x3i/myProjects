package nl.vandoren.app.uraandroid.Model;

/**
 * Created by Alex on 25-6-2015.
 *
 * Class holds information of worked hours
 */
public class WeekPlan{
    public String project;
    public String task;
    public String hours;
    public String date;
    public String dayName;

    public WeekPlan(String project, String task, String hours,String date){
        this.project = project;
        this.task = task;
        this.hours = hours;
        this.date = date;
    }

    public void setDayNameByNumber(int dayNumber){
        switch(dayNumber){
            case 0 :this.dayName="maandag";break;
            case 1 :this.dayName="dinsdag";break;
            case 2 :this.dayName="woensdag";break;
            case 3 :this.dayName="donderdag";break;
            case 4 :this.dayName="vrijdag";break;
            case 5 :this.dayName="zaterdag";break;
            case 6 :this.dayName="zondag";break;
        }
    }
}
