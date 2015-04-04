package com.example.allen.brofinder.adapter.factory;

import android.util.Log;

import com.example.allen.brofinder.domain.LocationSession;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LocationSessionFactory {

    public static List<LocationSession> createLocationSessionListFrom(JSONArray jsonOfLocationSessions) {
        List<LocationSession> locationSessionList = new ArrayList<>();
        for (int counter = 0; counter < jsonOfLocationSessions.length(); counter++) {
            try {
                JSONObject locationSessionJsonObject = jsonOfLocationSessions.getJSONObject(counter);
                String latitude = locationSessionJsonObject.getString("latitude");
                String longitude = locationSessionJsonObject.getString("longitude");
                String senderName = locationSessionJsonObject.getString("sender_name");
                locationSessionList.add(new LocationSession(Double.parseDouble(latitude), Double.parseDouble(longitude), senderName));
            }
            catch (Exception e){
                Log.e("LocationSessionFactory", e.toString());
            }
        }
        return locationSessionList;
    }
}
