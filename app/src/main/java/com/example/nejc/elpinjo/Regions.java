package com.example.nejc.elpinjo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Regions extends Activity {

    WifiManager mainWifi;
    List<ScanResult> wifiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        final ListView listview = (ListView) findViewById(R.id.listview);
        String[] values = new String[] { "Spalnica", "Kuhinja", "Kopalnica",
                "Hodnik", "Dnevna Soba", "Kino" };

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
        final Intent intent = new Intent(this, MeasureWifiSignalActivity.class);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view,
                int position, long id) {
                startActivity(intent);
            }
        });
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    public void displayWifiSignal(View view){

        mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
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
