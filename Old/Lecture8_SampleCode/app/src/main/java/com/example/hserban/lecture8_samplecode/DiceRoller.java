package com.example.hserban.lecture8_samplecode;

import static com.example.hserban.lecture8_samplecode.MainActivity.DEFAULT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DiceRoller extends Activity implements SensorEventListener, View.OnClickListener {

    public static final float accThreshhold = 10F;

    private EditText userInput;
    SensorManager mySensorManager;
    Sensor acc;

    private Button d100, d20, d12, d10, d8, d6, d4, d3, d2, clear;
    int dieCounter, dieSize, result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roller);

        userInput = (EditText)findViewById(R.id.userInput);

        clear = (Button)findViewById(R.id.buttonClear);
        clear.setOnClickListener(this);

        dieCounter = 0;
        dieSize = 0;
        result = 0;

        d100 = (Button)findViewById(R.id.d100);
        d100.setOnClickListener(this);

        d20 = (Button)findViewById(R.id.d20);
        d20.setOnClickListener(this);

        d12 = (Button)findViewById(R.id.d12);
        d12.setOnClickListener(this);

        d10 = (Button)findViewById(R.id.d10);
        d10.setOnClickListener(this);

        d8 = (Button)findViewById(R.id.d8);
        d8.setOnClickListener(this);

        d6 = (Button)findViewById(R.id.d6);
        d6.setOnClickListener(this);

        d4 = (Button)findViewById(R.id.d4);
        d4.setOnClickListener(this);

        d3 = (Button)findViewById(R.id.d3);
        d3.setOnClickListener(this);

        d2 = (Button)findViewById(R.id.d2);
        d2.setOnClickListener(this);

        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        acc = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonClear){
            doDisplay((""), Color.BLACK);
        }else{
            switch(v.getId()){
                case R.id.d100:
                    if(dieSize != 100) {
                        dieSize = 100;
                        dieCounter = 1;
                    }else{
                        dieCounter++;
                    }
                    break;
                case R.id.d20:
                    if(dieSize != 20){
                        dieSize = 20;
                        dieCounter = 1;
                    }else{
                        dieCounter++;
                    }
                    break;
                case R.id.d12:
                    if(dieSize != 12){
                        dieSize = 12;
                        dieCounter = 1;
                    }else{
                        dieCounter++;
                    }
                    break;
                case R.id.d10:
                    if(dieSize != 10){
                        dieSize = 10;
                        dieCounter = 1;
                    }else{
                        dieCounter++;
                    }
                    break;
                case R.id.d8:
                    if(dieSize != 8){
                        dieSize = 8;
                        dieCounter = 1;
                    }else{
                        dieCounter++;
                    }
                    break;
                case R.id.d6:
                    if(dieSize != 6){
                        dieSize = 6;
                        dieCounter = 1;
                    }else{
                        dieCounter++;
                    }
                    break;
                case R.id.d4:
                    if(dieSize != 4){
                        dieSize = 4;
                        dieCounter = 1;
                    }else{
                        dieCounter++;
                    }
                    break;
                case R.id.d3:
                    if(dieSize != 3){
                        dieSize = 3;
                        dieCounter = 1;
                    }else{
                        dieCounter++;
                    }
                    break;
                case R.id.d2:
                    if(dieSize != 2){
                        dieSize = 2;
                        dieCounter = 1;
                    }else{
                        dieCounter++;
                    }
                    break;
            }
            doDisplay((dieCounter + "d" + dieSize), Color.BLACK);
        }}


    @Override
    protected void onResume() {
        super.onResume();
        mySensorManager.registerListener(this, acc, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mySensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        String str =  userInput.getText().toString();
        String toShow;

        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float accVal0 = sensorEvent.values[0];
            float accVal1 = sensorEvent.values[1];
            float accVal2 = sensorEvent.values[2];

            if(accVal0 > accThreshhold || accVal0 < -accThreshhold ||
                    accVal1 > accThreshhold || accVal1 < -accThreshhold ||
                    accVal2 > accThreshhold || accVal2 < -accThreshhold){
                Toast.makeText(this, accVal0 + "," + accVal1 + "," + accVal2, Toast.LENGTH_LONG).show();

                result = doRandom(dieSize, dieCounter);
                toShow = Integer.toString(result);
                doDisplay(toShow,Color.RED);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //Not needed
    }

    public int doRandom(int size, int count){
        Random r = new Random();
        int total = 0;

        for (int i = 0; i < count; i++){
            int rand = r.nextInt(size - 1) + 1;
            total += rand;
        }
        return total;
    }

    public void doDisplay(String s, int c){
        userInput.setText(s);
        userInput.setTextColor(c);
    }
}

