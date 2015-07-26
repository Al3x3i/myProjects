package nl.vandoren.app.uraandroid.Connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Date;
import java.util.LinkedHashMap;

import nl.vandoren.app.uraandroid.Connection.Security.AESEngine;
import nl.vandoren.app.uraandroid.Connection.Security.LoginTask;
import nl.vandoren.app.uraandroid.Model.User;


/**
 * main Class which keeps login data.
 */
public class ConnectionManager extends BaseConnectionParameters {

    private AESEngine aesEngine;

    private LoginTask loginTask;
    private HttpRequest httpRequest;
    private Date date;
    private String urlBaseAddress;

    final String urlSSLHelloControl = "HelloMessage";
    final String urlSSLKeyExchangeControl = "KeyExchange";
    final String urlSSLoginControl = "LoginMessage";

    private String aesPrivateKey = null;

    private static ConnectionManager ourInstance = new ConnectionManager();

    public static ConnectionManager getInstance() {
        return ourInstance;
    }

    private ConnectionManager() {
        httpRequest = new HttpRequest();
        aesEngine = new AESEngine();
        aesPrivateKey = aesEngine.generateAESKey();
        date = new Date();
    }

    public void setConnectionSettings(String phoneImei){
        loginTask = new LoginTask(phoneImei);
        this.phoneImei = phoneImei;
    }

    public void setUrlBaseAddress(String address){
        if(address.isEmpty())
            urlBaseAddress= super.defaultAddressURL;
        else
            urlBaseAddress =address;
    }

    public String getUrlBaseAddress() {
        return this.urlBaseAddress;
    }

    public String getAesPrivateKey() {
        return this.aesPrivateKey;
    }

    public String[] getUserSessionID() {
        return super.userSessionID;
    }

    public User getUserObject(){return super.user;}

    //Login First Call
    public String getUrlSSLHelloControl(){
        return getUrlBaseAddress () + this.urlSSLHelloControl;
    }

    //Login Second Call
    public  String getUrlSSLKeyExchangeControl(){
        return getUrlBaseAddress () + this.urlSSLKeyExchangeControl;
    }

    public String getUrlSSLoginControl()
    {
        return getUrlBaseAddress () + this.urlSSLoginControl;
    }

    /**
     *  Encrypt message uses ARS key
     * @param message plain text
     * @return returns encrypted message
     */
    public String encryptAesMessage(String message) {
        try {
            return aesEngine.encrypt(message, aesPrivateKey);
        }
        catch (Exception ex){}
        return null;
    }

    /**
     * Decrypt message which came from service
     * @param message encrypted message
     * @return returns decrypted message
     */
    public String decryptAesMessage(String message){
        try {
            return aesEngine.decrypt(message, aesPrivateKey);
        }
        catch (Exception ex){}
        return null;
    }


    /**
     * Method establish connection, doesn't return value because is used Handler.
     * @param login login name
     * @param password password
     * @param h Handler
     * @param mContext Context, for error messages
     */
    public void login(String login, String password, Handler h, Context mContext) {
        boolean connected = isNetworkAvailable(mContext);
        if (!connected) {
            Toast.makeText(mContext, "Internet connection error", Toast.LENGTH_SHORT).show();
        } else {
            loginTask.establishConnection(login, password,h);
            super.user.lastTimeConnected = date.getTime();
            super.user.login = login;
            super.user.password = password;

        }

    }

    /**
     *Method encrypts JSON object, adds sessionID and implements http request
     * @param message request parameters, not encrypted, wrapped into JSON extension
     * @param httpURL reuqest url
     * @param requestMethod method type ex:GET,PUT ..
     * @return returns decrypted message from service
     */
    public String executeHTTPRequest(JSONObject message,String httpURL, String requestMethod){
        String encryptedMessage = encryptAesMessage(message.toString());

        LinkedHashMap<String,String> parameters = createParameteresForRequest(encryptedMessage);

        String respond = httpRequest.executeHttpGet(httpURL, parameters, requestMethod);

        if (respond != null && !respond.isEmpty()) {
            if (respond.equals("Session Error")) {
                if (user.lastTimeConnected != 0) {
                    // Try to make new connection and execute same code
                    if(!re_Connection(user.login, user.password)) {
                        return null;
                    }
                    parameters = createParameteresForRequest(encryptedMessage);
                    respond = httpRequest.executeHttpGet(httpURL, parameters, requestMethod);
                }
            }
            return decryptAesMessage(respond);
        }
        return null;
    }

    private LinkedHashMap<String,String> createParameteresForRequest(String encryptedMessage)
    {
        LinkedHashMap<String,String> parameters = new LinkedHashMap<String,String>();
        parameters.put("0", encryptedMessage);
        parameters.put("1", super.userSessionID[0]);
        return parameters;
    }

    //TODO this method also can check network connection, to do it, should find the way how to send error if not internet Context or just message
    private boolean re_Connection(String login, String password) {
        boolean respond = loginTask.establishConnection(login,password,null);
        return respond;
    }


    /**
     * Check wireless connection
     * @return return true if mobile phone has internet connection
     */
    private boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}


