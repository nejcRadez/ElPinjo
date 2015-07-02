package com.example.nejc.elpinjo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    WifiManager mainWifi;
    List<ScanResult> wifiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        super.onCreate(savedInstanceState);
        //Hide action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);
        //Fixes the typeface
        EditText password = (EditText) findViewById(R.id.editPassword);
        password.setTypeface(Typeface.DEFAULT);

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

        Map<String, Double> wifiMap = new HashMap<String, Double>();
        double signalLevelDouble;
        double x=10;
        double dbmToWatt;
        double signaStrength;
        int signalLevelDbm;
        Bundle extras = new Bundle();

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

    public void getRegistrationData(View view){

        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);

    }
}

