package com.example.nejc.elpinjo;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    WifiManager mainWifi;
    List<ScanResult> wifiList;
    double signalLevelSum;
    public final static String EXTRA_MESSAGE = "com.example.nejc.elpinjo.MESSAGE";
    public final static String EXTRA_MESSAGE2 = "com.example.nejc.elpinjo.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    public void getWifiSignal(View view){
        Intent intent = new Intent(this, DisplayWifiSignalActivity.class);
        wifiList = mainWifi.getScanResults();
        Map<String, Double> wifiMap = new HashMap<String, Double>();
        double signalLevelDouble;
        double x=10;
        double dbmToWatt;
        int signalLevelDbm;
        Map.Entry wifiName;
        double signaStrength;
        for (ScanResult result : wifiList) {
            signalLevelDbm = result.level;
            signalLevelDouble = (double) signalLevelDbm;
            dbmToWatt = Math.pow(x, signalLevelDouble/10);
            dbmToWatt = dbmToWatt/1000;
            wifiMap.put(result.SSID, dbmToWatt);
        }

        Iterator itr = wifiMap.entrySet().iterator();
        while(itr.hasNext()) {
            wifiName = itr.next();

            intent.putExtra(EXTRA_MESSAGE, signalStrength);
        }
        startActivity(intent);
    }
}
