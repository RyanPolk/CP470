package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "LoginActivity";
    private String fileName = "loginEmail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Basic operations
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Log which function we are currently in
        Log.i(ACTIVITY_NAME, "In onCreate()");
        //Open shared preference from file location and retrieve DefaultEmail
        SharedPreferences prefs = getSharedPreferences(fileName, MODE_PRIVATE);
        String email = prefs.getString("DefaultEmail", "email@domain.com");
        //Get EditText from layout and set the text as DefaultEmail value
        EditText txtLogin = findViewById(R.id.txtLogin);
        txtLogin.setText(email);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    public void onLogin(View view) {
        //Get EditText from layout and set email as string from txtLogin
        EditText txtLogin = findViewById(R.id.txtLogin);
        String email = txtLogin.getText().toString();
        //Open shared preference from file location and open editor
        SharedPreferences prefs = getSharedPreferences(fileName, MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        //Edit the DefaultEmail to be text from email and commit changes
        edit.putString("DefaultEmail", email);
        edit.commit();
        //Create intent to open MainActivity
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}