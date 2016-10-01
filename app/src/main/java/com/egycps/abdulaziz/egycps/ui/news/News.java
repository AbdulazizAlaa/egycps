package com.egycps.abdulaziz.egycps.ui.news;

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

public class News extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    TextView activityTitle;
    LinearLayout homeBtn;

    public static Intent getStartIntent(Context context){
        Intent i = new Intent(context, News.class);

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        init();

    }

    private void init(){
        //initializing the toolbar
        toolbar = (Toolbar) findViewById(R.id.news_toolbar);
        homeBtn = (LinearLayout) toolbar.findViewById(R.id.news_home_btn);
        activityTitle = (TextView) findViewById(R.id.news_title_tv);

        activityTitle.setText("News");

        homeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.news_home_btn:
                Log.i(GlobalEntities.NEWS_ACTIVITY_TAG, "onClick: home button");
                onBackPressed();
                break;
        }
    }
}
