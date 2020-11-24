package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.androidassignments.ListItemsActivity.ACTIVITY_NAME;

public class ChatWindow extends AppCompatActivity {
    private static ListView chat;
    private static EditText etMessage;
    private ArrayList<String> messages;
    private ChatAdapter messageAdapter;
    private ChatDatabaseHelper dbHelper;
    private SQLiteDatabase database;
    public static boolean frameLayoutExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        //Set the class variables for screen elements
        chat = findViewById(R.id.chatView);
        etMessage = findViewById(R.id.etMessage);
        //Update the message list
        updateMessageList();
        //Check to see if in tablet mode, set variable accordingly
        if(findViewById(R.id.contentView) != null) {
            frameLayoutExists = true;
        } else {
            frameLayoutExists = false;
        }
        //Event listener for the elements in the list view
        ListView lv = findViewById(R.id.chatView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3)
            {
                String message = messageAdapter.getItem(position);
                long itemID = messageAdapter.getItemId(position);

                if(frameLayoutExists) {
                    //Tablet mode
                    MessageFragment mFragment = new MessageFragment();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    Bundle args = new Bundle();
                    args.putLong("itemID", itemID);
                    args.putString("message", message);
                    mFragment.setArguments(args);
                    ft.replace(R.id.contentView, mFragment);
                    ft.commit();
                } else {
                    //Phone mode
                    Intent intent = new Intent(getApplication(), MessageDetails.class);
                    intent.putExtra("itemID", itemID);
                    intent.putExtra("message", message);
                    startActivityForResult(intent, 10);
                }
            }
        });
    }

    //Gets all messages from the database
    public void updateMessageList() {
        messages = new ArrayList<String>();
        //In this case, “this” is the ChatWindow, which is-A Context object
        messageAdapter =new ChatAdapter( this );
        chat.setAdapter(messageAdapter);
        //Open SQLiteDatabase using ChatDatabaseHelper
        dbHelper = new ChatDatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
        //Add all database messages to the messages ArrayList
        String sSQL = "SELECT KEY_MESSAGE FROM messages;";
        Cursor cursor = database.rawQuery(sSQL, null);
        Log.i(ACTIVITY_NAME, "Cursor’s column count = " + cursor.getColumnCount());
        Log.i(ACTIVITY_NAME, "Column name = " + cursor.getColumnName(cursor.getColumnIndex(dbHelper.ITEM_NAME)));
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + cursor.getString(cursor.getColumnIndex(dbHelper.ITEM_NAME)));
            messages.add(cursor.getString(cursor.getColumnIndex(dbHelper.ITEM_NAME)));
            cursor.moveToNext();
        }
        cursor.close();
    }

    //Gets the text in the EditText field, and adds it to your array list
    public void sendMessage(View view) {
        //Add text from EditText to database
        ContentValues values = new ContentValues();
        values.put(dbHelper.ITEM_NAME, etMessage.getText().toString());
        database.insert(dbHelper.TABLE_Of_My_ITEMS, null, values);
        //Add text from EditText to messages
        messages.add(etMessage.getText().toString());
        messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/getView()
        etMessage.setText("");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            long returnValue = data.getLongExtra("itemID",0);
            deleteMessage(returnValue);
        }
    }

    public void deleteMessage(long itemID) {
        //Delete returned message from database
        database.delete("messages", "KEY_ID = '" + itemID + "'", null);
        //Update the list message list
        updateMessageList();
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

        //Get the database ID of the message at position
        public long getItemId(int position) {
            String sSQL = "SELECT * FROM messages;";
            Cursor cursor = database.rawQuery(sSQL, null);
            cursor.moveToPosition(position);
            long itemID = cursor.getLong(cursor.getColumnIndex(dbHelper.Item_ID));
            cursor.close();
            return itemID;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
        database.close();
    }
}