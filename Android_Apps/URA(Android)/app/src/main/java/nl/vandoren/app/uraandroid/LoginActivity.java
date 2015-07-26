package nl.vandoren.app.uraandroid;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import nl.vandoren.app.uraandroid.Connection.ConnectionManager;

public class LoginActivity  extends AppCompatActivity {
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

        connectionManager = ConnectionManager.getInstance();

        mLogin = (EditText) findViewById(R.id.ed_login);
        mPassword = (EditText) findViewById(R.id.ed_password);
        checkBoxStaySignedIn = (CheckBox) findViewById(R.id.chBox_StaySignedIn);

        File file = new File("/data/data/"+this.getPackageName() +"/shared_prefs/URA.xml");
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
        sPref = getSharedPreferences("URA",MODE_PRIVATE);

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

    //When I return back from previous activity this method will be invoked
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
            File file = new File("/data/data/" + this.getPackageName() + "/shared_prefs/URA.xml");
            if (file.exists()) {
                file.delete();
            }
        }

        Log.i("myLog", "Login: " + mLogin.getText().toString() + "password: " + mPassword.getText().toString());
        //connectionManager.login("Roger" ,"admin1", handler,this);


        if (!mLogin.getText().toString().isEmpty() && !mPassword.getText().toString().isEmpty()) {
            connectionManager.login(mLogin.getText().toString() ,mPassword.getText().toString(),
                    handler,this);
        }
        else {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Save login and password in mobile phone memory
     * @param login login name
     * @param password password
     */

    private void saveLoginData(String login, String password)
    {
        sPref = getSharedPreferences("URA", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("lo",login); //Login
        ed.putString("pa",password); //Password
        ed.commit();
    }
}

