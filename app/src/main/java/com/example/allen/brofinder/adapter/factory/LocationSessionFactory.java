package com.example.allen.brofinder.adapter.factory;

import com.example.allen.brofinder.domain.LocationSession;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LocationSessionFactory {

    public static List<LocationSession> createLocationSessionListFrom(JSONArray jsonOfLocationSessions) throws Exception {
        List<LocationSession> locationSessionList = new ArrayList<>();
        for (int counter = 0; counter < jsonOfLocationSessions.length(); counter++) {
            JSONObject locationSessionJsonObject = jsonOfLocationSessions.getJSONObject(counter);
            String latitude = locationSessionJsonObject.getString("latitude");
            String longitude = locationSessionJsonObject.getString("longitude");
            String senderName = locationSessionJsonObject.getString("sender_name");
            locationSessionList.add(new LocationSession(Float.parseFloat(latitude), Float.parseFloat(longitude), senderName));
        }
        return locationSessionList;
    }
}
