package com.example.allen.brofinder.support;

import android.net.Uri;

public final class UriBuilder {
    private static final String SCHEME = "http";
    private static final String AUTHORITY = "10.0.3.2:8080";

    private static final String REGISTER_PATH = "register";
    private static final String FRIEND_PATH = "friends";

    public static String generateRegisterPath() {
        return new Uri.Builder()
                .scheme(SCHEME)
                .encodedAuthority(AUTHORITY)
                .appendPath(REGISTER_PATH)
                .build()
                .toString();
    }

    public static String generateFindFriendsPath(String userEmail) {
        return new Uri.Builder()
                .scheme(SCHEME)
                .encodedAuthority(AUTHORITY)
                .appendPath(FRIEND_PATH)
                .appendEncodedPath(userEmail)
                .build()
                .toString();
    }
}
