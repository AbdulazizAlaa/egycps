package com.egycps.abdulaziz.egycps.ui.news;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.egycps.abdulaziz.egycps.R;
import com.egycps.abdulaziz.egycps.data.model.News;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by abdulaziz on 10/1/16.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

    ArrayList<News> data;
    private final Context mContext;
    private final PublishSubject<News> onClickSubject = PublishSubject.create();

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

    public NewsAdapter(Context context, ArrayList<News> data) {
        mContext = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item_layout, parent, false);

        ImageView offerIV = (ImageView) v.findViewById(R.id.news_item_image_iv);
        TextView titleTV = (TextView) v.findViewById(R.id.news_item_title_tv);
        TextView descTV = (TextView) v.findViewById(R.id.news_item_content_tv);

        ViewHolder vh = new ViewHolder(v, offerIV, titleTV, descTV);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final News news = data.get(position);

        holder.titleTV.setText(news.getTitle());

        Spanned content = Html.fromHtml(news.getDescription());
        holder.descTV.setText(content);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(news);
            }
        });

        String image_path = GlobalEntities.ENDPOINT+news.getImage();
        image_path = image_path.replace(" ", "%20");
        Picasso.with(mContext).load(image_path).error(R.drawable.holder).placeholder(R.drawable.holder).into(holder.newsIV);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Observable<News> getPositionClicks(){
        return onClickSubject.asObservable();
    }

}
