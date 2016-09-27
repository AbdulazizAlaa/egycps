package com.egycps.abdulaziz.egycps.data.remote;

import com.egycps.abdulaziz.egycps.data.model.OfferCategory;
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
import rx.Observable;

/**
 * Created by abdulaziz on 9/27/16.
 */
public interface Service {

    @GET("feed/offerallCat.json")
    Observable<ArrayList<OfferCategory>> getOfferCategories();

    /******** Helper class that sets up a new services *******/
    class Creator{
        public static Service getService(){
            Type offerCategoriesListType = new TypeToken<ArrayList<OfferCategory>>(){}.getType();

            Gson gson = new GsonBuilder()
                        .registerTypeAdapter(offerCategoriesListType, new OfferCategoriesDeserializer<ArrayList<OfferCategory>>())
                        .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(GlobalEntities.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(Service.class);
        }
    }

    class OfferCategoriesDeserializer<T> implements JsonDeserializer<T>
    {
        @Override
        public T deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
                throws JsonParseException
        {

            String objArray  = "[";

            Set<Map.Entry<String, JsonElement>> entries = je.getAsJsonObject().entrySet();//will return members of your object
            for (Map.Entry<String, JsonElement> entry: entries) {
                //Log.i(GlobalEntities.SERVICE_TAG, "key: "+entry.getKey()+" obj: "+je.getAsJsonObject().get(entry.getKey()));
                objArray += je.getAsJsonObject().get(entry.getKey()) + ",";
            }
            objArray = objArray.substring(0, objArray.length()-1)+"]";
            //Log.i(GlobalEntities.SERVICE_TAG, "OfferCategories array: "+objArray);

            return new Gson().fromJson(objArray, type);

        }
    }

}