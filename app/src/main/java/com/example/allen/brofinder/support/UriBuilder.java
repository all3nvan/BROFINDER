package com.example.allen.brofinder.support;

import android.net.Uri;

public final class UriBuilder {
    private static final String SCHEME = "http";
    private static final String AUTHORITY = "104.131.112.87:3000";

    private static final String REGISTER_PATH = "users";
    private static final String FRIEND_PATH = "friends";

    public static String generateRegisterPath() {
        return generateBaseUri()
                .appendPath(REGISTER_PATH)
                .build()
                .toString();
    }

    public static String generateFindFriendsPath(String userEmail) {
        return generateBaseUri()
                .appendPath(FRIEND_PATH)
                .appendEncodedPath(userEmail)
                .build()
                .toString();
    }

    private static Uri.Builder generateBaseUri() {
        return new Uri.Builder()
                .scheme(SCHEME)
                .encodedAuthority(AUTHORITY);
    }
}
