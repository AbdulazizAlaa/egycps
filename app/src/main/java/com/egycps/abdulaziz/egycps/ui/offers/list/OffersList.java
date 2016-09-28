package com.egycps.abdulaziz.egycps.ui.offers.list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.egycps.abdulaziz.egycps.R;

public class OffersList extends AppCompatActivity {

    public static Intent getStartIntent(Context context){
        Intent i = new Intent(context, OffersList.class);

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_list);
    }
}
