package com.example.androidassignments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_Of_My_ITEMS = "messages";
    public static final String Item_ID = "KEY_ID";
    public static final String ITEM_NAME = "KEY_MESSAGE";
    protected static final String DATABASE_NAME = "chatDatabase.db";
    protected static final int VERSION_NUM = 3;
    private static final String DATABASE_CREATE = "create table " + TABLE_Of_My_ITEMS + "(" +
            Item_ID + " integer primary key autoincrement, " + ITEM_NAME + " text not null);";

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Of_My_ITEMS);
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);
    }
}
