package com.example.hserban.lecture8_samplecode;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDatabase2 {
    private SQLiteDatabase db;
    private Context context;
    private final MyHelper helper;

    public MyDatabase2 (Context c){
        context = c;
        helper = new MyHelper(context);
    }

    public long insertData (String x, String y, String n, String i){
        db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.FLOAT_XPOS, x);
        contentValues.put(Constants.FLOAT_YPOS, y);
        contentValues.put(Constants.STRING_NOTE, n);
        contentValues.put(Constants.INT_LINKED_TO_IMG_ID , i);
        long id = db.insert(Constants.TABLE_NAME_DnD, null, contentValues);
        return id;
    }

    public Cursor getData(){
        SQLiteDatabase db = helper.getWritableDatabase();

        String[] columns = {Constants.UID, Constants.FLOAT_XPOS, Constants.FLOAT_YPOS, Constants.STRING_NOTE, Constants.INT_LINKED_TO_IMG_ID};
        Cursor cursor = db.query(Constants.TABLE_NAME_DnD, columns, null, null, null, null, null);
        return cursor;
    }

    public int deleteRow(){
//        SQLiteDatabase db = helper.getWritableDatabase();
//        String[] whereArgs = {"herb"};
//        int count = db.delete(Constants.TABLE_NAME_DnD, Constants.INT_NOTE_ID + "=?", whereArgs);
//        return count;
        return 0;
    }


}

