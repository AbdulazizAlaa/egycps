package com.egycps.abdulaziz.egycps.ui.library.categories;

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
 * Created by abdulaziz on 10/2/16.
 */
public class LibraryCategoriesPresenter extends BasePresenter<LibraryCategoriesBaseView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;
    private final Context mContext;

    public LibraryCategoriesPresenter(Context context, DataManager mDataManager) {
        this.mDataManager = mDataManager;
        mContext = context;
    }

    @Override
    public void attachView(LibraryCategoriesBaseView baseView) {
        super.attachView(baseView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if(mSubscription!=null) mSubscription.unsubscribe();
    }

    public void saveLibraryCategories(ArrayList<Category> categories){
        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_PRESENTER_TAG, "setLibraryCategories");
        checkViewAttached();
//        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.setLibraryCategories(categories)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Category>() {
                    @Override
                    public void onCompleted() {
                        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_PRESENTER_TAG, "setLibraryCategories: completed");
                        getBaseView().saveLibraryCategoriesCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(GlobalEntities.LIBRARIES_CATEGORIES_PRESENTER_TAG, "setLibraryCategories: error :: " + e.getMessage());

                    }

                    @Override
                    public void onNext(Category category) {
                        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_PRESENTER_TAG, "setLibraryCategories: item " + category.getTitle());

                    }
                });

    }

    public void syncLibraryCategories(){
        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_PRESENTER_TAG, "syncLibraryCategories");
        checkViewAttached();
//        RxUtil.unsubscribe(mSubscription);
        //TODO: not using the concept of chaining well needs more thinking
        mSubscription = mDataManager.syncLibraryCategories()
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
                                Log.i(GlobalEntities.LIBRARIES_CATEGORIES_PRESENTER_TAG, "syncLibraryCategories: image: "+image_name+" :: exist");
                            }else{
                                Log.i(GlobalEntities.LIBRARIES_CATEGORIES_PRESENTER_TAG, "syncLibraryCategories: image: "+image_name+" :: does not exist");
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
                        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_PRESENTER_TAG, "syncLibraryCategories: completed");
                        getBaseView().syncLibraryCategoriesCompleted();
                    }

                    @Override
                    public final void onError(Throwable e) {
                        Log.e(GlobalEntities.LIBRARIES_CATEGORIES_PRESENTER_TAG, "syncLibraryCategories: error :: "+e.getMessage());
                        getBaseView().syncLibraryCategoriesError(e);
                    }

                    @Override
                    public void onNext(ArrayList<Category> libraryCategories) {
                        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_PRESENTER_TAG, "syncLibraryCategories: onNext");
//                        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_PRESENTER_TAG, "---------------------------------------");
//                        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_PRESENTER_TAG, "id :: "+response.getId());
//                        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_PRESENTER_TAG, "title :: "+response.getTitle());
//                        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_PRESENTER_TAG, "desc :: "+response.getDescription());
//                        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_PRESENTER_TAG, "image :: "+response.getImage());
                        getBaseView().syncLibraryCategories(libraryCategories);
                    }
                });
    }

    public void loadLibraryCategories(){
        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_PRESENTER_TAG, "loadLibraryCategories");
        checkViewAttached();
//        RxUtil.unsubscribe(mSubscription);
        mSubscription = mDataManager.getLibraryCategories()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Category>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_PRESENTER_TAG, "loadLibraryCategories: Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(GlobalEntities.LIBRARIES_CATEGORIES_PRESENTER_TAG, "loadLibraryCategories: onError: "+e.getMessage());
                        getBaseView().showLibraryCategoriesError(e);
                    }

                    @Override
                    public void onNext(List<Category> libraryCategories) {
                        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_PRESENTER_TAG, "loadLibraryCategories: onNext: "+libraryCategories.size());
                        if(libraryCategories.isEmpty()){
                            getBaseView().showLibraryCategoriesEmpty();
                        }else{
                            getBaseView().showLibraryCategories(libraryCategories);
                        }
                    }
                });

    }
}
