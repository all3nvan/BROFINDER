package com.example.allen.brofinder;

import com.example.allen.brofinder.support.UriBuilder;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Test;

public class UriBuilderTest {

    @Test
    public void shouldGenerateRegisterUri() {
        String expectedUri = "http://10.0.3.2:8080/register";
        String actualUri = UriBuilder.generateRegisterPath();
        assertThat(actualUri, is(equalTo(expectedUri)));
    }

    @Test
    public void shouldGenerateFindFriendsUri() {
        String expectedUri = "http://10.0.3.2:8080/friends/bryant.ubuntu@gmail.com";
        String actualUri = UriBuilder.generateFindFriendsPath("bryant.ubuntu@gmail.com");
        assertThat(actualUri, is(equalTo(expectedUri)));
    }
}
