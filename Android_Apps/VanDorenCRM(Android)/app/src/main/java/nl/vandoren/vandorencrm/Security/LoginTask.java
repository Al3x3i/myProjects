package nl.vandoren.vandorencrm.Security;

import android.os.AsyncTask;
import android.util.Log;
import android.os.Handler;;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;

import nl.vandoren.vandorencrm.ConnectionManager;

/**
 * Created by Alex on 2/27/2015.
 */
public class LoginTask{

    public static final int HTTP_TIMEOUT = 30 * 1000;
    private static HttpClient mHttpClient;
    CookieStore cookieStore = new BasicCookieStore();  // cookies is not implemented, they don't have functionality yet, later may be deleted.
    HttpContext localContext = new BasicHttpContext();
    RSAEngine rsaEngine;
    AESEngine aesEngine;
    BufferedReader in = null;
    private ConnectionManager connectionManager;
    String serviceRespondXML= null;
    String aesCypherTextForService = null;
    String aesCypherForServiceEncodedURL=null;
    String sslConfirmationEncrypted = null;
    String sslConfirmationDecrypted = null;
    String encryptedLoginName = null;
    String encryptedPassword = null;
    String httpPostURL = null;
    String httpPostResponse = null;
    String crmCertificateMessage = "CRM";
    boolean loginResult;
    public LoginTaskAsyncTask lasync;
    private Handler handler;

    public LoginTask(ConnectionManager mConnectionManager)
    {

        this.rsaEngine = new RSAEngine();
        this.connectionManager = mConnectionManager;
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
        //This method invokes handler which has implemented only in LoginActivity and Splash Activity, for Re-Conection is used variable loginResult;
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
            return connectionManager.getUrlSSLHelloControl() +"?message="+ crmCertificateMessage + "&clientIME=" + connectionManager.phoneImei;
        }

        private String getKeyExchangeUrlString(){
            return connectionManager.getUrlSSLKeyExchangeControl() + "?id=" + connectionManager.getUserSessionID()[0] + "&message=" + aesCypherForServiceEncodedURL;
        }

        /*******************
         * This Method implements main SSL logic.
         * @param loginName -User login Name
         * @param password - User Password
         * @return
         */
        private boolean getSSLConnection(String loginName, String password) {
            try {
                //App sends Hello Message to the Service. Service return SessionID and RSA publicKey(Each session will have new public key)
                serviceRespondXML = executeHttpGet(getHelloUrlString());

                //Encrypt message(EAS key) for service uses service RSA key.
                //variable userSessionID is array(for reason I will use it as reference ). Into this array I will save sessionID
                aesCypherTextForService = rsaEngine.Encrypt(serviceRespondXML, connectionManager.getAesPrivateKey(), connectionManager.getUserSessionID());

                //Create address string for special characters
                aesCypherForServiceEncodedURL = URLEncoder.encode(aesCypherTextForService, "UTF-8");

                //KeyExchange step. Send session ID and encrypted AES key.
                // return message should have confirmation that Service successfully stored EAS key in Session
                sslConfirmationEncrypted = executeHttpGet(getKeyExchangeUrlString());

                if(sslConfirmationEncrypted.isEmpty())
                    return false;

                //Decrypt message from Service uses AES key.
                sslConfirmationDecrypted = connectionManager.decryptAesMessage(sslConfirmationEncrypted);

                if (sslConfirmationDecrypted.equals("SSLConfirmed"));
                {
                    //Now Service and APP has same private key. APP can secure send encrypted login and password to service.
                    encryptedLoginName = connectionManager.encryptAesMessage(loginName);
                    encryptedPassword = connectionManager.encryptAesMessage(password);

                    ArrayList<NameValuePair> postParameters = new ArrayList<>();
                    postParameters.add(new BasicNameValuePair("0",connectionManager.getUserSessionID()[0]));
                    postParameters.add(new BasicNameValuePair("1",encryptedLoginName));
                    postParameters.add(new BasicNameValuePair("2",encryptedPassword));

                    // To send login and password is used POST method. REST endpoint in service
                    httpPostURL = connectionManager.getUrlSSLoginControl();
                    httpPostResponse = executeHttpPost(httpPostURL,postParameters);

                    //decrypt response message
                    String decryptedHttpPostResponse = connectionManager.decryptAesMessage(httpPostResponse);

                    // After whole task APP gets last confirmation message.
                    if (httpPostResponse !=null && decryptedHttpPostResponse.equals("LoginAccepted"))
                        return true;
                    else
                        return false;
                }
            }
            catch (Exception e)
            {
                return false;
            }
            finally {
            }
        }

        //TODO Find solution not to open every time new InputStream.
        private String executeHttpGet(String url){
            localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

            try{
                URI website = new URI(url);
                HttpGet request = new HttpGet(website);
                HttpClient client = getHttpClient();
                HttpResponse response = client.execute(request,localContext);

                in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String respond = in.readLine();

                Log.e("myLog", "The service public key is"+respond);
                return respond;
            }catch(Exception e){
                Log.e("myLog", "Error in httpGET connection " + e.toString());
                return null;
            }
        }

        public String executeHttpPost(String url, ArrayList<NameValuePair> postParameters) {
            BufferedReader in = null;
            try {
                HttpClient client = getHttpClient();
                HttpPost request = new HttpPost(url);

                request.setHeader("Content-Type", "application/x-www-form-urlencoded");
                //request.setHeader("Accept", "application/json"); return value // This approach is used if I want to send json message format

                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
                request.setEntity(formEntity);
                HttpResponse response = client.execute(request);
                in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String result = in.readLine();
                in.close();
                return result;
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
    }
}
