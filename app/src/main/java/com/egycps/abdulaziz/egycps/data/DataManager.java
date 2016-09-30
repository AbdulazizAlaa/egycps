package com.egycps.abdulaziz.egycps.data;

import android.util.Log;

import com.egycps.abdulaziz.egycps.data.local.DatabaseHelper;
import com.egycps.abdulaziz.egycps.data.local.PreferencesHelper;
import com.egycps.abdulaziz.egycps.data.model.OffersCategory;
import com.egycps.abdulaziz.egycps.data.remote.Service;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by abdulaziz on 9/27/16.
 */

public class DataManager {

    private static DataManager dataManager;

    private final Service mService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;

    private DataManager(Service mService, DatabaseHelper mDatabaseHelper, PreferencesHelper mPreferencesHelper) {
        Log.i(GlobalEntities.DATA_MANAGER_TAG, "DataManager: Created");
        this.mService = mService;
        this.mDatabaseHelper = mDatabaseHelper;
        this.mPreferencesHelper = mPreferencesHelper;
    }

    public static DataManager getInstance(Service mService, DatabaseHelper mDatabaseHelper, PreferencesHelper mPreferencesHelper){
        if(dataManager == null){
            dataManager = new DataManager(mService, mDatabaseHelper, mPreferencesHelper);
        }

        return dataManager;
    }

    public static boolean isNull(){
        return dataManager==null;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<OffersCategory> syncOffersCategories(){
        Log.i(GlobalEntities.DATA_MANAGER_TAG, "DataManager: syncOffersCategories");
        return mService.getOfferCategories()
                .flatMap(new Func1<ArrayList<OffersCategory>, Observable<OffersCategory>>() {
                    @Override
                    public Observable<OffersCategory> call(ArrayList<OffersCategory> offerCategories) {
                        return Observable.from(offerCategories);
                    }
                });
    }

    public Observable<List<OffersCategory>> getOffersCategories(){
        Log.i(GlobalEntities.DATA_MANAGER_TAG, "DataManager: getOffersCategories");
        return mDatabaseHelper.getOffersCategories();
    }

    public Observable<OffersCategory> setOffersCategories(List<OffersCategory> offersCategories){
        Log.i(GlobalEntities.DATA_MANAGER_TAG, "DataManager: setOfferCategories");
        return mDatabaseHelper.setOffersCategories(offersCategories);
    }

}
