package com.egycps.abdulaziz.egycps.ui.offers.branches;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.egycps.abdulaziz.egycps.R;
import com.egycps.abdulaziz.egycps.data.model.Branch;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by abdulaziz on 10/2/16.
 */
public class BranchesAdapter extends RecyclerView.Adapter<BranchesAdapter.ViewHolder> {

    ArrayList<Branch> data;
    private final Context mContext;
    private final PublishSubject<Branch> onClickSubject = PublishSubject.create();

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView titleTV;

        public ViewHolder(View itemView, TextView titleTV) {
            super(itemView);
            this.titleTV = titleTV;
        }
    }

    public BranchesAdapter(Context context, ArrayList<Branch> data) {
        mContext = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.branches_item_layout, parent, false);

        TextView titleTV = (TextView) v.findViewById(R.id.branches_item_title_tv);

        ViewHolder vh = new ViewHolder(v, titleTV);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Branch branch = data.get(position);

        holder.titleTV.setText(branch.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(branch);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Observable<Branch> getPositionClicks(){
        return onClickSubject.asObservable();
    }

}

