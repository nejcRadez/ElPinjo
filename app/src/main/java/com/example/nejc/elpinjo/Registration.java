package com.example.nejc.elpinjo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Registration extends AppCompatActivity {

    private EditText registrationPassword;
    private EditText registrationEmail;
    private EditText registrationUsername;
    private Button registrationSubmit;

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
            }

        });

    }

}