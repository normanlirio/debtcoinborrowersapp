package com.debtcoin.debtcoinapp.Activities;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.debtcoin.debtcoinapp.Fragments.ForgotPassword;
import com.debtcoin.debtcoinapp.Fragments.ForgotVerification;
import com.debtcoin.debtcoinapp.Fragments.NewPassword;
import com.debtcoin.debtcoinapp.R;

public class ForgotPasswordActivity extends AppCompatActivity implements  ForgotPassword.OnFragmentInteractionListener,
        ForgotVerification.OnFragmentInteractionListener, NewPassword.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        if(savedInstanceState == null) {
            Fragment fragment = new ForgotPassword();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_login_fragmentcontainer, fragment);
            ft.commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
