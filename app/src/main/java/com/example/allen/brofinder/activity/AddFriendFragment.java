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

import com.example.allen.brofinder.R;
import com.example.allen.brofinder.domain.User;
import com.example.allen.brofinder.support.UserArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddFriendFragment extends Fragment {
    private static final String TAG = "AddFriendFragment";
    private ListView searchListView;
    private ViewSwitcher viewSwitcher;
    private EditText searchTextBox;

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
        searchTextBox = (EditText) view.findViewById(R.id.search_box);
        searchTextBox.addTextChangedListener(new TextWatcher() {
            boolean isOnDefaultView = true;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0 && isOnDefaultView) {
                    viewSwitcher.showNext();
                    isOnDefaultView = false;
                }
                if (s.length() == 0 && !isOnDefaultView) {
                    viewSwitcher.showNext();
                    isOnDefaultView = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
            //Start intent to send location here
        }
    }

    private List<User> mockUserList() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("derp", "derp"));
        userList.add(new User("herp", "herp"));
        return userList;
    }
}
