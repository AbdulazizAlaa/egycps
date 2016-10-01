package com.egycps.abdulaziz.egycps.ui.members;

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

public class Members extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    TextView activityTitle;
    LinearLayout homeBtn;

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

        activityTitle.setText("Members");

        homeBtn.setOnClickListener(this);
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
