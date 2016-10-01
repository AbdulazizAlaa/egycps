package com.egycps.abdulaziz.egycps.ui.magazine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.egycps.abdulaziz.egycps.R;
import com.egycps.abdulaziz.egycps.data.model.Magazine;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by abdulaziz on 10/1/16.
 */
public class MagazineAdapter extends RecyclerView.Adapter<MagazineAdapter.ViewHolder>{

    ArrayList<Magazine> data;
    private final Context mContext;
    private final PublishSubject<Magazine> onClickSubject = PublishSubject.create();

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView newsIV;
        TextView titleTV;
        TextView descTV;

        public ViewHolder(View itemView, ImageView newsIV, TextView titleTV, TextView descTV) {
            super(itemView);
            this.newsIV = newsIV;
            this.titleTV = titleTV;
            this.descTV = descTV;
        }
    }

    public MagazineAdapter(Context context, ArrayList<Magazine> data) {
        mContext = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.magazine_item_layout, parent, false);

        ImageView offerIV = (ImageView) v.findViewById(R.id.magazine_item_image_iv);
        TextView titleTV = (TextView) v.findViewById(R.id.magazine_item_title_tv);
        TextView descTV = (TextView) v.findViewById(R.id.magazine_item_content_tv);

        ViewHolder vh = new ViewHolder(v, offerIV, titleTV, descTV);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Magazine magazine = data.get(position);

        holder.titleTV.setText(magazine.getTitle());

        String pdf_path = magazine.getPdf();
        Spanned path_spanned = Html.fromHtml("<a href='"+pdf_path+"'>PDF Link</a>");
        holder.descTV.setText(path_spanned);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(magazine);
            }
        });

        String image_path = GlobalEntities.ENDPOINT+magazine.getImage();
        image_path = image_path.replace(" ", "%20");
        Picasso.with(mContext).load(image_path).into(holder.newsIV);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Observable<Magazine> getPositionClicks(){
        return onClickSubject.asObservable();
    }
}
