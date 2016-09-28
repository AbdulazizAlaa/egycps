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
import com.egycps.abdulaziz.egycps.data.model.CategoriesAdapter;
import com.egycps.abdulaziz.egycps.data.model.OffersCategory;
import com.egycps.abdulaziz.egycps.ui.home.Home;
import com.egycps.abdulaziz.egycps.ui.offers.list.OffersList;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

import java.util.ArrayList;

import rx.functions.Action1;

public class OffersCategories extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    TextView activityTitle;
    LinearLayout homeBtn;

    RecyclerView categoriesRecyclerView;
    RecyclerView.LayoutManager categoriesLayoutManager;
    CategoriesAdapter categoriesAdapter;

    ArrayList<OffersCategory> categoriesList;


    public static Intent getStartIntent(Context context){
        Intent i = new Intent(context, OffersCategories.class);

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_categories);

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
        categoriesList.add(new OffersCategory("1", "Hotels", "image", "hiiiiiiiiiiiiiiii"));
        categoriesList.add(new OffersCategory("2", "Gym", "image", "hiiiiiiiiiiiiiiii"));
        categoriesList.add(new OffersCategory("3", "Hospitals", "image", "hiiiiiiiiiiiiiiii"));
        categoriesList.add(new OffersCategory("4", "Cars", "image", "hiiiiiiiiiiiiiiii"));
        categoriesList.add(new OffersCategory("5", "Hotels", "image", "hiiiiiiiiiiiiiiii"));
        categoriesList.add(new OffersCategory("6", "Hotels", "image", "hiiiiiiiiiiiiiiii"));

        categoriesLayoutManager = new GridLayoutManager(this, 2);
        categoriesRecyclerView.setLayoutManager(categoriesLayoutManager);

        final Context context = this;
        categoriesAdapter = new CategoriesAdapter(categoriesList);
        categoriesRecyclerView.setAdapter(categoriesAdapter);
        categoriesAdapter.getPositionClicks()
                        .subscribe(new Action1<String>() {
                            @Override
                            public void call(String id) {
                                Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "category id:: "+id);
                                Intent i = OffersList.getStartIntent(context);
                                i.putExtra(GlobalEntities.OFFER_CATEGORY_ID_TAG, id);
                                startActivity(i);
                            }
                        });


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
}
