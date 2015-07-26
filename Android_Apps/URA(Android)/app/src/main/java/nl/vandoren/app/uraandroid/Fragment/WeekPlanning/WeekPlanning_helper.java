package nl.vandoren.app.uraandroid.Fragment.WeekPlanning;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nl.vandoren.app.uraandroid.Model.WeekPlan;

/**
 * Created by Alex on 25-6-2015.
 */
public class WeekPlanning_helper {

    final String PROJECT = "Project";
    final String TASK = "Task";
    final String HOURS = "Hours";
    final String DATE = "Date";

    public ArrayList<WeekPlan> parseWorkedHoursFromJsonMessage(String message)
    {
        try {
            ArrayList<WeekPlan> myArray = new ArrayList<>();
            if(message.equals("")){
                return myArray;
            }
            JSONArray myList = new JSONArray(message);

            WeekPlan plan = null;

            for(int x = 0; x<myList.length();x++) {
                JSONObject o = myList.getJSONObject(x);
                plan = new WeekPlan(o.getString(PROJECT),o.getString(TASK),o.getString(HOURS),o.getString(DATE));
                myArray.add(plan);
            }
            return myArray;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
