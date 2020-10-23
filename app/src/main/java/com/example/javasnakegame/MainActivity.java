package com.example.javasnakegame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private MySurfaceView mySurfaceView;
    public static int screenHeight = 0;
    public static int screenWidth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Main activity created");
        setGameMode();
        initObjects();
        setContentView(mySurfaceView);
    }

    private void setGameMode() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
    }

    private void initObjects() {
        getScreenSizes();
        mySurfaceView = new MySurfaceView(this);
    }

    private void getScreenSizes() {
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        screenWidth = point.x;
        screenHeight = point.y;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: called");
        mySurfaceView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: called");
        mySurfaceView.resume();
    }
}

