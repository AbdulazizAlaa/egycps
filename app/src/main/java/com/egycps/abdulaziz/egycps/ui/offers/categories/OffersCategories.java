package com.egycps.abdulaziz.egycps.ui.offers.categories;

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
import com.egycps.abdulaziz.egycps.data.model.OffersCategory;
import com.egycps.abdulaziz.egycps.ui.offers.list.OffersList;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class OffersCategories extends AppCompatActivity implements OffersCategoriesBaseView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{

    Toolbar toolbar;
    TextView activityTitle;
    LinearLayout homeBtn;
    View mainView;

    RecyclerView categoriesRecyclerView;
    RecyclerView.LayoutManager categoriesLayoutManager;
    CategoriesAdapter categoriesAdapter;

    ArrayList<OffersCategory> categoriesList;

    OffersCategoriesPresenter mOffersCategoriesPresenter;

    SwipeRefreshLayout offersCategoriesRefreshL;

    public static Intent getStartIntent(Context context){
        Intent i = new Intent(context, OffersCategories.class);

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_categories);

        Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "onCreate: created");

        init();
    }

    private void init(){
        //initializing the toolbar
        mainView = findViewById(R.id.offers_categories_main_view);
        toolbar = (Toolbar) findViewById(R.id.offers_categories_toolbar);
        homeBtn = (LinearLayout) toolbar.findViewById(R.id.offers_categories_home_btn);
        activityTitle = (TextView) findViewById(R.id.offers_categories_title_tv);
        offersCategoriesRefreshL = (SwipeRefreshLayout) findViewById(R.id.offers_categories_refresh_l);

        offersCategoriesRefreshL.setOnRefreshListener(this);

        activityTitle.setText("Offers Categories");

        homeBtn.setOnClickListener(this);

        //initializing the categories recycler view
        categoriesRecyclerView = (RecyclerView) findViewById(R.id.offers_categories_cat_recycler_view);

        categoriesList = new ArrayList<OffersCategory>();
        categoriesLayoutManager = new GridLayoutManager(this, 2);
        categoriesRecyclerView.setLayoutManager(categoriesLayoutManager);

        final Context context = this;
        categoriesAdapter = new CategoriesAdapter(this, categoriesList);
        categoriesRecyclerView.setAdapter(categoriesAdapter);
        categoriesAdapter.getPositionClicks()
                        .subscribe(new Action1<OffersCategory>() {
                            @Override
                            public void call(OffersCategory cat) {
                                Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "category id:: "+cat.getId());
                                Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "category title:: "+cat.getTitle());
                                Intent i = OffersList.getStartIntent(context);
                                i.putExtra(GlobalEntities.OFFER_CATEGORY_ID_TAG, cat.getId());
                                i.putExtra(GlobalEntities.OFFER_CATEGORY_TITLE_TAG, cat.getTitle());
                                startActivity(i);
                            }
                        });

        //presenter
        mOffersCategoriesPresenter = new OffersCategoriesPresenter(this, DataManager.getInstance(this, null, null, null));
        mOffersCategoriesPresenter.attachView(this);

        offersCategoriesRefreshL.post(new Runnable() {
            @Override
            public void run() {
                offersCategoriesRefreshL.setRefreshing(true);
            }
        });

        mOffersCategoriesPresenter.loadOffersCategories();
        mOffersCategoriesPresenter.syncOffersCategories();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.offers_categories_home_btn:
                Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "onClick: home button");
                onBackPressed();
                break;
        }
    }

    @Override
    public void saveOffersCategoriesCompleted() {
        Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "saveCompleted");

        mOffersCategoriesPresenter.loadOffersCategories();
    }

    @Override
    public void syncOffersCategoriesCompleted() {
        Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "syncCompleted");

    }

    @Override
    public void syncOffersCategories(ArrayList<OffersCategory> categories) {
        Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "syncOffersCategories");

        mOffersCategoriesPresenter.saveOffersCategories(categories);
    }

    @Override
    public void syncOffersCategoriesError(Throwable e) {
        Log.e(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "syncError :: " + e.getMessage());
        mOffersCategoriesPresenter.loadOffersCategories();
//        Snackbar.make(mainView, "Oops.. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showOffersCategories(List<OffersCategory> categories) {
        Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "showOffersCategories"+categories.size());
        Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "showOffersCategories"+categoriesList.size());

        categoriesList.clear();
        categoriesList.addAll(categories);
        categoriesAdapter.notifyDataSetChanged();

        offersCategoriesRefreshL.setRefreshing(false);
    }

    @Override
    public void showOffersCategoriesEmpty() {
        Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "showOffersCategoriesEmpty");
        //refresh loader
        offersCategoriesRefreshL.setRefreshing(false);

        Snackbar.make(mainView, "Oops.. No Categories Could be Displayed!!", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showOffersCategoriesError(Throwable e) {
        Log.e(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "showError :: " + e.getMessage());
        //refresh loader
        offersCategoriesRefreshL.setRefreshing(false);
        Snackbar.make(mainView, "Oops.. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "on refresh");
        //refresh loader
        offersCategoriesRefreshL.setRefreshing(true);

        categoriesList.clear();
        categoriesAdapter.notifyDataSetChanged();
        mOffersCategoriesPresenter.syncOffersCategories();
    }
}
