package com.egycps.abdulaziz.egycps.ui.library.list;

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
import com.egycps.abdulaziz.egycps.data.model.Book;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by abdulaziz on 10/2/16.
 */
public class LibraryListAdapter extends RecyclerView.Adapter<LibraryListAdapter.ViewHolder> {

    ArrayList<Book> data;
    private final Context mContext;
    private final PublishSubject<Book> onClickSubject = PublishSubject.create();

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView bookIV;
        TextView titleTV;
        TextView descTV;

        public ViewHolder(View itemView, ImageView offerIV, TextView titleTV, TextView descTV) {
            super(itemView);
            this.bookIV = offerIV;
            this.titleTV = titleTV;
            this.descTV = descTV;
        }
    }

    public LibraryListAdapter(Context context, ArrayList<Book> data) {
        mContext = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item_layout, parent, false);

        ImageView bookIV = (ImageView) v.findViewById(R.id.book_item_image_iv);
        TextView titleTV = (TextView) v.findViewById(R.id.book_item_title_tv);
        TextView descTV = (TextView) v.findViewById(R.id.book_item_content_tv);

        ViewHolder vh = new ViewHolder(v, bookIV, titleTV, descTV);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Book book = data.get(position);

        holder.titleTV.setText(book.getTitle());

        String pdf_path = book.getFile();
        Spanned path_spanned = Html.fromHtml("<a href='"+pdf_path+"'>PDF Link</a>");
        holder.descTV.setText(path_spanned);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(book);
            }
        });

        String image_path = GlobalEntities.ENDPOINT+book.getImage();
        image_path = image_path.replace(" ", "%20");
        Picasso.with(mContext).load(image_path).error(R.drawable.holder).placeholder(R.drawable.holder).into(holder.bookIV);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Observable<Book> getPositionClicks(){
        return onClickSubject.asObservable();
    }
}
