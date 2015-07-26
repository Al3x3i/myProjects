package nl.vandoren.vandorencrm.Fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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


/**
 * Created by Aleksei on 3/15/2015.
 */
public class FindCompanyFragment extends Fragment {
    final String LOG_TAG = "myLogs";

    View rootview;
    private EditText edit_searchWord_CompanyName;
    private ListView myCompanyList;
    private ProgressDialog pDialog;

    private FindCompanyFragmentCallbacks mCallbacks; // Call Back in MainActivity

    private FindCompanyTask threadTask;

    private JSONArray requestResultCompanyList;
    private String selected_CompanyID;
    private String selected_CompanyName;
    private String selected_CompanyCountry;
    private String requestResultEmployeeList; // employee list stored in json format
    private Handler threadHandler;
    private ConnectionManager connectionManager;

    //Values for threadHandler
    final int STATUS_RETURNED_NOVALUE =0 ; // service doesn't have company which match to the entered company name
    final int STATUS_RETURNED_VALUE = 1; // at least one company name matches to the entered comapny name
    final int STATUS_SELECTEDCOMPANY = 2; // user seleced company from the list and will be shown CompanyResultFragment
    final int STATUS_ERROR =3;

    final String COMPANY_ID = "CompanyID";
    final String COMPANY_NAME = "CompanyName"; //
    final String COMPANY_COUNTRY = "CompanyCountry"; //

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (FindCompanyFragmentCallbacks) activity;
            threadTask = new FindCompanyTask();
            connectionManager = ConnectionManager.getInstance();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_find_company, container, false);
        edit_searchWord_CompanyName = (EditText)rootview.findViewById(R.id.edit_FindCompany);
        myCompanyList = (ListView)rootview.findViewById(R.id.companyList);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        edit_searchWord_CompanyName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchWord = edit_searchWord_CompanyName.getText().toString();
                    threadTask.setFindCompanyName(searchWord);
                    pDialog.show();
                    new Thread(threadTask).start();

                    // hide virtual keyboard
                    InputMethodManager inputManager = (InputMethodManager)
                            getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    return true;
                }
                return false;
            }
        });
        /**
         * ListView onItem click handler                                                            // IF COMPANY DON'T HAVE EMPLOYEE HANDLE MESSAGE  TO DO!!!
         */
        myCompanyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {

                    JSONObject j =requestResultCompanyList.getJSONObject(position);

                    selected_CompanyID = j.getString(COMPANY_ID);
                    selected_CompanyName = j.getString(COMPANY_NAME);
                    selected_CompanyCountry = j.getString(COMPANY_COUNTRY);

                    new Thread(new Runnable() {                                                                         //TO DO IF NO EMPLOYEE HANDLE EXCEPTION
                        @Override
                        public void run() {
                            android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                            requestResultEmployeeList = connectionManager.findCompany(selected_CompanyID,"FindSelectedCompanyDataWithEmployees",getActivity());  // return employees list

                            if(requestResultEmployeeList != null && !requestResultEmployeeList.isEmpty()) {
                                threadHandler.sendEmptyMessage(STATUS_SELECTEDCOMPANY);
                            }
                            else
                            {
                                threadHandler.sendEmptyMessage(STATUS_ERROR);
                            }
                        }
                    }).start();
                }
                catch(Exception ex){
                }
            }
        });

        threadHandler = new Handler()
        {
            public void handleMessage(android.os.Message msg)
            {
                switch (msg.what) {
                    case (STATUS_RETURNED_NOVALUE):
                        displayEmptyList();
                        break;
                    case (STATUS_RETURNED_VALUE):
                        displayCompanyList();
                        break;
                    case (STATUS_SELECTEDCOMPANY):
                        displaySelectedCompanyData();
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
        if(requestResultCompanyList != null) {
            displayCompanyList();
        }

        return rootview;
    }
    //http://www.androidhive.info/2012/01/android-json-parsing-tutorial/
    //http://www.c-sharpcorner.com/UploadFile/e14021/json-parsing-in-android-studio/
    public void displayCompanyList()
    {
        try {
            ArrayList<Map<String, String>> companyList = buildData(requestResultCompanyList);

            String[] from = {COMPANY_NAME, COMPANY_COUNTRY};
            int[] to = {R.id.firstLine, R.id.secondLine};

            SimpleAdapter adapter = new SimpleAdapter(getActivity(), companyList,
            R.layout.fragment_find_item, from, to);
            ListView t = (ListView)rootview.findViewById(R.id.companyList);
            t.setAdapter(adapter);
        }
        catch (JSONException ex){
            Log.d(LOG_TAG, "JsonException: "+ex.getMessage());
        }
        catch (Exception ex){}
    }

    public void displaySelectedCompanyData()
    {
        mCallbacks.onFindCompany_EmployeeItemSelected(selected_CompanyID, selected_CompanyName, selected_CompanyCountry, requestResultEmployeeList);
    }

    public void displayEmptyList(){
        Log.d(LOG_TAG, "onReceive: displayEmptyList");
        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,new String[]{"Data not found"});
            ListView t = (ListView)rootview.findViewById(R.id.companyList);
            t.setAdapter(adapter);
        }
        catch (Exception ex){}
    }

    private ArrayList<Map<String, String>> buildData(JSONArray list) throws JSONException{

        ArrayList<Map<String, String>> arrayList = new ArrayList<Map<String, String>>();

        for (int x = 0; x < list.length(); x++) {
            JSONObject j = list.getJSONObject(x);
            arrayList.add(putData(j.getString(COMPANY_ID),j.getString(COMPANY_NAME), j.getString(COMPANY_COUNTRY)));
        }

        return arrayList;
    }

    private HashMap<String, String> putData(String companyID, String name, String location) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put(COMPANY_NAME, companyID);
        item.put(COMPANY_NAME, name);
        item.put(COMPANY_COUNTRY, location);
        return item;
    }

    //Purpose of this class create thread which is used by button Find Company - btFindCompany
    class FindCompanyTask implements Runnable
    {
        private String FindCompanyName;
        public  void setFindCompanyName(String message) {
            this.FindCompanyName = message;
        }

        @Override
        public void run() {
            try {
                // Moves the current Thread into the background
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

                String result = connectionManager.findCompany(FindCompanyName,"FindCompanyList",getActivity());

                if(result == null || result.isEmpty()) {
                    threadHandler.sendEmptyMessage(STATUS_RETURNED_NOVALUE);
                    Thread.currentThread().interrupt();
                }
                else {
                    requestResultCompanyList = new JSONArray(result);
                    threadHandler.sendEmptyMessage(STATUS_RETURNED_VALUE);
                    FindCompanyName = null;
                }
            }
            catch (Exception ex){
            }
        }
    }

    //Class is used to provide call back in MainActivity
    public static interface FindCompanyFragmentCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onFindCompany_EmployeeItemSelected(String selected_CompanyID, String selected_CompanyName, String selected_CompanyCountry, String requestResultEmployeeList);
    }
}