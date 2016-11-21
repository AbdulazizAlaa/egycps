package com.egycps.abdulaziz.egycps.ui.members;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.egycps.abdulaziz.egycps.R;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

public class Members extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    TextView activityTitle;
    LinearLayout homeBtn;

    WebView membersWV;

    public static Intent getStartIntent(Context context){
        Intent i = new Intent(context, Members.class);

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

        init();
    }


    private void init(){
        //initializing the toolbar
        toolbar = (Toolbar) findViewById(R.id.members_toolbar);
        homeBtn = (LinearLayout) toolbar.findViewById(R.id.members_home_btn);
        activityTitle = (TextView) findViewById(R.id.members_title_tv);
        membersWV = (WebView) findViewById(R.id.members_web_view);

        activityTitle.setText("Members");

        homeBtn.setOnClickListener(this);

        //setting up members webview
        membersWV.loadUrl(GlobalEntities.MEMBERS_ENDPOINT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.members_home_btn:
                Log.i(GlobalEntities.MEMBERS_ACTIVITY_TAG, "onClick: home button");
                onBackPressed();
                break;
        }
    }
}
