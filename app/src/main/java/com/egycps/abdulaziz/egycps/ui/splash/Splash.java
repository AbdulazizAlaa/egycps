package com.egycps.abdulaziz.egycps.ui.splash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.egycps.abdulaziz.egycps.R;
import com.egycps.abdulaziz.egycps.data.DataManager;
import com.egycps.abdulaziz.egycps.data.local.DatabaseHelper;
import com.egycps.abdulaziz.egycps.data.local.DbOpenHelper;
import com.egycps.abdulaziz.egycps.data.local.PreferencesHelper;
import com.egycps.abdulaziz.egycps.data.remote.Service;
import com.egycps.abdulaziz.egycps.ui.home.Home;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity{

    private final int SPLASH_TIME_OUT = 1500;

    View mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log.i(GlobalEntities.SPLASH_ACTIVITY_TAG, "onCreate: Splash");

        DbOpenHelper dbOpenHelper = DbOpenHelper.getInstance(this.getApplicationContext());
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(dbOpenHelper);
        PreferencesHelper preferencesHelper = new PreferencesHelper();
        Service service = Service.Creator.getService();
        DataManager dataManager = DataManager.getInstance(this, service, databaseHelper, preferencesHelper);

        mainView = findViewById(R.id.splash_layout);

        Timer splashTimer = new Timer();
        splashTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.i(GlobalEntities.SPLASH_ACTIVITY_TAG, "Splash timer");
                jump();
            }
        }, SPLASH_TIME_OUT);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(GlobalEntities.SPLASH_ACTIVITY_TAG, "Touch event");

        jump();

        return super.onTouchEvent(event);
    }

    private void jump(){
        if(isFinishing())
            return;

        startActivity(Home.getStartIntent(this));
        finish();
    }
}
