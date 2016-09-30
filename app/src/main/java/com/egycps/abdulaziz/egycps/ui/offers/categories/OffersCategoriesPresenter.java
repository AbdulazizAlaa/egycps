package com.egycps.abdulaziz.egycps.ui.offers.categories;

import android.util.Log;

import com.egycps.abdulaziz.egycps.data.DataManager;
import com.egycps.abdulaziz.egycps.data.model.OffersCategory;
import com.egycps.abdulaziz.egycps.ui.base.BasePresenter;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;
import com.egycps.abdulaziz.egycps.utils.RxUtil;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by abdulaziz on 9/30/16.
 */
public class OffersCategoriesPresenter extends BasePresenter<OffersCategoriesBaseView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public OffersCategoriesPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(OffersCategoriesBaseView baseView) {
        super.attachView(baseView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if(mSubscription!=null) mSubscription.unsubscribe();
    }

    public void loadOffersCategories(){
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getOffersCategories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<OffersCategory>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "onCompleted: Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "onError: "+e.getMessage());
                        getBaseView().showError();
                    }

                    @Override
                    public void onNext(List<OffersCategory> offersCategories) {
                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "onNext: "+offersCategories.size());
                        if(offersCategories.isEmpty()){
                            getBaseView().showOffersCategoriesEmpty();
                        }else{
                            getBaseView().showOffersCategories(offersCategories);
                        }
                    }
                });

    }

}
