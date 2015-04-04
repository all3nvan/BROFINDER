package com.example.allen.brofinder.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ViewSwitcher;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.allen.brofinder.R;
import com.example.allen.brofinder.adapter.factory.UserFactory;
import com.example.allen.brofinder.domain.User;
import com.example.allen.brofinder.support.RestClient;
import com.example.allen.brofinder.support.UriBuilder;
import com.example.allen.brofinder.support.UserArrayAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddFriendFragment extends Fragment {
    private static final String TAG = "AddFriendFragment";
    private ListView searchListView;
    private ListView resultsListView;
    private ViewSwitcher viewSwitcher;
    private EditText searchTextBox;
    private UserArrayAdapter searchResultArrayAdapter;
    private List<User> searchResults;

    public static AddFriendFragment newInstance() {
        return new AddFriendFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_friend, container, false);
        viewSwitcher = (ViewSwitcher) view.findViewById(R.id.friend_search_viewswitcher);
        searchListView = (ListView) view.findViewById(R.id.search_friend_listview);
        resultsListView = (ListView) view.findViewById(R.id.results_listview);
        searchTextBox = (EditText) view.findViewById(R.id.search_box);
        searchTextBox.addTextChangedListener(new TextWatcher() {
            boolean isOnDefaultView = true;
            @Override
            public void beforeTextChanged(CharSequence searchInput, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence searchInput, int start, int before, int count) {
                if (searchInput.length() != 0 && isOnDefaultView) {
                    viewSwitcher.showNext();
                    isOnDefaultView = false;
                }
                if (searchInput.length() == 0 && !isOnDefaultView) {
                    viewSwitcher.showNext();
                    isOnDefaultView = true;
                }
                if (searchInput.length() != 0 && !isOnDefaultView) {
                    searchForUser(searchInput.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable searchInput) {

            }
        });
        List<User> recentUserList = mockUserList();
        UserArrayAdapter adapter = new UserArrayAdapter(getActivity(), R.layout.listview_user_row, recentUserList);
        searchListView.setAdapter(adapter);
        searchListView.setOnItemClickListener(new ListViewClickListener());
        return view;
    }

    private class ListViewClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long rowId) {
            Log.i(TAG, "CLICKED: " + position);
            //Start intent to send location here
        }
    }

    private List<User> mockUserList() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("derp", "derp"));
        userList.add(new User("herp", "herp"));
        return userList;
    }

    private void searchForUser(final String email) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("display_name", email);

        JSONObject jsonObject = new JSONObject(paramMap);
        JsonArrayRequest request
                = new JsonArrayRequest(Request.Method.POST, UriBuilder.generateSearchUserPath(), jsonObject, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i(TAG, "Successful volley response: " + response.toString());
                searchResults = UserFactory.createUserListFrom(response);
                searchResultArrayAdapter = new UserArrayAdapter(getActivity(), R.layout.listview_user_row, searchResults);
                resultsListView.setAdapter(searchResultArrayAdapter);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Searching for: " + email);
                Log.e(TAG, "Error during volley request: " + error.toString());
            }
        });
        RestClient.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
    }
}
