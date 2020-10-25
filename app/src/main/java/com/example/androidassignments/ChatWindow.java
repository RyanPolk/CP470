package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
    private static ListView chat;
    private static EditText etMessage;
    private static Button btnSend;
    private ArrayList<String> messages = new ArrayList<String>();
    private ChatAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        //Set the class variables for screen elements
        chat = findViewById(R.id.chatView);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);
        //in this case, “this” is the ChatWindow, which is-A Context object
        messageAdapter =new ChatAdapter( this );
        chat.setAdapter(messageAdapter);
    }

    //Gets the text in the EditText field, and adds it to your array list
    public void sendMessage(View view) {
        messages.add(etMessage.getText().toString());
        messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/getView()
        etMessage.setText("");
    }

    //Inner class
    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        //This returns the number of rows that will be in your listView
        public int getCount() {
            return messages.size();
        }

        //This returns the item to show in the list at the specified position
        public String getItem(int position) {
            return messages.get(position);
        }

        //This returns the layout that will be positioned at the specified row in the list
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null ;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(   getItem(position)  ); // get the string at position
            return result;

        }


    }

}