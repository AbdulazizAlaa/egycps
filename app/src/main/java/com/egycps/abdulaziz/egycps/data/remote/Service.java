package com.egycps.abdulaziz.egycps.data.remote;

import com.egycps.abdulaziz.egycps.data.model.Magazine;
import com.egycps.abdulaziz.egycps.data.model.News;
import com.egycps.abdulaziz.egycps.data.model.Offer;
import com.egycps.abdulaziz.egycps.data.model.OffersCategory;
import com.egycps.abdulaziz.egycps.utils.GeneralJsonDeserializer;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by abdulaziz on 9/27/16.
 */
public interface Service {

    @GET("feed/offerallCat.json")
    Observable<ArrayList<OffersCategory>> getOfferCategories();

    @GET("feed/offerCat.json")
    Observable<ArrayList<Offer>> getOffers(@Query("cat_id") String cat_id);

    @GET("feed/magazines.json")
    Observable<ArrayList<Magazine>> getMagazines();

    @GET("feed/allnews.json")
    Observable<ArrayList<News>> getNews();

    /******** Helper class that sets up a new services *******/
    class Creator{
        public static Service getService(){
            Type offerCategoriesListType = new TypeToken<ArrayList<OffersCategory>>(){}.getType();
            Type offersListType = new TypeToken<ArrayList<Offer>>(){}.getType();
            Type newsListType = new TypeToken<ArrayList<News>>(){}.getType();
            Type magazinesListType = new TypeToken<ArrayList<Magazine>>(){}.getType();

            Gson gson = new GsonBuilder()
                        .registerTypeAdapter(offerCategoriesListType, new GeneralJsonDeserializer<ArrayList<OffersCategory>>())
                        .registerTypeAdapter(offersListType, new GeneralJsonDeserializer<ArrayList<Offer>>())
                        .registerTypeAdapter(newsListType, new GeneralJsonDeserializer<ArrayList<News>>())
                        .registerTypeAdapter(magazinesListType, new GeneralJsonDeserializer<ArrayList<Magazine>>())
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