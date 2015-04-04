package com.example.allen.brofinder.activity;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.allen.brofinder.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private Location currentLocation;
    private Location destinationLocation;

    private static final String DESTINATION_LAT_PARAM = "destinationLat";
    private static final String DESTINATION_LON_PARAM = "destinationLon";

    private float destinationLat;
    private float destinationLon;

    public static MapFragment newInstance(String destinationLat, String destinationLon) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(DESTINATION_LAT_PARAM, destinationLat);
        args.putString(DESTINATION_LON_PARAM, destinationLon);
        fragment.setArguments(args);
        return fragment;
    }

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            destinationLat = Double.parseDouble(getArguments().getString(DESTINATION_LAT_PARAM));
            destinationLon = Double.parseDouble(getArguments().getString(DESTINATION_LON_PARAM));
        }

        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        // Mock destinationLocation
        destinationLocation = new Location("");
        destinationLocation.setLatitude(destinationLat);
        destinationLocation.setLongitude(destinationLon);
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        plotDestinationLocation(destinationLocation);
    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.disconnect();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onConnected(Bundle bundle) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(2000);
        locationRequest.setFastestInterval(2000);

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("GoogleClientApi", "Connection suspended");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("MapsActivity", "Location received: " + location.toString());
        this.currentLocation = location;
        googleApiClient.disconnect();
        plotCurrentLocationMarker(location);
        moveCamera(currentLocation, destinationLocation);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("GoogleClientApi", "Connection failed");
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            mMap.getUiSettings().setZoomControlsEnabled(true);
        }
    }

    private void plotCurrentLocationMarker(Location currentLocation) {
        LatLng latLong = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions marketOptions = new MarkerOptions()
                .position(latLong)
                .title("You");
        mMap.addMarker(marketOptions);
    }

    private void plotDestinationLocation(Location destinationLocation) {
        LatLng latLong = new LatLng(destinationLocation.getLatitude(), destinationLocation.getLongitude());
        MarkerOptions marketOptions = new MarkerOptions()
                .position(latLong)
                .title("Destination")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mMap.addMarker(marketOptions);
    }

    private void moveCamera(Location currentLocation, Location destinationLocation) {
        LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        LatLng destinationLatLng = new LatLng(destinationLocation.getLatitude(), destinationLocation.getLongitude());
        LatLngBounds bounds = LatLngBounds.builder()
                .include(currentLatLng)
                .include(destinationLatLng)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200));
    }
}
