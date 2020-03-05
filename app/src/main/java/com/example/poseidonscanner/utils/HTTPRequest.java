package com.example.poseidonscanner.utils;

import android.app.Activity;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.poseidonscanner.MainActivity;
import com.example.poseidonscanner.model.HTTPResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class HTTPRequest {
    private MainActivity activity;
    private static HTTPRequest instance;
    private RequestQueue queue;

    private HTTPRequest(Activity activity) {
        this.activity = (MainActivity)activity;
        this.queue = Volley.newRequestQueue(activity.getApplicationContext());
    }

    public static HTTPRequest getInstance(Activity activity) {
        if (HTTPRequest.instance == null) {
            instance = new HTTPRequest(activity);
        }

        return instance;
    }

    public void sendRequest(String url, Map<String, String> headers, JSONObject body) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                body,
                (JSONObject response) -> {
                    try {
                        boolean data = response.getBoolean("data");
                        String error = response.getString("error");

                        HTTPResponse resp = new HTTPResponse(data, error);

                        this.activity.notifyPaymentResult(resp);
                    } catch (JSONException e) {
                        System.err.println(e.getMessage());
                    }
                },
                (VolleyError err) -> {
                    if (err.networkResponse.statusCode == 500) {
                        HTTPResponse resp = new HTTPResponse(false, "Cannot connect to POI APP");
                        this.activity.notifyPaymentResult(resp);

                    } else {
                        HTTPResponse resp = new HTTPResponse(false, err.networkResponse.data.toString());
                        this.activity.notifyPaymentResult(resp);
                    }
                }
            ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };

        this.queue.add(request);
    }
}
