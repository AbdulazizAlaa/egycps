package com.egycps.abdulaziz.egycps.ui.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.egycps.abdulaziz.egycps.R;
import com.egycps.abdulaziz.egycps.data.model.OffersCategory;
import com.egycps.abdulaziz.egycps.ui.home.Home;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class Splash extends AppCompatActivity implements SplashBaseView{

    @Inject SplashPresenter mSplashPresenter;

    private final int SPLASH_TIME_OUT = 1500;

    View mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log.i(GlobalEntities.SPLASH_ACTIVITY_TAG, "onCreate: Splash");

        mainView = findViewById(R.id.splash_layout);

        Timer splashTimer = new Timer();
        splashTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.i(GlobalEntities.SPLASH_ACTIVITY_TAG, "Splash timer");
                jump();
            }
        }, SPLASH_TIME_OUT);

//        Service.Creator.getService().getOfferCategories()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .flatMap(new Func1<ArrayList<OfferCategory>, Observable<OfferCategory>>() {
//                    @Override
//                    public Observable<OfferCategory> call(ArrayList<OfferCategory> offerCategories) {
//                        return Observable.from(offerCategories);
//                    }
//                })
//                .subscribe(new Subscriber<OfferCategory>() {
//                    @Override
//                    public final void onCompleted() {
//                        // do nothing
//                        Log.i(GlobalEntities.SPLASH_ACTIVITY_TAG, "completed");
//
//                    }
//
//                    @Override
//                    public final void onError(Throwable e) {
//                        Log.e(GlobalEntities.SPLASH_ACTIVITY_TAG, e.getMessage());
//                    }
//
//                    @Override
//                    public final void onNext(OfferCategory response) {
//                        Log.i(GlobalEntities.SPLASH_ACTIVITY_TAG, "---------------------------------------");
//                        Log.i(GlobalEntities.SPLASH_ACTIVITY_TAG, "id :: "+response.getId());
//                        Log.i(GlobalEntities.SPLASH_ACTIVITY_TAG, "title :: "+response.getTitle());
//                        Log.i(GlobalEntities.SPLASH_ACTIVITY_TAG, "desc :: "+response.getDescription());
//                        Log.i(GlobalEntities.SPLASH_ACTIVITY_TAG, "image :: "+response.getImage());
//                    }
//                });

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

    @Override
    public void saveCategories(List<OffersCategory> categories) {

    }

    @Override
    public void showError() {

    }
}
