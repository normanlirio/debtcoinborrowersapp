package com.debtcoin.debtcoinapp.Util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.debtcoin.debtcoinapp.R;

import org.w3c.dom.Text;

/**
 * Created by Lirio on 6/10/2018.
 */

public class CustomProgressDialog extends ProgressDialog {
    private ImageView ivLoading;
    private TextView tvMessage;
    private AnimationDrawable animationDrawable;
    private String msg;
    private String subMessage;
    private TextView tvSubMessage;


    public  CustomProgressDialog(Context context, String message) {
        super(context);
        this.msg = message;
    }

    public  CustomProgressDialog(Context context, String message, String subMessage) {
        super(context);
        this.msg = message;
        this.subMessage = subMessage;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        ivLoading = findViewById(R.id.img_loading_loading);
        tvMessage = findViewById(R.id.text_loading_message);
        tvSubMessage = findViewById(R.id.text_loading_submessage);
        ivLoading.setBackgroundResource(R.drawable.loading);
        animationDrawable = (AnimationDrawable) ivLoading.getBackground();
        tvMessage.setText(msg);
        if(subMessage != null) {
            tvSubMessage.setText(subMessage);
        } else {
            tvSubMessage.setVisibility(View.GONE);
        }


    }

    @Override
    public void show() {
        super.show();
        animationDrawable.start();
    }
    @Override
    public void dismiss() {
        super.dismiss();
        animationDrawable.stop();
    }

    @Override
    public void setMessage(CharSequence message) {
        super.setMessage(message);

    }


}
