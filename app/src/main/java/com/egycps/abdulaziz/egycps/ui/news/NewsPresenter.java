package com.egycps.abdulaziz.egycps.ui.news;

import android.content.Context;
import android.util.Log;

import com.egycps.abdulaziz.egycps.data.DataManager;
import com.egycps.abdulaziz.egycps.data.model.News;
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
public class NewsPresenter extends BasePresenter<NewsBaseView>{

    private final DataManager mDataManager;
    private Subscription mSubscription;
    private final Context mContext;

    public NewsPresenter(Context mContext, DataManager mDataManager) {
        this.mContext = mContext;
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(NewsBaseView baseView) {
        super.attachView(baseView);
    }

    @Override
    public void detachView() {
        super.detachView();

        if(mSubscription!=null) mSubscription.unsubscribe();
    }

    public void syncNews(){
        Log.i(GlobalEntities.NEWS_PRESENTER_TAG, "syncNews: ");
        checkViewAttached();
        mSubscription = mDataManager.syncNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ArrayList<News>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(GlobalEntities.NEWS_PRESENTER_TAG, "syncNews: Completed");
                        getBaseView().syncNewsCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(GlobalEntities.NEWS_PRESENTER_TAG, "syncNews: Error: "+e.getMessage());
                        getBaseView().syncNewsError(e);
                    }

                    @Override
                    public void onNext(ArrayList<News> news) {
                        Log.i(GlobalEntities.NEWS_PRESENTER_TAG, "syncNews: onNext");
                        getBaseView().syncNews(news);
                    }
                });

    }


    public void saveNews(ArrayList<News> news){
        Log.i(GlobalEntities.NEWS_PRESENTER_TAG, "saveNews: ");
        checkViewAttached();
        mSubscription = mDataManager.setNews(news)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<News>() {
                    @Override
                    public void onCompleted() {
                        Log.i(GlobalEntities.NEWS_PRESENTER_TAG, "saveNews: Completed");
                        getBaseView().saveNewsCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(GlobalEntities.NEWS_PRESENTER_TAG, "saveNews: Error: "+e.getMessage());
                        getBaseView().saveNewsError(e);
                    }

                    @Override
                    public void onNext(News news) {
                        Log.i(GlobalEntities.NEWS_PRESENTER_TAG, "saveNews: onNext: "+news.getTitle());

                    }
                });
    }


    public void loadNews(){
        Log.i(GlobalEntities.NEWS_PRESENTER_TAG, "loadNews: ");
        checkViewAttached();
        mSubscription = mDataManager.getNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<News>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(GlobalEntities.NEWS_PRESENTER_TAG, "loadNews: ");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(GlobalEntities.NEWS_PRESENTER_TAG, "loadNews: Error: "+e.getMessage());
                        getBaseView().showNewsError(e);
                    }

                    @Override
                    public void onNext(List<News> news) {
                        Log.i(GlobalEntities.NEWS_PRESENTER_TAG, "loadNews: onNext");
                        if(news.isEmpty()){
                            getBaseView().showNewsEmpty();
                        }else{
                            getBaseView().showNews(news);
                        }
                    }
                });
    }

}
