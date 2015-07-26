package nl.vandoren.app.uraandroid.Fragment.WorkedHours;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import nl.vandoren.app.uraandroid.R;

/** Class is used to show Dialog Fragment when timer is worked and worked hours list item is clicked.
 * Created by Alex on 2-7-2015.
 */
public class Dialog_notification extends DialogFragment implements DialogInterface.OnClickListener {

    final String LOG_TAG = "myLogs";
    private NotificationDialogCallBack callBack;
    private String notificationMessage;
    private String notificationTitle;


    public void setSettings(NotificationDialogCallBack item,String title, String message){
        try{
            callBack = item;
            this.notificationTitle= title;
            this.notificationMessage= message;
        }catch(ClassCastException ex) {
            Log.e(LOG_TAG,"Timer Dialog Exxception:" + ex.getMessage());
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setTitle(notificationTitle)
                .setIcon(R.drawable.ic_timer_alert)
                .setPositiveButton(R.string.yes, this)
                .setNegativeButton(R.string.no, this)
                .setMessage(notificationMessage);

        return adb.create();
    }

    public void onClick(DialogInterface dialog, int which) {
        int i = 0;
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                i = R.string.yes;
                break;
            case Dialog.BUTTON_NEGATIVE:
                i = R.string.no;
                break;
        }
        if(callBack !=null){
            callBack.notificationDialogOnClickListener(i);
        }
    }

    public interface NotificationDialogCallBack {
        void notificationDialogOnClickListener(int respond);
    }
}