package nl.vandoren.vandorencrm.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nl.vandoren.vandorencrm.ConnectionManager;
import nl.vandoren.vandorencrm.R;

import static nl.vandoren.vandorencrm.Fragment.FindEmployeeFragment.FindEmployeeListCallbacks;

/**

 */
public class FindCompany_EmployeesResultFragment extends Fragment {

    final String COMPANY_ID = "CompanyID";
    final String COMPANY_NAME = "CompanyName"; //
    final String COMPANY_COUNTRY = "CompanyCountry"; //
    final String EMPLOYEE_FULLNAME = "EmployeeName"; // to create adapter
    final String EMPLOYEE_ID = "EmployeeID"; // Employee ID
    final String EMPLOYEE_FUNCTION = "Function"; // for json parsing

    // below parameters are used for thread handler
    final int STATUS_SELECTEDEMPLOYEE = 0;
    final int STATUS_SELECTEDCOMPANY = 1;
    final int STATUS_ERROR =2;

    private String selectedCompanyID;
    private String companyName;
    private String companyCountry;

    private JSONArray jsonArrayEmployeeList;

    private String requestResultEmployeeFullData;
    private String requestResutlCompanyFullData;

    //Callback for MainActivity class
    private FindEmployeeListCallbacks onEmployeeListItem_Click_Callbacks;
    private FindCompany_EmployeeResultCallbacks onCompanyButtonInfoClick;

    //Thread class which is used to get full data of company
    private FindCompanyAllDataTask threadTask;

    private Handler threadHandler;
    private ConnectionManager connectionManager;

    private View rootview;
    private TextView textview_companyName;
    private TextView textview_companyLocation;
    private RelativeLayout bt_getFullCompanyInfo;
    private ListView myEmployeeList;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onEmployeeListItem_Click_Callbacks = (FindEmployeeListCallbacks) activity;
            onCompanyButtonInfoClick = (FindCompany_EmployeeResultCallbacks) activity;
            connectionManager = ConnectionManager.getInstance();
            threadTask = new FindCompanyAllDataTask();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("Company Data");
        rootview = inflater.inflate(R.layout.fragment_company_employee_result, null);

        textview_companyName =(TextView)rootview.findViewById(R.id.text_companyName);
        textview_companyLocation =(TextView)rootview.findViewById(R.id.text_companyCountry);
        bt_getFullCompanyInfo =(RelativeLayout)rootview.findViewById(R.id.rel_btCompanyInfo);
        myEmployeeList = (ListView)rootview.findViewById(R.id.list_employeeList_CompanyResult);

        //read data from previous FindCompanyFragment
        Bundle args = getArguments();
        selectedCompanyID = args.getString(COMPANY_ID);
        companyName = args.getString(COMPANY_NAME);
        companyCountry = args.getString(COMPANY_COUNTRY);
        String employeeList = args.getString("selected_Company_EmployeeList"); //read json object received from previous fragment

        displayCompanyData(companyName,companyCountry,employeeList);

        //Handler for list onItemclick
        myEmployeeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {

                    JSONObject j =jsonArrayEmployeeList.getJSONObject(position);

                    final String selected_EmployeeID = j.getString(EMPLOYEE_ID);

                    new Thread(new Runnable() {                                                                         //TO DO IF NO EMPLOYEE HANDLE EXCEPTION
                        @Override
                        public void run() {
                            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                            requestResultEmployeeFullData = connectionManager.findCompany(selected_EmployeeID, "FindSelectedEmployeeData",getActivity());  // return employees list

                            if(requestResultEmployeeFullData != null && !requestResultEmployeeFullData.isEmpty())
                                threadHandler.sendEmptyMessage(STATUS_SELECTEDEMPLOYEE);
                            else
                                threadHandler.sendEmptyMessage(STATUS_ERROR);
                        }
                    }).start();
                } catch (Exception ex) {
                }
            }
        });

        threadHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case (STATUS_SELECTEDEMPLOYEE):
                        displayEmployeeData();
                        break;
                    case (STATUS_SELECTEDCOMPANY):
                        displayCompanyFullData();
                        break;
                    case (STATUS_ERROR):
                        Toast.makeText(getActivity(), "Unexpected service error occurred", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        //Company Info button onClick handler
        bt_getFullCompanyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threadTask.setCompanyID(selectedCompanyID);
                new Thread(threadTask).start();
            }
        });

        return rootview;
    }


    private void displayEmployeeData()
    {
        onEmployeeListItem_Click_Callbacks.onFindEmployeeItemSelected(requestResultEmployeeFullData);
    }

    private void displayCompanyFullData()
    {
        onCompanyButtonInfoClick.onFindCompany_EmployeeResult_ButtonCompanyInfoSelected(requestResutlCompanyFullData);
    }

    /**
     * Called when fragment is created and displays Company name, location and working employee list
     * @param companyName
     * @param companyCountry
     * @param employeeList employee list from which possible to create json object
     */
    private void displayCompanyData(String companyName,String companyCountry,String employeeList)
    {
        try
        {
            textview_companyName.setText(companyName);
            textview_companyLocation.setText(companyCountry);

            jsonArrayEmployeeList = new JSONArray(employeeList);

            ArrayList<Map<String, String>> companyList = buildData(jsonArrayEmployeeList);

            String[] from = {EMPLOYEE_FULLNAME, EMPLOYEE_FUNCTION};
            int[] to = {R.id.firstLine, R.id.secondLine};

            SimpleAdapter adapter = new SimpleAdapter(getActivity(), companyList,
                    R.layout.fragment_find_item, from, to);
            myEmployeeList.setAdapter(adapter);

        }
        catch (Exception ex)
        {

        }
    }

    /**
     * Called to create handle json document and create Adapter
     * @param list Json document //"[{\"EmployeeID\":\"12312344\",\"Position\":\"Manager\",\"FirstName\":\"Julian\",\"SecondName\":\"`Ceveney\"}....
     * @return Adapter for ListView
     * @throws org.json.JSONException
     */
    private ArrayList<Map<String, String>> buildData(JSONArray list) throws JSONException {

        ArrayList<Map<String, String>> arrayList = new ArrayList<Map<String, String>>();

        for (int x = 0; x < list.length(); x++) {
            JSONObject j = list.getJSONObject(x);
            arrayList.add(putData(j.getString(EMPLOYEE_FULLNAME), j.getString(EMPLOYEE_ID),j.getString(EMPLOYEE_FUNCTION)));
        }

        return arrayList;
    }

    private HashMap<String, String> putData(String fullName,String employeeID, String function) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put(EMPLOYEE_ID, employeeID);
        item.put(EMPLOYEE_FULLNAME, fullName);
        item.put(EMPLOYEE_FUNCTION, function);
        return item;
    }

    public static interface FindCompany_EmployeeResultCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onFindCompany_EmployeeResult_ButtonCompanyInfoSelected(String selectedCompanyID);
    }


    //class is used in thread to get all data of selected company
    class FindCompanyAllDataTask implements Runnable
    {
        private String companyID;
        public  void setCompanyID(String message) {
            this.companyID = message;
        }

        @Override
        public void run() {
            try {
                // Moves the current Thread into the background
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

                requestResutlCompanyFullData = connectionManager.findCompany(companyID,"FindSelectedCompanyData",getActivity());

                if(requestResutlCompanyFullData == null || requestResutlCompanyFullData.isEmpty()) {
                    threadHandler.sendEmptyMessage(STATUS_ERROR);
                    Thread.currentThread().interrupt();
                }
                else {
                    threadHandler.sendEmptyMessage(STATUS_SELECTEDCOMPANY);
                    companyID = null;
                }
            }
            catch (Exception ex){
            }
        }
    }
}
