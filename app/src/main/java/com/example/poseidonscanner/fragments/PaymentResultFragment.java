package com.example.poseidonscanner.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.poseidonscanner.MainActivity;
import com.example.poseidonscanner.R;
import com.example.poseidonscanner.interfaces.Controller;
import com.example.poseidonscanner.model.HTTPResponse;
import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentResultFragment extends Fragment implements View.OnClickListener {
    RelativeLayout loader;

    ImageView resultImage;
    TextView resultHeader, errorReason;

    MaterialButton homeBtn;

    Controller controller;

    public PaymentResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_result, container, false);

        this.loader = view.findViewById(R.id.progress_loader);
        this.resultImage = view.findViewById(R.id.result_image);
        this.resultHeader = view.findViewById(R.id.result_header);
        this.errorReason = view.findViewById(R.id.error_reason);
        this.homeBtn = view.findViewById(R.id.home_btn);

        this.loader.setVisibility(View.VISIBLE);
        this.homeBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == this.homeBtn.getId()) {
            this.controller.changePage(MainActivity.SPLASH_PAGE);
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

    public void showPaymentResult(HTTPResponse response) {
        this.loader.setVisibility(View.GONE);

        System.out.println(response.isSuccess());

        if (response.isSuccess()) {
            this.resultImage.setImageDrawable(this.getContext().getDrawable(R.drawable.ic_check_circle_black_24dp));
            this.resultImage.setColorFilter(this.getContext().getColor(R.color.success));
            this.resultHeader.setText(this.getContext().getText(R.string.payment_success_label));
            this.errorReason.setText("");
        } else {
            this.resultImage.setImageDrawable(this.getContext().getDrawable(R.drawable.ic_error_black_24dp));
            this.resultImage.setColorFilter(this.getContext().getColor(R.color.danger));
            this.resultHeader.setText(this.getContext().getText(R.string.payment_error_label));
            this.errorReason.setText(response.getError());
        }
    }
}
