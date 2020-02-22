package com.example.poseidonscanner.interfaces;

import com.example.poseidonscanner.model.State;

public interface Controller {
    void changePage(int code);
    State getCurrentState();
}
