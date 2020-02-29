package com.example.poseidonscanner.model;

public class HTTPResponse {
    private boolean data;
    private String error;

    public HTTPResponse(boolean data, String error) {
        this.data = data;
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public boolean isSuccess() {
        return data && this.error.isEmpty();
    }
}
