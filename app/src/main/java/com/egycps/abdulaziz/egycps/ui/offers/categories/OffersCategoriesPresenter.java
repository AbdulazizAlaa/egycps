package com.egycps.abdulaziz.egycps.ui.offers.categories;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.egycps.abdulaziz.egycps.data.DataManager;
import com.egycps.abdulaziz.egycps.data.model.OffersCategory;
import com.egycps.abdulaziz.egycps.ui.base.BasePresenter;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;
import com.egycps.abdulaziz.egycps.utils.RxUtil;
import com.egycps.abdulaziz.egycps.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
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
    private final Context mContext;

    @Inject
    public OffersCategoriesPresenter(Context context, DataManager mDataManager) {
        this.mDataManager = mDataManager;
        mContext = context;
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

    public void syncImage(String path){
        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "syncImage");
//        checkViewAttached();
//        RxUtil.unsubscribe(mSubscription);
//        mSubscription =
//                mDataManager.syncImage(path)
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeOn(Schedulers.newThread())
//                        .subscribe(new Subscriber<Bitmap>() {
//                            @Override
//                            public void onCompleted() {
//                                Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "syncImage: completed");
//                                getBaseView().syncImageCompleted();
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "syncImage: error :: " + e.getMessage());
//                                getBaseView().syncImageError(e);
//                            }
//
//                            @Override
//                            public void onNext(Bitmap bitmap) {
//                                Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "syncImage: item ");
//                                getBaseView().syncImage(bitmap);
//                            }
//                        });
    }

    public void saveOffersCategories(ArrayList<OffersCategory> categories){
        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "setOffersCategories");
//        ArrayList<OffersCategory> list = new ArrayList<OffersCategory>();
//        list.add(category);
        checkViewAttached();
        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.setOffersCategories(categories)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<OffersCategory>() {
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
//                .filter(new Func1<OffersCategory, Boolean>() {
//                    @Override
//                    public Boolean call(OffersCategory category) {
//                        String image_name = category.getImage();
//                        Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "syncOffersCategories: image:: "+image_name);
//                        if(Utils.isImageExistInStorage(mContext, image_name)){
//                            Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "syncOffersCategories: image:: exist");
//                            return true;
//                        }else{
//                            Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "syncOffersCategories: image:: does not exist");
//                            return false;
//                        }
//                    }
//                })
                .map(new Func1<ArrayList<OffersCategory>, ArrayList<OffersCategory>>() {
                    @Override
                    public ArrayList<OffersCategory> call(ArrayList<OffersCategory> categories) {
                        for(OffersCategory category : categories){
                            final String path = category.getImage();
                            String[] names = path.split("/");
                            final String image_name = "/"+names[names.length-1];
                            Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "syncOffersCategories: image:: "+image_name);
                            if(Utils.isImageExistInStorage(mContext, image_name)){
                                Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "syncOffersCategories: image:: exist");
                            }else{
                                Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "syncOffersCategories: image:: does not exist");
                                Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Bitmap image;
                                        try {
                                            image = Picasso.with(mContext).load(GlobalEntities.ENDPOINT+path).get();
                                            Utils.saveToInternalStorage(mContext, image, image_name);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                thread.start();

                            }

                        }
                        return categories;
                    }
                })
                .subscribe(new Subscriber<ArrayList<OffersCategory>>() {
                    @Override
                    public final void onCompleted() {
                        // do nothing
                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "syncOffersCategories: completed");
                        getBaseView().syncOffersCategoriesCompleted();
                    }

                    @Override
                    public final void onError(Throwable e) {
                        Log.e(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "syncOffersCategories: error :: "+e.getMessage());
                        getBaseView().syncOffersCategoriesError(e);
                    }

                    @Override
                    public void onNext(ArrayList<OffersCategory> offersCategories) {
                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "syncOffersCategories: onNext");
//                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "---------------------------------------");
//                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "id :: "+response.getId());
//                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "title :: "+response.getTitle());
//                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "desc :: "+response.getDescription());
//                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "image :: "+response.getImage());
                        getBaseView().syncOffersCategories(offersCategories);
                    }

//                    @Override
//                    public final void onNext(OffersCategory response) {
//                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "syncOffersCategories: onNext");
//                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "---------------------------------------");
//                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "id :: "+response.getId());
//                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "title :: "+response.getTitle());
//                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "desc :: "+response.getDescription());
//                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "image :: "+response.getImage());
//                        getBaseView().syncOffersCategories(response);
//                    }
                });
//                .subscribe(new Subscriber<OffersCategory>() {
//                    @Override
//                    public final void onCompleted() {
//                        // do nothing
//                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "syncOffersCategories: completed");
//                        getBaseView().syncOffersCategoriesCompleted();
//                    }
//
//                    @Override
//                    public final void onError(Throwable e) {
//                        Log.e(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "syncOffersCategories: error :: "+e.getMessage());
//                        getBaseView().syncOffersCategoriesError(e);
//                    }
//
//                    @Override
//                    public final void onNext(OffersCategory response) {
//                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "syncOffersCategories: onNext");
//                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "---------------------------------------");
//                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "id :: "+response.getId());
//                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "title :: "+response.getTitle());
//                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "desc :: "+response.getDescription());
//                        Log.i(GlobalEntities.OFFERS_CATEGORIES_PRESENTER_TAG, "image :: "+response.getImage());
//                        getBaseView().syncOffersCategories(response);
//                    }
//                });
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
                        getBaseView().showOffersCategoriesError(e);
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
