package com.egycps.abdulaziz.egycps.data.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.egycps.abdulaziz.egycps.data.model.OffersCategory;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by abdulaziz on 9/27/16.
 */

public class DatabaseHelper {

    private static DatabaseHelper databaseHelper;

    private final BriteDatabase mDB;

    private DatabaseHelper(DbOpenHelper dbOpenHelper){
        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "DatabaseHelper: created");
        mDB = SqlBrite.create().wrapDatabaseHelper(dbOpenHelper, Schedulers.io());
    }

    public static DatabaseHelper getInstance(DbOpenHelper dbOpenHelper){
        if(databaseHelper == null){
            databaseHelper = new DatabaseHelper(dbOpenHelper);
        }

        return databaseHelper;
    }

    public static boolean isNull(){
        return databaseHelper==null;
    }

    public BriteDatabase getBriteDb(){
        return mDB;
    }

    public Observable<OffersCategory> setOffersCategories(final Collection<OffersCategory> categories){
        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "DatabaseHelper: setOffersCategories");
        return Observable.create(new Observable.OnSubscribe<OffersCategory>() {
            @Override
            public void call(Subscriber<? super OffersCategory> subscriber) {
                if(subscriber.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDB.newTransaction();
                try {
                    mDB.delete(Db.OffersCategoriesTable.TABLE_NAME, null);
                    for(OffersCategory category : categories){
                        long result = mDB.insert(Db.OffersCategoriesTable.TABLE_NAME,
                                    Db.OffersCategoriesTable.toContentValues(category),
                                    SQLiteDatabase.CONFLICT_REPLACE);

                        if(result >= 0) subscriber.onNext(category);
                    }
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                }finally {
                    transaction.end();
                }
            }
        });

    }


    public Observable<List<OffersCategory>> getOffersCategories(){
        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "DatabaseHelper: getOffersCategories");
        return mDB.createQuery(Db.OffersCategoriesTable.TABLE_NAME,
                    "SELECT * FROM " + Db.OffersCategoriesTable.TABLE_NAME)
                .mapToList(new Func1<Cursor, OffersCategory>() {
                    @Override
                    public OffersCategory call(Cursor cursor) {
                        return Db.OffersCategoriesTable.parseCursor(cursor);
                    }
                });
    }

}
