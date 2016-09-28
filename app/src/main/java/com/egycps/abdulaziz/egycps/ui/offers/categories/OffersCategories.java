package com.egycps.abdulaziz.egycps.ui.offers.categories;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egycps.abdulaziz.egycps.R;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

public class OffersCategories extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    TextView activityTitle;
    LinearLayout homeBtn;


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
        toolbar = (Toolbar) findViewById(R.id.offers_categories_toolbar);
        homeBtn = (LinearLayout) toolbar.findViewById(R.id.offers_categories_home_btn);
        activityTitle = (TextView) findViewById(R.id.offers_categories_title_tv);

        activityTitle.setText("Offers Categories");

        homeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.offers_categories_home_btn:
                Log.i(GlobalEntities.OFFERS_CATEGORIES_ACTIVITY_TAG, "onClick: home button");
                break;
        }
    }
}
