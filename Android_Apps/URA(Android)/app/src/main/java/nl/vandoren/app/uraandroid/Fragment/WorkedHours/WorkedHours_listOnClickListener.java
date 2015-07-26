package nl.vandoren.app.uraandroid.Fragment.WorkedHours;

import android.view.View;
import android.widget.AdapterView;

import nl.vandoren.app.uraandroid.Fragment.WorkedHours.CalendarFragment.Calendar_fragment;
import nl.vandoren.app.uraandroid.Model.Project;
import nl.vandoren.app.uraandroid.Model.ProjectController;
import nl.vandoren.app.uraandroid.R;

/**
 * Created by Alex on 8-7-2015.
 */
public class WorkedHours_listOnClickListener implements AdapterView.OnItemClickListener,
        Dialog_notification.NotificationDialogCallBack {
    Dialog_notification pDialog;
    WorkedHours_fragment fragment;
    int selectedItem;

    public WorkedHours_listOnClickListener( WorkedHours_fragment fragment ){
        this.fragment = fragment;
        pDialog = new Dialog_notification();
        pDialog.setSettings(this,"Timer Alert!","Are you sure want to update task and select new?");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        selectedItem = i;
        if(fragment.timerFlag){
            pDialog.show(fragment.getFragmentManager(),"Click dialog");
        }else{
            workedHoursListItemSelected(selectedItem);
        }};

    @Override
    public void notificationDialogOnClickListener(int respond) {
        if(respond == R.string.yes){
            fragment.bt_startTimer.performClick();
            fragment.bt_addTask.performClick();  // the timer will be stopped
            workedHoursListItemSelected(selectedItem);
        }
    }

    private void workedHoursListItemSelected(int itemNr){
        //row id in list by task(excluded day rows)
        int id = fragment.myProjectListAdapter.myData.get(itemNr).rowID;
        String tempDBID = fragment.myProjectListAdapter.myData.get(itemNr).taskDBID;
        try {
            //this id is used to find all tasks and fill in spinner taskList
            fragment.tempProject = fragment.controller.workedHoursProjectsList.get(id).clone();
            for (Project p : fragment.controller.availableProjectsList) {
                if (p.projectID.equals(fragment.tempProject.projectID)) {

                    //set all task for this project
                    fragment.tempProject.taskList = p.taskList;

                    //set new task to the spinner button
                    fragment.setSpinnerTaskList(fragment.tempProject);

                    //set same selected task name to spinner
                    for (int x = 0; x < fragment.tempProject.taskList.size(); x++) {
                        if (fragment.tempProject.taskList.get(x).taskDBID.equals(tempDBID)) {
                            fragment.spinner_taskName.setSelection(x);
                            break;
                        }
                    }
                    break;
                }
            }
            fragment.bt_selectProject.setText(fragment.tempProject.projectID);

            int[] time = ProjectController.getProjectTimeFromString(fragment.tempProject);

            fragment.editText_hour.setText(String.valueOf(time[0]));
            fragment.editText_minute.setText(String.valueOf(time[1]));

            //highlight selected day
            ((Calendar_fragment) fragment.workedHoursfragmentPagerAdapter.getItem(1)).
                    adapter.highlightSelectedDay(fragment.tempProject.projectDayNameNumber);

            //redraw screen
            ((Calendar_fragment) fragment.workedHoursfragmentPagerAdapter.getItem(1)).
                    adapter.notifyDataSetChanged();

            //set new calendar number
            fragment.calendarSelectedNumber = fragment.tempProject.projectDayNameNumber;

            //set new date from selected item
            fragment.calendarSelectedDate = ((Calendar_fragment)
                    fragment.workedHoursfragmentPagerAdapter.getItem(1)).
                    adapter.dayString.get(fragment.tempProject.projectDayNameNumber);
        } catch (Exception e) {

        }
    }
};