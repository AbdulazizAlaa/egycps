package com.egycps.abdulaziz.egycps.ui.news.item;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egycps.abdulaziz.egycps.R;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;
import com.squareup.picasso.Picasso;

public class NewsItemActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    TextView activityTitle;
    LinearLayout homeBtn;
    View mainView;
    TextView titleTV;
    TextView contentTV;
    ImageView imageIV;

    String title, content, image_path;

    public static Intent getStartIntent(Context context){
        Intent i = new Intent(context, NewsItemActivity.class);

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_item);
        Log.i(GlobalEntities.NEWS_ITEM_ACTIVITY_TAG, "onCreate: create");

        if(getIntent()!=null){
            title = getIntent().getStringExtra(GlobalEntities.NEWS_TITLE_TAG);
            content = getIntent().getStringExtra(GlobalEntities.NEWS_CONTENT_TAG);
            image_path = getIntent().getStringExtra(GlobalEntities.NEWS_IMAGE_TAG);
            Log.i(GlobalEntities.NEWS_ITEM_ACTIVITY_TAG, "intent: title: "+title);
            Log.i(GlobalEntities.NEWS_ITEM_ACTIVITY_TAG, "intent: content: "+content);
            Log.i(GlobalEntities.NEWS_ITEM_ACTIVITY_TAG, "intent: image_path: "+image_path);

        }else{
            title = "";
            content = "";
            image_path = "";
        }

        init();

    }


    private void init(){
        //initializing the toolbar
        mainView = findViewById(R.id.news_ac_main_view);
        toolbar = (Toolbar) findViewById(R.id.news_ac_item_toolbar);
        homeBtn = (LinearLayout) toolbar.findViewById(R.id.news_ac_item_home_btn);
        activityTitle = (TextView) findViewById(R.id.news_item_ac_title_tv);
        titleTV = (TextView) findViewById(R.id.news_item_ac_news_title_tv);
        contentTV = (TextView) findViewById(R.id.news_item_ac_content_tv);
        imageIV = (ImageView) findViewById(R.id.news_item_ac_image_IV);

        activityTitle.setText("News");
        titleTV.setText(title);
        contentTV.setText(content);

        image_path = GlobalEntities.ENDPOINT+image_path;
        image_path = image_path.replace(" ", "%20");
        Picasso.with(this).load(image_path).error(R.drawable.holder).placeholder(R.drawable.holder).into(imageIV);

        homeBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.news_ac_item_home_btn:
                Log.i(GlobalEntities.NEWS_ITEM_ACTIVITY_TAG, "onClick: home button");
                onBackPressed();
                break;
        }
    }
}
