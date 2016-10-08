package com.egycps.abdulaziz.egycps.data.local;

import android.content.ContentValues;
import android.database.Cursor;

import com.egycps.abdulaziz.egycps.data.model.Book;
import com.egycps.abdulaziz.egycps.data.model.Branch;
import com.egycps.abdulaziz.egycps.data.model.Category;
import com.egycps.abdulaziz.egycps.data.model.Magazine;
import com.egycps.abdulaziz.egycps.data.model.News;
import com.egycps.abdulaziz.egycps.data.model.Offer;

/**
 * Created by abdulaziz on 9/27/16.
 */
public class Db {

    public Db() {}

    public abstract static class BranchesTable{
        public static final String TABLE_NAME = "branches";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LAT = "latitude";
        public static final String COLUMN_LONG = "longitude";
        public static final String COLUMN_OFFER_ID = "offer_id";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " TEXT PRIMARY KEY, " +
                        COLUMN_NAME + " TEXT NOT NULL, " +
                        COLUMN_LAT + " TEXT NOT NULL, " +
                        COLUMN_LONG + " TEXT NOT NULL, " +
                        COLUMN_OFFER_ID + " TEXT NOT NULL " +
                ");";

        public static final String DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

        public static ContentValues toContentValues(Branch branch){
            ContentValues values = new ContentValues();

            values.put(COLUMN_ID, branch.getId());
            values.put(COLUMN_NAME, branch.getName());
            values.put(COLUMN_LAT, branch.getLatitude());
            values.put(COLUMN_LONG, branch.getLongitude());
            values.put(COLUMN_OFFER_ID, branch.getOffer_id());

            return values;
        }

        public static Branch parseCursor(Cursor cursor){
            Branch branch = new Branch(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LONG)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OFFER_ID))
            );

            return branch;
        }
    }

    public abstract static class BooksTable{
        public static final String TABLE_NAME = "books";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_FILE = "file";
        public static final String COLUMN_CAT_ID = "cat_id";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " TEXT PRIMARY KEY, " +
                        COLUMN_TITLE + " TEXT NOT NULL, " +
                        COLUMN_IMAGE + " TEXT NOT NULL, " +
                        COLUMN_FILE + " TEXT NOT NULL, " +
                        COLUMN_CAT_ID + " TEXT NOT NULL " +
                        ");";

        public static final String DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

        public static ContentValues toContentValues(Book book){
            ContentValues values = new ContentValues();

            values.put(COLUMN_ID, book.getId());
            values.put(COLUMN_TITLE, book.getTitle());
            values.put(COLUMN_IMAGE, book.getImage());
            values.put(COLUMN_FILE, book.getFile());
            values.put(COLUMN_CAT_ID, book.getCategory_library_id());

            return values;
        }

        public static Book parseCursor(Cursor cursor){
            Book book = new Book(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FILE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CAT_ID))
            );

            return book;
        }
    }

    public abstract static class MagazinesTable{
        public static final String TABLE_NAME = "magazines";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_PDF = "pdf";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " TEXT PRIMARY KEY, " +
                        COLUMN_TITLE + " TEXT NOT NULL, " +
                        COLUMN_IMAGE + " TEXT NOT NULL, " +
                        COLUMN_PDF + " TEXT NOT NULL " +
                ");";

        public static final String DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

        public static ContentValues toContentValues(Magazine magazine){
            ContentValues values = new ContentValues();

            values.put(COLUMN_ID, magazine.getId());
            values.put(COLUMN_TITLE, magazine.getTitle());
            values.put(COLUMN_IMAGE, magazine.getImage());
            values.put(COLUMN_PDF, magazine.getPdf());

            return values;
        }

        public static Magazine parseCursor(Cursor cursor){
            Magazine magazine = new Magazine(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PDF))
                    );

            return magazine;
        }
    }

    public abstract static class NewsTable{
        public static final String TABLE_NAME = "news";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESC = "desc";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_CREATE_DATE = "create_date";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " TEXT PRIMARY KEY, " +
                        COLUMN_TITLE + " TEXT NOT NULL, " +
                        COLUMN_DESC + " TEXT NOT NULL, " +
                        COLUMN_IMAGE + " TEXT NOT NULL, " +
                        COLUMN_CREATE_DATE + " TIMESTAMP NOT NULL " +
                ");";

        public static final String DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

        public static ContentValues toContentValues(News news){
            ContentValues values = new ContentValues();

            values.put(COLUMN_ID, news.getId());
            values.put(COLUMN_TITLE, news.getTitle());
            values.put(COLUMN_DESC, news.getDescription());
            values.put(COLUMN_IMAGE, news.getImage());
            values.put(COLUMN_CREATE_DATE, news.getCreated_at());

            return values;
        }

        public static News parseCursor(Cursor cursor){
            News news = new News(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESC)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATE_DATE))
            );

            return news;
        }
    }

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

    public abstract static class LibraryCategoriesTable{
        public static final String TABLE_NAME = "library_categories";

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

        public static ContentValues toContentValues(Category category){
            ContentValues values = new ContentValues();

            values.put(COLUMN_ID, category.getId());
            values.put(COLUMN_TITLE, category.getTitle());
            values.put(COLUMN_IMAGE, category.getImage());
            values.put(COLUMN_DESC, category.getDescription());

            return values;
        }

        public static Category parseCursor(Cursor cursor){
            Category category = new Category(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESC))
            );

            return category;
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

        public static ContentValues toContentValues(Category category){
            ContentValues values = new ContentValues();

            values.put(COLUMN_ID, category.getId());
            values.put(COLUMN_TITLE, category.getTitle());
            values.put(COLUMN_IMAGE, category.getImage());
            values.put(COLUMN_DESC, category.getDescription());

            return values;
        }

        public static Category parseCursor(Cursor cursor){
            Category category = new Category(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESC))
            );

            return category;
        }
    }

}
