package com.example.hserban.lecture8_samplecode;

import static com.example.hserban.lecture8_samplecode.MainActivity.DEFAULT;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
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
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import static com.example.hserban.lecture8_samplecode.MainActivity.DEFAULT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;

public class SqlActivity extends AppCompatActivity implements View.OnTouchListener {
    EditText note;
    MyDatabase2 dndNotes;
    ImageView click_image_id, xyPos_id;
    RelativeLayout rl;

    String color = DEFAULT;
    private Button colorChangeAdd, colorChangeView, goToCameraView;

    String xPosStr = DEFAULT;
    String yPosStr = DEFAULT;

    private int REQUEST_CODE_PERMISSIONS = 101;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA"};
    String path = DEFAULT;

    public final int xFix = 32;
    public final int yFix = 144;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);

        rl = (RelativeLayout) findViewById(R.id.layout);
        note = (EditText)findViewById(R.id.noteEditText);
        dndNotes = new MyDatabase2(this);

        colorChangeAdd = (Button)findViewById(R.id.addNoteButton);
        colorChangeView = (Button)findViewById(R.id.viewAllButton);
        goToCameraView = (Button)findViewById(R.id.goToCamera);

        SharedPreferences sharedPrefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        color = sharedPrefs.getString("selectedColor", DEFAULT);
        colorChangeAdd.setBackgroundColor((Color.parseColor(color)));
        colorChangeView.setBackgroundColor((Color.parseColor(color)));
        goToCameraView.setBackgroundColor((Color.parseColor(color)));

        click_image_id = findViewById(R.id.click_image);
        xyPos_id = findViewById(R.id.xyPos);
        xyPos_id.setVisibility(View.INVISIBLE);

        if(path != DEFAULT){
            loadImg();
        }
    }

    public void addPin (View view){

        if(xPosStr == DEFAULT || yPosStr == DEFAULT){
            xPosStr = "0";
            yPosStr = "0";
        }

        String noteStr = note.getText().toString();

        Toast.makeText(this, "imgRefStr: "  + path , Toast.LENGTH_SHORT).show();

        long id = dndNotes.insertData(xPosStr, yPosStr, noteStr, path);
        if (id < 0){
            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "success, id: " + id, Toast.LENGTH_SHORT).show();
        }
        note.setText("");
    }

    public void viewResults(View view){
        Intent intent = new Intent(this, RecyclerActivity.class);
        startActivity(intent);
    }

    public void delete(View view){
        int count = dndNotes.deleteRow();
        Toast.makeText(this, ""+ count, Toast.LENGTH_SHORT).show();
    }

    public void getPic(View view) { //Take new picture
        if(allPermissionsGranted()){
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            path = getExternalCacheDir() + File.separator + System.currentTimeMillis() + ".png";
            Uri uriImage = FileProvider.getUriForFile(this, "com.example.hserban.lecture8_samplecode.provider", new File(path));
            camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, uriImage);

            resultlauncher.launch(camera_intent);
        } else{
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }
    }

    public void loadImg(){
        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
        Bitmap photo = BitmapFactory.decodeFile(path);
        click_image_id.setImageBitmap(photo);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Log.d("tester","In SqlActivity - onTouch");
        //Do nothing
        return false;
    }

    public int getToolBarHeight(){
        int[] atrrs = new int[] {androidx.appcompat.R.attr.actionBarSize};
        TypedArray ta = this.obtainStyledAttributes(atrrs);
        int toolBarHeight = ta.getDimensionPixelSize(0,-1);
        ta.recycle();
        Log.d("tester","toolBarHeight: " + toolBarHeight);
        return  toolBarHeight;
    }

    @Override
    public boolean onTouchEvent (MotionEvent event){
        float x = event.getX() - xFix;
        float y = event.getY() - getToolBarHeight() - yFix;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d("tester","Location tapped: " + x + "," + y);
            xPosStr = String.valueOf(x);
            yPosStr = String.valueOf(y);
            int imgTop = click_image_id.getTop();
            int imgBottom = click_image_id.getBottom();
            int imgRight = click_image_id.getRight();
            int imgLeft = click_image_id.getLeft();

            if(x >= imgLeft && x <= imgRight){
                if(y >= imgTop && y <= imgBottom){
                    xyPos_id.setX(x);
                    xyPos_id.setY(y);
                    xyPos_id.setVisibility(View.VISIBLE);

                    xPosStr = String.valueOf(x);
                    yPosStr = String.valueOf(y);
                }
            }
            return true;
        }else{
            return false;
        }
    }

    //~~~Camera Stuff~~~
    ActivityResultLauncher<Intent> resultlauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d("tester", "In SqlActivity (Camera Stuff) - onActivityResult");
                    Bitmap photo = BitmapFactory.decodeFile(path);
                    click_image_id.setImageBitmap(photo);
                }
            });

    private boolean allPermissionsGranted(){
        for(String permission : REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(allPermissionsGranted()){
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            resultlauncher.launch(camera_intent);
        } else{
            Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}

