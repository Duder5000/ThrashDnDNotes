package com.example.hserban.lecture8_samplecode;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    public static final String DEFAULT = "not available";

    private Button colorChangeSql, colorChangeDice;

    private RadioGroup colorButtons;
    String color = DEFAULT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorChangeSql = (Button)findViewById(R.id.sqlButton);
        colorChangeDice = (Button)findViewById(R.id.diceButton);

        colorButtons = (RadioGroup) findViewById(R.id.radioGroupColors);
        colorButtons.setOnCheckedChangeListener(this);

        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        color = sharedPrefs.getString("selectedColor", DEFAULT);
        changeColors(color);

    }

    boolean checkIfColorSelected(){
        if(color.equals(DEFAULT)){
            Toast.makeText(this, "No color data found", Toast.LENGTH_SHORT).show();
            return  false;
        }else{
            return true;
        }
    }

    void changeColors(String c){
        if(c == DEFAULT){
            c = "#ffffff";
            color = "#ffffff";
        }
        colorChangeSql.setBackgroundColor((Color.parseColor(c)));
        colorChangeDice.setBackgroundColor((Color.parseColor(c)));
    }

    public void sqlPage(View view) {
        leavingMain(view);
        Intent intent = new Intent (this, SqlActivity.class);
        startActivity(intent);
    }

    public void dicePage(View view) {
        leavingMain(view);
        Intent intent = new Intent (this, DiceRoller.class);
        startActivity(intent);
    }

    public void saveData(View view) {
        Log.d("tester","In saveData");
        Toast.makeText(this, "Colour: " +  color, Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("selectedColor", color);
        editor.commit();

        changeColors(color);
        checkIfColorSelected();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioRed:
                color = "#ff0000";
                break;
            case R.id.radioBlue:
                color = "#0000ff";
                break;
            case R.id.radioGreen:
                color = "#00ff00";
                break;
            case R.id.radioBrown:
                color = "#a52a2a";
                break;
            case R.id.radioYellow:
                color = "#ffff00";
                break;
        }
    }

    public void leavingMain (View v){ //Fix for crashing on my phone
        if(!checkIfColorSelected()){
            color = "#E0E0E0";
            saveData(v);
        }
    }

}
