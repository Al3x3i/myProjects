package nl.vandoren.vandorencrm.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nl.vandoren.vandorencrm.Animation.SlidingText;
import nl.vandoren.vandorencrm.ConnectionManager;
import nl.vandoren.vandorencrm.R;


/**
 * Created by Aleksei on 3/18/2015.
 */
public class EmployeeResultFragment extends Fragment {
    View rootview;

    private ImageView image_Phone;
    private ImageView image_Email;
    private ImageView image_Url;
    private ImageView image_Information;

    private TextView text_CompanyName;
    private TextView text_Function;
    private TextView text_Phone;
    private TextView text_Email;
    private TextView text_URL;
    private TextView text_information;
    private TextView text_scroll_Information;

    private Spinner dropDownPhoneSpinner;

    //below values for Json parsing
    final String EMPLOYEE_FULLNAME = "EmployeeName";
    final String COMPANY_ID = "CompanyID";
    final String EMPLOYEE_COMPANYNAME = "CompanyName";
    final String EMPLOYEE_FUNCTION = "Function";
    final String EMPLOYEE_MOBILE = "Mobile";
    final String EMPLOYEE_PHONE1 = "Phone1";
    final String EMPLOYEE_PHONE2 = "Phone2";
    final String EMPLOYEE_EMAIL = "Email";
    final String EMPLOYEE_URL = "URL";
    final String INFORMATION = "Information";

    final String disabledTextColor = "#FFC0C0C0";

    RelativeLayout bt_company_information;
    RelativeLayout phoneButton;
    RelativeLayout urlButton;
    RelativeLayout emailButton;
    RelativeLayout informationButton;

    private FindCompanyAllDataTask threadTask;
    private ConnectionManager connectionManager;
    private Handler threadHandler;
    private String requestResutlCompanyFullData;

    final int STATUS_SELECTEDCOMPANY = 1;
    final int STATUS_ERROR =2;

    private ArrayList<String> phoneNumbers = new ArrayList<String>();
    private String employee_notes;
    private String companyID;

    private FindCompany_EmployeeResultCallbacks onCompanyButtonInfoClick;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
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
        rootview = inflater.inflate(R.layout.fragment_employee_result,null);

        image_Phone =(ImageView)rootview.findViewById(R.id.id_phone_image);
        image_Email=(ImageView)rootview.findViewById(R.id.id_email_image);
        image_Url=(ImageView)rootview.findViewById(R.id.id_url_image);
        image_Information=(ImageView)rootview.findViewById(R.id.id_information_image);

        text_CompanyName =(TextView)rootview.findViewById(R.id.text_employeeCompany);

        text_Function =(TextView)rootview.findViewById(R.id.text_employeeFunction);
        text_Phone =(TextView)rootview.findViewById(R.id.id_text_phone);
        dropDownPhoneSpinner = (Spinner)rootview.findViewById(R.id.id_phonespinner);
        text_Email =(TextView)rootview.findViewById(R.id.id_text_email);
        text_URL =(TextView)rootview.findViewById(R.id.id_text_url);
        text_information = (TextView)rootview.findViewById(R.id.id_text_information);
        text_scroll_Information =(TextView)rootview.findViewById(R.id.id_text_scroll_information);

        bt_company_information = (RelativeLayout)rootview.findViewById(R.id.bt_company_information);
        phoneButton = (RelativeLayout)rootview.findViewById(R.id.id_phone_btn);
        urlButton = (RelativeLayout)rootview.findViewById(R.id.id_url_btn);
        emailButton = (RelativeLayout)rootview.findViewById(R.id.id_email_btn);
        informationButton = (RelativeLayout)rootview.findViewById(R.id.id_information_btn);

        bt_company_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threadTask.setCompanyID(companyID);
                new Thread(threadTask).start();
            }
        });

        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + text_Phone.getText()));
                startActivity(intent);
            }
        });

        urlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+text_URL.getText()));
                startActivity(intent);
            }
        });

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{ text_Email.getText().toString()});
                emailIntent.setType("text/plain");
                startActivity(Intent.createChooser(emailIntent, ""));
            }
        });

        informationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text_scroll_Information.isShown()){
                    text_scroll_Information.setText("");
                    text_scroll_Information.setVisibility(View.INVISIBLE);
                    SlidingText.slide_up(getActivity(), text_scroll_Information);
                }
                else{
                    text_scroll_Information.setText(Html.fromHtml(employee_notes));
                    text_scroll_Information.setVisibility(View.VISIBLE);
                    SlidingText.slide_down(getActivity(), text_scroll_Information);
                }
            }
        });

        dropDownPhoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                text_Phone.setText(phoneNumbers.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        threadHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case (STATUS_SELECTEDCOMPANY):
                        displayCompanyFullData();
                        break;
                    case (STATUS_ERROR):
                        Toast.makeText(getActivity(), "Unexpected error occurred on service", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        displayEmployeeData();

        return rootview;
    }

    private void displayCompanyFullData()
    {
        onCompanyButtonInfoClick.onFindCompany_EmployeeResult_ButtonCompanyInfoSelected(requestResutlCompanyFullData);
    }

    private void displayEmployeeData() {

        try {
            ArrayList<String> phoneNumbersTitles = new ArrayList<String>();
            Bundle args = getArguments();

            String employeeData = args.getString("requestResultEmployeeFullData");

            JSONObject json = new JSONObject(employeeData);
            ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(json.getString(EMPLOYEE_FULLNAME));

            companyID = json.getString(COMPANY_ID);
            text_CompanyName.setText(json.getString(EMPLOYEE_COMPANYNAME));
            text_Function.setText(json.getString(EMPLOYEE_FUNCTION));

//region phone_spinner
            if(!json.getString(EMPLOYEE_MOBILE).isEmpty()) {
                phoneNumbersTitles.add("Mobile");
                phoneNumbers.add(json.getString(EMPLOYEE_MOBILE));
            }
            if(!json.getString(EMPLOYEE_PHONE1).isEmpty()) {
                phoneNumbersTitles.add("Phone1");
                phoneNumbers.add(json.getString(EMPLOYEE_PHONE1));
            }
            if(!json.getString(EMPLOYEE_PHONE2).isEmpty()) {
                phoneNumbersTitles.add("Phone2");
                phoneNumbers.add(json.getString(EMPLOYEE_PHONE2));
            }
//endregion
//region button's text
            if(phoneNumbers.size() != 0) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, phoneNumbersTitles);
                dropDownPhoneSpinner.setAdapter(adapter);
                text_Phone.setText(phoneNumbers.get(0));
                image_Phone.setImageResource(R.drawable.ic_action_call_on);
                // if not enough space for text then text will be moved from right to left
                if(text_Phone.getText().length()>25)
                    text_Phone.setSelected(true);
            }
            else {
                phoneButton.setEnabled(false);
                dropDownPhoneSpinner.setEnabled(false);
                text_Phone.setTextColor(Color.parseColor(disabledTextColor));
            }
            //set website button
            if(!json.getString(EMPLOYEE_URL).isEmpty()) {
                text_URL.setText(json.getString(EMPLOYEE_URL));
                image_Url.setImageResource(R.drawable.ic_action_web_site_on);
                // if not enough space for text then text will be moved from right to left
                if(text_Email.getText().length()>25)
                    text_Email.setSelected(true);
            }
            else {
                urlButton.setEnabled(false);
                text_URL.setTextColor(Color.parseColor(disabledTextColor));
            }
            //set email button
            if(!json.getString(EMPLOYEE_EMAIL).isEmpty()) {
                text_Email.setText(json.getString(EMPLOYEE_EMAIL));
                image_Email.setImageResource(R.drawable.ic_action_email_on);
                // if not enough space for text then text will be moved from right to left
                if(text_Email.getText().length()>25)
                    text_Email.setSelected(true);
            }
            else {
                emailButton.setEnabled(false);
                text_Email.setTextColor(Color.parseColor(disabledTextColor));
            }
//endregion
//region displayInformation
            if(!json.getString(INFORMATION).isEmpty()) {
                employee_notes = json.getString(INFORMATION);
                image_Information.setImageResource(R.drawable.ic_menu_more_on);
            }
            else{
                informationButton.setEnabled(false);
                text_information.setTextColor(Color.parseColor(disabledTextColor));
            }
//endregion
        }
        catch(JSONException ex){}
    }


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
                threadHandler.sendEmptyMessage(STATUS_SELECTEDCOMPANY);
                companyID =null;
            }
            catch (Exception ex){
            }
        }
    }

    public static interface FindCompany_EmployeeResultCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onFindCompany_EmployeeResult_ButtonCompanyInfoSelected(String selectedCompanyID);
    }
}

