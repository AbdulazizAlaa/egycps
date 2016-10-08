package com.egycps.abdulaziz.egycps.data.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.egycps.abdulaziz.egycps.data.model.Book;
import com.egycps.abdulaziz.egycps.data.model.Branch;
import com.egycps.abdulaziz.egycps.data.model.Category;
import com.egycps.abdulaziz.egycps.data.model.Magazine;
import com.egycps.abdulaziz.egycps.data.model.News;
import com.egycps.abdulaziz.egycps.data.model.Offer;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.Collection;
import java.util.List;

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

    public Observable<Branch> setBranches(final Collection<Branch> branches){
        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "DatabaseHelper: setBranches: "+branches.size());
        return Observable.create(new Observable.OnSubscribe<Branch>() {
            @Override
            public void call(Subscriber<? super Branch> subscriber) {
                if(subscriber.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDB.newTransaction();
                try {
//                    mDB.delete(Db.BranchesTable.TABLE_NAME, null);
                    for(Branch branch : branches){
                        long result = mDB.insert(Db.BranchesTable.TABLE_NAME,
                                Db.BranchesTable.toContentValues(branch),
                                SQLiteDatabase.CONFLICT_REPLACE);
                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "DatabaseHelper: setBranch: Branch: "+branch.getId()+" result: "+result);

                        if(result >= 0) subscriber.onNext(branch);
                    }
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                }finally {
                    transaction.end();
                }
            }
        });

    }

    public Observable<List<Branch>> getBranches(String offer_id){
        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "DatabaseHelper: getBranches");
        return mDB.createQuery(Db.BranchesTable.TABLE_NAME,
                "SELECT * FROM " + Db.BranchesTable.TABLE_NAME +
                " WHERE " + Db.BranchesTable.COLUMN_OFFER_ID + "=" + offer_id)
                .mapToList(new Func1<Cursor, Branch>() {
                    @Override
                    public Branch call(Cursor cursor) {
                        Branch branch = Db.BranchesTable.parseCursor(cursor);
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "Cursor Count:: "+cursor.getCount());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "---------------------------------------");
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "id :: "+magazine.getId());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "title :: "+magazine.getTitle());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "desc :: "+magazine.getPdf());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "image :: "+magazine.getImage());
                        return branch;
                    }
                });
    }

    public Observable<Book> setBooks(final Collection<Book> books){
        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "DatabaseHelper: setBooks: "+books.size());
        return Observable.create(new Observable.OnSubscribe<Book>() {
            @Override
            public void call(Subscriber<? super Book> subscriber) {
                if(subscriber.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDB.newTransaction();
                try {
//                    mDB.delete(Db.BooksTable.TABLE_NAME, null);
                    for(Book book : books){
                        long result = mDB.insert(Db.BooksTable.TABLE_NAME,
                                Db.BooksTable.toContentValues(book),
                                SQLiteDatabase.CONFLICT_REPLACE);
                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "DatabaseHelper: setBook: Book: "+book.getTitle()+" result: "+result);

                        if(result >= 0) subscriber.onNext(book);
                    }
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                }finally {
                    transaction.end();
                }
            }
        });

    }

    public Observable<List<Book>> getBooks(String cat_id){
        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "DatabaseHelper: getBooks");
        return mDB.createQuery(Db.BooksTable.TABLE_NAME,
                "SELECT * FROM " + Db.BooksTable.TABLE_NAME +
                " WHERE " + Db.BooksTable.COLUMN_CAT_ID + "=" + cat_id)
                .mapToList(new Func1<Cursor, Book>() {
                    @Override
                    public Book call(Cursor cursor) {
                        Book book = Db.BooksTable.parseCursor(cursor);
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "Cursor Count:: "+cursor.getCount());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "---------------------------------------");
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "id :: "+magazine.getId());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "title :: "+magazine.getTitle());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "desc :: "+magazine.getPdf());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "image :: "+magazine.getImage());
                        return book;
                    }
                });
    }

    public Observable<Magazine> setMagazines(final Collection<Magazine> magazines){
        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "DatabaseHelper: setMagazines: "+magazines.size());
        return Observable.create(new Observable.OnSubscribe<Magazine>() {
            @Override
            public void call(Subscriber<? super Magazine> subscriber) {
                if(subscriber.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDB.newTransaction();
                try {
//                    mDB.delete(Db.MagazinesTable.TABLE_NAME, null);
                    for(Magazine magazine : magazines){
                        long result = mDB.insert(Db.MagazinesTable.TABLE_NAME,
                                Db.MagazinesTable.toContentValues(magazine),
                                SQLiteDatabase.CONFLICT_REPLACE);
                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "DatabaseHelper: setMagazines: magazine: "+magazine.getTitle()+" result: "+result);

                        if(result >= 0) subscriber.onNext(magazine);
                    }
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                }finally {
                    transaction.end();
                }
            }
        });

    }

    public Observable<List<Magazine>> getMagazines(){
        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "DatabaseHelper: getMagazines");
        return mDB.createQuery(Db.MagazinesTable.TABLE_NAME,
                "SELECT * FROM " + Db.MagazinesTable.TABLE_NAME)
                .mapToList(new Func1<Cursor, Magazine>() {
                    @Override
                    public Magazine call(Cursor cursor) {
                        Magazine magazine = Db.MagazinesTable.parseCursor(cursor);
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "Cursor Count:: "+cursor.getCount());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "---------------------------------------");
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "id :: "+magazine.getId());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "title :: "+magazine.getTitle());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "desc :: "+magazine.getPdf());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "image :: "+magazine.getImage());
                        return magazine;
                    }
                });
    }

    public Observable<News> setNews(final Collection<News> news){
        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "DatabaseHelper: setNews: "+news.size());
        return Observable.create(new Observable.OnSubscribe<News>() {
            @Override
            public void call(Subscriber<? super News> subscriber) {
                if(subscriber.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDB.newTransaction();
                try {
//                    mDB.delete(Db.NewsTable.TABLE_NAME, null);
                    for(News n : news){
                        long result = mDB.insert(Db.NewsTable.TABLE_NAME,
                                Db.NewsTable.toContentValues(n),
                                SQLiteDatabase.CONFLICT_REPLACE);
                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "DatabaseHelper: setNews: news: "+n.getTitle()+" result: "+result);

                        if(result >= 0) subscriber.onNext(n);
                    }
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                }finally {
                    transaction.end();
                }
            }
        });

    }

    public Observable<List<News>> getNews(){
        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "DatabaseHelper: getNews");
        return mDB.createQuery(Db.NewsTable.TABLE_NAME,
                "SELECT * FROM " + Db.NewsTable.TABLE_NAME +
                " ORDER BY " + Db.NewsTable.COLUMN_CREATE_DATE + " LIMIT 10")
                .mapToList(new Func1<Cursor, News>() {
                    @Override
                    public News call(Cursor cursor) {
                        News news = Db.NewsTable.parseCursor(cursor);
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "Cursor Count:: "+cursor.getCount());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "---------------------------------------");
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "id :: "+news.getId());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "title :: "+news.getTitle());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "desc :: "+news.getDescription());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "image :: "+news.getImage());
                        return news;
                    }
                });
    }

    public Observable<Offer> setOffers(final Collection<Offer> offers){
        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "DatabaseHelper: setOffers: "+offers.size());
        return Observable.create(new Observable.OnSubscribe<Offer>() {
            @Override
            public void call(Subscriber<? super Offer> subscriber) {
                if(subscriber.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDB.newTransaction();
                try {
//                    mDB.delete(Db.OffersTable.TABLE_NAME, null);
                    for(Offer offer : offers){
                        long result = mDB.insert(Db.OffersTable.TABLE_NAME,
                                Db.OffersTable.toContentValues(offer),
                                SQLiteDatabase.CONFLICT_REPLACE);
                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "DatabaseHelper: setOffers: offer: "+offer.getName()+" result: "+result);

                        if(result >= 0) subscriber.onNext(offer);
                    }
                    transaction.markSuccessful();
                    subscriber.onCompleted();
                }finally {
                    transaction.end();
                }
            }
        });

    }

    public Observable<List<Offer>> getOffers(String cat_id){
        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "DatabaseHelper: getOffers");
        return mDB.createQuery(Db.OffersTable.TABLE_NAME,
                "SELECT * FROM " + Db.OffersTable.TABLE_NAME +
                " WHERE " + Db.OffersTable.COLUMN_CAT_ID + " = '" + cat_id + "'")
                .mapToList(new Func1<Cursor, Offer>() {
                    @Override
                    public Offer call(Cursor cursor) {
                        Offer offer = Db.OffersTable.parseCursor(cursor);
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "Cursor Count:: "+cursor.getCount());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "---------------------------------------");
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "id :: "+offer.getId());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "title :: "+offer.getTitle());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "desc :: "+offer.getDesc());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "image :: "+offer.getImage());
                        return offer;
                    }
                });
    }

    public Observable<Category> setOffersCategories(final Collection<Category> categories){
        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "DatabaseHelper: setOffersCategories: "+categories.size());
        return Observable.create(new Observable.OnSubscribe<Category>() {
            @Override
            public void call(Subscriber<? super Category> subscriber) {
                if(subscriber.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDB.newTransaction();
                try {
//                    mDB.delete(Db.OffersCategoriesTable.TABLE_NAME, null);
                    for(Category category : categories){
                        long result = mDB.insert(Db.OffersCategoriesTable.TABLE_NAME,
                                    Db.OffersCategoriesTable.toContentValues(category),
                                    SQLiteDatabase.CONFLICT_REPLACE);
                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "DatabaseHelper: setOffersCategories: category: "+category.getTitle()+" result: "+result);

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


    public Observable<List<Category>> getOffersCategories(){
        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "DatabaseHelper: getOffersCategories");
        return mDB.createQuery(Db.OffersCategoriesTable.TABLE_NAME,
                "SELECT * FROM " + Db.OffersCategoriesTable.TABLE_NAME)
                .mapToList(new Func1<Cursor, Category>() {
                    @Override
                    public Category call(Cursor cursor) {
                        Category category = Db.OffersCategoriesTable.parseCursor(cursor);
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "Cursor Count:: "+cursor.getCount());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "---------------------------------------");
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "id :: "+category.getId());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "title :: "+category.getTitle());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "desc :: "+category.getDescription());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "image :: "+category.getImage());
                        return category;
                    }
                });
    }

    public Observable<Category> setLibraryCategories(final Collection<Category> categories){
        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "DatabaseHelper: setLibraryCategories: "+categories.size());
        return Observable.create(new Observable.OnSubscribe<Category>() {
            @Override
            public void call(Subscriber<? super Category> subscriber) {
                if(subscriber.isUnsubscribed()) return;
                BriteDatabase.Transaction transaction = mDB.newTransaction();
                try {
//                    mDB.delete(Db.LibraryCategoriesTable.TABLE_NAME, null);
                    for(Category category : categories){
                        long result = mDB.insert(Db.LibraryCategoriesTable.TABLE_NAME,
                                Db.LibraryCategoriesTable.toContentValues(category),
                                SQLiteDatabase.CONFLICT_REPLACE);
                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "DatabaseHelper: setLibraryCategories: category: "+category.getTitle()+" result: "+result);

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


    public Observable<List<Category>> getLibraryCategories(){
        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "DatabaseHelper: getLibraryCategories");
        return mDB.createQuery(Db.LibraryCategoriesTable.TABLE_NAME,
                "SELECT * FROM " + Db.LibraryCategoriesTable.TABLE_NAME)
                .mapToList(new Func1<Cursor, Category>() {
                    @Override
                    public Category call(Cursor cursor) {
                        Category category = Db.LibraryCategoriesTable.parseCursor(cursor);
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "Cursor Count:: "+cursor.getCount());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "---------------------------------------");
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "id :: "+category.getId());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "title :: "+category.getTitle());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "desc :: "+category.getDescription());
//                        Log.i(GlobalEntities.DATABASE_HELPER_TAG, "image :: "+category.getImage());
                        return category;
                    }
                });
    }

}
