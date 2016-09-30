package com.egycps.abdulaziz.egycps.ui.offers.categories;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egycps.abdulaziz.egycps.R;
import com.egycps.abdulaziz.egycps.data.DataManager;
import com.egycps.abdulaziz.egycps.data.model.CategoriesAdapter;
import com.egycps.abdulaziz.egycps.data.model.OffersCategory;
import com.egycps.abdulaziz.egycps.ui.home.Home;
import com.egycps.abdulaziz.egycps.ui.offers.list.OffersList;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.functions.Action1;

public class OffersCategories extends AppCompatActivity implements OffersCategoriesBaseView, View.OnClickListener{

    Toolbar toolbar;
    TextView activityTitle;
    LinearLayout homeBtn;

    RecyclerView categoriesRecyclerView;
    RecyclerView.LayoutManager categoriesLayoutManager;
    CategoriesAdapter categoriesAdapter;

    ArrayList<OffersCategory> categoriesList;

    OffersCategoriesPresenter mOffersCategoriesPresenter;

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
        toolbar = (Toolbar) findViewById(R.id.offers_categories_toolbar);
        homeBtn = (LinearLayout) toolbar.findViewById(R.id.offers_categories_home_btn);
        activityTitle = (TextView) findViewById(R.id.offers_categories_title_tv);

        activityTitle.setText("Offers Categories");

        homeBtn.setOnClickListener(this);

        //initializing the categories recycler view
        categoriesRecyclerView = (RecyclerView) findViewById(R.id.offers_categories_cat_recycler_view);

        categoriesList = new ArrayList<OffersCategory>();
//        categoriesList.add(new OffersCategory("1", "Hotels", "image", "hiiiiiiiiiiiiiiii"));
//        categoriesList.add(new OffersCategory("2", "Gym", "image", "hiiiiiiiiiiiiiiii"));
//        categoriesList.add(new OffersCategory("3", "Hospitals", "image", "hiiiiiiiiiiiiiiii"));
//        categoriesList.add(new OffersCategory("4", "Cars", "image", "hiiiiiiiiiiiiiiii"));
//        categoriesList.add(new OffersCategory("5", "Hotels", "image", "hiiiiiiiiiiiiiiii"));
//        categoriesList.add(new OffersCategory("6", "Hotels", "image", "hiiiiiiiiiiiiiiii"));

        categoriesLayoutManager = new GridLayoutManager(this, 2);
        categoriesRecyclerView.setLayoutManager(categoriesLayoutManager);

        final Context context = this;
        categoriesAdapter = new CategoriesAdapter(categoriesList);
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

        //database
//        ArrayList<OffersCategory> list = new ArrayList<OffersCategory>();
//        list.add(new OffersCategory("1", "Hotels", "image", "hiiiiiiiiiiiiiiii"));
//        list.add(new OffersCategory("2", "Gym", "image", "hiiiiiiiiiiiiiiii"));
//        list.add(new OffersCategory("3", "Hospitals", "image", "hiiiiiiiiiiiiiiii"));
//        list.add(new OffersCategory("4", "Cars", "image", "hiiiiiiiiiiiiiiii"));
//        list.add(new OffersCategory("5", "Hotels", "image", "hiiiiiiiiiiiiiiii"));
//        list.add(new OffersCategory("6", "Hotels", "image", "hiiiiiiiiiiiiiiii"));
//        DataManager.getInstance(null, null, null).setOffersCategories(list).subscribe(new Subscriber<OffersCategory>() {
//            @Override
//            public void onCompleted() {
//                Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "setOffersCategories: completed");
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "setOffersCategories: error :: " + e.getMessage());
//
//            }
//
//            @Override
//            public void onNext(OffersCategory category) {
//                Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "setOffersCategories: item " + category.getTitle());
//
//            }
//        });

        //presenter
        mOffersCategoriesPresenter = new OffersCategoriesPresenter(DataManager.getInstance(null, null, null));
        mOffersCategoriesPresenter.attachView(this);
        mOffersCategoriesPresenter.loadOffersCategories();

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
    public void showOffersCategories(List<OffersCategory> categories) {
        Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "showOffersCategories");
        categoriesList.addAll(categories);
        categoriesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showOffersCategoriesEmpty() {
        Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "showOffersCategoriesEmpty");
        //TODO: add an empty view and snack bar indicting the error
    }

    @Override
    public void showError() {
        Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "showError");
        //TODO: add an empty view and snack bar indicting the error
    }
}
