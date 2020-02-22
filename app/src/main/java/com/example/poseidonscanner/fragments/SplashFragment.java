package com.example.poseidonscanner.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.poseidonscanner.MainActivity;
import com.example.poseidonscanner.R;
import com.example.poseidonscanner.activities.ScannerActivity;
import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment implements View.OnClickListener {
    MaterialButton scanButton;

    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        this.scanButton = view.findViewById(R.id.scan_btn);
        this.scanButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == this.scanButton.getId()) {
            Intent intent = new Intent(this.getActivity(), ScannerActivity.class);
            this.getActivity().startActivityForResult(intent, MainActivity.SCANNER_ACTIVITY);
        }
    }
}
