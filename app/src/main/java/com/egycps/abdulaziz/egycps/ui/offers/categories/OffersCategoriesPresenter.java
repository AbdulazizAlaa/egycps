package com.egycps.abdulaziz.egycps.ui.offers.categories;

import android.util.Log;

import com.egycps.abdulaziz.egycps.data.DataManager;
import com.egycps.abdulaziz.egycps.data.model.OffersCategory;
import com.egycps.abdulaziz.egycps.ui.base.BasePresenter;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;
import com.egycps.abdulaziz.egycps.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
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

    public void saveOffersCategories(OffersCategory category){
        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "setOffersCategories");
        ArrayList<OffersCategory> list = new ArrayList<OffersCategory>();
        list.add(category);
        DataManager.getInstance(null, null, null).setOffersCategories(list).subscribe(new Subscriber<OffersCategory>() {
            @Override
            public void onCompleted() {
                Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "setOffersCategories: completed");

            }

            @Override
            public void onError(Throwable e) {
                Log.e(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "setOffersCategories: error :: " + e.getMessage());

            }

            @Override
            public void onNext(OffersCategory category) {
                Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "setOffersCategories: item " + category.getTitle());

            }
        });

    }

    public void syncOffersCategories(){
        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "syncOffersCategories");
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.syncOffersCategories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<OffersCategory>() {
                    @Override
                    public final void onCompleted() {
                        // do nothing
                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "syncOffersCategories: completed");
                        getBaseView().syncCompleted();
                    }

                    @Override
                    public final void onError(Throwable e) {
                        Log.e(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "syncOffersCategories: error :: "+e.getMessage());
                        getBaseView().syncError(e);
                    }

                    @Override
                    public final void onNext(OffersCategory response) {
                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "syncOffersCategories: onNext");
//                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "---------------------------------------");
//                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "id :: "+response.getId());
//                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "title :: "+response.getTitle());
//                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "desc :: "+response.getDescription());
//                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "image :: "+response.getImage());
                        getBaseView().syncOffersCategories(response);
                    }
                });
    }

    public void loadOffersCategories(){
        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "loadOffersCategories");
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getOffersCategories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<OffersCategory>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "loadOffersCategories: Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "loadOffersCategories: onError: "+e.getMessage());
                        getBaseView().showError(e);
                    }

                    @Override
                    public void onNext(List<OffersCategory> offersCategories) {
                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "loadOffersCategories: onNext: "+offersCategories.size());
                        if(offersCategories.isEmpty()){
                            getBaseView().showOffersCategoriesEmpty();
                        }else{
                            getBaseView().showOffersCategories(offersCategories);
                        }
                    }
                });

    }

}
