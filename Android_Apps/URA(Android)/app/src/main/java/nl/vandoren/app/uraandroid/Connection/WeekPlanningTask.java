package nl.vandoren.app.uraandroid.Connection;

import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;

/**
 * Created by Alex on 25-6-2015.
 */
public class WeekPlanningTask extends BaseConnectionParameters{
    ConnectionManager connectionManager = ConnectionManager.getInstance();
    JSONObject plainJsonObject;
    Message handlerMsg;
    Handler myHandler;

    /**
     * Subscribe handler objects which will handle http connection callbacks
     * @param handler
     */
    public WeekPlanningTask(Handler handler){
        myHandler = handler;
    }

    /**
     * Reads week planning data from service, For this creates request parameters and invoke http request
     * in new Thread, is used GET method. Through handler returns back the message from service.
     * @param firstDay From date
     * @param lastDay Till date
     */
    public void getWeekPlanning(String firstDay, String lastDay)
    {
        try {
            String searchWord =  firstDay + ";" +lastDay + ";" + super.getCapa_DBID();
            plainJsonObject = new JSONObject();  // Will include message and digital signature
            plainJsonObject.put("phoneImei", super.phoneImei);
            plainJsonObject.put("searchWord", searchWord); // request parameters
            final String url = this.getWeekPlanningURL();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String respond = connectionManager.executeHTTPRequest(plainJsonObject, url, "GET");
                    if (respond!=null)
                    {
                        handlerMsg = myHandler.obtainMessage(0,respond);
                        myHandler.sendMessage(handlerMsg);
                    }else{
                        myHandler.sendEmptyMessage(1);
                    }
                }
            }).start();
        }
        catch (Exception ex)
        {
            myHandler.sendEmptyMessage(1);
        }
    }


}
