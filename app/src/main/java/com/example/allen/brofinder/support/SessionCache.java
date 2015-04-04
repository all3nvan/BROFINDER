package com.example.allen.brofinder.support;

import com.example.allen.brofinder.domain.User;
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

    public List<User> getRecentUsers(){
        String jsonString = getSessionSharedPreferences().getString(Constants.SHARED_PREFERENCES_PROPERTY_SESSION_LIST, null);

        if (jsonString == null){
            return new ArrayList<>();
        }
        return gson.fromJson(jsonString, new TypeToken<List<User>>(){}.getType());

    }

    public void saveRecentUser(User user){
        String sessionList = getSessionSharedPreferences().getString(Constants.SHARED_PREFERENCES_PROPERTY_SESSION_LIST, null);
        List<User> recentUserList;
        if (sessionList == null) {
            recentUserList = new ArrayList<>();
            recentUserList.add(user);

        } else {
            recentUserList = gson.fromJson(sessionList, new TypeToken<List<User>>() {}.getType());
            recentUserList.add(user);
        }

        String jsonString = gson.toJson(recentUserList);
        getSessionSharedPreferences().edit().putString(Constants.SHARED_PREFERENCES_PROPERTY_SESSION_LIST, jsonString).apply();
    }
}
