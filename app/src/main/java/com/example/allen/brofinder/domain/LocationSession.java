package com.example.allen.brofinder.domain;


public class LocationSession {
    private float latitude;
    private float longitude;

    private String senderName;

    public LocationSession(float latitude, float longitude, String senderName){
        this.latitude = latitude;
        this.longitude = longitude;
        this.senderName = senderName;
    }

    public float getLongitude(){
        return longitude;
    }

    public float getLatitude(){
        return latitude;
    }

    public String getSenderName(){
        return senderName;
    }

}
