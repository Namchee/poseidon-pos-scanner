package com.example.poseidonscanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.example.poseidonscanner.fragments.PasscodeFragment;
import com.example.poseidonscanner.fragments.PaymentResultFragment;
import com.example.poseidonscanner.fragments.ScanResultFragment;
import com.example.poseidonscanner.fragments.SplashFragment;
import com.example.poseidonscanner.interfaces.Controller;
import com.example.poseidonscanner.model.HTTPResponse;
import com.example.poseidonscanner.model.State;

public class MainActivity extends AppCompatActivity implements Controller {
    public static final int RESULT_OK = 1;

    public static final int SPLASH_PAGE = 10;
    public static final int SCAN_RESULT_PAGE = 11;
    public static final int PIN_PAGE = 12;
    public static final int PAYMENT_RESULT_PAGE = 13;

    public static final int SCANNER_ACTIVITY = 20;

    public final State appState = new State();

    SplashFragment splashFragment;
    ScanResultFragment scanResultFragment;
    PasscodeFragment passcodeFragment;
    PaymentResultFragment paymentResultFragment;

    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.fm = this.getSupportFragmentManager();
        this.initializeFragments();

        this.changePage(SPLASH_PAGE);
    }

    @Override
    public void changePage(int code) {
        FragmentTransaction ft = this.fm.beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

        switch (code) {
            case SPLASH_PAGE: {
                ft.replace(R.id.fragment_container, this.splashFragment);
                break;
            }
            case SCAN_RESULT_PAGE: {
                ft.replace(R.id.fragment_container, this.scanResultFragment);
                break;
            }
            case PIN_PAGE: {
                ft.replace(R.id.fragment_container, this.passcodeFragment);
                break;
            }
            case PAYMENT_RESULT_PAGE: {
                ft.replace(R.id.fragment_container, this.paymentResultFragment);
                break;
            }
        }

        ft.addToBackStack(null);

        ft.commitAllowingStateLoss();
    }

    private void initializeFragments() {
        FragmentTransaction ft = this.fm.beginTransaction();

        this.splashFragment = new SplashFragment();
        this.scanResultFragment = new ScanResultFragment();
        this.passcodeFragment = new PasscodeFragment();
        this.paymentResultFragment = new PaymentResultFragment();

        ft.add(R.id.fragment_container, this.splashFragment);
        ft.add(R.id.fragment_container, this.scanResultFragment);
        ft.add(R.id.fragment_container, this.passcodeFragment);
        ft.add(R.id.fragment_container, this.paymentResultFragment);

        ft.replace(R.id.fragment_container, this.splashFragment);

        ft.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == SCANNER_ACTIVITY && resultCode == RESULT_OK) {
            String imagePath = data.getStringExtra("imagePath");
            String contents = data.getStringExtra("contents");

            if (imagePath != null && contents != null) {
                Bitmap image = BitmapFactory.decodeFile(imagePath);

                this.appState.setQRPicture(image);
                this.appState.setQRResult(contents);

                this.changePage(SCAN_RESULT_PAGE);
            }
        }
    }

    public void notifyPaymentResult(HTTPResponse paymentResult) {
        this.paymentResultFragment.showPaymentResult(paymentResult);
    }

    @Override
    public State getCurrentState() {
        return this.appState;
    }
}
