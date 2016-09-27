package com.egycps.abdulaziz.egycps.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.egycps.abdulaziz.egycps.R;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

public class Home extends Activity implements View.OnClickListener{


    LinearLayout newsLL, offersLL, libraryLL, magazineLL, membersLL, contactsLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.i(GlobalEntities.HOME_ACTIVITY_TAG, "onCreate: home");
        init();
    }


    private void init(){
        newsLL = (LinearLayout) findViewById(R.id.home_menu_news_item);
        offersLL = (LinearLayout) findViewById(R.id.home_menu_offers_item);
        libraryLL = (LinearLayout) findViewById(R.id.home_menu_library_item);
        magazineLL = (LinearLayout) findViewById(R.id.home_menu_magazine_item);
        membersLL = (LinearLayout) findViewById(R.id.home_menu_members_item);
        contactsLL = (LinearLayout) findViewById(R.id.home_menu_contacts_item);

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
                break;

            case R.id.home_menu_offers_item:
                Log.i(GlobalEntities.HOME_ACTIVITY_TAG, "onClick: offers");
                break;

            case R.id.home_menu_library_item:
                Log.i(GlobalEntities.HOME_ACTIVITY_TAG, "onClick: library");
                break;

            case R.id.home_menu_magazine_item:
                Log.i(GlobalEntities.HOME_ACTIVITY_TAG, "onClick: magazine");
                break;

            case R.id.home_menu_members_item:
                Log.i(GlobalEntities.HOME_ACTIVITY_TAG, "onClick: members");
                break;

            case R.id.home_menu_contacts_item:
                Log.i(GlobalEntities.HOME_ACTIVITY_TAG, "onClick: contacts");
                break;
        }
    }
}
