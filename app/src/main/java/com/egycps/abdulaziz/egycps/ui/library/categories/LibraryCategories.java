package com.egycps.abdulaziz.egycps.ui.library.categories;

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
import com.egycps.abdulaziz.egycps.ui.offers.categories.CategoriesAdapter;
import com.egycps.abdulaziz.egycps.ui.library.list.LibraryList;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class LibraryCategories extends AppCompatActivity implements View.OnClickListener, LibraryCategoriesBaseView, SwipeRefreshLayout.OnRefreshListener{

    Toolbar toolbar;
    TextView activityTitle;
    LinearLayout homeBtn;
    View mainView;

    RecyclerView categoriesRecyclerView;
    RecyclerView.LayoutManager categoriesLayoutManager;
    CategoriesAdapter categoriesAdapter;

    ArrayList<Category> categoriesList;

    LibraryCategoriesPresenter mLibraryCategoriesPresenter;

    SwipeRefreshLayout libraryCategoriesRefreshL;

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
        mainView = findViewById(R.id.library_categories_main_view);
        toolbar = (Toolbar) findViewById(R.id.library_categories_toolbar);
        homeBtn = (LinearLayout) toolbar.findViewById(R.id.library_categories_home_btn);
        activityTitle = (TextView) findViewById(R.id.library_categories_title_tv);
        libraryCategoriesRefreshL = (SwipeRefreshLayout) findViewById(R.id.library_categories_refresh_l);

        libraryCategoriesRefreshL.setOnRefreshListener(this);

        activityTitle.setText("Library Categories");

        homeBtn.setOnClickListener(this);

        //initializing the categories recycler view
        categoriesRecyclerView = (RecyclerView) findViewById(R.id.library_categories_cat_recycler_view);

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
                        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_ACTIVITY_TAG, "category id:: "+cat.getId());
                        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_ACTIVITY_TAG, "category title:: "+cat.getTitle());
                        Intent i = LibraryList.getStartIntent(context);
                        i.putExtra(GlobalEntities.LIBRARY_CATEGORY_ID_TAG, cat.getId());
                        i.putExtra(GlobalEntities.LIBRARY_CATEGORY_TITLE_TAG, cat.getTitle());
                        startActivity(i);
                    }
                });


        //presenter
        mLibraryCategoriesPresenter = new LibraryCategoriesPresenter(this, DataManager.getInstance(this, null, null, null));
        mLibraryCategoriesPresenter.attachView(this);

        libraryCategoriesRefreshL.post(new Runnable() {
            @Override
            public void run() {
                libraryCategoriesRefreshL.setRefreshing(true);
            }
        });

        mLibraryCategoriesPresenter.loadLibraryCategories();
        mLibraryCategoriesPresenter.syncLibraryCategories();

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

    @Override
    public void saveLibraryCategoriesCompleted() {
        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_ACTIVITY_TAG, "saveCompleted");

        mLibraryCategoriesPresenter.loadLibraryCategories();
    }

    @Override
    public void syncLibraryCategoriesCompleted() {
        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_ACTIVITY_TAG, "syncCompleted");

    }

    @Override
    public void syncLibraryCategories(ArrayList<Category> categories) {
        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_ACTIVITY_TAG, "syncLibraryCategories");

        mLibraryCategoriesPresenter.saveLibraryCategories(categories);
    }

    @Override
    public void syncLibraryCategoriesError(Throwable e) {
        Log.e(GlobalEntities.LIBRARIES_CATEGORIES_ACTIVITY_TAG, "syncError :: " + e.getMessage());
        mLibraryCategoriesPresenter.loadLibraryCategories();
//        Snackbar.make(mainView, "Oops.. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showLibraryCategories(List<Category> categories) {
        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_ACTIVITY_TAG, "showLibraryCategories"+categories.size());
        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_ACTIVITY_TAG, "showLibraryCategories"+categoriesList.size());

        categoriesList.clear();
        categoriesList.addAll(categories);
        categoriesAdapter.notifyDataSetChanged();

        libraryCategoriesRefreshL.setRefreshing(false);
    }

    @Override
    public void showLibraryCategoriesEmpty() {
        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_ACTIVITY_TAG, "showLibraryCategoriesEmpty");
        //refresh loader
        libraryCategoriesRefreshL.setRefreshing(false);

        Snackbar.make(mainView, "Oops.. No Categories Could be Displayed!!", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showLibraryCategoriesError(Throwable e) {
        Log.e(GlobalEntities.LIBRARIES_CATEGORIES_ACTIVITY_TAG, "showError :: " + e.getMessage());
        //refresh loader
        libraryCategoriesRefreshL.setRefreshing(false);
        Snackbar.make(mainView, "Oops.. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        Log.i(GlobalEntities.LIBRARIES_CATEGORIES_ACTIVITY_TAG, "on refresh");
        //refresh loader
        libraryCategoriesRefreshL.setRefreshing(true);

        categoriesList.clear();
        categoriesAdapter.notifyDataSetChanged();
        mLibraryCategoriesPresenter.syncLibraryCategories();
    }

}
