package com.example.poseidonscanner.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.poseidonscanner.MainActivity;
import com.example.poseidonscanner.R;
import com.example.poseidonscanner.activities.ScannerActivity;
import com.example.poseidonscanner.interfaces.Controller;
import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScanResultFragment extends Fragment implements View.OnClickListener {
    ImageView qrCodeView;
    MaterialButton confirmButton, rescanButton, backButton;

    Controller appController;

    public ScanResultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scan_result, container, false);

        this.qrCodeView = view.findViewById(R.id.qrcode_view);
        this.confirmButton = view.findViewById(R.id.qr_confirm_button);
        this.rescanButton = view.findViewById(R.id.rescan_button);
        this.backButton = view.findViewById(R.id.scan_back_btn);

        this.rescanButton.setOnClickListener(this);
        this.confirmButton.setOnClickListener(this);
        this.backButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == this.rescanButton.getId()) {
            Intent intent = new Intent(this.getActivity(), ScannerActivity.class);
            this.getActivity().startActivityForResult(intent, MainActivity.SCANNER_ACTIVITY);
        } else if (id == this.confirmButton.getId()) {
            this.appController.changePage(MainActivity.PIN_PAGE);
        } else if (id == this.backButton.getId()) {
            this.getActivity().onBackPressed();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Controller) {
            this.appController = (Controller)context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!this.appController.getCurrentState().isEmpty()) {
            this.qrCodeView.setImageBitmap(this.appController.getCurrentState().getQRPicture());
            this.qrCodeView.invalidate();
        }
    }
}
