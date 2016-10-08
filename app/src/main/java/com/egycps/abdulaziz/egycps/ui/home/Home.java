package com.egycps.abdulaziz.egycps.ui.home;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egycps.abdulaziz.egycps.R;
import com.egycps.abdulaziz.egycps.data.local.PreferencesHelper;
import com.egycps.abdulaziz.egycps.data.remote.EgyCpsFirebaseMessagingService;
import com.egycps.abdulaziz.egycps.ui.contact_us.ContactUs;
import com.egycps.abdulaziz.egycps.ui.library.categories.LibraryCategories;
import com.egycps.abdulaziz.egycps.ui.magazine.MagazineActivity;
import com.egycps.abdulaziz.egycps.ui.members.Members;
import com.egycps.abdulaziz.egycps.ui.news.list.NewsActivity;
import com.egycps.abdulaziz.egycps.ui.offers.categories.OffersCategories;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;
import com.google.android.gms.common.ConnectionResult;
import com.google.firebase.iid.FirebaseInstanceId;


public class Home extends Activity implements View.OnClickListener{


    LinearLayout newsLL, offersLL, libraryLL, magazineLL, membersLL, contactsLL;
    FrameLayout newsNotificationBadge, offersNotificationBadge;

    TextView newsNotificationText, offersNotificationText;

    int offers_num_notifications, news_num_notifications;

    public static Intent getStartIntent(Context context){
        Intent i = new Intent(context, Home.class);

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.i(GlobalEntities.HOME_ACTIVITY_TAG, "onCreate: home");

        init();

        registerReceiver(notificationHandler, new IntentFilter(GlobalEntities.BROADCAST_NOTIFICATION_HANDLER_TAG));

        startService(new Intent(Home.this, EgyCpsFirebaseMessagingService.class));
        Log.d(GlobalEntities.HOME_ACTIVITY_TAG, "Refreshed token: " + FirebaseInstanceId.getInstance().getToken());

    }


    @Override
    protected void onResume() {
        super.onResume();

        updateNotification();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(notificationHandler);
    }

    private void updateNotification(){
        news_num_notifications = Integer.parseInt(PreferencesHelper.getFromPrefs(this, GlobalEntities.NEWS_NOTIFICATION_COUNT_TAG, "0"));
        offers_num_notifications = Integer.parseInt(PreferencesHelper.getFromPrefs(this, GlobalEntities.OFFERS_NOTIFICATION_COUNT_TAG, "0"));
        if(news_num_notifications > 0){
            newsNotificationText.setText(String.valueOf(news_num_notifications));
            newsNotificationBadge.setVisibility(View.VISIBLE);
        }
        if(offers_num_notifications > 0){
            offersNotificationText.setText(String.valueOf(offers_num_notifications));
            offersNotificationBadge.setVisibility(View.VISIBLE);
        }
    }

    private void init(){
        //initializing menu items
        newsLL = (LinearLayout) findViewById(R.id.home_menu_news_item);
        offersLL = (LinearLayout) findViewById(R.id.home_menu_offers_item);
        libraryLL = (LinearLayout) findViewById(R.id.home_menu_library_item);
        magazineLL = (LinearLayout) findViewById(R.id.home_menu_magazine_item);
        membersLL = (LinearLayout) findViewById(R.id.home_menu_members_item);
        contactsLL = (LinearLayout) findViewById(R.id.home_menu_contacts_item);

        //initializing notification badges
        newsNotificationBadge = (FrameLayout) findViewById(R.id.home_menu_news_notification_badge);
        offersNotificationBadge = (FrameLayout) findViewById(R.id.home_menu_offers_notification_badge);

        //initializing notification text
        newsNotificationText = (TextView) findViewById(R.id.home_menu_news_notification_text);
        offersNotificationText = (TextView) findViewById(R.id.home_menu_offers_notification_text);

        //adding on click listeners
        newsLL.setOnClickListener(this);
        offersLL.setOnClickListener(this);
        libraryLL.setOnClickListener(this);
        magazineLL.setOnClickListener(this);
        membersLL.setOnClickListener(this);
        contactsLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.home_menu_news_item:
                Log.i(GlobalEntities.HOME_ACTIVITY_TAG, "onClick: news");
                startActivity(NewsActivity.getStartIntent(this));
                if(news_num_notifications > 0){
                    newsNotificationText.setText("");
                    newsNotificationBadge.setVisibility(View.GONE);
                    news_num_notifications = 0;
                    PreferencesHelper.saveToPrefs(getApplicationContext(), GlobalEntities.NEWS_NOTIFICATION_COUNT_TAG, String.valueOf(news_num_notifications));
                }
                break;

            case R.id.home_menu_offers_item:
                Log.i(GlobalEntities.HOME_ACTIVITY_TAG, "onClick: offers");
                startActivity(OffersCategories.getStartIntent(this));
                if(offers_num_notifications > 0){
                    offersNotificationText.setText("");
                    offersNotificationBadge.setVisibility(View.GONE);
                    offers_num_notifications = 0;
                    PreferencesHelper.saveToPrefs(getApplicationContext(), GlobalEntities.OFFERS_NOTIFICATION_COUNT_TAG, String.valueOf(offers_num_notifications));
                }
                break;

            case R.id.home_menu_library_item:
                Log.i(GlobalEntities.HOME_ACTIVITY_TAG, "onClick: library");
                startActivity(LibraryCategories.getStartIntent(this));
                break;

            case R.id.home_menu_magazine_item:
                Log.i(GlobalEntities.HOME_ACTIVITY_TAG, "onClick: magazine");
                startActivity(MagazineActivity.getStartIntent(this));
                break;

            case R.id.home_menu_members_item:
                Log.i(GlobalEntities.HOME_ACTIVITY_TAG, "onClick: members");
                startActivity(Members.getStartIntent(this));
                break;

            case R.id.home_menu_contacts_item:
                Log.i(GlobalEntities.HOME_ACTIVITY_TAG, "onClick: contacts");
                startActivity(ContactUs.getStartIntent(this));
                break;
        }
    }

    private BroadcastReceiver notificationHandler = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateNotification();
        }
    };
}
