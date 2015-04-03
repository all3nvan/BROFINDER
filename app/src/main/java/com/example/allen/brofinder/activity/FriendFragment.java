package com.example.allen.brofinder.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.allen.brofinder.R;
import com.example.allen.brofinder.adapter.factory.UserFactory;
import com.example.allen.brofinder.support.Constants;
import com.example.allen.brofinder.support.RestClient;
import com.example.allen.brofinder.support.UriBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FriendFragment extends Fragment {
    private final static String TAG = "FriendFragment";
    private ListView friendListView;
    private UserFactory userFactory;

    public static FriendFragment newInstance() {
        FriendFragment friendFragment = new FriendFragment();
        return friendFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userFactory = new UserFactory();
        retrieveFriends(getAccountEmail());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        friendListView = (ListView) view.findViewById(R.id.friend_listview);
        return view;
    }

    private void retrieveFriends(String userEmail) {
        JsonArrayRequest request
                = new JsonArrayRequest(Request.Method.GET, UriBuilder.generateFindFriendsPath(userEmail), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i(TAG, "Successful volley response: " + response.toString());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error during volley request: " + error.toString());
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
}
