package com.example.androidassignments;

import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class MessageFragment extends Fragment {
    long itemID;
    String message;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null){
            // Get back arguments
            if(getArguments() != null) {
                itemID = getArguments().getLong("itemID", 0);
                message = getArguments().getString("message", "");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        // Inflate the xml file for the fragment
        View view = inflater.inflate(R.layout.fragment_message, parent, false);
        //Set button on click event
        Button button = (Button) view.findViewById(R.id.btnDelete);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(ChatWindow.frameLayoutExists) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("itemID", itemID);
                    getActivity().setResult(Activity.RESULT_OK, resultIntent);
                } else {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("itemID", itemID);
                    getActivity().setResult(Activity.RESULT_OK, resultIntent);
                    getActivity().finish();
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Set values for view here
        TextView txtMessage = view.findViewById(R.id.txtMessage);
        TextView txtID = view.findViewById(R.id.txtID);
        //Update view
        String temp = "Message = " + message;
        txtMessage.setText(temp);
        temp = "Item ID = " + String.valueOf(itemID);
        txtID.setText(temp);
    }
}
