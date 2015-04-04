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
import com.example.allen.brofinder.domain.User;
import com.example.allen.brofinder.support.Constants;
import com.example.allen.brofinder.support.RestClient;
import com.example.allen.brofinder.support.UriBuilder;
import com.example.allen.brofinder.support.UserArrayAdapter;
import com.example.allen.brofinder.support.UserItemArrayAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

public class FriendFragment extends Fragment {
    private final static String TAG = "FriendFragment";
    private ListView friendListView;
    private List<User> friendList;

    public static FriendFragment newInstance() {
        FriendFragment friendFragment = new FriendFragment();
        return friendFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retrieveFriends(getAccountEmail());
    }

    //test method to return friends
    private List<User> testRetrieveFriends(){
        List<User> userList = new ArrayList<User>();

        for(int i = 0; i <100; i++)
            userList.add(new User("bryant", "test_email@test.com"));


        return userList;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        friendListView = (ListView) view.findViewById(R.id.friend_listview);
        return view;
    }

    private void retrieveFriends(String userEmail) {
        Map<String, String> requestBodyMap = new HashMap<String, String>();
        requestBodyMap.put("account_name", userEmail);
        JSONObject jsonRequest = new JSONObject(requestBodyMap);
        JsonArrayRequest request
                = new JsonArrayRequest(Request.Method.POST, UriBuilder.generateFindFriendsPath(), jsonRequest, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i(TAG, "Successful volley response: " + response.toString());
                friendList = UserFactory.createUserListFrom(response);
                UserArrayAdapter adapter = new UserArrayAdapter(getActivity(), R.layout.listview_user_row, friendList);
                friendListView.setAdapter(adapter);
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
