package nl.vandoren.app.uraandroid.Connection.Security;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.URLEncoder;
import java.util.HashMap;

import nl.vandoren.app.uraandroid.Connection.ConnectionManager;
import nl.vandoren.app.uraandroid.Connection.HttpRequest;

;

/**
 * Created by Alex on 5/26/2015.
 */
public class LoginTask{
    static final String TAG = "myLoginLog";

    final String JSON_CONFIRMATION = "Confirmation";
    final String JSON_FULLNAME = "fullName";
    final String URA_DBID = "URA_DBID";
    final String URA_SYNTUS = "syntus";
    final String CAPA_DBID = "capa_DBID";

    RSAEngine rsaEngine;
    BufferedReader in = null;
    ConnectionManager connectionManager;
    String serviceRespondXML= null;
    String phoneImei = null;
    String aesCypherTextForService = null;
    String aesCypherForServiceEncodedURL=null;
    String sslConfirmationEncrypted = null;
    String sslConfirmationDecrypted = null;
    String encryptedLoginName = null;
    String encryptedPassword = null;
    String httpPostURL = null;
    String httpPostResponse = null;
    String crmCertificateMessage = "URA";
    boolean loginResult = false;

    private LoginTaskAsyncTask lasync;
    private Handler handler;

    public LoginTask(String phoneImei)
    {
        this.phoneImei = phoneImei;
        this.rsaEngine = new RSAEngine();
        this.connectionManager = ConnectionManager.getInstance(); //get SingleTon object
    }
    public boolean establishConnection(String login, String password, Handler h){
        try
        {
            //Note AsyncTask instances can only be used one time.
            lasync = new LoginTaskAsyncTask();
            if(h!=null) {
                this.handler = h;
                lasync.execute(login, password);
            }
            //I cannot use execute here, because this method called from main thread.
            else
            {
                this.handler = null;
                loginResult = lasync.getSSLConnection(login,password);
            }

        }
        catch(Exception e)
        {
            Log.i("myLog", "Connection Error: " + e.getMessage());
        }
        return loginResult;
    }

    class LoginTaskAsyncTask extends AsyncTask<String, Void, Void>
    {
        protected void onPreExecute() {
            loginResult = false;
        }

        @Override
        protected Void doInBackground(String... params) {
            Log.i("myLog", "ProgressTask doInBackground");
            loginResult = getSSLConnection(params[0],params[1]);
            if(handler != null) {
                if (loginResult)
                    handler.sendEmptyMessage(1);
                else
                    handler.sendEmptyMessage(2);
            }
            return null;
        }

        //This method returns value with delay. It means that UI thread will continue before end of this method.
        @Override
        protected void onPostExecute(Void result) {
            Log.i("myLog", "ProgressTask onPostExecute");
            //pDialog.dismiss();
        }

        /***
         * Generates url address for Hello Message to Service
         * @return
         */
        private String getHelloUrlString(){
            return connectionManager.getUrlSSLHelloControl() +"?message="+ crmCertificateMessage + "&phoneIMEI=" + phoneImei;
        }

        private String getKeyExchangeUrlString(){
            return connectionManager.getUrlSSLKeyExchangeControl() +  "?message=" + aesCypherForServiceEncodedURL + "&sessionID=" + connectionManager.getUserSessionID()[0];
        }


        private boolean getSSLConnection(String loginName, String password) {
            try {
                //App sends Hello Message to the Service. Service return SessionID and RSA publicKey(Each session will have new public key)
                serviceRespondXML = HttpRequest.executeHttpGet(getHelloUrlString(), null, "GET");

                //Encrypt message(EAS key) for service uses service RSA key.
                //variable userSessionID is array(for reason I will use it as reference ). Into this array I will save sessionID
                aesCypherTextForService = rsaEngine.Encrypt(serviceRespondXML, connectionManager.getAesPrivateKey(), connectionManager.getUserSessionID());


                //Create address string for special characters
                aesCypherForServiceEncodedURL = URLEncoder.encode(aesCypherTextForService, "UTF-8");

                //KeyExchange step. Send session ID and encrypted AES key.
                // return message should have confirmation that Service successfully stored EAS key in Session
                sslConfirmationEncrypted = HttpRequest.executeHttpGet(getKeyExchangeUrlString(), null, "GET");

                if(sslConfirmationEncrypted.isEmpty())
                    return false;

                //Decrypt message from Service uses AES key.
                sslConfirmationDecrypted = connectionManager.decryptAesMessage(sslConfirmationEncrypted);

                if (sslConfirmationDecrypted.equals("SSLConfirmed"));
                {
                    //Now Service and APP has same private key. APP can secure send encrypted login and password to service.
                    encryptedLoginName = connectionManager.encryptAesMessage(loginName);
                    encryptedPassword = connectionManager.encryptAesMessage(password);

                    //TODO to improve security first make hashmap and then encrypt because each time the order can be different, to get back wcf has special parser and dynamic variable
                    HashMap<String,String> postParameters = new HashMap<String,String>();
                    postParameters.put("0", connectionManager.getUserSessionID()[0]);
                    postParameters.put("1",encryptedLoginName);
                    postParameters.put("2", encryptedPassword);

                    // To send login and password is used POST method. REST endpoint in service
                    httpPostURL = connectionManager.getUrlSSLoginControl();
                    httpPostResponse = HttpRequest.executeHttpPost(httpPostURL, postParameters);

                    //decrypt response message
                    String decryptedHttpPostResponse = connectionManager.decryptAesMessage(httpPostResponse);

                    if (httpPostResponse !=null && parseLoginMessageFromService(decryptedHttpPostResponse)) {

                        //Parse decrypted message(Json Format)
                        return true;
                    }
                    else
                        return false;
                }
            }
            catch (Exception e)
            {
                Log.e(TAG,"Error occured while login: " + e.getMessage());
                return false;
            }
            finally {
            }
        }

        private boolean parseLoginMessageFromService(String httpPostResponse)
        {
            try {
                JSONObject jSonObject = new JSONObject(httpPostResponse);
                String confirmed = jSonObject.getString(JSON_CONFIRMATION);


                if(confirmed != null && confirmed.equals("LoginAccepted")){

                    // save user properties
                    connectionManager.getUserObject().fullName = jSonObject.getString(JSON_FULLNAME);
                    //account can be null, then set -1 and week planning is not available
                    connectionManager.getUserObject().uraAccount_Dbid = (jSonObject.getString(URA_DBID).equals(""))?-1:jSonObject.getInt(URA_DBID);
                    connectionManager.getUserObject().ura_syntus = jSonObject.getString(URA_SYNTUS);
                    connectionManager.getUserObject().capa_Dbid = jSonObject.getString(CAPA_DBID);

                    return true;
                }
                //Serice didn't confirm connection;
                return false;

            } catch (JSONException e) {
                Log.e(TAG,"Error while json parsing after confirmation: " + e.getMessage());
                return false;
            }
        }
    }
}
