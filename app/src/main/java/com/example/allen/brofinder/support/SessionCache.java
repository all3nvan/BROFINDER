package com.example.allen.brofinder.support;


import com.example.allen.brofinder.domain.LocationSession;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
public class SessionCache {
    private List<LocationSession> locationSessionList;
    private Context mContext;
    private static Gson gson;

    public SessionCache(Context context){
        this.mContext = context;
        gson = new GsonBuilder().create();
    }

    private SharedPreferences getSessionSharedPreferences(){
        return mContext.getSharedPreferences(Constants.SHARED_PREFERENCES_SESSION_NAME, Context.MODE_PRIVATE);
    }

    private List<LocationSession> getSessionHistory(){
        String jsonString = getSessionSharedPreferences().getString(Constants.SHARED_PREFERENCES_PROPERTY_SESSION_LIST, null);

        if (jsonString == null){
            return new ArrayList<LocationSession>();
        }

        else{
            return gson.fromJson(jsonString, new TypeToken<List<LocationSession>>(){}.getType());
        }
    }

    public void saveSession(LocationSession locationSession){
        String jsonString = gson.toJson(locationSession);
        getSessionSharedPreferences().edit().putString(Constants.SHARED_PREFERENCES_PROPERTY_SESSION_LIST, jsonString).apply();
    }


}
