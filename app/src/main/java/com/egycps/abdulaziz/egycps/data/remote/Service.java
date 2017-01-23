package com.egycps.abdulaziz.egycps.data.remote;

import com.egycps.abdulaziz.egycps.data.model.Book;
import com.egycps.abdulaziz.egycps.data.model.Branch;
import com.egycps.abdulaziz.egycps.data.model.Category;
import com.egycps.abdulaziz.egycps.data.model.Magazine;
import com.egycps.abdulaziz.egycps.data.model.News;
import com.egycps.abdulaziz.egycps.data.model.Offer;
import com.egycps.abdulaziz.egycps.utils.GeneralJsonDeserializer;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by abdulaziz on 9/27/16.
 */
public interface Service {

    @GET("feed/offerallCat.json")
    Observable<ArrayList<Category>> getOfferCategories();

    @GET("feed/libraryallCat.json")
    Observable<ArrayList<Category>> getLibraryCategories();

    @GET("feed/magazineallCat.json")
    Observable<ArrayList<Category>> getMagazineCategories();

    @GET("feed/offerCat.json")
    Observable<ArrayList<Offer>> getOffers(@Query("cat_id") String cat_id);

    @GET("feed/allbranchesbyoffer.json")
    Observable<ArrayList<Branch>> getBranches(@Query("offer_id") String Offer_id);

    @GET("feed/libraryCat.json")
    Observable<ArrayList<Book>> getBooks(@Query("cat_id") String cat_id);

    @GET("feed/magazinebyCat.json")
    Observable<ArrayList<Magazine>> getMagazines(@Query("cat_id") String cat_id);

    @GET("feed/allnews.json")
    Observable<ArrayList<News>> getNews();

    /******** Helper class that sets up a new services *******/
    class Creator{
        public static Service getService(){
            Type CategoriesListType = new TypeToken<ArrayList<Category>>(){}.getType();
            Type offersListType = new TypeToken<ArrayList<Offer>>(){}.getType();
            Type newsListType = new TypeToken<ArrayList<News>>(){}.getType();
            Type magazinesListType = new TypeToken<ArrayList<Magazine>>(){}.getType();
            Type booksListType = new TypeToken<ArrayList<Book>>(){}.getType();
            Type branchesListType = new TypeToken<ArrayList<Branch>>(){}.getType();

            Gson gson = new GsonBuilder()
                        .registerTypeAdapter(CategoriesListType, new GeneralJsonDeserializer<ArrayList<Category>>())
                        .registerTypeAdapter(offersListType, new GeneralJsonDeserializer<ArrayList<Offer>>())
                        .registerTypeAdapter(newsListType, new GeneralJsonDeserializer<ArrayList<News>>())
                        .registerTypeAdapter(magazinesListType, new GeneralJsonDeserializer<ArrayList<Magazine>>())
                        .registerTypeAdapter(booksListType, new GeneralJsonDeserializer<ArrayList<Book>>())
                        .registerTypeAdapter(branchesListType, new GeneralJsonDeserializer<ArrayList<Branch>>())
                        .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GlobalEntities.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(Service.class);
        }
    }

}