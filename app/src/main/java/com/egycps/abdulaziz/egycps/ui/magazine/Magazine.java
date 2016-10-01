package com.egycps.abdulaziz.egycps.ui.magazine;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egycps.abdulaziz.egycps.R;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

public class Magazine extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    TextView activityTitle;
    LinearLayout homeBtn;

    public static Intent getStartIntent(Context context){
        Intent i = new Intent(context, Magazine.class);

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine);

        init();

    }

    private void init(){
        //initializing the toolbar
        toolbar = (Toolbar) findViewById(R.id.magazine_toolbar);
        homeBtn = (LinearLayout) toolbar.findViewById(R.id.magazine_home_btn);
        activityTitle = (TextView) findViewById(R.id.magazine_title_tv);

        activityTitle.setText("Magazine");

        homeBtn.setOnClickListener(this);
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
}
