package com.egycps.abdulaziz.egycps.data.local;

import android.content.ContentValues;
import android.database.Cursor;

import com.egycps.abdulaziz.egycps.data.model.Offer;
import com.egycps.abdulaziz.egycps.data.model.OffersCategory;

/**
 * Created by abdulaziz on 9/27/16.
 */
public class Db {

    public Db() {}

    public abstract static class OffersTable{
        public static final String TABLE_NAME = "offers";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESC = "desc";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_INFO = "info";
        public static final String COLUMN_CAT_ID = "cat_id";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " TEXT PRIMARY KEY, " +
                        COLUMN_TITLE + " TEXT NOT NULL, " +
                        COLUMN_DESC + " TEXT NOT NULL, " +
                        COLUMN_IMAGE + " TEXT NOT NULL, " +
                        COLUMN_INFO + " TEXT NOT NULL, " +
                        COLUMN_CAT_ID + " TEXT NOT NULL " +
                ");";

        public static final String DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

        public static ContentValues toContentValues(Offer offer){
            ContentValues values = new ContentValues();

            values.put(COLUMN_ID, offer.getId());
            values.put(COLUMN_TITLE, offer.getName());
            values.put(COLUMN_DESC, offer.getDescription());
            values.put(COLUMN_IMAGE, offer.getImage());
            values.put(COLUMN_INFO, offer.getInformation());
            values.put(COLUMN_CAT_ID, offer.getCategory_offer_id());

            return values;
        }

        public static Offer parseCursor(Cursor cursor){
            Offer offer = new Offer(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESC)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INFO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CAT_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE))
            );

            return offer;
        }
    }

    public abstract static class OffersCategoriesTable{
        public static final String TABLE_NAME = "offers_categories";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_DESC = "desc";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " TEXT PRIMARY KEY, " +
                        COLUMN_TITLE + " TEXT NOT NULL, " +
                        COLUMN_IMAGE + " TEXT NOT NULL, " +
                        COLUMN_DESC + " TEXT NOT NULL " +
                ");";

        public static final String DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

        public static ContentValues toContentValues(OffersCategory category){
            ContentValues values = new ContentValues();

            values.put(COLUMN_ID, category.getId());
            values.put(COLUMN_TITLE, category.getTitle());
            values.put(COLUMN_IMAGE, category.getImage());
            values.put(COLUMN_DESC, category.getDescription());

            return values;
        }

        public static OffersCategory parseCursor(Cursor cursor){
            OffersCategory category = new OffersCategory(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESC))
            );

            return category;
        }
    }

}
