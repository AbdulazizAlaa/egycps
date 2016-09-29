package com.egycps.abdulaziz.egycps.data.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.egycps.abdulaziz.egycps.R;

import java.util.ArrayList;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by abdulaziz on 9/29/16.
 */
public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {

    ArrayList<Offer> data;
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

    public OffersAdapter(ArrayList<Offer> data) {
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

        holder.titleTV.setText(data.get(position).getTitle());
        holder.descTV.setText(data.get(position).getDesc());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(offer);
            }
        });
        //TODO: load image into offer image view
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Observable<Offer> getPositionClicks(){
        return onClickSubject.asObservable();
    }

}
