package com.egycps.abdulaziz.egycps.ui.library.list;

import android.content.Context;
import android.util.Log;

import com.egycps.abdulaziz.egycps.data.DataManager;
import com.egycps.abdulaziz.egycps.data.model.Book;
import com.egycps.abdulaziz.egycps.ui.base.BasePresenter;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by abdulaziz on 10/2/16.
 */
public class LibraryListPresenter extends BasePresenter<LibraryListBaseView>{

    private final DataManager mDataManager;
    private Subscription mSubscription;
    private final Context mContext;

    public LibraryListPresenter(Context mContext, DataManager mDataManager) {
        this.mContext = mContext;
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(LibraryListBaseView baseView) {
        super.attachView(baseView);
    }

    @Override
    public void detachView() {
        super.detachView();

        if(mSubscription!=null) mSubscription.unsubscribe();
    }

    public void syncBooks(String cat_id){
        Log.i(GlobalEntities.LIBRARIES_LIST_PRESENTER_TAG, "syncBooks: ");
        checkViewAttached();
        mSubscription = mDataManager.syncBooks(cat_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ArrayList<Book>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(GlobalEntities.LIBRARIES_LIST_PRESENTER_TAG, "syncBooks: Completed");
                        getBaseView().syncBooksCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(GlobalEntities.LIBRARIES_LIST_PRESENTER_TAG, "syncBooks: Error: "+e.getMessage());
                        getBaseView().syncBooksError(e);
                    }

                    @Override
                    public void onNext(ArrayList<Book> books) {
                        Log.i(GlobalEntities.LIBRARIES_LIST_PRESENTER_TAG, "syncBooks: onNext");
                        getBaseView().syncBooks(books);
                    }
                });

    }


    public void saveBooks(ArrayList<Book> books){
        Log.i(GlobalEntities.LIBRARIES_LIST_PRESENTER_TAG, "saveBooks: ");
        checkViewAttached();
        mSubscription = mDataManager.setBooks(books)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Book>() {
                    @Override
                    public void onCompleted() {
                        Log.i(GlobalEntities.LIBRARIES_LIST_PRESENTER_TAG, "saveBooks: Completed");
                        getBaseView().saveBooksCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(GlobalEntities.LIBRARIES_LIST_PRESENTER_TAG, "saveBooks: Error: "+e.getMessage());
                        getBaseView().saveBooksError(e);
                    }

                    @Override
                    public void onNext(Book book) {
                        Log.i(GlobalEntities.LIBRARIES_LIST_PRESENTER_TAG, "saveBooks: onNext: "+book.getTitle());

                    }
                });
    }


    public void loadBooks(String cat_id){
        Log.i(GlobalEntities.LIBRARIES_LIST_PRESENTER_TAG, "loadBooks: ");
        checkViewAttached();
        mSubscription = mDataManager.getBooks(cat_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Book>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(GlobalEntities.LIBRARIES_LIST_PRESENTER_TAG, "loadBooks: ");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(GlobalEntities.LIBRARIES_LIST_PRESENTER_TAG, "loadBooks: Error: "+e.getMessage());
                        getBaseView().showBooksError(e);
                    }

                    @Override
                    public void onNext(List<Book> books) {
                        Log.i(GlobalEntities.LIBRARIES_LIST_PRESENTER_TAG, "loadBooks: onNext");
                        if(books.isEmpty()){
                            getBaseView().showBooksEmpty();
                        }else{
                            getBaseView().showBooks(books);
                        }
                    }
                });
    }


}
