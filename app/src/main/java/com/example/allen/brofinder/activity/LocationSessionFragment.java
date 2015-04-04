package com.example.allen.brofinder.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.allen.brofinder.R;
import com.example.allen.brofinder.adapter.factory.LocationSessionFactory;
import com.example.allen.brofinder.domain.LocationSession;
import com.example.allen.brofinder.support.Constants;
import com.example.allen.brofinder.support.LocationSessionItemArrayAdapter;
import com.example.allen.brofinder.support.RestClient;
import com.example.allen.brofinder.support.UriBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationSessionFragment extends Fragment {
    private final static String TAG = "LocationSessionFragment";
    private ListView locationSessionView;
    private List<LocationSession> locationSessionList;

    public static LocationSessionFragment newInstance(){
        LocationSessionFragment locationSessionFragment = new LocationSessionFragment();
        return locationSessionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        retrieveLocationSessions(getAccountEmail());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.d(TAG, "In onCreateView");
        View view = inflater.inflate(R.layout.fragment_location_session, container, false);
        Log.d(TAG, "finished onCreateView");
        return view;

    }




    private void retrieveLocationSessions(String userEmail){
        Map<String, String> requestBodyMap = new HashMap<>();
        requestBodyMap.put("account_name", userEmail);
        JSONObject jsonRequest = new JSONObject(requestBodyMap);
        JsonArrayRequest request
                = new JsonArrayRequest(Request.Method.POST, UriBuilder.generateLocationSessionsPath(), jsonRequest, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i(TAG, "Successful volley response: " + response.toString());
                locationSessionList = LocationSessionFactory.createLocationSessionListFrom(response);
                LocationSessionItemArrayAdapter adapter = new LocationSessionItemArrayAdapter(getActivity(), R.layout.listview_location_session_row, locationSessionList);
                locationSessionView.setAdapter(adapter);
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.e(TAG, "Error during volley request:" + error.toString());
            }
        });

        RestClient.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
    }



    private SharedPreferences getGCMPreferences() {
        return getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private String getAccountEmail() {
        return getGCMPreferences().getString(Constants.SHARED_PREFERENCES_PROPERTY_ACCOUNT_EMAIL, "");
    }

    private List<LocationSession> testRetrieveLocationSession(){
        List<LocationSession> locationSessionList = new ArrayList<LocationSession>();

        for(int i = 0; i < 100; i++)
            locationSessionList.add(new LocationSession(1.40f, 1.23f, "bryant"));

        return locationSessionList;

    }
}


