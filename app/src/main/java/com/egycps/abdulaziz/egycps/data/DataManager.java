package com.egycps.abdulaziz.egycps.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.egycps.abdulaziz.egycps.data.local.DatabaseHelper;
import com.egycps.abdulaziz.egycps.data.local.PreferencesHelper;
import com.egycps.abdulaziz.egycps.data.model.Magazine;
import com.egycps.abdulaziz.egycps.data.model.News;
import com.egycps.abdulaziz.egycps.data.model.Offer;
import com.egycps.abdulaziz.egycps.data.model.OffersCategory;
import com.egycps.abdulaziz.egycps.data.remote.Service;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by abdulaziz on 9/27/16.
 */

public class DataManager {

    private static DataManager dataManager;

    private final Service mService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final Context mContext;

    private DataManager(Context context, Service mService, DatabaseHelper mDatabaseHelper, PreferencesHelper mPreferencesHelper) {
        Log.i(GlobalEntities.DATA_MANAGER_TAG, "DataManager: Created");
        mContext = context;
        this.mService = mService;
        this.mDatabaseHelper = mDatabaseHelper;
        this.mPreferencesHelper = mPreferencesHelper;
    }

    public static DataManager getInstance(Context context, Service mService, DatabaseHelper mDatabaseHelper, PreferencesHelper mPreferencesHelper){
        if(dataManager == null){
            dataManager = new DataManager(context, mService, mDatabaseHelper, mPreferencesHelper);
        }

        return dataManager;
    }

    public static boolean isNull(){
        return dataManager==null;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<ArrayList<Magazine>> syncMagazines(){
        Log.i(GlobalEntities.DATA_MANAGER_TAG, "DataManager: syncMagazines");
        return mService.getMagazines().asObservable();
    }

    public Observable<List<Magazine>> getMagazines(){
        Log.i(GlobalEntities.DATA_MANAGER_TAG, "DataManager: getMagazines");
        return mDatabaseHelper.getMagazines();
    }

    public Observable<Magazine> setMagazines(List<Magazine> magazines){
        Log.i(GlobalEntities.DATA_MANAGER_TAG, "DataManager: setMagazines");
        return mDatabaseHelper.setMagazines(magazines);
    }

    public Observable<ArrayList<News>> syncNews(){
        Log.i(GlobalEntities.DATA_MANAGER_TAG, "DataManager: syncNews");
        return mService.getNews().asObservable();
    }

    public Observable<List<News>> getNews(){
        Log.i(GlobalEntities.DATA_MANAGER_TAG, "DataManager: getNews");
//        Observable<List<News>> newsObservable = new Observable<List<News>>();
//        if(mDatabaseHelper!=null)
//            newsObservable = mDatabaseHelper.getNews().take(20);
//        return newsObservable;
        return mDatabaseHelper.getNews();
    }

    public Observable<News> setNews(List<News> news){
        Log.i(GlobalEntities.DATA_MANAGER_TAG, "DataManager: setNews");
        return mDatabaseHelper.setNews(news);
    }

    public Observable<ArrayList<Offer>> syncOffers(String cat_id){
        Log.i(GlobalEntities.DATA_MANAGER_TAG, "DataManager: syncOffers");
        return mService.getOffers(cat_id).asObservable();
    }

    public Observable<List<Offer>> getOffers(String cat_id){
        Log.i(GlobalEntities.DATA_MANAGER_TAG, "DataManager: getOffers");
        return mDatabaseHelper.getOffers(cat_id);
    }

    public Observable<Offer> setOffers(List<Offer> offers){
        Log.i(GlobalEntities.DATA_MANAGER_TAG, "DataManager: setOffers");
        return mDatabaseHelper.setOffers(offers);
    }

    public Observable<ArrayList<OffersCategory>> syncOffersCategories(){
        Log.i(GlobalEntities.DATA_MANAGER_TAG, "DataManager: syncOffersCategories");
        return mService.getOfferCategories().asObservable();
    }

    public Observable<List<OffersCategory>> getOffersCategories(){
        Log.i(GlobalEntities.DATA_MANAGER_TAG, "DataManager: getOffersCategories");
        return mDatabaseHelper.getOffersCategories();
    }

    public Observable<OffersCategory> setOffersCategories(List<OffersCategory> offersCategories){
        Log.i(GlobalEntities.DATA_MANAGER_TAG, "DataManager: setOfferCategories");
        return mDatabaseHelper.setOffersCategories(offersCategories);
    }

    public Observable<Bitmap> syncImage(final String path){
        Log.i(GlobalEntities.DATA_MANAGER_TAG, "DataManager: syncImage");

        return Observable.fromCallable(new Callable<Bitmap>() {
            @Override
            public Bitmap call() throws Exception {
                Bitmap image = Picasso.with(mContext).load(GlobalEntities.ENDPOINT+path).get();
                return image;
            }
        });

    }

}
