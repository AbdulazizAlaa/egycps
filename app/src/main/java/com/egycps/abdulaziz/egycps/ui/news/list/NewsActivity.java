package com.egycps.abdulaziz.egycps.ui.news.list;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egycps.abdulaziz.egycps.R;
import com.egycps.abdulaziz.egycps.data.DataManager;
import com.egycps.abdulaziz.egycps.data.model.News;
import com.egycps.abdulaziz.egycps.ui.news.item.NewsItemActivity;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class NewsActivity extends AppCompatActivity implements View.OnClickListener, NewsBaseView, SwipeRefreshLayout.OnRefreshListener{

    Toolbar toolbar;
    TextView activityTitle;
    LinearLayout homeBtn;
    View mainView;

    RecyclerView newsRecyclerView;
    RecyclerView.LayoutManager newsLayoutManager;
    NewsAdapter newsAdapter;

    ArrayList<News> newsList;

    NewsPresenter mNewsListPresenter;

    SwipeRefreshLayout newsListRefreshL;

    public static Intent getStartIntent(Context context){
        Intent i = new Intent(context, NewsActivity.class);

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_activity);

        init();

    }

    private void init(){
        //initializing the toolbar
        mainView = findViewById(R.id.news_main_view);
        toolbar = (Toolbar) findViewById(R.id.news_toolbar);
        homeBtn = (LinearLayout) toolbar.findViewById(R.id.news_home_btn);
        activityTitle = (TextView) findViewById(R.id.news_title_tv);
        newsListRefreshL = (SwipeRefreshLayout) findViewById(R.id.news_refresh_l);

        newsListRefreshL.setOnRefreshListener(this);

        activityTitle.setText("News");

        homeBtn.setOnClickListener(this);

        //initializing the categories recycler view
        newsRecyclerView = (RecyclerView) findViewById(R.id.news_recycler_view);

        newsList = new ArrayList<News>();

        newsLayoutManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        newsRecyclerView.setLayoutManager(newsLayoutManager);

        final Context context = this;
        newsAdapter = new NewsAdapter(this, newsList);
        newsRecyclerView.setAdapter(newsAdapter);
        newsAdapter.getPositionClicks()
                .subscribe(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        Log.i(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "offer id:: "+news.getId());
                        Log.i(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "offer title:: "+news.getTitle());
                        Log.i(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "offer DESC:: "+news.getDescription());

                        Intent i = NewsItemActivity.getStartIntent(context);
                        i.putExtra(GlobalEntities.NEWS_TITLE_TAG, news.getTitle());
                        i.putExtra(GlobalEntities.NEWS_CONTENT_TAG, news.getDescription());
                        i.putExtra(GlobalEntities.NEWS_IMAGE_TAG, news.getImage());
                        startActivity(i);

                    }
                });


        //Presenter
        mNewsListPresenter = new NewsPresenter(this, DataManager.getInstance(null, null, null, null));
        mNewsListPresenter.attachView(this);

        newsListRefreshL.post(new Runnable() {
            @Override
            public void run() {
                newsListRefreshL.setRefreshing(true);
            }
        });

        mNewsListPresenter.loadNews();
        mNewsListPresenter.syncNews();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.news_home_btn:
                Log.i(GlobalEntities.NEWS_ACTIVITY_TAG, "onClick: home button");
                onBackPressed();
                break;
        }
    }

    @Override
    public void saveNewsCompleted() {
        Log.i(GlobalEntities.NEWS_ACTIVITY_TAG, "saveNews: Completed");

        mNewsListPresenter.loadNews();
    }

    @Override
    public void saveNewsError(Throwable e) {
        Log.e(GlobalEntities.NEWS_ACTIVITY_TAG, "saveNews: Error: "+e.getMessage());

        newsListRefreshL.setRefreshing(false);
//        Snackbar.make(mainView, "Oops.. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void syncNews(List<News> news) {
        Log.i(GlobalEntities.NEWS_ACTIVITY_TAG, "syncNews: ");

        mNewsListPresenter.saveNews((ArrayList<News>) news);
    }

    @Override
    public void syncNewsError(Throwable e) {
        Log.e(GlobalEntities.NEWS_ACTIVITY_TAG, "syncNews: Error: "+e.getMessage());

        mNewsListPresenter.loadNews();
//        Snackbar.make(mainView, "Oops.. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void syncNewsCompleted() {
        Log.i(GlobalEntities.NEWS_ACTIVITY_TAG, "syncNews: Completed");

    }

    @Override
    public void showNews(List<News> news) {
        Log.i(GlobalEntities.NEWS_ACTIVITY_TAG, "showNews: ");

        newsList.clear();
        newsList.addAll(news);
        newsAdapter.notifyDataSetChanged();

        newsListRefreshL.setRefreshing(false);
    }

    @Override
    public void showNewsError(Throwable e) {
        Log.e(GlobalEntities.NEWS_ACTIVITY_TAG, "showNews: Error: "+e.getMessage());

        newsListRefreshL.setRefreshing(false);

        Snackbar.make(mainView, "Oops.. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showNewsEmpty() {
        Log.i(GlobalEntities.NEWS_ACTIVITY_TAG, "showNews: empty");

        newsListRefreshL.setRefreshing(false);

        Snackbar.make(mainView, "Oops.. No News Could be Displayed!!", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        Log.i(GlobalEntities.NEWS_ACTIVITY_TAG, "News: refresh");

        newsListRefreshL.setRefreshing(true);

        newsList.clear();
        newsAdapter.notifyDataSetChanged();
        mNewsListPresenter.syncNews();
    }
}
