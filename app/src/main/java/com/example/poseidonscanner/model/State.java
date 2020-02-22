package com.example.poseidonscanner.model;

import android.graphics.Bitmap;

public class State {
    Bitmap bmp;
    String qrString;

    public State() {
        this.qrString = "";
        this.bmp = null;
    }

    public void setQRResult(String str) {
        this.qrString = str;
    }

    public void setQRPicture(Bitmap bmp) {
        this.bmp = bmp;
    }

    public String getQRResult() {
        return this.qrString;
    }

    public Bitmap getQRPicture() {
        return this.bmp;
    }

    public boolean isEmpty() {
        return this.bmp == null && this.qrString.isEmpty();
    }

    public void emptyState() {
        this.qrString = "";
        this.bmp = null;
    }
}
