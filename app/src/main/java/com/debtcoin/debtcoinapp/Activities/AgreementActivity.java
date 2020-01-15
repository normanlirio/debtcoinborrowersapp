package com.debtcoin.debtcoinapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.debtcoin.debtcoinapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AgreementActivity extends AppCompatActivity {
    @BindView(R.id.radiobtn_left)
    RadioButton rbTnc;
    @BindView(R.id.radiobtn_right)
    RadioButton rbPrivacy;
    @BindView(R.id.button_next)
    Button btnAccept;
    @BindView(R.id.text_agreement)
    TextView tvAgreement;
    @BindView(R.id.rdogrp_sndmsg)
    RadioGroup rgAgreement;
    private String tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        ButterKnife.bind(this);
        Bundle intent = getIntent().getExtras();
        if(intent != null) {
            tab = intent.getString("tab");
            Log.v("TAB", tab);
        }
        rbTnc.setText("Terms and Conditions");
        rbPrivacy.setText("Privacy Policy");

        btnAccept.setText("Accept");

        Spanned tnc = Html.fromHtml( getResources().getString(R.string.terms_and_conditions));
        Spanned privacy = Html.fromHtml( getResources().getString(R.string.privacy_policy));

        if(tab.equalsIgnoreCase("tnc")) {
            Log.v("TAB", tab);
            tvAgreement.setText(tnc);
            rbPrivacy.setChecked(false);
            rbTnc.setChecked(true);
        } else  {
            Log.v("TAB", tab);
            rbPrivacy.setChecked(true);
            rbTnc.setChecked(false);
            tvAgreement.setText(privacy);
        }
        rgAgreement.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radiobtn_left :
                        tvAgreement.setText(tnc);
                        break;
                    case R.id.radiobtn_right :
                        tvAgreement.setText(privacy);
                        break;

                }
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
