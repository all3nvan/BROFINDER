package com.example.allen.brofinder.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.allen.brofinder.R;
import com.example.allen.brofinder.domain.User;
import com.example.allen.brofinder.support.UserArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddFriendFragment extends Fragment {
    private static final String TAG = "AddFriendFragment";
    private ListView searchListView;

    public static AddFriendFragment newInstance() {
        return new AddFriendFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_friend, container, false);
        searchListView = (ListView) view.findViewById(R.id.search_friend_listview);
        List<User> userList = mockUserList();
        UserArrayAdapter adapter = new UserArrayAdapter(getActivity(), R.layout.listview_user_row, userList);
        searchListView.setAdapter(adapter);
        searchListView.setOnItemClickListener(new ListViewClickListener());
        return view;
    }

    private class ListViewClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long rowId) {
            Log.i(TAG, "CLICKED: " + position);
        }
    }

    private List<User> mockUserList() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("derp", "derp"));
        userList.add(new User("herp", "herp"));
        return userList;
    }
}
