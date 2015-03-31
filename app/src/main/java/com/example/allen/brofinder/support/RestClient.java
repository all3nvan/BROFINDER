package com.example.allen.brofinder.support;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RestClient {
    private static RestClient restClient;
    private RequestQueue requestQueue;
    private static Context context;

    private RestClient(Context context) {
        this.context = context;
    }

    public static synchronized RestClient getInstance(Context context) {
        if (restClient == null) {
            restClient = new RestClient(context);
        }

        return restClient;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
