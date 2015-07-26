package nl.vandoren.app.uraandroid.Fragment.WorkedHours;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import nl.vandoren.app.uraandroid.Fragment.WorkedHours.CalendarFragment.CalendarPageChangeListener;
import nl.vandoren.app.uraandroid.Fragment.WorkedHours.CalendarFragment.Calendar_fragment;
import nl.vandoren.app.uraandroid.Model.Project;
import nl.vandoren.app.uraandroid.Model.ProjectController;
import nl.vandoren.app.uraandroid.Model.User;
import nl.vandoren.app.uraandroid.R;


/**
 * Created by Alex on 5/19/2015.
 */
public class WorkedHours_fragment extends Fragment implements
        Dialog_projectList.CallBack, //WorkedHours list callBack
        Calendar_fragment.onSomeEventListener, // calendar callBack
        Dialog_notification.NotificationDialogCallBack //timer dialog callBack(invoked when timer is enabled and clicked new item in workedHoursList)
{

    private final String TAG = "myLogs";

    private int timerMinutes = 0; //amount of minutes
    private int timerHours = 0; // amount of hours
    public int calendarSelectedNumber; //ex MON =0, TUE = 1, WED = 2, etc
    public String calendarSelectedDate; //ex 2015-06-16
    private boolean weekStatus = true; //if status false then worked hours cannot be updated
    public boolean timerFlag; // timer button onClick flag

    private ViewPager pager;
    private ListView lvTaskList;
    public Button bt_selectProject;
    public RelativeLayout bt_addTask;
    public RelativeLayout bt_startTimer;
    private ImageView timerButtonIcon;
    public Spinner spinner_taskName;
    public EditText editText_hour;
    public EditText editText_minute;
    private TextView totalHours;
    private MenuItem menuItem;

    private CalendarPageChangeListener calendarPageChangeListener;

    public WorkedHours_fragment_controller controller;

    public WorkedHours_fragmentPagerAdapter workedHoursfragmentPagerAdapter;
    public WorkedHoursProjectList_adapter myProjectListAdapter;
    //WorkedHoursTask workedHoursTask;
    public Dialog_projectList projectListDialog;
    public Dialog_notification pDialog; //timer dialog, is invoked while changing screen and active timer
    private WorkedHours_listOnClickListener lvItemClickListener;
    private WorkedHours_listLongOnClickListener lvItemLongClickListener;

    static private Timer timer;

    public Project tempProject;
    private ArrayList<String> spinnerTaskList; //available project's task list


    public WorkedHours_fragment() {
        tempProject = new Project();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // manage menu's icon changing
        controller = new WorkedHours_fragment_controller(this);

        workedHoursfragmentPagerAdapter = new WorkedHours_fragmentPagerAdapter(getChildFragmentManager(),this); // "this" is used to subscribe for callBack"

        //Spinner button task list
        spinnerTaskList = new ArrayList<>();

        //Dialog window for selecting new project after clicking project button
        projectListDialog = new Dialog_projectList();
        projectListDialog.setCallBack(this);

        //adapter to display worked hours list
        myProjectListAdapter = new WorkedHoursProjectList_adapter(getActivity());

        //worked hours list onClickListener
        lvItemClickListener = new WorkedHours_listOnClickListener(this);
        lvItemLongClickListener = new WorkedHours_listLongOnClickListener(this);

        //read available projects
        controller.getAvailableProjects();

        //calendar week change listener(for swiping weeks). One page equals to one week
        calendarPageChangeListener =new CalendarPageChangeListener(workedHoursfragmentPagerAdapter,this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Locale.setDefault(Locale.UK);
        View rootView;

        if(User.getInstance().uraAccount_Dbid <0 ){
            rootView = inflater.inflate(R.layout.not_available, container, false);
        }
        else {
            rootView = inflater.inflate(R.layout.fragment_workedhours, container, false);

            //region set view screen elements
            pager = (ViewPager) rootView.findViewById(R.id.fragment_weekplanning_pager);
            lvTaskList = (ListView) rootView.findViewById(R.id.id_worked_hours_list);
            lvTaskList.setOnItemClickListener(lvItemClickListener);
            lvTaskList.setOnItemLongClickListener(lvItemLongClickListener);

            bt_addTask = (RelativeLayout) rootView.findViewById(R.id.id_addtask);
            bt_addTask.setOnClickListener(addTaskButton);

            bt_selectProject = (Button) rootView.findViewById(R.id.id_selectProject);
            bt_selectProject.setOnClickListener(projectButton);

            bt_startTimer = (RelativeLayout) rootView.findViewById(R.id.bt_timer);
            bt_startTimer.setOnClickListener(timerButton);

            timerButtonIcon = (ImageView) rootView.findViewById(R.id.id_timerIcon);

            spinner_taskName = (Spinner) rootView.findViewById(R.id.id_spinner_taskname);
            spinner_taskName.setOnItemSelectedListener(spinnerOnItemClickListener);

            editText_hour = (EditText) rootView.findViewById(R.id.id_ed_hour);
            editText_minute = (EditText) rootView.findViewById(R.id.id_ed_minute);

            totalHours = (TextView) rootView.findViewById(R.id.id_totalHours);
            //endregion

            ((Calendar_fragment) workedHoursfragmentPagerAdapter.getItem(0)).setPreviousWeek();
            ((Calendar_fragment) workedHoursfragmentPagerAdapter.getItem(2)).setNextWeek();

            calendarPageChangeListener.setPager(pager);
            pager.setOnPageChangeListener(calendarPageChangeListener);

            //set calendar Adapter
            pager.setAdapter(workedHoursfragmentPagerAdapter);
            //set start page 1
            pager.setCurrentItem(1, false);

            //set task list adapter(Time,Project,Task)
            lvTaskList.setAdapter(myProjectListAdapter);

            //read worked hours for the first week
            controller.loadFirstWeekWorkedProjects();

            timerFlag = false;
        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        menuItem = menu.findItem(R.id.datalist_lock);
        displayWeekStatusLock();
    }

    /**
     * for the week which has status 2 is used closed lock image in action bar
     */
    private void displayWeekStatusLock(){
        if(weekStatus){
            menuItem.setIcon(R.drawable.ic_lock_open);
        }
        else{
            menuItem.setIcon(R.drawable.ic_lock_close);
        }
    }

    /**
     *     Displays worked hours, projects names and tasks per week,
     *     After calendar scrolling, adding or updating tasks this method will be invoked
     */
    public void displayWorkedHoursList(ArrayList<Project> projectsList){

        try {
            //check status of workedHours, if not then change status and icon
            if (projectsList.size() != 0 && projectsList.get(0).taskStatus != 1) {
                weekStatus = false;
            } else {
                weekStatus = true;
            }
            displayWeekStatusLock();

            myProjectListAdapter.clearItems();
            String dayName = "";
            Project tempProject;
            for (int x = 0; x < projectsList.size(); x++) {
                tempProject = projectsList.get(x);
                /**
                 * Check new day name
                 */
                if (!dayName.equals(tempProject.projectDayName)) {
                    myProjectListAdapter.addSeparatorItem(tempProject.projectDayName);
                }
                myProjectListAdapter.addItem(tempProject.projectDayName,
                        tempProject.projectID, tempProject.projectTaskName,
                        tempProject.projectTaskDbid, tempProject.projectHours, x);

                dayName = tempProject.projectDayName;
            }
            myProjectListAdapter.notifyDataSetChanged();

            //Display Total Hours
            String weekHours= ProjectController.getTotalHours(projectsList);
            this.totalHours.setText(weekHours);
        }
        catch (Exception ex){
            Log.i(TAG, "DisplayWorkedHoursList Exception: " + ex.getMessage());
        }
    }

    /**
     * Project button onClickHandler
     */
    private View.OnClickListener projectButton = new  View.OnClickListener (){
        @Override
        public void onClick(View v){
            projectListDialog.show(getActivity().getFragmentManager(),"projects");
        }
    };

    /**
     * Task spinner(button) onClickHandler
     */
    private AdapterView.OnItemSelectedListener spinnerOnItemClickListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(tempProject.projectTaskDbid != tempProject.taskList.get(position).taskDBID){
                tempProject.projectTaskDbid = tempProject.taskList.get(position).taskDBID;
                tempProject.projectTaskName = tempProject.taskList.get(position).taskName;
                tempProject.tableUrenDbid =null;
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    /**
     * Timer button handler. After click the timer will be started. BUT if I open week planning timer values will be deleted.
     * If date and project is not selected, then timer button is not active.
     */
    private View.OnClickListener timerButton = new  View.OnClickListener (){
        Date dStart;
        Date dCurrent;

        int delay = 60000;
        long millis = 0;
        int newHours = 0;
        int newMinutes = 0;
        int hh = 0;
        int mm = 0;
        long diffInMs = 0;
        @Override
        public void onClick(View v) {
            if (bt_selectProject.getText().equals("Project")) {
                Toast.makeText(getActivity(), "Please select project", Toast.LENGTH_SHORT).show();
                return;
            } else if (calendarSelectedDate == null || calendarSelectedDate.equals("")) {
                Toast.makeText(getActivity(), "Please select date", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                if (!timerFlag) {
                    millis = System.currentTimeMillis();

                    //set start time
                    dStart = new Date(millis);

                    bt_startTimer.setBackgroundResource(R.drawable.bg_timer_on);
                    timerButtonIcon.setImageResource(R.drawable.clock_stop);
                    timer = new Timer();
                    if (!editText_hour.getText().toString().equals("")) {
                        timerHours = Integer.parseInt(editText_hour.getText().toString());

                    } else {
                        editText_hour.setText("0");
                    }
                    if (!editText_minute.getText().toString().equals("")) {
                        timerMinutes = Integer.parseInt(editText_minute.getText().toString());

                    } else {
                        editText_minute.setText("0");
                    }
                    //not possible to click and change time while working timer
                    editText_hour.setFocusable(false);
                    editText_minute.setFocusable(false);

                    timerFlag = !timerFlag;

                    timer.schedule((new TimerTask() {
                        @Override
                        public void run() {

                            millis = System.currentTimeMillis();
                            dCurrent = new Date(millis);

                            //find time difference and add to entered time
                            diffInMs = dCurrent.getTime() - dStart.getTime();
                            hh = (int) (TimeUnit.MILLISECONDS.toHours(diffInMs));
                            mm = (int) (TimeUnit.MILLISECONDS.toMinutes(diffInMs));
                            newHours = timerHours + hh;
                            newMinutes = timerMinutes + mm;

                            try {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                    editText_hour.setText(Integer.toString(newHours));
                                    editText_minute.setText(Integer.toString(newMinutes));
                                    }
                                });
                            } catch (Exception ex) {
                                Log.i(TAG, "TimerTask thread exception: " + ex.getMessage());
                            }
                        }
                    }), delay, delay);
                } else {
                    //Set all settings back to default
                    timerFlag = !timerFlag;
                    bt_startTimer.setBackgroundResource(R.drawable.bg_timer);
                    timerButtonIcon.setImageResource(R.drawable.clock_run);
                    editText_hour.setFocusableInTouchMode(true);
                    editText_minute.setFocusableInTouchMode(true);
                    timer.cancel();
                    timer.purge();
                    timer = null;
                }
            } catch (Exception ex) {
                Log.i(TAG, "TimerButton Exception: " + ex.getMessage());
            }
        }
    };

    //selectedWeekDayNumber is used to find right datum and dayNumber
    //region  addTask button Region
    private View.OnClickListener addTaskButton = new  View.OnClickListener (){
        @Override
        public void onClick(View v) {
            try {
                if (weekStatus) {
                    if (bt_selectProject.getText() != null && spinner_taskName.getSelectedItem() != null) {
                        timerHours = 0;
                        timerMinutes = 0;
                        if (timerFlag == true) {
                            bt_startTimer.performClick(); // stop timer
                        }

                        boolean hours = editText_hour.getText().toString().trim().length() > 0;
                        if (hours) {
                            timerHours = Integer.parseInt(editText_hour.getText().toString());
                        }

                        boolean minutes = editText_minute.getText().toString().trim().length() > 0;
                        if (minutes) {
                            timerMinutes = Integer.parseInt(editText_minute.getText().toString());
                        }

                        /**
                         * Selected time cannot be empty
                         */
                        if (timerHours != 0 || timerMinutes != 0) {
                            if (calendarSelectedDate != null) {
                                tempProject.projectDayNameNumber = calendarSelectedNumber;
                                tempProject.projectDate = calendarSelectedDate;
                                ProjectController.setNewTime(tempProject, timerHours, timerMinutes);
                                ProjectController.setDayNameByNumber(tempProject);
                                controller.saveTask(tempProject);
                            } else {
                                Toast.makeText(getActivity(), "Please select date", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Please enter worked hours", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please select project", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Selected week is already committed", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception ex){
                Log.i(TAG,"Add task button exception: "+ex.getMessage());
            }
        }

    };
    //endregion

    /**
     * Add task list to the spinner button
     * @param project project with available task list
     */
    public void setSpinnerTaskList(Project project){

        spinnerTaskList.clear();
        for(int i = 0;i<project.getTaskList().size();i++){
            spinnerTaskList.add(project.getTaskList().get(i).taskName);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, spinnerTaskList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_taskName.setAdapter(adapter);
    }

    //region Call Back Region

    //CallBack from WorkedHours_dialog_projectList
    @Override
    public void onListViewItemSelected(int selectedItem)  {
        //REFERENCE PROBLEM<MAYBE CREATE NEW PROJECT THAN MAKE REFERENCE

        this.tempProject = (Project)controller.availableProjectsList.get(selectedItem).clone();
        bt_selectProject.setText(tempProject.projectID);
        setSpinnerTaskList(this.tempProject);
    }

    //CallBack from WorkedHours_calendar_fragment
    @Override
    public void onCalendarClickListener(int number, String selectedDate) {
        calendarSelectedNumber = number;
        calendarSelectedDate = selectedDate;
    }

    //Dialog Timer callBack
    @Override
    public void notificationDialogOnClickListener(int respond) {
        if(respond == R.string.yes){
            bt_startTimer.performClick();
            bt_addTask.performClick();
        }else if(timer !=null){
            timer.cancel();
            timer.purge();
            timer=null;
        }
    }
    //endregion
}