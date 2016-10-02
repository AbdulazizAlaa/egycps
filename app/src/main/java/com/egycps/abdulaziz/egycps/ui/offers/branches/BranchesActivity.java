package com.egycps.abdulaziz.egycps.ui.offers.branches;

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
import com.egycps.abdulaziz.egycps.data.model.Branch;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class BranchesActivity extends AppCompatActivity implements BranchesBaseView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    String id;

    Toolbar toolbar;
    TextView activityTitle;
    LinearLayout homeBtn;
    View mainView;

    RecyclerView branchesRecyclerView;
    RecyclerView.LayoutManager branchesLayoutManager;
    BranchesAdapter branchesAdapter;

    ArrayList<Branch> branchesList;

    BranchesPresenter mBranchesPresenter;

    SwipeRefreshLayout branchesListRefreshL;

    public static Intent getStartIntent(Context context){
        Intent i = new Intent(context, BranchesActivity.class);

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branches);

        if(getIntent() != null){
            id = getIntent().getStringExtra(GlobalEntities.OFFER_OFFER_ID_TAG);
        }else {
            id = "1";
        }

        init();
    }

    private void init(){
        //initializing the toolbar
        mainView = findViewById(R.id.branches_main_view);
        toolbar = (Toolbar) findViewById(R.id.branches_toolbar);
        homeBtn = (LinearLayout) toolbar.findViewById(R.id.branches_home_btn);
        activityTitle = (TextView) findViewById(R.id.branches_title_tv);
        branchesListRefreshL = (SwipeRefreshLayout) findViewById(R.id.branches_refresh_l);

        branchesListRefreshL.setOnRefreshListener(this);

        activityTitle.setText("Branches");

        homeBtn.setOnClickListener(this);

        //initializing the categories recycler view
        branchesRecyclerView = (RecyclerView) findViewById(R.id.branches_recycler_view);

        branchesList = new ArrayList<Branch>();

        branchesLayoutManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        branchesRecyclerView.setLayoutManager(branchesLayoutManager);

        final Context context = this;
        branchesAdapter = new BranchesAdapter(this, branchesList);
        branchesRecyclerView.setAdapter(branchesAdapter);
        branchesAdapter.getPositionClicks()
                .subscribe(new Action1<Branch>() {
                    @Override
                    public void call(Branch branch) {
                        Log.i(GlobalEntities.BRANCHES_ACTIVITY_TAG, "branch id:: "+branch.getId());
                        Log.i(GlobalEntities.BRANCHES_ACTIVITY_TAG, "branch title:: "+branch.getName());

                        try{
                            double latitude = Double.parseDouble(branch.getLatitude());
                            double longitude = Double.parseDouble(branch.getLongitude());
                            String label = branch.getName();
                            String uriBegin = "geo:" + latitude + "," + longitude;
                            String query = latitude + "," + longitude + "(" + label + ")";
                            String encodedQuery = Uri.encode(query);
                            String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                            Uri uri = Uri.parse(uriString);
                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                            Snackbar.make(mainView, "Oops.. Your Maps Does not look to be working!!", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });


        //Presenter
        mBranchesPresenter = new BranchesPresenter(this, DataManager.getInstance(null, null, null, null));
        mBranchesPresenter.attachView(this);

        branchesListRefreshL.post(new Runnable() {
            @Override
            public void run() {
                branchesListRefreshL.setRefreshing(true);
            }
        });

        Log.i(GlobalEntities.BRANCHES_ACTIVITY_TAG, "offer id:: "+id);
        mBranchesPresenter.loadBranches(id);
        mBranchesPresenter.syncBranches(id);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.branches_home_btn:
                Log.i(GlobalEntities.BRANCHES_ACTIVITY_TAG, "onClick: home button");
                onBackPressed();
                break;
        }
    }

    @Override
    public void saveBranchesListCompleted() {
        Log.i(GlobalEntities.BRANCHES_ACTIVITY_TAG, "saveBranchesList: Completed");
        mBranchesPresenter.loadBranches(id);
    }

    @Override
    public void saveBranchesListError(Throwable e){
        Log.e(GlobalEntities.BRANCHES_ACTIVITY_TAG, "saveBranchesList: Error: "+e.getMessage());
        branchesListRefreshL.setRefreshing(false);

//        Snackbar.make(mainView, "Oops.. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void syncBranchesList(List<Branch> branches) {
        Log.i(GlobalEntities.BRANCHES_ACTIVITY_TAG, "syncBranchesList: ");
        mBranchesPresenter.saveBranches((ArrayList<Branch>) branches);
    }

    @Override
    public void syncBranchesListError(Throwable e) {
        Log.e(GlobalEntities.BRANCHES_ACTIVITY_TAG, "syncBranchesList: Error:: "+e.getMessage());
        mBranchesPresenter.loadBranches(id);
//        Snackbar.make(mainView, "Oops.. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void syncBranchesListCompleted() {
        Log.i(GlobalEntities.BRANCHES_ACTIVITY_TAG, "syncBranchesList: Completed");

    }

    @Override
    public void showBranchesList(List<Branch> branches) {
        Log.i(GlobalEntities.BRANCHES_ACTIVITY_TAG, "showBranchesList: ");

        branchesList.clear();
        branchesList.addAll(branches);
        branchesAdapter.notifyDataSetChanged();

        branchesListRefreshL.setRefreshing(false);
    }

    @Override
    public void showBranchesListError(Throwable e) {
        Log.e(GlobalEntities.BRANCHES_ACTIVITY_TAG, "showBranchesList: Error:: "+e.getMessage());
        branchesListRefreshL.setRefreshing(false);
        Snackbar.make(mainView, "Oops.. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showBranchesListEmpty() {
        Log.i(GlobalEntities.BRANCHES_ACTIVITY_TAG, "showBranchesList: Empty");
        branchesListRefreshL.setRefreshing(false);

        Snackbar.make(mainView, "Oops.. No Branches Could be Displayed!!", Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onRefresh() {
        Log.i(GlobalEntities.BRANCHES_ACTIVITY_TAG, "BranchesList: refresh: "+id);

        branchesListRefreshL.setRefreshing(true);

        branchesList.clear();
        branchesAdapter.notifyDataSetChanged();
        mBranchesPresenter.syncBranches(id);
    }
}
