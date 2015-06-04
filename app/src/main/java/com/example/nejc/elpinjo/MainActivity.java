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

import java.util.List;


public class MainActivity extends ActionBarActivity {

    WifiManager mainWifi;
    List<ScanResult> wifiList;
    int signalLevelSum;
    public final static String EXTRA_MESSAGE = "com.example.nejc.elpinjo.MESSAGE";
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
        for (ScanResult result : wifiList) {

            int signalLevel = result.level;
            signalLevelSum += signalLevel;
        }
        String signalStrength = Integer.toString(signalLevelSum);
        intent.putExtra(EXTRA_MESSAGE, signalStrength);
        startActivity(intent);
    }
}
