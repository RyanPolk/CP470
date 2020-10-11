package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Basic operations
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log which function we are currently in
        Log.i(ACTIVITY_NAME, "In onCreate()");
        //Reference to button
        Button myButton = findViewById(R.id.btnButton);
        //Button onClickListener,
        myButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //Intent to open ListItemsActivity and uses startActivityForResult
                Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);
                startActivityForResult(intent, 10);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent data) {
        super.onActivityResult(requestCode, responseCode, data);
        //Checks to see if the request code matches, if so log event
        if(requestCode == 10 && responseCode == RESULT_OK) {
            String messagePassed = data.getStringExtra("Response");
            Log.i(ACTIVITY_NAME, "Returned to MainActivity.onActivityResult");
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(this , "ListItemsActivity passed: " + messagePassed, duration);
            toast.show(); //display your message box
        }
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
}