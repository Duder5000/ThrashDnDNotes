package com.example.hserban.lecture8_samplecode;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class  MyHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String CREATE_TABLE_DnD =
            "CREATE TABLE "+
                    Constants.TABLE_NAME_DnD + " (" +
                    Constants.UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Constants.FLOAT_XPOS + " TEXT, " +
                    Constants.FLOAT_YPOS + " TEXT, " +
                    Constants.STRING_NOTE + " TEXT," +
                    Constants.INT_LINKED_TO_IMG_ID  + " TEXT" +
                    ");";

    private static final String DROP_TABLE_DnD = "DROP TABLE IF EXISTS " + Constants.TABLE_NAME_DnD;

    public MyHelper(Context context){
        super (context, Constants.DATABASE_NAME_DnD, null, Constants.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_DnD);
            Toast.makeText(context, "onCreate() called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, "exception onCreate() db", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE_DnD);
            onCreate(db);
            Toast.makeText(context, "onUpgrade called", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(context, "exception onUpgrade() db", Toast.LENGTH_LONG).show();
        }
    }
}

