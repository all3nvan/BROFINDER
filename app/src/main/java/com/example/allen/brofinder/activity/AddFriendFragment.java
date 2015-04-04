package com.example.allen.brofinder.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.allen.brofinder.R;
import com.example.allen.brofinder.domain.User;
import com.example.allen.brofinder.support.UserArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddFriendFragment extends Fragment {
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
        return view;
    }

    private List<User> mockUserList() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("derp", "derp"));
        userList.add(new User("herp", "herp"));
        return userList;
    }
}
