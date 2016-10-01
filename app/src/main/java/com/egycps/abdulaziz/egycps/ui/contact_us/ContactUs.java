package com.egycps.abdulaziz.egycps.ui.contact_us;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egycps.abdulaziz.egycps.R;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

public class ContactUs extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    TextView activityTitle;
    LinearLayout homeBtn;

    TextView contentTV;
    Spanned content;
    Button contactB;

    public static Intent getStartIntent(Context context){
        Intent i = new Intent(context, ContactUs.class);

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        init();
    }

    private void init(){
        //initializing the toolbar
        toolbar = (Toolbar) findViewById(R.id.contact_us_toolbar);
        homeBtn = (LinearLayout) toolbar.findViewById(R.id.contact_us_home_btn);
        activityTitle = (TextView) findViewById(R.id.contact_us_title_tv);
        contentTV = (TextView) findViewById(R.id.contact_us_content_tv);
        contactB = (Button) findViewById(R.id.contact_us_contact_btn);

        activityTitle.setText("Contact Us");

        homeBtn.setOnClickListener(this);
        contactB.setOnClickListener(this);

        //setting content
        content = Html.fromHtml(getString(R.string.contact_us_text));
        contentTV.setText(content);
        contentTV.setAutoLinkMask(Linkify.WEB_URLS);
        contentTV.setLinksClickable(true);
        contentTV.setMovementMethod(LinkMovementMethod.getInstance());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.contact_us_home_btn:
                Log.i(GlobalEntities.CONTACTS_ACTIVITY_TAG, "onClick: home button");
                onBackPressed();
                break;
            case R.id.contact_us_contact_btn:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:"
                        + "info@egycps.com"
                        + "?subject=" + "Feedback" + "&body=" + "");
                intent.setData(data);
                startActivity(intent);
                break;
        }
    }
}
