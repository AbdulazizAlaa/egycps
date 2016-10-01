package com.egycps.abdulaziz.egycps.ui.magazine;

import android.content.Context;
import android.util.Log;

import com.egycps.abdulaziz.egycps.data.DataManager;
import com.egycps.abdulaziz.egycps.data.model.Magazine;
import com.egycps.abdulaziz.egycps.ui.base.BasePresenter;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by abdulaziz on 10/1/16.
 */
public class MagazinePresenter extends BasePresenter<MagazineBaseView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;
    private final Context mContext;

    public MagazinePresenter(Context mContext, DataManager mDataManager) {
        this.mContext = mContext;
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(MagazineBaseView baseView) {
        super.attachView(baseView);
    }

    @Override
    public void detachView() {
        super.detachView();

        if(mSubscription!=null) mSubscription.unsubscribe();
    }

    public void syncMagazines(){
        Log.i(GlobalEntities.MAGAZINE_PRESENTER_TAG, "syncMagazines: ");
        checkViewAttached();
        mSubscription = mDataManager.syncMagazines()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ArrayList<Magazine>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(GlobalEntities.MAGAZINE_PRESENTER_TAG, "syncMagazines: Completed");
                        getBaseView().syncMagazinesCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(GlobalEntities.MAGAZINE_PRESENTER_TAG, "syncMagazines: Error: "+e.getMessage());
                        getBaseView().syncMagazinesError(e);
                    }

                    @Override
                    public void onNext(ArrayList<Magazine> magazines) {
                        Log.i(GlobalEntities.MAGAZINE_PRESENTER_TAG, "syncMagazines: onNext");
                        getBaseView().syncMagazines(magazines);
                    }
                });

    }


    public void saveMagazines(ArrayList<Magazine> magazines){
        Log.i(GlobalEntities.MAGAZINE_PRESENTER_TAG, "saveMagazines: ");
        checkViewAttached();
        mSubscription = mDataManager.setMagazines(magazines)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Magazine>() {
                    @Override
                    public void onCompleted() {
                        Log.i(GlobalEntities.MAGAZINE_PRESENTER_TAG, "saveMagazines: Completed");
                        getBaseView().saveMagazinesCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(GlobalEntities.MAGAZINE_PRESENTER_TAG, "saveMagazines: Error: "+e.getMessage());
                        getBaseView().saveMagazinesError(e);
                    }

                    @Override
                    public void onNext(Magazine magazine) {
                        Log.i(GlobalEntities.MAGAZINE_PRESENTER_TAG, "saveMagazines: onNext: "+magazine.getTitle());

                    }
                });
    }


    public void loadMagazines(){
        Log.i(GlobalEntities.MAGAZINE_PRESENTER_TAG, "loadMagazines: ");
        checkViewAttached();
        mSubscription = mDataManager.getMagazines()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Magazine>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(GlobalEntities.MAGAZINE_PRESENTER_TAG, "loadMagazines: ");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(GlobalEntities.MAGAZINE_PRESENTER_TAG, "loadMagazines: Error: "+e.getMessage());
                        getBaseView().showMagazinesError(e);
                    }

                    @Override
                    public void onNext(List<Magazine> magazines) {
                        Log.i(GlobalEntities.MAGAZINE_PRESENTER_TAG, "loadMagazines: onNext");
                        if(magazines.isEmpty()){
                            getBaseView().showMagazinesEmpty();
                        }else{
                            getBaseView().showMagazines(magazines);
                        }
                    }
                });
    }

}
