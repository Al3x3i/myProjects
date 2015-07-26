package nl.vandoren.vandorencrm.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nl.vandoren.vandorencrm.Animation.SlidingText;
import nl.vandoren.vandorencrm.R;


/**
 * Created by Alex on 3/18/2015.
 */
public class CompanyResultFragment extends Fragment {
    View rootview;

    private ImageView image_Phone;
    private ImageView image_Email;
    private ImageView image_Url;
    private ImageView image_Information;

    private TextView text_Address;
    private TextView text_City;
    private TextView text_PostCode;
    private TextView text_Country;
    private TextView text_Telefax;
    private TextView text_Prospect;
    private TextView text_Phone;
    private TextView text_URL;
    private TextView text_Email;
    private TextView text_information;
    private TextView text_scroll_Information;

    private Spinner dropDownPhoneSpinner;

    RelativeLayout phoneButton;
    RelativeLayout urlButton;
    RelativeLayout emailButton;
    RelativeLayout informationButton;

    //below values for Json parsing
    final String COMPANY_NAME = "CompanyName";
    final String COMPANY_ADDRESS = "Address";
    final String COMPANY_CITY = "City";
    final String COMPANY_POSTCODE = "PostCode";
    final String COMPANY_COUNTRY = "Country";
    final String COMPANY_FAX = "Fax";
    final String COMPANY_MOBILE = "Mobile";
    final String COMPANY_PHONE1 = "Phone1";
    final String COMPANY_PHONE2 = "Phone2";
    final String COMPANY_URL = "URL";
    final String COMPANY_EMAIL = "Email";
    final String INFORMATION = "Information";
    final String DEPARTMENT = "DepartmentName";

    final String disabledTextColor = "#FFC0C0C0";

    private ArrayList<String> phoneNumbers = new ArrayList<String>();
    String company_notes;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_company_result, null);

        image_Phone =(ImageView)rootview.findViewById(R.id.id_phone_image);
        image_Email=(ImageView)rootview.findViewById(R.id.id_email_image);
        image_Url=(ImageView)rootview.findViewById(R.id.id_url_image);
        image_Information=(ImageView)rootview.findViewById(R.id.id_information_image);

        text_Address =(TextView)rootview.findViewById(R.id.text_companyAddress);
        text_City =(TextView)rootview.findViewById(R.id.text_companyCity);
        text_PostCode =(TextView)rootview.findViewById(R.id.text_companyPostCode);
        text_Country =(TextView)rootview.findViewById(R.id.text_companyCountry);
        text_Telefax =(TextView)rootview.findViewById(R.id.text_companyTelefax);
        text_Prospect = (TextView)rootview.findViewById(R.id.text_companyProspect);
        text_Phone =(TextView)rootview.findViewById(R.id.id_text_phone);
        dropDownPhoneSpinner = (Spinner)rootview.findViewById(R.id.id_phonespinner);
        text_URL =(TextView)rootview.findViewById(R.id.id_text_url);
        text_Email =(TextView)rootview.findViewById(R.id.id_text_email);
        text_information = (TextView)rootview.findViewById(R.id.id_text_information);
        text_scroll_Information = (TextView)rootview.findViewById(R.id.id_text_scroll_information);

        phoneButton = (RelativeLayout)rootview.findViewById(R.id.id_phone_btn);
        urlButton = (RelativeLayout)rootview.findViewById(R.id.id_url_btn);
        emailButton = (RelativeLayout)rootview.findViewById(R.id.id_email_btn);
        informationButton = (RelativeLayout)rootview.findViewById(R.id.id_information_btn);

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
                    text_scroll_Information.setText(Html.fromHtml(company_notes));
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

        displayEmployeeData();
        return rootview;
    }

    // Go through json text and add proper information to button's text
    private void displayEmployeeData() {

        try {
            ArrayList<String> phoneNumbersTitles = new ArrayList<String>();
        Bundle args = getArguments();

        String employeeData = args.getString("requestResultCompanyFullData");

        JSONObject json = new JSONObject(employeeData);

//region company data
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(json.getString(COMPANY_NAME));

        if(!json.getString(COMPANY_ADDRESS).isEmpty())
            text_Address.setText(json.getString(COMPANY_ADDRESS));
        else
            text_Address.setVisibility(View.GONE);

        if(!json.getString(COMPANY_CITY).isEmpty())
            text_City.setText(json.getString(COMPANY_CITY));
        else
            text_City.setVisibility(View.GONE);

        if(!json.getString(COMPANY_POSTCODE).isEmpty())
            text_PostCode.setText(json.getString(COMPANY_POSTCODE));
        else
            text_PostCode.setVisibility(View.GONE);

        if(!json.getString(COMPANY_COUNTRY).isEmpty())
            text_Country.setText(json.getString(COMPANY_COUNTRY));
        else
            text_Country.setVisibility(View.GONE);

        if(!json.getString(COMPANY_FAX).isEmpty())
            text_Telefax.setText("Fax: " + json.getString(COMPANY_FAX));
        else
            text_Telefax.setVisibility(View.GONE);

        if(!json.getString(DEPARTMENT).isEmpty()) {
            String [] departments =  json.getString(DEPARTMENT).split(";");
            text_Prospect.setText("");
            for(int i = 0; i<departments.length;i++)
            {
                text_Prospect.append(departments[i]);
                if (departments.length != i+1)
                    text_Prospect.append("\n");
            }
        }
        else
            text_Prospect.setVisibility(View.GONE);
//endregion
//region phone_spinner
        if(!json.getString(COMPANY_MOBILE).isEmpty()) {
            phoneNumbersTitles.add("Mobile");
            phoneNumbers.add(json.getString(COMPANY_MOBILE));

        }
        if(!json.getString(COMPANY_PHONE1).isEmpty()) {
            phoneNumbersTitles.add("Phone1");
            phoneNumbers.add(json.getString(COMPANY_PHONE1));
        }
        if(!json.getString(COMPANY_PHONE2).isEmpty()) {
            phoneNumbersTitles.add("Phone2");
            phoneNumbers.add(json.getString(COMPANY_PHONE2));

        }
//endregion
//region button's text
        if(phoneNumbers.size() !=0 ) {
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
        if(!json.getString(COMPANY_URL).isEmpty()) {
            text_URL.setText(json.getString(COMPANY_URL));
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
        if(!json.getString(COMPANY_EMAIL).isEmpty()) {
            text_Email.setText(json.getString(COMPANY_EMAIL));
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
            company_notes = json.getString(INFORMATION);
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
}

