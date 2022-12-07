package com.example.hserban.lecture8_samplecode;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class RecyclerActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    RecyclerView myRecycler;
    MyDatabase2 db;
    MyAdapter myAdapter;
    MyHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        myRecycler = (RecyclerView) findViewById(R.id.recycler);

        db = new MyDatabase2(this);
        helper = new MyHelper(this);

        Cursor cursor = db.getData();

        int index1 = cursor.getColumnIndex(Constants.FLOAT_XPOS);
        int index2 = cursor.getColumnIndex(Constants.FLOAT_YPOS);
        int index3 = cursor.getColumnIndex(Constants.STRING_NOTE);
        int index4 = cursor.getColumnIndex(Constants.INT_LINKED_TO_IMG_ID);

        ArrayList<String> mArrayList = new ArrayList<String>();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String xPosName = cursor.getString(index1);
            String yPosName = cursor.getString(index2);
            String noteName = cursor.getString(index3);
            String imgRefName = cursor.getString(index4);

            String s = xPosName +"," + yPosName +"," + noteName  +"," + imgRefName;

            mArrayList.add(s);
            cursor.moveToNext();
        }

        myAdapter = new MyAdapter(mArrayList);
        myRecycler.setAdapter(myAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("tester", "In RecyclerActivity - onItemClick");
    }

    public void testTest(){
        Log.d("tester","testTest()");
    }

}
