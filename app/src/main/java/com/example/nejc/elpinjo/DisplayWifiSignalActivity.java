package com.example.nejc.elpinjo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;


public class DisplayWifiSignalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_wifi_signal);

        LinearLayout layout = (LinearLayout) findViewById(R.id.displayWifiLayout);

        double wifiSignalStrength;
        String wifiSignalStrengthString;
        Intent intent = getIntent();
        HashMap<String, Double> hashMap = (HashMap<String, Double>) intent.getSerializableExtra("hashMap");
        for(String wifiName : hashMap.keySet()) {
            TextView textView = new TextView(this);
            wifiSignalStrength = hashMap.get(wifiName);
            wifiSignalStrengthString = Double.toString(wifiSignalStrength);
            textView.setTextSize(20);
            textView.setText(wifiName + wifiSignalStrengthString);
            layout.addView(textView);
        }

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
}
