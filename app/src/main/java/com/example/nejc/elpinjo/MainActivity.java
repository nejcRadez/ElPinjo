package com.example.nejc.elpinjo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.example.nejc.elpinjo.API.UsersAPI;
import com.example.nejc.elpinjo.models.Auth;
import com.example.nejc.elpinjo.models.User;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity {

    WifiManager mainWifi;
    List<ScanResult> wifiList;
    private EditText signInPassword;
    private EditText signInEmail;
    public User user;

    public static final String ENDPOINT="http://10.0.2.2/xampp/Pino/public/index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        super.onCreate(savedInstanceState);
        //Hide action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);
        //Fixes the typeface
        EditText password = (EditText) findViewById(R.id.signInPassword);
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

    public void getRegistrationData(View view){

        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);

    }

    public void signInUser(View view){

        signInPassword = (EditText) findViewById(R.id.signInPassword);
        signInEmail = (EditText) findViewById(R.id.signInEmail);

        String valuePassword = signInPassword.getText().toString();
        String valueEmail = signInEmail.getText().toString();

        user = new User();

        user.setPassword(valuePassword);
        user.setEmail(valueEmail);

        if (isOnline()) {
            signInUserRequest(user);
        } else {
            Toast.makeText(MainActivity.this, "Network is not awailable", Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);

    }

    protected boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }else{
            return false;
        }
    }

    private void signInUserRequest(User user){
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build();

        UsersAPI api = adapter.create(UsersAPI.class);

        api.signInUser(user.getEmail(), user.getPassword(), new Callback<Auth>() {
            @Override
            public void success(Auth auth, Response response) {
                if (auth.isValid()) {
                    Toast.makeText(MainActivity.this, "You have successfully signed in!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this, "Your email or password is not correct!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, "Your request was unsuccessful!", Toast.LENGTH_LONG).show();
            }
        });
    }
}

