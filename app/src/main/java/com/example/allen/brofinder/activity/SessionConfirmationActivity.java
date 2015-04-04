package com.example.allen.brofinder.activity;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.allen.brofinder.R;
import com.example.allen.brofinder.domain.LocationSession;
import com.example.allen.brofinder.support.RestClient;
import com.example.allen.brofinder.support.SessionCache;
import com.example.allen.brofinder.support.UriBuilder;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SessionConfirmationActivity extends FragmentActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleApiClient mGoogleApiClient;

    private String senderEmail;
    private String receiverEmail;
    private Location currentLocation;
    private String receiverDisplayName;
    private SessionCache sessionCache;

    private static final String TAG = "SessionConfirmActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_confirmation);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        sessionCache = new SessionCache(this);
        senderEmail = getIntent().getExtras().getString("senderEmail");
        receiverEmail = getIntent().getExtras().getString("receiverEmail");
        receiverDisplayName = getIntent().getExtras().getString("receiverDisplayName");

        final TextView textView = (TextView) findViewById(R.id.confirmation_recipient_name);
        textView.setText("Location will be sent to " + receiverDisplayName);

        final Button button = (Button) findViewById(R.id.session_confirmation_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Send lat and long to backend here
                final String latitude = ((Double)currentLocation.getLatitude()).toString();
                final String longitude = ((Double)currentLocation.getLongitude()).toString();
                Map<String, String> paramMap = new HashMap<>();
                paramMap.put("account_name", senderEmail);
                paramMap.put("receiver_name", receiverEmail);
                paramMap.put("latitude", latitude);
                paramMap.put("longitude", longitude);

                JSONObject jsonObject = new JSONObject(paramMap);
                JsonArrayRequest request =
                        new JsonArrayRequest(Request.Method.POST, UriBuilder.generateLocationPath(), jsonObject, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Log.i(TAG, "WOOOO successfully sent location");
                                LocationSession locationSession = new LocationSession(Float.parseFloat(latitude), Float.parseFloat(longitude), receiverEmail);
                                sessionCache.saveSession(locationSession);
                            }

                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i(TAG, "no location sent");
                            }
                        });

                RestClient.getInstance(getApplicationContext()).addToRequestQueue(request);
            }
        });
    }

    @Override
    public void onConnected(Bundle bundle) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(2000);
        locationRequest.setFastestInterval(2000);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "Location changed: " + location.toString());

        this.currentLocation = location;
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed");
    }

    @Override
    protected void onResume(){
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }

}
