package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MessageDetails extends AppCompatActivity {
    long itemID;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        if(savedInstanceState == null){
            itemID = getIntent().getLongExtra("itemID", 0);
            message = getIntent().getStringExtra("message");
        }

        MessageFragment mFragment = new MessageFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        args.putLong("itemID", itemID);
        args.putString("message", message);
        mFragment.setArguments(args);
        ft.add(R.id.frame1, mFragment);
        ft.commit();
    }

}