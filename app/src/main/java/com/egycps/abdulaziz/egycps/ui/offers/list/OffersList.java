package com.egycps.abdulaziz.egycps.ui.offers.list;

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
import com.egycps.abdulaziz.egycps.data.model.Offer;
import com.egycps.abdulaziz.egycps.ui.offers.branches.BranchesActivity;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class OffersList extends AppCompatActivity implements OffersListBaseView, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{

    String id;
    String title;

    Toolbar toolbar;
    TextView activityTitle;
    LinearLayout homeBtn;
    View mainView;

    RecyclerView offersRecyclerView;
    RecyclerView.LayoutManager offersLayoutManager;
    OffersAdapter offersAdapter;

    ArrayList<Offer> offersList;

    OffersListPresenter mOffersListPresenter;

    SwipeRefreshLayout offersListRefreshL;

    public static Intent getStartIntent(Context context){
        Intent i = new Intent(context, OffersList.class);

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_list);
        Log.i(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "onCreate: OffersList");

        if(getIntent() != null){
            Log.i(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "Title: "+getIntent().getStringExtra(GlobalEntities.OFFER_CATEGORY_TITLE_TAG));
            title = getIntent().getStringExtra(GlobalEntities.OFFER_CATEGORY_TITLE_TAG);
            id = getIntent().getStringExtra(GlobalEntities.OFFER_CATEGORY_ID_TAG);
        }else{
            title = "Offers List";
            id = "1";
        }

        init();

    }

    private void init(){
        //initializing the toolbar
        mainView = findViewById(R.id.offers_list_main_view);
        toolbar = (Toolbar) findViewById(R.id.offers_list_toolbar);
        homeBtn = (LinearLayout) toolbar.findViewById(R.id.offers_list_home_btn);
        activityTitle = (TextView) findViewById(R.id.offers_list_title_tv);
        offersListRefreshL = (SwipeRefreshLayout) findViewById(R.id.offers_list_offers_refresh_l);

        offersListRefreshL.setOnRefreshListener(this);

        activityTitle.setText(title);

        homeBtn.setOnClickListener(this);

        //initializing the categories recycler view
        offersRecyclerView = (RecyclerView) findViewById(R.id.offers_list_offers_recycler_view);

        offersList = new ArrayList<Offer>();

        offersLayoutManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        offersRecyclerView.setLayoutManager(offersLayoutManager);

        final Context context = this;
        offersAdapter = new OffersAdapter(this, offersList);
        offersRecyclerView.setAdapter(offersAdapter);
        offersAdapter.getPositionClicks()
                .subscribe(new Action1<Offer>() {
                    @Override
                    public void call(Offer offer) {
                        Log.i(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "offer id:: "+offer.getId());
                        Log.i(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "offer title:: "+offer.getName());

                        Intent i = BranchesActivity.getStartIntent(context);
                        i.putExtra(GlobalEntities.OFFER_OFFER_ID_TAG, offer.getId());
                        startActivity(i);

                    }
                });


        //Presenter
        mOffersListPresenter = new OffersListPresenter(this, DataManager.getInstance(null, null, null, null));
        mOffersListPresenter.attachView(this);

        offersListRefreshL.post(new Runnable() {
            @Override
            public void run() {
                offersListRefreshL.setRefreshing(true);
            }
        });

        mOffersListPresenter.loadOffers(id);
        mOffersListPresenter.syncOffers(id);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.offers_list_home_btn:
                Log.i(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "onClick: home button");
                onBackPressed();
                break;
        }
    }

    @Override
    public void saveOffersListCompleted() {
        Log.i(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "saveOffersList: Completed");
        mOffersListPresenter.loadOffers(id);
    }

    @Override
    public void saveOffersListError(Throwable e){
        Log.e(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "saveOffersList: Error: "+e.getMessage());
        offersListRefreshL.setRefreshing(false);

//        Snackbar.make(mainView, "Oops.. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void syncOffersList(List<Offer> offers) {
        Log.i(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "syncOffersList: ");
        mOffersListPresenter.saveOffers((ArrayList<Offer>) offers);
    }

    @Override
    public void syncOffersListError(Throwable e) {
        Log.e(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "syncOffersList: Error:: "+e.getMessage());
        mOffersListPresenter.loadOffers(id);
//        Snackbar.make(mainView, "Oops.. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void syncOffersListCompleted() {
        Log.i(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "syncOffersList: Completed");

    }

    @Override
    public void showOffersList(List<Offer> offers) {
        Log.i(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "showOffersList: ");

        offersList.clear();
        offersList.addAll(offers);
        offersAdapter.notifyDataSetChanged();

        offersListRefreshL.setRefreshing(false);
    }

    @Override
    public void showOffersListError(Throwable e) {
        Log.e(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "showOffersList: Error:: "+e.getMessage());
        offersListRefreshL.setRefreshing(false);
        Snackbar.make(mainView, "Oops.. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showOffersListEmpty() {
        Log.i(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "showOffersList: Empty");
        offersListRefreshL.setRefreshing(false);

        Snackbar.make(mainView, "Oops.. No Offers Could be Displayed!!", Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onRefresh() {
        Log.i(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "OffersList: refresh");

        offersListRefreshL.setRefreshing(true);

        offersList.clear();
        offersAdapter.notifyDataSetChanged();
        mOffersListPresenter.syncOffers(id);
    }
}
