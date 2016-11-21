package com.egycps.abdulaziz.egycps.ui.offers.list;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
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
import com.egycps.abdulaziz.egycps.data.local.PreferencesHelper;
import com.egycps.abdulaziz.egycps.data.model.Offer;
import com.egycps.abdulaziz.egycps.ui.offers.branches.BranchesActivity;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;
import com.egycps.abdulaziz.egycps.utils.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class OffersList extends AppCompatActivity implements OffersListBaseView, View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

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

    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    private FusedLocationProviderApi fusedLocationProviderApi = LocationServices.FusedLocationApi;
    private GoogleApiClient googleApiClient;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public static Intent getStartIntent(Context context) {
        Intent i = new Intent(context, OffersList.class);

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_list);
        Log.i(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "onCreate: OffersList");

        if (getIntent() != null) {
            Log.i(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "Title: " + getIntent().getStringExtra(GlobalEntities.OFFER_CATEGORY_TITLE_TAG));
            title = getIntent().getStringExtra(GlobalEntities.OFFER_CATEGORY_TITLE_TAG);
            id = getIntent().getStringExtra(GlobalEntities.OFFER_CATEGORY_ID_TAG);
        } else {
            title = "Offers List";
            id = "1";
        }

        init();

    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (googleApiClient.isConnected()) {
            startLocationUpdates();
            Log.d(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "Location update resumed .....................");
        }
    }

    private void init() {
        //GPS
        if (!Utils.isGooglePlayServicesAvailable(this, this)) {
            Log.i(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "init: Google play services not working");
        } else {
            createLocationRequest();

            googleApiClient = new GoogleApiClient.Builder(OffersList.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

//            TODO: get last known location
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
//                return;
            }else{
                Location location = fusedLocationProviderApi.getLastLocation(googleApiClient);
                if(location != null){
                    Log.d(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "Firing onLocationChanged..............................................");
                    Log.d(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "Location: lat: "+location.getLatitude()+" --- lng: "+location.getLongitude());
                    mCurrentLocation = location;
                    PreferencesHelper.saveToPrefs(OffersList.this, GlobalEntities.LATITUDE_TAG, String.valueOf(location.getLatitude()));
                    PreferencesHelper.saveToPrefs(OffersList.this, GlobalEntities.LONGITUDE_TAG, String.valueOf(location.getLongitude()));
                }
            }

        }

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
        /////
        offersList.add(new Offer("1", "offer", "desc", "hiiiiii1", "1", ""));
        /////

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

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, mLocationRequest, this);
        Log.d(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "Location update started ..............: ");
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient, this);
        Log.d(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "Location update stopped .......................");
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "Connection failed: " + connectionResult.toString());

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "Firing onLocationChanged..............................................");
        Log.d(GlobalEntities.OFFERS_LIST_ACTIVITY_TAG, "Location: lat: "+location.getLatitude()+" --- lng: "+location.getLongitude());
        mCurrentLocation = location;
        PreferencesHelper.saveToPrefs(OffersList.this, GlobalEntities.LATITUDE_TAG, String.valueOf(location.getLatitude()));
        PreferencesHelper.saveToPrefs(OffersList.this, GlobalEntities.LONGITUDE_TAG, String.valueOf(location.getLongitude()));

    }
}
