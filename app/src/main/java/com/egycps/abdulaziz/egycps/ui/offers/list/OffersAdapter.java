package com.egycps.abdulaziz.egycps.ui.offers.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.egycps.abdulaziz.egycps.R;
import com.egycps.abdulaziz.egycps.data.model.Offer;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by abdulaziz on 9/29/16.
 */
public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {

    ArrayList<Offer> data;
    private final Context mContext;
    private final PublishSubject<Offer> onClickSubject = PublishSubject.create();

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView offerIV;
        TextView titleTV;
        TextView descTV;

        public ViewHolder(View itemView, ImageView offerIV, TextView titleTV, TextView descTV) {
            super(itemView);
            this.offerIV = offerIV;
            this.titleTV = titleTV;
            this.descTV = descTV;
        }
    }

    public OffersAdapter(Context context, ArrayList<Offer> data) {
        mContext = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.offer_item_layout, parent, false);

        ImageView offerIV = (ImageView) v.findViewById(R.id.offer_item_image_iv);
        TextView titleTV = (TextView) v.findViewById(R.id.offer_item_title_tv);
        TextView descTV = (TextView) v.findViewById(R.id.offer_item_content_tv);

        ViewHolder vh = new ViewHolder(v, offerIV, titleTV, descTV);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Offer offer = data.get(position);

        holder.titleTV.setText(offer.getName());

        Spanned content = Html.fromHtml(offer.getDescription());
        holder.descTV.setText(content);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(offer);
            }
        });

        String image_path = GlobalEntities.ENDPOINT+offer.getImage();
        image_path = image_path.replace(" ", "%20");
        Picasso.with(mContext).load(image_path).into(holder.offerIV);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Observable<Offer> getPositionClicks(){
        return onClickSubject.asObservable();
    }

}
