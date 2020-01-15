package com.debtcoin.debtcoinapp.Activities;

import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.debtcoin.debtcoinapp.Fragments.ApplicantLimitFragment;
import com.debtcoin.debtcoinapp.Fragments.StepEight;
import com.debtcoin.debtcoinapp.Fragments.StepFive;
import com.debtcoin.debtcoinapp.Fragments.StepFour;
import com.debtcoin.debtcoinapp.Fragments.StepNine;
import com.debtcoin.debtcoinapp.Fragments.StepOne;
import com.debtcoin.debtcoinapp.Fragments.StepSeven;
import com.debtcoin.debtcoinapp.Fragments.StepSix;
import com.debtcoin.debtcoinapp.Fragments.StepTen;
import com.debtcoin.debtcoinapp.Fragments.StepThree;
import com.debtcoin.debtcoinapp.Fragments.StepTwo;
import com.debtcoin.debtcoinapp.Globals.Methods;
import com.debtcoin.debtcoinapp.R;

public class RegisterActivity extends AppCompatActivity implements ApplicantLimitFragment.OnFragmentInteractionListener,
        StepOne.OnFragmentInteractionListener, StepTwo.OnFragmentInteractionListener, StepThree.OnFragmentInteractionListener,
        StepFour.OnFragmentInteractionListener, StepFive.OnFragmentInteractionListener, StepSix.OnFragmentInteractionListener,
        StepSeven.OnFragmentInteractionListener, StepEight.OnFragmentInteractionListener, StepTen.OnFragmentInteractionListener,
        StepNine.OnFragmentInteractionListener
{

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bundle bundle = getIntent().getExtras();
        if(savedInstanceState == null) {
            if(bundle != null) {
                boolean hasExceeded = bundle.getBoolean("hasExceeded");
                if(hasExceeded) {
                    Methods.switchFragmentNoBackStack(RegisterActivity.this, new ApplicantLimitFragment());
                } else {
                    Methods.switchFragmentNoBackStack(RegisterActivity.this, new StepOne());
                }
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 4000);
    }
}
