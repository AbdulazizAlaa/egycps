package com.egycps.abdulaziz.egycps.ui.magazine.list;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.egycps.abdulaziz.egycps.data.model.Magazine;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class MagazineActivity extends AppCompatActivity implements View.OnClickListener, MagazineBaseView, SwipeRefreshLayout.OnRefreshListener{

    String id;
    String title;

    Toolbar toolbar;
    TextView activityTitle;
    LinearLayout homeBtn;
    View mainView;

    RecyclerView magazinesRecyclerView;
    RecyclerView.LayoutManager magazinesLayoutManager;
    MagazineAdapter magazinesAdapter;

    ArrayList<Magazine> magazinesList;

    MagazinePresenter mMagazinesListPresenter;

    SwipeRefreshLayout magazinesListRefreshL;

    public static Intent getStartIntent(Context context){
        Intent i = new Intent(context, MagazineActivity.class);

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine_activity);

        if(getIntent() != null){
            Log.i(GlobalEntities.MAGAZINE_ACTIVITY_TAG, "Title: "+getIntent().getStringExtra(GlobalEntities.MAGAZINE_CATEGORY_TITLE_TAG));
            title = getIntent().getStringExtra(GlobalEntities.MAGAZINE_CATEGORY_TITLE_TAG);
            id = getIntent().getStringExtra(GlobalEntities.MAGAZINE_CATEGORY_ID_TAG);
        }else{
            title = "Magazine List";
            id = "1";
        }

        init();

    }

    private void init(){
        //initializing the toolbar
        mainView = findViewById(R.id.magazine_main_view);
        toolbar = (Toolbar) findViewById(R.id.magazine_toolbar);
        homeBtn = (LinearLayout) toolbar.findViewById(R.id.magazine_home_btn);
        activityTitle = (TextView) findViewById(R.id.magazine_title_tv);
        magazinesListRefreshL = (SwipeRefreshLayout) findViewById(R.id.magazine_refresh_l);

        magazinesListRefreshL.setOnRefreshListener(this);

        activityTitle.setText("Magazine");

        homeBtn.setOnClickListener(this);

        //initializing the categories recycler view
        magazinesRecyclerView = (RecyclerView) findViewById(R.id.magazine_recycler_view);

        magazinesList = new ArrayList<Magazine>();

        magazinesLayoutManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        magazinesRecyclerView.setLayoutManager(magazinesLayoutManager);

        final Context context = this;
        magazinesAdapter = new MagazineAdapter(this, magazinesList);
        magazinesRecyclerView.setAdapter(magazinesAdapter);
        magazinesAdapter.getPositionClicks()
                .subscribe(new Action1<Magazine>() {
                    @Override
                    public void call(Magazine magazine) {
                        Log.i(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "magazine id:: "+magazine.getId());
                        Log.i(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "magazine title:: "+magazine.getTitle());

                        String url = GlobalEntities.ENDPOINT+magazine.getPdf();
                        url = url.replace(" ", "%20");
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);

                    }
                });


        //Presenter
        mMagazinesListPresenter = new MagazinePresenter(this, DataManager.getInstance(null, null, null, null));
        mMagazinesListPresenter.attachView(this);

        magazinesListRefreshL.post(new Runnable() {
            @Override
            public void run() {
                magazinesListRefreshL.setRefreshing(true);
            }
        });

        mMagazinesListPresenter.loadMagazines(id);
        mMagazinesListPresenter.syncMagazines(id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.magazine_home_btn:
                Log.i(GlobalEntities.MAGAZINE_ACTIVITY_TAG, "onClick: home button");
                onBackPressed();
                break;
        }
    }

    @Override
    public void saveMagazinesCompleted() {
        Log.i(GlobalEntities.MAGAZINE_ACTIVITY_TAG, "saveMagazines: Completed");
        mMagazinesListPresenter.loadMagazines(id);
    }

    @Override
    public void saveMagazinesError(Throwable e) {
        Log.e(GlobalEntities.MAGAZINE_ACTIVITY_TAG, "saveMagazines: Error: "+e.getMessage());
        magazinesListRefreshL.setRefreshing(false);
//        Snackbar.make(mainView, "Oops.. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void syncMagazines(List<Magazine> magazines) {
        Log.i(GlobalEntities.MAGAZINE_ACTIVITY_TAG, "syncMagazines: ");
        mMagazinesListPresenter.saveMagazines((ArrayList<Magazine>) magazines);
    }

    @Override
    public void syncMagazinesError(Throwable e) {
        Log.e(GlobalEntities.MAGAZINE_ACTIVITY_TAG, "syncMagazines: Error: "+e.getMessage());
        mMagazinesListPresenter.loadMagazines(id);
//        Snackbar.make(mainView, "Oops.. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void syncMagazinesCompleted() {
        Log.i(GlobalEntities.MAGAZINE_ACTIVITY_TAG, "syncMagazines: Completed");

    }

    @Override
    public void showMagazines(List<Magazine> magazines) {
        Log.i(GlobalEntities.MAGAZINE_ACTIVITY_TAG, "showMagazines: ");

        magazinesList.clear();
        magazinesList.addAll(magazines);
        magazinesAdapter.notifyDataSetChanged();

        magazinesListRefreshL.setRefreshing(false);
    }

    @Override
    public void showMagazinesError(Throwable e) {
        Log.e(GlobalEntities.MAGAZINE_ACTIVITY_TAG, "showMagazines: Error: "+e.getMessage());

        magazinesListRefreshL.setRefreshing(false);
        Snackbar.make(mainView, "Oops.. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showMagazinesEmpty() {
        Log.i(GlobalEntities.MAGAZINE_ACTIVITY_TAG, "showMagazines: Empty");

        magazinesListRefreshL.setRefreshing(false);

        Snackbar.make(mainView, "Oops.. No Magazines Could be Displayed!!", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        Log.i(GlobalEntities.MAGAZINE_ACTIVITY_TAG, "Magazine: Refresh");

        magazinesListRefreshL.setRefreshing(true);

        magazinesList.clear();
        magazinesAdapter.notifyDataSetChanged();
        mMagazinesListPresenter.syncMagazines(id);
    }
}
