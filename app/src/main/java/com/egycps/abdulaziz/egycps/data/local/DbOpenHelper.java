package com.egycps.abdulaziz.egycps.data.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.egycps.abdulaziz.egycps.utils.GlobalEntities;

/**
 * Created by abdulaziz on 9/27/16.
 */

public class DbOpenHelper extends SQLiteOpenHelper{

    private static DbOpenHelper dbOpenHelper;

    public static final String DATABASE_NAME = "egycps.db";
    public static final int DATABASE_VERSION = 1;

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
            db.execSQL(Db.OffersTable.CREATE);
            db.execSQL(Db.NewsTable.CREATE);
            db.execSQL(Db.MagazinesTable.CREATE);
            db.execSQL(Db.LibraryCategoriesTable.CREATE);
            db.execSQL(Db.BooksTable.CREATE);
            db.execSQL(Db.BranchesTable.CREATE);

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
            db.execSQL(Db.OffersTable.DROP);
            db.execSQL(Db.NewsTable.DROP);
            db.execSQL(Db.MagazinesTable.DROP);
            db.execSQL(Db.LibraryCategoriesTable.DROP);
            db.execSQL(Db.BooksTable.DROP);
            db.execSQL(Db.BranchesTable.DROP);

            db.execSQL(Db.OffersCategoriesTable.CREATE);
            db.execSQL(Db.OffersTable.CREATE);
            db.execSQL(Db.NewsTable.CREATE);
            db.execSQL(Db.MagazinesTable.CREATE);
            db.execSQL(Db.LibraryCategoriesTable.CREATE);
            db.execSQL(Db.BooksTable.CREATE);
            db.execSQL(Db.BranchesTable.CREATE);

            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }
}
