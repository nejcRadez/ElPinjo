package com.example.nejc.elpinjo;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tadej on 19.7.2015.
 */
public class MeasureWifiSignalActivity extends AppCompatActivity {

    WifiManager mainWifi;
    List<ScanResult> wifiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_wifi_signal);
        addListenerOnButton();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void addListenerOnButton() {
        Button btn = (Button) findViewById(R.id.measure_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWifiSignal(v);
            }
        });
    }

    public void getWifiSignal(View view){

        Intent intent = new Intent(this, DisplayWifiSignalActivity.class);

        Map<String, Double> wifiMap = new HashMap<String, Double>();
        double signalLevelDouble;
        double x=10;
        double dbmToWatt;
        int signalLevelDbm;


        wifiList = mainWifi.getScanResults();
        for (ScanResult result : wifiList) {
            signalLevelDbm = result.level;
            signalLevelDouble = (double) signalLevelDbm;
            dbmToWatt = Math.pow(x, signalLevelDouble/10);
            dbmToWatt = dbmToWatt/1000;
            wifiMap.put(result.SSID, dbmToWatt);
        }
        intent.putExtra("hashMap", (Serializable) wifiMap);
        startActivity(intent);
    }
}

