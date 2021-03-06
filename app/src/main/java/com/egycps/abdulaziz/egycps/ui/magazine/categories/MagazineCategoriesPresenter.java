package com.egycps.abdulaziz.egycps.ui.magazine.categories;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.egycps.abdulaziz.egycps.data.DataManager;
import com.egycps.abdulaziz.egycps.data.model.Category;
import com.egycps.abdulaziz.egycps.ui.base.BasePresenter;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;
import com.egycps.abdulaziz.egycps.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by abdulaziz on 1/23/17.
 */

public class MagazineCategoriesPresenter extends BasePresenter<MagazineCategoriesBaseView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;
    private final Context mContext;

    public MagazineCategoriesPresenter(Context context, DataManager mDataManager) {
        this.mDataManager = mDataManager;
        mContext = context;
    }

    @Override
    public void attachView(MagazineCategoriesBaseView baseView) {
        super.attachView(baseView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if(mSubscription!=null) mSubscription.unsubscribe();
    }

    public void saveMagazineCategories(ArrayList<Category> categories){
        Log.i(GlobalEntities.MAGAZINE_CATEGORIES_PRESENTER_TAG, "setMagazineCategories");
        checkViewAttached();
//        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.setMagazineCategories(categories)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Category>() {
                    @Override
                    public void onCompleted() {
                        Log.i(GlobalEntities.MAGAZINE_CATEGORIES_PRESENTER_TAG, "setMagazineCategories: completed");
                        getBaseView().saveMagazineCategoriesCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(GlobalEntities.MAGAZINE_CATEGORIES_PRESENTER_TAG, "setMagazineCategories: error :: " + e.getMessage());

                    }

                    @Override
                    public void onNext(Category category) {
                        Log.i(GlobalEntities.MAGAZINE_CATEGORIES_PRESENTER_TAG, "setMagazineCategories: item " + category.getTitle());

                    }
                });

    }

    public void syncMagazineCategories(){
        Log.i(GlobalEntities.MAGAZINE_CATEGORIES_PRESENTER_TAG, "syncMagazineCategories");
        checkViewAttached();
//        RxUtil.unsubscribe(mSubscription);
        //TODO: not using the concept of chaining well needs more thinking
        mSubscription = mDataManager.syncMagazineCategories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Func1<ArrayList<Category>, ArrayList<Category>>() {
                    @Override
                    public ArrayList<Category> call(ArrayList<Category> categories) {
                        for(Category category : categories){
                            final String path = category.getImage();
                            String[] names = path.split("/");
                            final String image_name = "/"+names[names.length-1];
                            if(Utils.isImageExistInStorage(mContext, image_name)){
                                Log.i(GlobalEntities.MAGAZINE_CATEGORIES_PRESENTER_TAG, "syncMagazineCategories: image: "+image_name+" :: exist");
                            }else{
                                Log.i(GlobalEntities.MAGAZINE_CATEGORIES_PRESENTER_TAG, "syncMagazineCategories: image: "+image_name+" :: does not exist");
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
                .subscribe(new Subscriber<ArrayList<Category>>() {
                    @Override
                    public final void onCompleted() {
                        // do nothing
                        Log.i(GlobalEntities.MAGAZINE_CATEGORIES_PRESENTER_TAG, "syncMagazineCategories: completed");
                        getBaseView().syncMagazineCategoriesCompleted();
                    }

                    @Override
                    public final void onError(Throwable e) {
                        Log.e(GlobalEntities.MAGAZINE_CATEGORIES_PRESENTER_TAG, "syncMagazineCategories: error :: "+e.getMessage());
                        getBaseView().syncMagazineCategoriesError(e);
                    }

                    @Override
                    public void onNext(ArrayList<Category> MagazineCategories) {
                        Log.i(GlobalEntities.MAGAZINE_CATEGORIES_PRESENTER_TAG, "syncMagazineCategories: onNext");
                        getBaseView().syncMagazineCategories(MagazineCategories);
                    }
                });
    }

    public void loadMagazineCategories(){
        Log.i(GlobalEntities.MAGAZINE_CATEGORIES_PRESENTER_TAG, "loadMagazineCategories");
        checkViewAttached();
//        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getMagazineCategories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Category>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(GlobalEntities.MAGAZINE_CATEGORIES_PRESENTER_TAG, "loadMagazineCategories: Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(GlobalEntities.MAGAZINE_CATEGORIES_PRESENTER_TAG, "loadMagazineCategories: onError: "+e.getMessage());
                        getBaseView().showMagazineCategoriesError(e);
                    }

                    @Override
                    public void onNext(List<Category> MagazineCategories) {
                        Log.i(GlobalEntities.MAGAZINE_CATEGORIES_PRESENTER_TAG, "loadMagazineCategories: onNext: "+MagazineCategories.size());
                        if(MagazineCategories.isEmpty()){
                            getBaseView().showMagazineCategoriesEmpty();
                        }else{
                            getBaseView().showMagazineCategories(MagazineCategories);
                        }
                    }
                });

    }
}

