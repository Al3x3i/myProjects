package nl.vandoren.vandorencrm.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nl.vandoren.vandorencrm.ConnectionManager;
import nl.vandoren.vandorencrm.R;


/**
 * Created by Alex on 3/17/2015.
 */
public class FindEmployeeFragment extends Fragment {

    final String LOG_TAG = "myLogs";

    View rootview;
    private Button btFindCompany;
    private EditText edit_searchWord_FirstName;
    private EditText edit_searchWord_SecondName;
    private ListView myEmployeeList;
    private ProgressDialog pDialog;

    private FindEmployeeListCallbacks mCallbacks;

    private FindEmployeeTask threadTask;

    private JSONArray requestResultEmployeeList;

    private String requestResultEmployeeFullData;

    private Handler threadHandler;
    ConnectionManager connectionManager;

    final int STATUS_RETURNED_NOVALUE =0 ; //
    final int STATUS_RETURNED_VALUE = 1; //
    final int STATUS_SELECTEDEMPLOYEE = 2; //
    final int STATUS_ERROR =3;

    final String EMPLOYEE_ID = "EmployeeID";
    final String EMPLOYEE_FULLNAME = "EmployeeName";
    final String EMPLOYEE_FUNCTION = "Function";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (FindEmployeeListCallbacks) activity;
            threadTask = new FindEmployeeTask();
            connectionManager = ConnectionManager.getInstance();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_find_employee, null);
        btFindCompany = (Button) rootview.findViewById(R.id.bt_FindEmployee);
        edit_searchWord_FirstName = (EditText) rootview.findViewById(R.id.edit_FindEmployeeFirstName);
        edit_searchWord_SecondName = (EditText) rootview.findViewById(R.id.edit_FindEmployeeSecondName);
        myEmployeeList = (ListView) rootview.findViewById(R.id.employeeList);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        btFindCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = edit_searchWord_FirstName.getText().toString();
                String secondName =  edit_searchWord_SecondName.getText().toString();

                threadTask.setFindFirstName(firstName);
                threadTask.setFindSecondName(secondName);
                pDialog.show();
                new Thread(threadTask).start();
            }
        });
        /**
         * ListView onItem click handler
         */

        myEmployeeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    JSONObject j =requestResultEmployeeList.getJSONObject(position);

                    final String selected_EmployeeID = j.getString(EMPLOYEE_ID);

                    new Thread(new Runnable() {                                                                         //TO DO IF NO EMPLOYEE HANDLE EXCEPTION
                        @Override
                        public void run() {
                            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                            requestResultEmployeeFullData = connectionManager.findCompany(selected_EmployeeID, "FindSelectedEmployeeData",getActivity());  // return employees list

                            if(requestResultEmployeeFullData != null && !requestResultEmployeeFullData.isEmpty()) {
                                threadHandler.sendEmptyMessage(STATUS_SELECTEDEMPLOYEE);
                            }
                            else
                            {
                                threadHandler.sendEmptyMessage(STATUS_ERROR);
                            }
                        }
                    }).start();
                } catch (Exception ex) {
                }
            }
        });

        threadHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case (STATUS_RETURNED_NOVALUE):
                        displayEmptyList();
                        break;
                    case (STATUS_RETURNED_VALUE):
                        displayEmployeeList();
                        break;
                    case (STATUS_SELECTEDEMPLOYEE):
                        displaySelectedEmployeeData();
                        break;
                    case (STATUS_ERROR):
                        Toast.makeText(getActivity(), "Unexpected service error occurred", Toast.LENGTH_SHORT).show();
                        break;
                }
                if(pDialog.isShowing())
                    pDialog.dismiss();
            }
        };

        //If user clicked back button, then will be shown previous result
        if(requestResultEmployeeList != null) {
            displayEmployeeList();
        }
        return rootview;
    }

    public void displayEmployeeList()
    {
        try {
            ArrayList<Map<String, String>> serviceResult_employeeList = buildData(requestResultEmployeeList);

            String[] from = {EMPLOYEE_FULLNAME, EMPLOYEE_FUNCTION};
            int[] to = {R.id.firstLine, R.id.secondLine};

            SimpleAdapter adapter = new SimpleAdapter(getActivity(), serviceResult_employeeList,
                    R.layout.fragment_find_item, from, to);
            ListView t = (ListView)rootview.findViewById(R.id.employeeList);
            t.setAdapter(adapter);
        }
        catch (Exception ex){}
    }

    public void displaySelectedEmployeeData()
    {
        mCallbacks.onFindEmployeeItemSelected(requestResultEmployeeFullData);
    }

    public void displayEmptyList()
    {    Log.d(LOG_TAG, "onReceive: displayEmptyList");
        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,new String[]{"Data not found"});
            ListView t = (ListView)rootview.findViewById(R.id.employeeList);
            t.setAdapter(adapter);
        }
        catch (Exception ex){}
    }

    private ArrayList<Map<String, String>> buildData(JSONArray list) throws JSONException {

        ArrayList<Map<String, String>> arrayList = new ArrayList<Map<String, String>>();

        for (int x = 0; x < list.length(); x++) {
            JSONObject j = list.getJSONObject(x);
            arrayList.add(putData(j.getString(EMPLOYEE_FULLNAME), j.getString(EMPLOYEE_FUNCTION), j.getString(EMPLOYEE_ID)));
        }

        return arrayList;
    }

    private HashMap<String, String> putData(String fullName, String function, String id) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put(EMPLOYEE_FULLNAME, fullName);
        item.put(EMPLOYEE_FUNCTION, function);
        item.put(EMPLOYEE_ID, id);
        return item;
    }

    class FindEmployeeTask implements Runnable
    {
        private String findFirstName;
        private String findSecondName;
        public  void setFindFirstName(String message) {
            this.findFirstName = message;
        }
        public  void setFindSecondName(String message) {
            this.findSecondName = message;
        }

        @Override
        public void run() {
            try {
                // Moves the current Thread into the background
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

                String result = connectionManager.findEmployee(findFirstName, findSecondName, "FindEmployeeList",getActivity());

                if(result == null || result.isEmpty()) {
                    threadHandler.sendEmptyMessage(STATUS_RETURNED_NOVALUE);
                    Thread.currentThread().interrupt();
                }
                else {
                    requestResultEmployeeList = new JSONArray(result);
                    threadHandler.sendEmptyMessage(STATUS_RETURNED_VALUE);
                    findFirstName = null;
                    findSecondName = null;
                }
            }
            catch (Exception ex){

            }
        }
    }

    public static interface FindEmployeeListCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onFindEmployeeItemSelected(String requestResultEmployeeFullData);
    }
}
