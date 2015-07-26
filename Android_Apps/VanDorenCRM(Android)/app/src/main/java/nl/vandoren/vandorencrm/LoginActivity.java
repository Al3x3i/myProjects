package nl.vandoren.vandorencrm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;



public class LoginActivity extends ActionBarActivity {

    ConnectionManager connectionManager;
    Handler handler; // connection call back

    private EditText mLogin;
    private EditText mPassword;
    private CheckBox checkBoxStaySignedIn;

    SharedPreferences sPref;

    final int STATUS_NONE = 0;
    final int STATUS_CONNECTED = 1;
    final int STATUS_NOTCONNECTED = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Set action bar color
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0066CC"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

        connectionManager = ConnectionManager.getInstance();

        mLogin = (EditText) findViewById(R.id.ed_login);
        mPassword = (EditText) findViewById(R.id.ed_password);
        checkBoxStaySignedIn = (CheckBox) findViewById(R.id.chBox_StaySignedIn);

        File file = new File("/data/data/"+this.getPackageName() +"/shared_prefs/MRC.xml");
        if (file.exists()) {
            loadLoginSettings();
        }

        handler =new Handler(){
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case STATUS_NONE:
                        break;
                    case STATUS_CONNECTED:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        break;
                    case STATUS_NOTCONNECTED:
                        Toast.makeText(LoginActivity.this, "Wrong login/password ", Toast.LENGTH_SHORT).show();
                        break;
                }
            };
        };
    }

    private void loadLoginSettings()
    {
        sPref = getSharedPreferences("MRC",MODE_PRIVATE);

        if (!sPref.getString("lo","").isEmpty() && !sPref.getString("pa","").isEmpty())
        {
            mLogin.setText(sPref.getString("lo",""));
            mPassword.setText(sPref.getString("pa",""));
            checkBoxStaySignedIn.setChecked(true);
        }
    }

    private void loadAppSettings() {
        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String my_edittext_preference = mySharedPreferences.getString("serviceIP","");

        connectionManager.setUrlBaseAddress(my_edittext_preference);
    }

    //When I return back from previous activity I will first run this method
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        loadAppSettings();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            displaySettingWindow();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Show settings window
    private void displaySettingWindow() {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, SetPreferenceActivity.class);
        startActivityForResult(intent, 0);
    }

    public void btSignIn_onClick(View view) {

        if (checkBoxStaySignedIn.isChecked()) {
            saveLoginData(mLogin.getText().toString(), mPassword.getText().toString());
        }
        //To remove filled in text I just remove file, it doesn't work in another acitivy(secure mode)
        //Other just re-write Shared Preference
        else {
            File file = new File("/data/data/" + this.getPackageName() + "/shared_prefs/MRC.xml");
            if (file.exists()) {
                file.delete();
            }
        }

        Log.i("myLog", "Login: " + mLogin.getText().toString() + "password: " + mPassword.getText().toString());
        if (!mLogin.getText().toString().isEmpty() && !mPassword.getText().toString().isEmpty()) {
            connectionManager.login(mLogin.getText().toString() ,mPassword.getText().toString(), handler, this);
        }
        else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }

    }

    private void saveLoginData(String encryptedLogin, String encryptedPassword)
    {
        sPref = getSharedPreferences("MRC", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("lo",encryptedLogin); //Login
        ed.putString("pa",encryptedPassword); //Password
        ed.commit();
    }
}

