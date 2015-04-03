package com.example.allen.brofinder.adapter.factory;

import com.example.allen.brofinder.domain.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserFactory {

    public List<User> createUserListFrom(JSONArray jsonOfUsers) throws Exception {
        List<User> userList = new ArrayList<>();
        for (int counter = 0; counter < jsonOfUsers.length(); counter++) {
            JSONObject userObject = jsonOfUsers.getJSONObject(counter);
            String email = userObject.getString("email");
            String displayName = userObject.getString("displayName");
            userList.add(new User(displayName, email));
        }
        return userList;
    }
}
