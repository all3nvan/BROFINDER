package com.example.allen.brofinder.support;

import android.location.Location;

public class MapUtils {

    public static Location calculateMidPointLocation(Location currentLocation, Location destinationLocation) {
        double dLon = Math.toRadians(destinationLocation.getLongitude() - currentLocation.getLongitude());

        double currentLat = Math.toRadians(currentLocation.getLatitude());
        double currentLon = Math.toRadians(currentLocation.getLongitude());
        double destLat = Math.toRadians(destinationLocation.getLatitude());

        double Bx = Math.cos(destLat) * Math.cos(dLon);
        double By = Math.cos(destLat) * Math.sin(dLon);
        double lat3 = Math.atan2(Math.sin(currentLat) + Math.sin(destLat), Math.sqrt((Math.cos(currentLat) + Bx) * (Math.cos(currentLat) + Bx) + By * By));
        double lon3 = currentLon + Math.atan2(By, Math.cos(currentLat) + Bx);

        Location midPointLocation = new Location("");
        midPointLocation.setLatitude(Math.toDegrees(lat3));
        midPointLocation.setLongitude(Math.toDegrees(lon3));

        return midPointLocation;
    }

    public static int calculateCameraZoom(Location currentLocation, Location destinationLocation) {
        float distance = currentLocation.distanceTo(destinationLocation);
        Double zoomLevelDouble = 16.69229236 + (-0.000302288 * distance);
        Integer zoomLevel = zoomLevelDouble.intValue() - 1;
        if(zoomLevel < 3)
            zoomLevel = 3;
        return zoomLevel;
    }
}
