package nl.vandoren.app.uraandroid.Fragment.WorkedHours;

import android.view.View;
import android.widget.AdapterView;

import nl.vandoren.app.uraandroid.R;

/**
 * Created by Aleksei on 7/16/2015.
 */
public class WorkedHours_listLongOnClickListener implements AdapterView.OnItemLongClickListener,
        Dialog_notification.NotificationDialogCallBack {
    Dialog_notification pDialog;
    WorkedHours_fragment fragment;
    int selectedItem;

    public WorkedHours_listLongOnClickListener( WorkedHours_fragment fragment ){
        this.fragment = fragment;
        pDialog = new Dialog_notification();
        pDialog.setSettings(this,"Delete Alert!","Are you sure want to delete task?");
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        selectedItem = position;
        pDialog.show(fragment.getFragmentManager(), "LongClick dialog");
        return true;
    }

    @Override
    public void notificationDialogOnClickListener(int respond) {
        if(respond == R.string.yes){
            workedHoursListItemSelected(selectedItem);
        }
    }

    private void workedHoursListItemSelected(int itemNr) {
        //get nr in list, except day numbers.
        int id = fragment.myProjectListAdapter.myData.get(itemNr).rowID;
        fragment.tempProject = fragment.controller.workedHoursProjectsList.get(id);
        fragment.controller.deleteTask(fragment.tempProject);
    }
}
