package com.example.popie.broadcastreceiver_sharedpreference_notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    final static String Key = "MyPreferences";
   
    @BindView(R.id.wifiSwitch)
    Switch wifiSwitch;
    @BindView(R.id.airplaneSwitch)
    Switch airplaneSwitch;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.tvBatteryLevel)
    TextView tvBatteryLevel;
    WifiManager wifiManager;
    SharedPreferences sharedPreferences;
    IntentFilter intentFilter;
    //dynamic broadcastReciever for battery check
    BroadcastReceiver batterBroadcastReceiver = new BroadcastReceiver() {

        String batteryLow = "Battery level is LOW!";
        String batteryOkay = "Battery level is OKAY!";

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.BATTERY_LOW")) {
                tvBatteryLevel.setText(batteryLow);
            } else if (intent.getAction().equals("android.intent.action.BATTERY_OKAY")) {
                tvBatteryLevel.setText(batteryOkay);
            }
        }
    };

    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //checking the current wifi state and setting accordingly
        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        if (wifiManager != null) {
            if (wifiManager.isWifiEnabled()) {
                wifiSwitch.setChecked(true);
            } else
                wifiSwitch.setChecked(false);
        }


        //getting sharedPreference instance pointing to the file containing value
        sharedPreferences = getSharedPreferences(Key, MODE_PRIVATE);

        //getting the saved name
        String name = sharedPreferences.getString("name", "");
        etName.setText(name);

        intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BATTERY_LOW");
        intentFilter.addAction("android.intent.action.BATTERY_OKAY");

    }

    //wifeSwitch checkChangedListener
    @OnCheckedChanged(R.id.wifiSwitch)
    protected void onCheckedChanged(CompoundButton buttonView,
                                    boolean isChecked) {
        if (buttonView.getId() == R.id.wifiSwitch) {
            if (isChecked) {
                wifiManager.setWifiEnabled(true);
            } else {
                wifiManager.setWifiEnabled(false);
            }
        }
    }


    //onClick on btnSave
    @OnClick(R.id.btnSave)
    protected void onClick() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", etName.getText().toString());
        editor.apply();
        Toast.makeText(getApplicationContext(), "Name Saved!", Toast.LENGTH_SHORT).show();
    }


    //registering batteryBroadcastReciever
    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(batterBroadcastReceiver, intentFilter);
    }

    //unregistering batteryBroadcastReciever
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(batterBroadcastReceiver);
    }
}

