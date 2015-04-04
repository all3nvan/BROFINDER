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
    private Context mContext;
    private static Gson gson;

    public SessionCache(Context context){
        this.mContext = context;
        gson = new GsonBuilder().create();
    }

    private SharedPreferences getSessionSharedPreferences(){
        return mContext.getSharedPreferences(Constants.SHARED_PREFERENCES_SESSION_NAME, Context.MODE_PRIVATE);
    }

    public List<LocationSession> getSessionHistory(){
        String jsonString = getSessionSharedPreferences().getString(Constants.SHARED_PREFERENCES_PROPERTY_SESSION_LIST, null);

        if (jsonString == null){
            return new ArrayList<>();
        }
        return gson.fromJson(jsonString, new TypeToken<List<LocationSession>>(){}.getType());

    }

    public void saveSession(LocationSession locationSession){
        String sessionList = getSessionSharedPreferences().getString(Constants.SHARED_PREFERENCES_PROPERTY_SESSION_LIST, null);
        List<LocationSession> locationSessionList;
        if (sessionList == null) {
            locationSessionList = new ArrayList<>();
            locationSessionList.add(locationSession);

        } else {
            locationSessionList = gson.fromJson(sessionList, new TypeToken<List<LocationSession>>() {}.getType());
            locationSessionList.add(locationSession);
        }

        String jsonString = gson.toJson(locationSessionList);
        getSessionSharedPreferences().edit().putString(Constants.SHARED_PREFERENCES_PROPERTY_SESSION_LIST, jsonString).apply();
    }
}
