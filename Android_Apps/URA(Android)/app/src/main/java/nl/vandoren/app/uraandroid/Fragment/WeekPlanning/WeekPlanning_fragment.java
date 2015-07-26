package nl.vandoren.app.uraandroid.Fragment.WeekPlanning;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import nl.vandoren.app.uraandroid.Connection.WeekPlanningTask;
import nl.vandoren.app.uraandroid.Fragment.WorkedHours.CalendarFragment.Calendar_fragment;
import nl.vandoren.app.uraandroid.Model.WeekPlan;
import nl.vandoren.app.uraandroid.R;

/**
 * Created by Alex on 5/19/2015.
 */
public class WeekPlanning_fragment extends Fragment {
    final String TAG = "myLogs";

    ViewPager pager;
    ListView lvProjectList;

    PagerAdapter myPagerAdapter;
    WeekPlanning_adapter myProjectListAdapter;

    WeekPlanningTask planningTask;
    WeekPlanning_helper helper;
    IncomingHandler myHandler;
    ArrayList<WeekPlan> myPlanning;
    private List<String> dayList;

    MenuItem menuItem;
    CalendarChangeListener calendarPageChangeListener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myHandler = new IncomingHandler();
        planningTask = new WeekPlanningTask(myHandler);
        helper = new WeekPlanning_helper();
        myPlanning = new ArrayList<>();
        dayList = new ArrayList<String>();
        Locale.setDefault(Locale.UK);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // manage menu's icon changing
        myPagerAdapter = new PagerAdapter(getChildFragmentManager()); // "this is used to subscribe for callBack"
        myProjectListAdapter = new WeekPlanning_adapter(getActivity());
        calendarPageChangeListener =new CalendarChangeListener(myPagerAdapter,this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weekplanning, container, false);
        pager = (ViewPager) rootView.findViewById(R.id.fragment_weekplanning_pager);
        lvProjectList = (ListView) rootView.findViewById(R.id.id_weekplanning_list);


        ((Calendar_fragment) myPagerAdapter.getItem(0)).setPreviousWeek();
        ((Calendar_fragment) myPagerAdapter.getItem(2)).setNextWeek();

        calendarPageChangeListener.setPager(pager);
        pager.setOnPageChangeListener(calendarPageChangeListener);

        //set calendar Adapter
        pager.setAdapter(myPagerAdapter);

        //set start page 1
        pager.setCurrentItem(1, false);
        lvProjectList.setAdapter(myProjectListAdapter);
        loadWeekPlanning();

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        menuItem = menu.findItem(R.id.datalist_lock);
        menuItem.setVisible(false);
    }

    /**
     * Reads week planning of the last week
     */
    public void loadWeekPlanning(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        Calendar c = (GregorianCalendar) Calendar.getInstance().clone();
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        String itemvalue;
        for (int n = 0; n < 7; n++) {
            itemvalue = df.format(c.getTime());
            dayList.add(itemvalue);
            c.add(Calendar.DAY_OF_WEEK, 1);
        }
        planningTask.getWeekPlanning(dayList.get(0), dayList.get(6));
    }

    /**
     * After swiping calendar the method will be invoked to display new week planning
     * @param tempList
     */
    public void getWeekPlanning(List<String> tempList){
        this.dayList = tempList;
        planningTask.getWeekPlanning(dayList.get(0), dayList.get(6));
    }

    private void setDayNameByDate(ArrayList<WeekPlan> planList, List<String> tempList){
        String tempDate;
        for(int x = 0; x<planList.size();x++)
        {
            tempDate = planList.get(x).date;
            for(int y = 0; y<tempList.size();y++){
                if((tempDate.contains(tempList.get(y)))){
                    planList.get(x).setDayNameByNumber(y);
                    break;
                }
            }
        }
    }

    private void displayWeekPlanning(ArrayList<WeekPlan> planList){
        try {
            myProjectListAdapter.clearItems();
            String dayName = "";
            WeekPlan tempPlan;
            for (int x = 0; x < planList.size(); x++) {
                tempPlan = planList.get(x);
                if (!dayName.equals(tempPlan.dayName)) {
                    myProjectListAdapter.addSeparatorItem(tempPlan.dayName);
                }
                myProjectListAdapter.addItem(tempPlan.dayName, tempPlan.project, tempPlan.task,
                        tempPlan.hours);
                dayName = tempPlan.dayName;
            }
            myProjectListAdapter.notifyDataSetChanged();
        }
        catch (Exception ex){
            Log.i(TAG, "DisplayWeekPlanning Exception: " + ex.getMessage());
        }
    }

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            try {
                switch (msg.what) {
                    case 0:
                        myPlanning = helper.parseWorkedHoursFromJsonMessage((String) msg.obj);
                        if (myPlanning != null) {
                            setDayNameByDate(myPlanning, dayList);
                            displayWeekPlanning(myPlanning);
                        } else {
                            Toast.makeText(getActivity(), "Error while parsing week planning", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case 1:
                        //error while loading week planning
                        Toast.makeText(getActivity(), "Error while loading week planning", Toast.LENGTH_LONG).show();
                        break;
                }
            }
            catch (Exception ex){
                Log.i(TAG, "WeekPlanning Handler Exception: " + ex.getMessage());
            }
        }
    };
}
