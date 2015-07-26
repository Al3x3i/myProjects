package nl.vandoren.vandorencrm;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import nl.vandoren.vandorencrm.Model.User;
import nl.vandoren.vandorencrm.Security.AESEngine;
import nl.vandoren.vandorencrm.Security.LoginTask;

/**
 * main Class which keeps login data.
 */
public class ConnectionManager {

    private static final int HTTP_TIMEOUT = 30 * 1000;
    private static HttpClient mHttpClient;

    private AESEngine aesEngine;

    LoginTask mLoginTask;
    private Date date;
    private User user;
    public String phoneImei;

    private String urlBaseAddress;
    private String urlDefaultAddress = //removed
    //private String urlDefaultAddress = "http://192.168.30.122:8080/VanDorenCRM/AppService/";
    final String urlFindCompany = "DataExchange";
    final String urlSSLHelloControl = "HelloMessage";
    final String urlSSLKeyExchangeControl = "KeyExchange";
    final String urlSSLoginControl = "LoginMessage";

    private String userSessionID[] = new String[1]; //is used for service request
    private String aesPrivateKey = null;

    private static ConnectionManager ourInstance = new ConnectionManager();

    public static ConnectionManager getInstance() {
        return ourInstance;
    }

    private ConnectionManager() {
        aesEngine = new AESEngine();
        mLoginTask = new LoginTask(this);
        aesPrivateKey = aesEngine.generateAESKey();
        date = new Date();
        user = User.getInstance();
    }

    public void setConnectionSettings(String phoneImei) {
        this.phoneImei = phoneImei;
    }

    public void setUrlBaseAddress(String address) {
        if (address.isEmpty())
            urlBaseAddress = urlDefaultAddress;
        else
            urlBaseAddress = address;
    }

    public String getUrlBaseAddress() {
        return this.urlBaseAddress;
    }

    public String getAesPrivateKey() {
        return this.aesPrivateKey;
    }

    public String[] getUserSessionID() {
        return this.userSessionID;
    }

    public String getUrlSSLHelloControl() {
        return getUrlBaseAddress() + this.urlSSLHelloControl;
    }

    public String getUrlSSLKeyExchangeControl() {
        return getUrlBaseAddress() + this.urlSSLKeyExchangeControl;
    }

    public String getUrlSSLoginControl() {
        return getUrlBaseAddress() + this.urlSSLoginControl;
    }

    public String encryptAesMessage(String message) {
        try {
            return aesEngine.encrypt(message, aesPrivateKey);
        } catch (Exception ex) {
        }
        return null;
    }

    public String decryptAesMessage(String message) {
        try {
            return aesEngine.decrypt(message, aesPrivateKey);
        } catch (Exception ex) {
        }
        return null;
    }


    public void login(String login, String password, Handler h, Context mContext) {
        boolean connected = isNetworkAvailable(mContext);
        if (!connected) {
            Toast.makeText(mContext, "Internet connection error", Toast.LENGTH_SHORT).show();
        } else {
            //TODO add if statement, if returned false, yhe don't store login and password
            mLoginTask.establishConnection(login, password, h);

            user.lastTimeConnected = date.getTime();
            user.login = login;
            user.password = password;
        }
    }

    public boolean re_Connection(String login, String password, Context context) {
        boolean connected = isNetworkAvailable(context);
        if (!connected) {
            Toast.makeText(context, "Internet connection error", Toast.LENGTH_SHORT).show();
        } else {
            boolean respond = mLoginTask.establishConnection(login,password,null);
            if (!respond) {
                Toast.makeText(context, "Connection Error", Toast.LENGTH_SHORT).show();
            }
            return respond;
        }
        return false;
    }

    /**
     * Called when user wants to get information about company
     *
     * @param companyName   can be companyName or companyID, depends from which fragment the method was invoked.
     * @param searchCommand
     * @return
     */
    public String findCompany(String companyName, String searchCommand, Context mContext) {
        try {
            JSONObject plainJsonObject = new JSONObject();  // Will include message and digital signature
            plainJsonObject.put("phoneImei", phoneImei);
            plainJsonObject.put("command", searchCommand);
            plainJsonObject.put("searchWord", companyName);
            String encryptedMessage = aesEngine.encrypt(plainJsonObject.toString(), aesPrivateKey);

            return callHttpPostWithParameters(encryptedMessage, mContext);
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * Called when user wants to get information about employee
     *
     * @param firstName
     * @param secondName
     * @param searchCommand
     * @return
     */
    public String findEmployee(String firstName, String secondName, String searchCommand, Context mContext) {
        try {
            JSONObject plainJsonObject = new JSONObject();  // Will include message and digital signature
            plainJsonObject.put("phoneImei", phoneImei);
            plainJsonObject.put("command", searchCommand);
            plainJsonObject.put("searchWord", firstName + ";" + secondName);

            String encryptedMessage = aesEngine.encrypt(plainJsonObject.toString(), aesPrivateKey);

            return callHttpPostWithParameters(encryptedMessage, mContext);
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * Add additional parameters http post request
     *
     * @return
     */
    private String callHttpPostWithParameters(String encryptedMessage, Context mContext) throws Exception {

        ArrayList<NameValuePair> postParameters = createParameteresForRequest(encryptedMessage);
        String respond = executeHttpPost(urlBaseAddress + urlFindCompany, postParameters).toString();

        if (respond != null && !respond.isEmpty()) {
            if (respond.equals("Session Error")) {
                if (user.lastTimeConnected != 0) {
                    // Try to make new connection and execute same code
                    if(!re_Connection(user.login, user.password, mContext))
                        return null;
                    postParameters = createParameteresForRequest(encryptedMessage);
                    respond = executeHttpPost(urlBaseAddress + urlFindCompany, postParameters).toString();
                }
            }
            return aesEngine.decrypt(respond, aesPrivateKey);
        }
        return null;
    }

    private ArrayList<NameValuePair> createParameteresForRequest(String encryptedMessage)
    {
        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("0", userSessionID[0])); //userSessionID[0]
        postParameters.add(new BasicNameValuePair("1", encryptedMessage)); //encyptedMessage
        return postParameters;
    }

    /**
     *
     * @param url connection address
     * @param postParameters is used by service to know how to handle request. The include: sessionID and message(phoneImei, search word, command, )
     * @return
     */
    private  StringBuilder executeHttpPost(String url, ArrayList<NameValuePair> postParameters) {            //TO DO This method maybe should return other format for reason that later I will content in Adapter
        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient();
            HttpPost request = new HttpPost(url);

            request.setHeader("Content-Type", "application/x-www-form-urlencoded");

            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
            request.setEntity(formEntity);
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuilder responseStringBuilder = new StringBuilder();

            String inputStr;

            while((inputStr =in.readLine())!=null)
                responseStringBuilder.append(inputStr);

            return responseStringBuilder;
        }
        catch(Exception e){
            Log.e("myLog", "Error in httpPOST connection " + e.toString());
            return null;
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *
     * @return http client with connection parameters
     */
    private  HttpClient getHttpClient() {
        if (mHttpClient == null) {
            mHttpClient = new DefaultHttpClient();
            final HttpParams params = mHttpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
            ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
        }
        return mHttpClient;
    }
    private boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}


