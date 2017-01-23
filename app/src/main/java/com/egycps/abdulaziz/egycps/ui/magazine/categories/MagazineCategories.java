package com.egycps.abdulaziz.egycps.ui.magazine.categories;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egycps.abdulaziz.egycps.R;
import com.egycps.abdulaziz.egycps.data.DataManager;
import com.egycps.abdulaziz.egycps.data.model.Category;
import com.egycps.abdulaziz.egycps.ui.magazine.list.MagazineActivity;
import com.egycps.abdulaziz.egycps.ui.offers.categories.CategoriesAdapter;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class MagazineCategories extends AppCompatActivity implements View.OnClickListener, MagazineCategoriesBaseView, SwipeRefreshLayout.OnRefreshListener{

    Toolbar toolbar;
    TextView activityTitle;
    LinearLayout homeBtn;
    View mainView;

    RecyclerView categoriesRecyclerView;
    RecyclerView.LayoutManager categoriesLayoutManager;
    CategoriesAdapter categoriesAdapter;

    ArrayList<Category> categoriesList;

    MagazineCategoriesPresenter mMagazineCategoriesPresenter;

    SwipeRefreshLayout MagazineCategoriesRefreshL;

    public static Intent getStartIntent(Context context){
        Intent i = new Intent(context, MagazineCategories.class);

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine_categories);

        init();
    }

    private void init(){
        //initializing the toolbar
        mainView = findViewById(R.id.magazine_categories_main_view);
        toolbar = (Toolbar) findViewById(R.id.magazine_categories_toolbar);
        homeBtn = (LinearLayout) toolbar.findViewById(R.id.magazine_categories_home_btn);
        activityTitle = (TextView) findViewById(R.id.magazine_categories_title_tv);
        MagazineCategoriesRefreshL = (SwipeRefreshLayout) findViewById(R.id.magazine_categories_refresh_l);

        MagazineCategoriesRefreshL.setOnRefreshListener(this);

        activityTitle.setText("Magazine Categories");

        homeBtn.setOnClickListener(this);

        //initializing the categories recycler view
        categoriesRecyclerView = (RecyclerView) findViewById(R.id.magazine_categories_cat_recycler_view);

        categoriesList = new ArrayList<Category>();

        categoriesLayoutManager = new GridLayoutManager(this, 2);
        categoriesRecyclerView.setLayoutManager(categoriesLayoutManager);

        final Context context = this;
        categoriesAdapter = new CategoriesAdapter(this, categoriesList);
        categoriesRecyclerView.setAdapter(categoriesAdapter);
        categoriesAdapter.getPositionClicks()
                .subscribe(new Action1<Category>() {
                    @Override
                    public void call(Category cat) {
                        Log.i(GlobalEntities.MAGAZINE_CATEGORIES_ACTIVITY_TAG, "category id:: "+cat.getId());
                        Log.i(GlobalEntities.MAGAZINE_CATEGORIES_ACTIVITY_TAG, "category title:: "+cat.getTitle());
                        Intent i = MagazineActivity.getStartIntent(context);
                        i.putExtra(GlobalEntities.MAGAZINE_CATEGORY_ID_TAG, cat.getId());
                        i.putExtra(GlobalEntities.MAGAZINE_CATEGORY_TITLE_TAG, cat.getTitle());
                        startActivity(i);
                    }
                });


        //presenter
        mMagazineCategoriesPresenter = new MagazineCategoriesPresenter(this, DataManager.getInstance(this, null, null, null));
        mMagazineCategoriesPresenter.attachView(this);

        MagazineCategoriesRefreshL.post(new Runnable() {
            @Override
            public void run() {
                MagazineCategoriesRefreshL.setRefreshing(true);
            }
        });

        mMagazineCategoriesPresenter.loadMagazineCategories();
        mMagazineCategoriesPresenter.syncMagazineCategories();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.magazine_categories_home_btn:
                Log.i(GlobalEntities.MAGAZINE_CATEGORIES_ACTIVITY_TAG, "onClick: home button");
                onBackPressed();
                break;
        }
    }

    @Override
    public void saveMagazineCategoriesCompleted() {
        Log.i(GlobalEntities.MAGAZINE_CATEGORIES_ACTIVITY_TAG, "saveCompleted");

        mMagazineCategoriesPresenter.loadMagazineCategories();
    }

    @Override
    public void syncMagazineCategoriesCompleted() {
        Log.i(GlobalEntities.MAGAZINE_CATEGORIES_ACTIVITY_TAG, "syncCompleted");

    }

    @Override
    public void syncMagazineCategories(ArrayList<Category> categories) {
        Log.i(GlobalEntities.MAGAZINE_CATEGORIES_ACTIVITY_TAG, "syncMagazineCategories");

        mMagazineCategoriesPresenter.saveMagazineCategories(categories);
    }

    @Override
    public void syncMagazineCategoriesError(Throwable e) {
        Log.e(GlobalEntities.MAGAZINE_CATEGORIES_ACTIVITY_TAG, "syncError :: " + e.getMessage());
        mMagazineCategoriesPresenter.loadMagazineCategories();
//        Snackbar.make(mainView, "Oops.. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMagazineCategories(List<Category> categories) {
        Log.i(GlobalEntities.MAGAZINE_CATEGORIES_ACTIVITY_TAG, "showMagazineCategories"+categories.size());
        Log.i(GlobalEntities.MAGAZINE_CATEGORIES_ACTIVITY_TAG, "showMagazineCategories"+categoriesList.size());

        categoriesList.clear();
        categoriesList.addAll(categories);
        categoriesAdapter.notifyDataSetChanged();

        MagazineCategoriesRefreshL.setRefreshing(false);
    }

    @Override
    public void showMagazineCategoriesEmpty() {
        Log.i(GlobalEntities.MAGAZINE_CATEGORIES_ACTIVITY_TAG, "showMagazineCategoriesEmpty");
        //refresh loader
        MagazineCategoriesRefreshL.setRefreshing(false);

        Snackbar.make(mainView, "Oops.. No Categories Could be Displayed!!", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMagazineCategoriesError(Throwable e) {
        Log.e(GlobalEntities.MAGAZINE_CATEGORIES_ACTIVITY_TAG, "showError :: " + e.getMessage());
        //refresh loader
        MagazineCategoriesRefreshL.setRefreshing(false);
        Snackbar.make(mainView, "Oops.. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        Log.i(GlobalEntities.MAGAZINE_CATEGORIES_ACTIVITY_TAG, "on refresh");
        //refresh loader
        MagazineCategoriesRefreshL.setRefreshing(true);

        categoriesList.clear();
        categoriesAdapter.notifyDataSetChanged();
        mMagazineCategoriesPresenter.syncMagazineCategories();
    }
}
