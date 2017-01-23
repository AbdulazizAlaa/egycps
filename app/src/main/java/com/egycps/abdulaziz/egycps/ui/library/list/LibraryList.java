package com.egycps.abdulaziz.egycps.ui.library.list;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egycps.abdulaziz.egycps.R;
import com.egycps.abdulaziz.egycps.data.DataManager;
import com.egycps.abdulaziz.egycps.data.model.Book;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class LibraryList extends AppCompatActivity implements View.OnClickListener, LibraryListBaseView, SwipeRefreshLayout.OnRefreshListener{

    String id;
    String title;

    Toolbar toolbar;
    TextView activityTitle;
    LinearLayout homeBtn;
    View mainView;

    RecyclerView libraryRecyclerView;
    RecyclerView.LayoutManager libraryLayoutManager;
    LibraryListAdapter libraryAdapter;

    ArrayList<Book> libraryList;

    LibraryListPresenter mLibraryListPresenter;

    SwipeRefreshLayout libraryListRefreshL;

    public static Intent getStartIntent(Context context){
        Intent i = new Intent(context, LibraryList.class);

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_list);
        Log.i(GlobalEntities.LIBRARIES_LIST_ACTIVITY_TAG, "onCreate: LibraryList");

        if(getIntent() != null){
            Log.i(GlobalEntities.LIBRARIES_LIST_ACTIVITY_TAG, "Title: "+getIntent().getStringExtra(GlobalEntities.LIBRARY_CATEGORY_TITLE_TAG));
            title = getIntent().getStringExtra(GlobalEntities.LIBRARY_CATEGORY_TITLE_TAG);
            id = getIntent().getStringExtra(GlobalEntities.LIBRARY_CATEGORY_ID_TAG);
        }else{
            title = "Library List";
            id = "1";
        }

        init();
    }

    private void init(){
        //initializing the toolbar
        mainView = findViewById(R.id.library_list_main_view);
        toolbar = (Toolbar) findViewById(R.id.library_list_toolbar);
        homeBtn = (LinearLayout) toolbar.findViewById(R.id.library_list_home_btn);
        activityTitle = (TextView) findViewById(R.id.library_list_title_tv);
        libraryListRefreshL = (SwipeRefreshLayout) findViewById(R.id.library_list_offers_refresh_l);

        libraryListRefreshL.setOnRefreshListener(this);

        activityTitle.setText(title);

        homeBtn.setOnClickListener(this);

        //initializing the categories recycler view
        libraryRecyclerView = (RecyclerView) findViewById(R.id.library_list_offers_recycler_view);

        libraryList = new ArrayList<Book>();

        libraryLayoutManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        libraryRecyclerView.setLayoutManager(libraryLayoutManager);

        final Context context = this;
        libraryAdapter = new LibraryListAdapter(this, libraryList);
        libraryRecyclerView.setAdapter(libraryAdapter);
        libraryAdapter.getPositionClicks()
                .subscribe(new Action1<Book>() {
                    @Override
                    public void call(Book book) {
                        Log.i(GlobalEntities.LIBRARIES_LIST_ACTIVITY_TAG, "offer id:: "+book.getId());
                        Log.i(GlobalEntities.LIBRARIES_LIST_ACTIVITY_TAG, "offer title:: "+book.getTitle());

                        String url = GlobalEntities.ENDPOINT+book.getFile();
                        url = url.replace(" ", "%20");
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);

                    }
                });

        //Presenter
        mLibraryListPresenter = new LibraryListPresenter(this, DataManager.getInstance(null, null, null, null));
        mLibraryListPresenter.attachView(this);

        libraryListRefreshL.post(new Runnable() {
            @Override
            public void run() {
                libraryListRefreshL.setRefreshing(true);
            }
        });

        mLibraryListPresenter.loadBooks(id);
        mLibraryListPresenter.syncBooks(id);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.library_list_home_btn:
                Log.i(GlobalEntities.LIBRARIES_LIST_ACTIVITY_TAG, "onClick: home button");
                onBackPressed();
                //TODO: should return to home activity not just the previous one
                break;
        }
    }

    @Override
    public void saveBooksCompleted() {
        Log.i(GlobalEntities.LIBRARIES_LIST_ACTIVITY_TAG, "saveBooks: Completed");
        mLibraryListPresenter.loadBooks(id);
    }

    @Override
    public void saveBooksError(Throwable e){
        Log.e(GlobalEntities.LIBRARIES_LIST_ACTIVITY_TAG, "saveBooks: Error: "+e.getMessage());
        libraryListRefreshL.setRefreshing(false);

//        Snackbar.make(mainView, "Oops.. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void syncBooks(List<Book> books) {
        Log.i(GlobalEntities.LIBRARIES_LIST_ACTIVITY_TAG, "syncBooks: ");
        mLibraryListPresenter.saveBooks((ArrayList<Book>) books);
    }

    @Override
    public void syncBooksError(Throwable e) {
        Log.e(GlobalEntities.LIBRARIES_LIST_ACTIVITY_TAG, "syncBooks: Error:: "+e.getMessage());
        mLibraryListPresenter.loadBooks(id);
//        Snackbar.make(mainView, "Oops.. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void syncBooksCompleted() {
        Log.i(GlobalEntities.LIBRARIES_LIST_ACTIVITY_TAG, "syncBooks: Completed");

    }

    @Override
    public void showBooks(List<Book> books) {
        Log.i(GlobalEntities.LIBRARIES_LIST_ACTIVITY_TAG, "showBooks: ");

        libraryList.clear();
        libraryList.addAll(books);
        libraryAdapter.notifyDataSetChanged();

        libraryListRefreshL.setRefreshing(false);
    }

    @Override
    public void showBooksError(Throwable e) {
        Log.e(GlobalEntities.LIBRARIES_LIST_ACTIVITY_TAG, "showBooks: Error:: "+e.getMessage());
        libraryListRefreshL.setRefreshing(false);
        Snackbar.make(mainView, "Oops.. "+e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showBooksEmpty() {
        Log.i(GlobalEntities.LIBRARIES_LIST_ACTIVITY_TAG, "showBooks: Empty");
        libraryListRefreshL.setRefreshing(false);

        Snackbar.make(mainView, "Oops.. No Books Could be Displayed!!", Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onRefresh() {
        Log.i(GlobalEntities.LIBRARIES_LIST_ACTIVITY_TAG, "Books: refresh");

        libraryListRefreshL.setRefreshing(true);

        libraryList.clear();
        libraryAdapter.notifyDataSetChanged();
        mLibraryListPresenter.syncBooks(id);
    }
}
