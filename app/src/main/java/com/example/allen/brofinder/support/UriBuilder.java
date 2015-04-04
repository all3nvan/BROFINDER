package com.example.allen.brofinder.support;

import android.net.Uri;

public final class UriBuilder {
    private static final String SCHEME = "http";
    private static final String AUTHORITY = "104.131.112.87:3000";

    private static final String REGISTER_PATH = "users";
    private static final String SEARCH_PATH = "search";
    private static final String LOCATION_SESSION_PATH = "locations";
    private static final String LOCATION_PATH = "location";

    public static String generateRegisterPath() {
        return generateBaseUri()
                .appendPath(REGISTER_PATH)
                .build()
                .toString();
    }

    public static String generateSearchUserPath() {
        return generateBaseUri()
                .appendPath(REGISTER_PATH)
                .appendPath(SEARCH_PATH)
                .build()
                .toString();
    }

    public static String generateLocationSessionsPath() {
        return generateBaseUri()
                .appendPath(LOCATION_PATH)
                .appendEncodedPath("history")
                .build()
                .toString();
    }

    public static String generateLocationPath() {
        return generateBaseUri()
                .appendPath(LOCATION_PATH)
                .build()
                .toString();
    }

    private static Uri.Builder generateBaseUri() {
        return new Uri.Builder()
                .scheme(SCHEME)
                .encodedAuthority(AUTHORITY);
    }


}
