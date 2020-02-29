package com.example.poseidonscanner.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.poseidonscanner.MainActivity;
import com.example.poseidonscanner.R;
import com.example.poseidonscanner.interfaces.Controller;
import com.example.poseidonscanner.model.PoseidonRequest;
import com.example.poseidonscanner.utils.HTTPRequest;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class PasscodeFragment extends Fragment implements View.OnClickListener {
    MaterialButton backButton, confirmButton;

    EditText pin;

    Controller controller;

    public PasscodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_passcode, container, false);

        this.pin = view.findViewById(R.id.pin_code);
        this.backButton = view.findViewById(R.id.pin_back_btn);
        this.confirmButton = view.findViewById(R.id.pin_confirm_btn);

        this.backButton.setOnClickListener(this);
        this.confirmButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == this.backButton.getId()) {
            this.getActivity().onBackPressed();
        } else if (id == this.confirmButton.getId()) {
            if (this.pin.getText().toString().length() < 6) {
                Context context = this.getContext();
                CharSequence text = "Please input 6 digit PIN";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            } else {
                try {
                    this.sendRequest();
                } catch (JSONException e) {

                }
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Controller) {
            this.controller = (Controller)context;
        } else {
            throw new ClassCastException("Must implement Controller");
        }
    }

    private void sendRequest() throws JSONException {
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer proif-kelompok-2");

        PoseidonRequest request = new PoseidonRequest(this.controller.getCurrentState().getQRResult(), this.pin.getText().toString());

        HTTPRequest.getInstance(this.getActivity()).sendRequest("http://localhost:8080", header, request.getJSONObject());
        this.controller.changePage(MainActivity.PAYMENT_RESULT_PAGE);
    }
}
