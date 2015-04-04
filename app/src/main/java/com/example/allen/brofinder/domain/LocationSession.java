package com.example.allen.brofinder.domain;


public class LocationSession {
    private double latitude;
    private double longitude;

    private String senderName;

    public LocationSession(double latitude, double longitude, String senderName){
        this.latitude = latitude;
        this.longitude = longitude;
        this.senderName = senderName;
    }

    public double getLongitude(){
        return longitude;
    }

    public double getLatitude(){
        return latitude;
    }

    public String getSenderName(){
        return senderName;
    }

}
