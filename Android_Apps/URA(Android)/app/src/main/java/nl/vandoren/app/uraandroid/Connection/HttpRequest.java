package nl.vandoren.app.uraandroid.Connection;

import android.text.Html;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alex on 5/27/2015.
 */
public class HttpRequest {
     private final static int HTTP_TIMEOUT = 30 * 1000;

    /**
     * Method Implements http request only for GET, PUT, DELETE
     * @param desiredUrl request url
     * @param parameters parameters which will be transferred
     * @param requestMethod request method GET, PUT, DELETE
     * @return returns return return value from service
     */
    public static String executeHttpGet(String desiredUrl, HashMap<String,String> parameters, String requestMethod) {
        HttpURLConnection connection=null;

        BufferedReader reader = null;
        try{
            /**
             * Create string from parameters
             */
            if (parameters != null) {
                StringBuilder params = new StringBuilder();
                for (Map.Entry<String, String> e : parameters.entrySet()) {
                    params.append(e.getKey() + "=" + URLEncoder.encode(e.getValue(), "UTF-8") + "&");
                }
                desiredUrl = desiredUrl+"?"+ params.toString();
            }

            URL url = new URL(desiredUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod);
            //connection.setReadTimeout(HTTP_TIMEOUT); //TODO SET TIME_OUT
            connection.setConnectTimeout(HTTP_TIMEOUT);
            connection.connect();

            int http_status = connection.getResponseCode();

            if (http_status == 200 ){
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line = reader.readLine();
                reader.close();
                return Html.fromHtml(line).toString(); // translate special characters to readable text
            }
            return null;
        }catch(Exception e){
            Log.e("myLog", "Error in httpGET connection " + e.toString());
            return null;
        }
        finally {
            if(connection!=null)
                connection.disconnect();
        }
    }

    /**
     *  Method implements only http POST request
     * @param desiredUrl reuqest URL
     * @param postParameters parameters which will be transferred
     * @return return service return message
     */
    public static String executeHttpPost(String desiredUrl, HashMap<String,String> postParameters) {

        HttpURLConnection urlConnection = null;
        try {
            // create connection
            URL urlToRequest = new URL(desiredUrl);
            urlConnection = (HttpURLConnection) urlToRequest.openConnection();
            urlConnection.setConnectTimeout(HTTP_TIMEOUT);
            urlConnection.setReadTimeout(HTTP_TIMEOUT);

            // handle POST parameters
            if (postParameters != null) {
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");


                StringBuilder params = new StringBuilder();
                for(Map.Entry<String, String> e : postParameters.entrySet()) {

                    params.append(e.getKey()+"="+ URLEncoder.encode(e.getValue(), "UTF-8") + "&");
                }
                urlConnection.setFixedLengthStreamingMode(params.toString().length());
                //send the POST out
                DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());

                wr.writeBytes(params.toString());
                wr.flush();
                wr.close();
            }

            // handle issues
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                // read respond
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                String line = reader.readLine();
                reader.close();
                return line;
            }
            return null;
        } catch (MalformedURLException e) {
            // handle invalid URL
        } catch (SocketTimeoutException e) {
            //  timeout
        } catch (IOException e) {
            // handle I/0
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

}
