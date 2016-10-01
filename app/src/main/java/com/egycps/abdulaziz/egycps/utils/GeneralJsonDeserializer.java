package com.egycps.abdulaziz.egycps.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

/**
 * Created by abdulaziz on 10/1/16.
 */
public class GeneralJsonDeserializer<T> implements JsonDeserializer<T> {
    @Override
    public T deserialize(JsonElement je, Type type, JsonDeserializationContext jdc)
            throws JsonParseException
    {

        String objArray  = "[";

        Set<Map.Entry<String, JsonElement>> entries = je.getAsJsonObject().entrySet();//will return members of your object
        for (Map.Entry<String, JsonElement> entry: entries) {
//            Log.i(GlobalEntities.SERVICE_TAG, "key: "+entry.getKey()+" obj: "+je.getAsJsonObject().get(entry.getKey()));
            objArray += je.getAsJsonObject().get(entry.getKey()) + ",";
        }
        objArray = objArray.substring(0, objArray.length()-1)+"]";
//        Log.i(GlobalEntities.SERVICE_TAG, "Deserializer array: "+objArray);

        return new Gson().fromJson(objArray, type);

    }
}
