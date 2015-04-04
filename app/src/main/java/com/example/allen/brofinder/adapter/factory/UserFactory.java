package com.example.allen.brofinder.adapter.factory;

import android.util.Log;

import com.example.allen.brofinder.domain.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserFactory {

    public static List<User> createUserListFrom(JSONArray jsonOfUsers)  {
        List<User> userList = new ArrayList<>();
        for (int counter = 0; counter < jsonOfUsers.length(); counter++) {
            try {
                JSONObject userObject = jsonOfUsers.getJSONObject(counter);
                String email = userObject.getString("account_name");
                String displayName = userObject.getString("display_name");
                userList.add(new User(displayName, email));
            }
            catch (Exception e){
                Log.e("UserFactory", e.toString());
            }
        }
        return userList;
    }
}
