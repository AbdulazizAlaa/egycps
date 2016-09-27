package com.egycps.abdulaziz.egycps.ui.splash;

import com.egycps.abdulaziz.egycps.data.DataManager;
import com.egycps.abdulaziz.egycps.ui.base.BasePresenter;

import rx.Subscription;

/**
 * Created by abdulaziz on 9/27/16.
 */
public class SplashPresenter extends BasePresenter<SplashBaseView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    public SplashPresenter(DataManager dataManager){
        mDataManager = dataManager;
    }

    @Override
    public void attachView(SplashBaseView baseView){
        super.attachView(baseView);
    }

    @Override
    public void detachView(){
        super.detachView();
        if(mSubscription!=null) mSubscription.unsubscribe();
    }




}
