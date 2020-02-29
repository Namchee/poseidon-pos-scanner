package com.example.poseidonscanner.model;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class PoseidonRequest {
    private String payload;
    private String pin;

    public PoseidonRequest(String payload, String pin) {
        this.payload = payload;
        this.pin = pin;
    }

    public String getJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public JSONObject getJSONObject() throws JSONException {
        JSONObject obj = new JSONObject();

        obj.put("payload", this.payload);
        obj.put("pin", this.pin);

        return obj;
    }
}
