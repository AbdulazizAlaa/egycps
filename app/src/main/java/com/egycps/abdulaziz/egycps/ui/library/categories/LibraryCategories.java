package com.egycps.abdulaziz.egycps.ui.library.categories;

import android.content.Context;
import android.content.Intent;
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
import com.egycps.abdulaziz.egycps.data.model.CategoriesAdapter;
import com.egycps.abdulaziz.egycps.data.model.OffersCategory;
import com.egycps.abdulaziz.egycps.ui.library.list.LibraryList;
import com.egycps.abdulaziz.egycps.ui.offers.categories.OffersCategoriesPresenter;
import com.egycps.abdulaziz.egycps.ui.offers.list.OffersList;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

import java.util.ArrayList;

import rx.functions.Action1;

public class LibraryCategories extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    TextView activityTitle;
    LinearLayout homeBtn;

    RecyclerView categoriesRecyclerView;
    RecyclerView.LayoutManager categoriesLayoutManager;
    CategoriesAdapter categoriesAdapter;

    ArrayList<OffersCategory> categoriesList;

    public static Intent getStartIntent(Context context){
        Intent i = new Intent(context, LibraryCategories.class);

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_categories);

        init();
    }

    private void init(){
        //initializing the toolbar
        toolbar = (Toolbar) findViewById(R.id.library_categories_toolbar);
        homeBtn = (LinearLayout) toolbar.findViewById(R.id.library_categories_home_btn);
        activityTitle = (TextView) findViewById(R.id.library_categories_title_tv);

        activityTitle.setText("Library Categories");

        homeBtn.setOnClickListener(this);

        //initializing the categories recycler view
        categoriesRecyclerView = (RecyclerView) findViewById(R.id.library_categories_cat_recycler_view);

        categoriesList = new ArrayList<OffersCategory>();
        categoriesList.add(new OffersCategory("1", "Law", "ssss", "ssssss"));
        categoriesList.add(new OffersCategory("2", "health", "ssss", "ssssss"));
        categoriesList.add(new OffersCategory("3", "bla", "ssss", "ssssss"));

        categoriesLayoutManager = new GridLayoutManager(this, 2);
        categoriesRecyclerView.setLayoutManager(categoriesLayoutManager);

        final Context context = this;
        categoriesAdapter = new CategoriesAdapter(this, categoriesList);
        categoriesRecyclerView.setAdapter(categoriesAdapter);
        categoriesAdapter.getPositionClicks()
                .subscribe(new Action1<OffersCategory>() {
                    @Override
                    public void call(OffersCategory cat) {
                        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_ACTIVITY_TAG, "category id:: "+cat.getId());
                        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_ACTIVITY_TAG, "category title:: "+cat.getTitle());
                        Intent i = LibraryList.getStartIntent(context);
                        i.putExtra(GlobalEntities.LIBRARY_CATEGORY_ID_TAG, cat.getId());
                        i.putExtra(GlobalEntities.LIBRARY_CATEGORY_TITLE_TAG, cat.getTitle());
                        startActivity(i);
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.library_categories_home_btn:
                Log.i(GlobalEntities.LIBRARIES_CATEGORIES_ACTIVITY_TAG, "onClick: home button");
                onBackPressed();
                break;
        }
    }
}
