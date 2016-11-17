package com.intelli.h.minor_t2.WifiTether;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

public class HttpRequestAsyncTask extends AsyncTask<Void, Void, Void> {

    private String requestReply, ipAddress, portNumber;
    private Context context;
    private AlertDialog alertDialog;
    private String parameter;
    private String parameterValue;

    public HttpRequestAsyncTask(Context context, String parameterValue, String ipAddress, String portNumber, String parameter) {
        this.context = context;

        alertDialog = new AlertDialog.Builder(this.context)
                .setTitle("HTTP Response From IP Address:")
                .setCancelable(true)
                .create();

        this.ipAddress = ipAddress;
        this.parameterValue = parameterValue;
        this.portNumber = portNumber;
        this.parameter = parameter;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        alertDialog.setMessage("Data sent, waiting for reply from server...");
        if (!alertDialog.isShowing()) {
            alertDialog.show();
        }
        requestReply = sendRequest(parameterValue, ipAddress, portNumber, parameter);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        alertDialog.setMessage(requestReply);
        if (!alertDialog.isShowing()) {
            alertDialog.show();
        }
    }

    @Override
    protected void onPreExecute() {
        alertDialog.setMessage("Sending data to server, please wait...");
        if (!alertDialog.isShowing()) {
            alertDialog.show();
        }
    }

    public String sendRequest(String parameterValue, String ipAddress, String portNumber, String parameterName) {
        String serverResponse = "ERROR";

        try {
            HttpClient httpclient = new DefaultHttpClient(); // create an HTTP client
            // define the URL e.g. http://myIpaddress:myport/?pin=13 (to toggle pin 13 for example)
            URI website = new URI("http://" + ipAddress + ":" + portNumber + "/?" + parameterName + "=" + parameterValue);
            HttpGet getRequest = new HttpGet(); // create an HTTP GET object
            getRequest.setURI(website); // set the URL of the GET request
            HttpResponse response = httpclient.execute(getRequest); // execute the request
            // get the ip address server's reply
            InputStream content = null;
            content = response.getEntity().getContent();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    content
            ));
            serverResponse = in.readLine();
            // Close the connection
            content.close();
        } catch (ClientProtocolException e) {
            // HTTP error
            serverResponse = e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            // IO error
            serverResponse = e.getMessage();
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // URL syntax error
            serverResponse = e.getMessage();
            e.printStackTrace();
        }
        // return the server's reply/response text
        return serverResponse;
    }

}
