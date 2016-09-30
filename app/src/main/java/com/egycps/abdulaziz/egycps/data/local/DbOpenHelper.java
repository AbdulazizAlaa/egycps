package com.egycps.abdulaziz.egycps.data.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by abdulaziz on 9/27/16.
 */

public class DbOpenHelper extends SQLiteOpenHelper{

    private static DbOpenHelper dbOpenHelper;

    public static final String DATABASE_NAME = "egycps.db";
    public static final int DATABASE_VERSION = 2;

    private DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DbOpenHelper getInstance(Context context){
        if(dbOpenHelper == null){
            dbOpenHelper = new DbOpenHelper(context);
        }

        return dbOpenHelper;
    }

    public static boolean isNull(){
        return dbOpenHelper==null;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        Log.i(GlobalEntities.DB_OPEN_HELPER, "Configure database");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(GlobalEntities.DB_OPEN_HELPER, "Creating database");
        db.beginTransaction();
        try {
            db.execSQL(Db.OffersCategoriesTable.CREATE);

            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(GlobalEntities.DB_OPEN_HELPER, "Upgrading database");
        db.beginTransaction();
        try {
            db.execSQL(Db.OffersCategoriesTable.DROP);

            db.execSQL(Db.OffersCategoriesTable.CREATE);

            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }
}
