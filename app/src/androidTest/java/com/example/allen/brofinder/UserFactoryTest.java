package com.example.allen.brofinder;

import com.example.allen.brofinder.adapter.factory.UserFactory;
import com.example.allen.brofinder.domain.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

public class UserFactoryTest {
    private UserFactory userFactory;
    private Gson gson = new Gson();

    @Before
    public void setUp() {
        userFactory = new UserFactory();
    }

    @Test
    public void shouldCreateListOfUsersFromJson() throws Throwable {
        List<User> expectedUsersToConvertToJson = new ArrayList<>();
        User user1 = new User("herpie derp", "derp@gmail.com");
        User user2 = new User("slurpy herp", "herp@gmail.com");
        expectedUsersToConvertToJson.add(user1);
        expectedUsersToConvertToJson.add(user2);
        String expectedUsersJsonString = gson.toJson(expectedUsersToConvertToJson);
        JSONArray jsonOfUsers = new JSONArray(expectedUsersJsonString);

        List<User> createdUserList = userFactory.createUserListFrom(jsonOfUsers);

        assertThat(createdUserList.get(0).getDisplayName(), is(equalTo(user1.getDisplayName())));
        assertThat(createdUserList.get(0).getEmail(), is(equalTo(user1.getEmail())));
        assertThat(createdUserList.get(1).getDisplayName(), is(equalTo(user2.getDisplayName())));
        assertThat(createdUserList.get(1).getEmail(), is(equalTo(user2.getEmail())));
        assertEquals(createdUserList.size(), 2);
    }

    @Test
    public void shouldReturnEmptyListIfUserJsonIsEmpty() throws Throwable {
        List<User> expectedUsersToConvertToJson = new ArrayList<>();
        String expectedUsersJsonString = gson.toJson(expectedUsersToConvertToJson);
        JSONArray jsonOfUsers = new JSONArray(expectedUsersJsonString);

        List<User> createdUserList = userFactory.createUserListFrom(jsonOfUsers);

        assertEquals(createdUserList.size(), 0);
    }
}
