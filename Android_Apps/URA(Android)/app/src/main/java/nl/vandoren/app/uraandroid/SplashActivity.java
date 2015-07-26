package nl.vandoren.app.uraandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import nl.vandoren.app.uraandroid.Connection.ConnectionManager;

/**
 * Created by Alex on 5/19/2015.
 */
public class SplashActivity extends Activity implements Runnable {

    TextView spashScreenVersion;

    private static final int DELAY = 1000;
    ConnectionManager connectionManager;
    SharedPreferences mySharedPreferences;
    TelephonyManager mngr;
    public Handler handler;

    final int STATUS_DEFAULT = 0;
    final int STATUS_CONNECTED = 1;
    final int STATUS_NOTCONNECTED = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        spashScreenVersion = (TextView)findViewById(R.id.id_app_versionname);

        try {
            spashScreenVersion.setText("Ver "+ getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        connectionManager = ConnectionManager.getInstance();
        mngr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        connectionManager.setConnectionSettings(mngr.getDeviceId());


        loadAppSettings();

        handler =new Handler(){
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case STATUS_DEFAULT:
                        break;
                    case STATUS_CONNECTED:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        break;
                    case STATUS_NOTCONNECTED:
                        Handler h =new Handler();
                        h.postDelayed(SplashActivity.this,DELAY);
                        break;
                }
            };
        };

        String[] loginData = loadLoginSettings();
        if (!loginData[0].isEmpty() && !loginData[1].isEmpty())
        {
            connectionManager.login(loginData[0],loginData[1],handler,this);
        }
        else
            handler.sendEmptyMessage(2);
    }

    /**
     * Load App settings
     */
    private void loadAppSettings() {
        mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String my_edittext_preference = mySharedPreferences.getString("serviceIP","");

        connectionManager.setUrlBaseAddress(my_edittext_preference);
    }

    /**
     * Load login settings
     * @return Returns array with login and password
     */
    private String[] loadLoginSettings()
    {
        mySharedPreferences = getSharedPreferences("URA",MODE_PRIVATE);
        return new String[]{mySharedPreferences.getString("lo",""),mySharedPreferences.getString("pa","")};
    }

    @Override
    public void run() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
