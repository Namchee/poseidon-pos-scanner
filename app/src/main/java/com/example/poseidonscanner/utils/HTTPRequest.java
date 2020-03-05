package com.example.poseidonscanner.utils;

import android.app.Activity;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
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

                        HTTPResponse resp = new HTTPResponse(data, null);

                        this.activity.notifyPaymentResult(resp);
                    } catch (JSONException e) {
                        System.err.println(e.getMessage());
                    }
                },
                (VolleyError err) -> {
                    try {
                        NetworkResponse networkResponse = err.networkResponse;

                        if (networkResponse != null && networkResponse.data != null) {
                            String respString = new String(networkResponse.data);

                            JSONObject obj = new JSONObject(respString);

                            HTTPResponse httpResponse = new HTTPResponse(false, obj.getString("error"));

                            this.activity.notifyPaymentResult(httpResponse);
                        } else {
                            HTTPResponse httpResponse = new HTTPResponse(false, "Cannot connect to POI App");

                            this.activity.notifyPaymentResult(httpResponse);
                        }
                    } catch (JSONException e) {
                        System.err.println(e);
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
