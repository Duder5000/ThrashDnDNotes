package com.example.hserban.lecture8_samplecode;

import static com.example.hserban.lecture8_samplecode.MainActivity.DEFAULT;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ReadNote extends AppCompatActivity implements View.OnTouchListener {
    ImageView pin1_id, ImgDisplay_id;
    TextView NoteDisplay_id;
    Float extraX, extraY;
    String extraNote, extraImg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_note);

        ImgDisplay_id = findViewById(R.id.ImgDisplay);
        NoteDisplay_id = findViewById(R.id.NoteDisplay);
        pin1_id = findViewById(R.id.PinRead);
        pin1_id.setVisibility(View.INVISIBLE);

        Intent intentReadNote = getIntent();
        extraX = Float.valueOf(intentReadNote.getStringExtra("xPosExtra"));
        extraY = Float.valueOf(intentReadNote.getStringExtra("yPosExtra"));
        extraNote = intentReadNote.getStringExtra("noteExtra");
        extraImg = intentReadNote.getStringExtra("imgRefExtra");

        noteLoadImg(extraImg);
        showNote(extraNote);
        showPin(extraX, extraY);
    }

    public void showPin(float xPin, float yPin){
        Toast.makeText(this, "Load Pin", Toast.LENGTH_SHORT).show();
        pin1_id.setVisibility(View.VISIBLE);
        pin1_id.setX(xPin);
        pin1_id.setY(yPin);
    }

    public void showNote(String s){
        NoteDisplay_id.setText(s);
    }

    public void noteLoadImg(String p){
        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();

        Bitmap photo = BitmapFactory.decodeFile(p);
        ImgDisplay_id.setImageBitmap(photo);
    }

    public void returnBack (View v){
        Log.d("tester","In returnBack");
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}

















