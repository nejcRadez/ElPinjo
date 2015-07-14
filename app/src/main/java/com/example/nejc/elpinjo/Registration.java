package com.example.nejc.elpinjo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nejc.elpinjo.API.UsersAPI;
import com.example.nejc.elpinjo.models.User;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Registration extends AppCompatActivity {

    private EditText registrationPassword;
    private EditText registrationEmail;
    private EditText registrationUsername;
    private Button registrationSubmit;
    public User user;

    public static final String ENDPOINT="http://10.0.2.2/xampp/Pino/public/index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //LinearLayout layout = (LinearLayout) findViewById(R.id.displayRegistrationLayout);

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

        registrationPassword = (EditText) findViewById(R.id.registerPassword);
        registrationEmail = (EditText) findViewById(R.id.registerEmail);
        registrationUsername = (EditText) findViewById(R.id.registerUsername);
        registrationSubmit = (Button) findViewById(R.id.registerButton);

        registrationSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String valuePassword = registrationPassword.getText().toString();
                String valueUsername = registrationUsername.getText().toString();
                String valueEmail = registrationEmail.getText().toString();

                user = new User();

                user.setPassword(valuePassword);
                user.setUsername(valueUsername);
                user.setEmail(valueEmail);

                if (isOnline()) {
                    registerUser(user);
                }else{
                    Toast.makeText(Registration.this, "Network is not awailable", Toast.LENGTH_LONG).show();
                }
            }

        });

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

    private void registerUser(User user){
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build();

        UsersAPI api = adapter.create(UsersAPI.class);

        api.createUser(user.getUsername(), user.getEmail(), user.getPassword(), new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                Toast.makeText(Registration.this, user.getUsername() + " you have successfully registered!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(Registration.this, "Your registration was unsuccessful!", Toast.LENGTH_LONG).show();
            }
        });
    }

}