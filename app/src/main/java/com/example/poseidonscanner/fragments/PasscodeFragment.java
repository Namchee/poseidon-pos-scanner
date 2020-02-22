package com.example.poseidonscanner.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.poseidonscanner.R;
import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class PasscodeFragment extends Fragment implements View.OnClickListener {
    MaterialButton backButton, confirmButton;

    public PasscodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_passcode, container, false);
        this.backButton = view.findViewById(R.id.pin_back_btn);
        this.confirmButton = view.findViewById(R.id.pin_confirm_btn);

        this.backButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == this.backButton.getId()) {
            this.getActivity().onBackPressed();
        }
    }
}
