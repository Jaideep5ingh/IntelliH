package com.intelli.h.minor_t2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;
import android.widget.GridView;
import com.intelli.h.minor_t2.BT_Integration.DeviceList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.R.color.black;


public class ControlPanel extends AppCompatActivity {
    Button Discnt;
    GridView gridView;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    Toolbar toolbar;
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS);

        new ConnectBT().execute();

        setContentView(R.layout.activity_control_panel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Discnt = (Button)findViewById(R.id.discnt);

        gridView = (GridView) findViewById(R.id.controlGrid);
        gridView.setAdapter(new GridAdapter(this));


        Discnt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Disconnect(); //close connection
            }
        });
    }

    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //return to the first layout

    }

    private void turnOffLed()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("0".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void turnOnLed()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("1".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(ControlPanel.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            } catch (IOException e) {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }

        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

    public class GridAdapter extends BaseAdapter {

        private List<String> bulbList = new ArrayList<>();
        private LayoutInflater inflater;
        private Context context;

        public GridAdapter(Context context) {

            bulbList.add("Bulb 1");
            bulbList.add("Bulb 2");
            bulbList.add("Bulb 3");
            this.context = context;
            inflater = ((Activity) context).getLayoutInflater();
        }

        @Override
        public int getCount() {
            return bulbList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return (position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = inflater.inflate(R.layout.control_panel_item,parent,false);
            final ImageButton imageButton = (ImageButton)view.findViewById(R.id.bulb_image_button);
            LinearLayout switcher = (LinearLayout) view.findViewById(R.id.switcher);
            TextView textView = (TextView) view.findViewById(R.id.bulbName);
            textView.setText(bulbList.get(position));
            imageButton.setBackgroundColor(view.getResources().getColor(black));
            imageButton.setImageResource(R.drawable.ic_lightbulb);
            imageButton.setOnClickListener(new View.OnClickListener() {
                boolean flag = true;
                @Override
                public void onClick(View v) {
                    flag = !flag;
                    if (!flag){
                        imageButton.setBackgroundColor(v.getResources().getColor(R.color.colorAccent));
                        imageButton.setImageResource(R.drawable.ic_lightbulb_lit);
                        turnOnLed();
                    }else{
                        imageButton.setBackgroundColor(v.getResources().getColor(black));
                        imageButton.setImageResource(R.drawable.ic_lightbulb);
                        turnOffLed();
                    }

                }
            });
            return view;
        }
    }
    }
