package com.egycps.abdulaziz.egycps.data.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.egycps.abdulaziz.egycps.R;
import com.egycps.abdulaziz.egycps.ui.offers.categories.OffersCategories;

import java.util.ArrayList;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by abdulaziz on 9/28/16.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder>{


    ArrayList<OffersCategory> data;
    private final PublishSubject<String> onClickSubject = PublishSubject.create();

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView categoryIV;
        TextView categoryTV;

        public ViewHolder(View itemView, ImageView categoryIV, TextView categoryTV) {
            super(itemView);
            this.categoryIV = categoryIV;
            this.categoryTV = categoryTV;
        }
    }

    public CategoriesAdapter(ArrayList<OffersCategory> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.category_item_layout, parent, false);


        ImageView categoryIV = (ImageView) v.findViewById(R.id.category_item_cat_image_iv);
        TextView categoryTV = (TextView) v.findViewById(R.id.category_item_title_tv);

        ViewHolder vh = new ViewHolder(v, categoryIV, categoryTV);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String id = data.get(position).getId();
        holder.categoryTV.setText(data.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(id);
            }
        });
        //TODO: get image url and load image to view
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Observable<String> getPositionClicks(){
        return onClickSubject.asObservable();
    }
}
