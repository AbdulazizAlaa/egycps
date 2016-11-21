package com.egycps.abdulaziz.egycps.ui.offers.list;

import android.content.Context;
import android.util.Log;

import com.egycps.abdulaziz.egycps.data.DataManager;
import com.egycps.abdulaziz.egycps.data.model.Offer;
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
public class OffersListPresenter extends BasePresenter<OffersListBaseView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;
    private final Context mContext;

    public OffersListPresenter(Context mContext, DataManager mDataManager) {
        this.mContext = mContext;
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(OffersListBaseView baseView) {
        super.attachView(baseView);
    }

    @Override
    public void detachView() {
        super.detachView();

        if(mSubscription!=null) mSubscription.unsubscribe();
    }


    public void syncOffers(String cat_id){
        Log.i(GlobalEntities.OFFERS_LIST_PRESENTER_TAG, "syncOffers: "+cat_id);
        checkViewAttached();
        mSubscription = mDataManager.syncOffers(cat_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ArrayList<Offer>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(GlobalEntities.OFFERS_LIST_PRESENTER_TAG, "syncOffers: Completed");
                        getBaseView().syncOffersListCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(GlobalEntities.OFFERS_LIST_PRESENTER_TAG, "syncOffers: Error: "+e.getMessage());
                        getBaseView().syncOffersListError(e);
                    }

                    @Override
                    public void onNext(ArrayList<Offer> offers) {
                        Log.i(GlobalEntities.OFFERS_LIST_PRESENTER_TAG, "syncOffers: onNext");
                        getBaseView().syncOffersList(offers);
                    }
                });

    }

    public void saveOffers(ArrayList<Offer> offers){
        Log.i(GlobalEntities.OFFERS_LIST_PRESENTER_TAG, "saveOffers: ");
        checkViewAttached();
        mSubscription = mDataManager.setOffers(offers)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Offer>() {
                    @Override
                    public void onCompleted() {
                        Log.i(GlobalEntities.OFFERS_LIST_PRESENTER_TAG, "saveOffers: Completed");
                        getBaseView().saveOffersListCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(GlobalEntities.OFFERS_LIST_PRESENTER_TAG, "saveOffers: Error: "+e.getMessage());
                        getBaseView().saveOffersListError(e);
                    }

                    @Override
                    public void onNext(Offer offer) {
                        Log.i(GlobalEntities.OFFERS_LIST_PRESENTER_TAG, "saveOffers: onNext: "+offer.getName());

                    }
                });
    }

    public void loadOffers(String cat_id){
        Log.i(GlobalEntities.OFFERS_LIST_PRESENTER_TAG, "loadOffers: ");
        checkViewAttached();
        mSubscription = mDataManager.getOffers(cat_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Offer>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(GlobalEntities.OFFERS_LIST_PRESENTER_TAG, "loadOffers: ");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(GlobalEntities.OFFERS_LIST_PRESENTER_TAG, "loadOffers: Error: "+e.getMessage());
                        getBaseView().showOffersListError(e);
                    }

                    @Override
                    public void onNext(List<Offer> offers) {
                        Log.i(GlobalEntities.OFFERS_LIST_PRESENTER_TAG, "loadOffers: onNext");
                        if(offers.isEmpty()){
                            getBaseView().showOffersListEmpty();
                        }else{
                            getBaseView().showOffersList(offers);
                        }
                    }
                });
    }
}
