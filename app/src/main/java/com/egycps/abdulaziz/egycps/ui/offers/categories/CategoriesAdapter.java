package com.egycps.abdulaziz.egycps.ui.offers.categories;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.egycps.abdulaziz.egycps.R;
import com.egycps.abdulaziz.egycps.data.model.OffersCategory;
import com.egycps.abdulaziz.egycps.utils.Utils;

import java.util.ArrayList;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by abdulaziz on 9/28/16.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder>{


    ArrayList<OffersCategory> data;
    Context mContext;
    private final PublishSubject<OffersCategory> onClickSubject = PublishSubject.create();

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView categoryIV;
        TextView categoryTV;

        public ViewHolder(View itemView, ImageView categoryIV, TextView categoryTV) {
            super(itemView);
            this.categoryIV = categoryIV;
            this.categoryTV = categoryTV;
        }
    }

    public CategoriesAdapter(Context context, ArrayList<OffersCategory> data) {
        mContext = context;
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
        final OffersCategory cat = data.get(position);
        holder.categoryTV.setText(cat.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(cat);
            }
        });

        String[] names = cat.getImage().split("/");
        String image_filename = names[names.length-1];

//        Log.i(GlobalEntities.OFFERS_CATEGORIES_ADAPTER_TAG, "onBindViewHolder: image path: "+GlobalEntities.APP_DIR_TAG+image_filename);

        Bitmap image = Utils.loadImageFromStorage(mContext, image_filename);
        if(image != null){
            holder.categoryIV.setImageBitmap(image);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Observable<OffersCategory> getPositionClicks(){
        return onClickSubject.asObservable();
    }
}
