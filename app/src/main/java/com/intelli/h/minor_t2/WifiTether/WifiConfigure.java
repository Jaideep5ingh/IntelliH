package com.intelli.h.minor_t2.WifiTether;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.intelli.h.minor_t2.POJO.Pin;
import com.intelli.h.minor_t2.R;

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

public class WifiConfigure extends AppCompatActivity implements View.OnClickListener {

    public final static String PREF_IP = "PREF_IP_ADDRESS";
    public final static String PREF_PORT = "PREF_PORT_NUMBER";
    Pin selectedPin;
    private Button confirm;
    private EditText editTextIPAddress, editTextPortNumber;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("HTTP_HELPER_PREFS", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        confirm = (Button) findViewById(R.id.connectButton);
        confirm.setOnClickListener(this);

        editTextIPAddress = (EditText) findViewById(R.id.editTextIPAddress);
        editTextPortNumber = (EditText) findViewById(R.id.editTextPortNumber);

        editTextIPAddress.setText(sharedPreferences.getString(PREF_IP, ""));
        editTextPortNumber.setText(sharedPreferences.getString(PREF_PORT, ""));
    }


    @Override
    public void onClick(View view) {

        String ipAddress = editTextIPAddress.getText().toString().trim();
        String portNumber = editTextPortNumber.getText().toString().trim();

        editor.putString(PREF_IP, ipAddress);
        editor.putString(PREF_PORT, portNumber);
        editor.commit();

        if (ipAddress.length() > 0 && portNumber.length() > 0) {
            new HttpRequestAsyncTask(
                    view.getContext(), selectedPin.getPinNo(), ipAddress, portNumber, "pin"
            ).execute();
        }
    }
}
