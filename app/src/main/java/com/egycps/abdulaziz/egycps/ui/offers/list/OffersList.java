package com.egycps.abdulaziz.egycps.ui.offers.list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egycps.abdulaziz.egycps.R;
import com.egycps.abdulaziz.egycps.data.model.CategoriesAdapter;
import com.egycps.abdulaziz.egycps.data.model.Offer;
import com.egycps.abdulaziz.egycps.data.model.OffersAdapter;
import com.egycps.abdulaziz.egycps.data.model.OffersCategory;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

import java.util.ArrayList;

import rx.functions.Action1;

public class OffersList extends AppCompatActivity implements View.OnClickListener{

    String id;
    String title;

    Toolbar toolbar;
    TextView activityTitle;
    LinearLayout homeBtn;

    RecyclerView offersRecyclerView;
    RecyclerView.LayoutManager offersLayoutManager;
    OffersAdapter offersAdapter;

    ArrayList<Offer> offersList;

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
            id = getIntent().getStringExtra(GlobalEntities.OFFER_CATEGORY_TITLE_TAG);
        }else{
            title = "Offers List";
            id = "1";
        }

        init();


    }

    private void init(){
        //initializing the toolbar
        toolbar = (Toolbar) findViewById(R.id.offers_list_toolbar);
        homeBtn = (LinearLayout) toolbar.findViewById(R.id.offers_list_home_btn);
        activityTitle = (TextView) findViewById(R.id.offers_list_title_tv);

        activityTitle.setText(title);

        homeBtn.setOnClickListener(this);

        //initializing the categories recycler view
        offersRecyclerView = (RecyclerView) findViewById(R.id.offers_list_offers_recycler_view);

        offersList = new ArrayList<Offer>();
        String desc = "this is a limited time offer for all you egyptian pilots every where in the world.";
        offersList.add(new Offer("1", "Egypt air offer", desc, "hiiiiiiiiiiiiiiii", "1", "image"));
        offersList.add(new Offer("2", "Gym offer", desc, "hiiiiiiiiiiiiiiii", "1", "image"));
        offersList.add(new Offer("3", "Hospitals offer", desc, "hiiiiiiiiiiiiiiii", "1", "image"));
        offersList.add(new Offer("4", "Cars offer", desc, "hiiiiiiiiiiiiiiii", "1", "image"));
        offersList.add(new Offer("5", "Hotels offer", desc, "hiiiiiiiiiiiiiiii", "1", "image"));
        offersList.add(new Offer("6", "Hotels offer", desc, "hiiiiiiiiiiiiiiii", "1", "image"));

        offersLayoutManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        offersRecyclerView.setLayoutManager(offersLayoutManager);

        final Context context = this;
        offersAdapter = new OffersAdapter(offersList);
        offersRecyclerView.setAdapter(offersAdapter);
        offersAdapter.getPositionClicks()
                .subscribe(new Action1<Offer>() {
                    @Override
                    public void call(Offer o) {
                        Log.i(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "offer id:: "+o.getId());
                        Log.i(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "offer title:: "+o.getTitle());
                    }
                });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.offers_list_home_btn:
                Log.i(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "onClick: home button");
                onBackPressed();
                //TODO: should return to home activity not just the previous one
                break;
        }
    }
}
