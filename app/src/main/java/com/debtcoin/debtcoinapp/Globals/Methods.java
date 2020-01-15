package com.debtcoin.debtcoinapp.Globals;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.debtcoin.debtcoinapp.API.model.LoanApplication;
import com.debtcoin.debtcoinapp.API.model.LoanDetails;
import com.debtcoin.debtcoinapp.Activities.LoginActivity;
import com.debtcoin.debtcoinapp.Activities.MainActivity;
import com.debtcoin.debtcoinapp.Database.DBPullProcess;
import com.debtcoin.debtcoinapp.Fragments.Home;
import com.debtcoin.debtcoinapp.Fragments.StepOne;
import com.debtcoin.debtcoinapp.R;
import com.debtcoin.debtcoinapp.Util.CustomProgressDialog;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

/**
 * Created by Lirio on 4/28/2018.
 */

public class Methods {
    //Switch to new fragment
    public static void switchFragment(Activity activity, Fragment fragment) {
        FragmentTransaction ft = ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_login_fragmentcontainer, fragment).addToBackStack(null);
        ft.commit();

    }
    public static void switchFragmentNoBackStack(Activity activity, Fragment fragment) {
        FragmentTransaction ft = ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_login_fragmentcontainer, fragment);
        ft.commit();

    }
    public static boolean isEmpty(EditText editText) {

        String input = editText.getText().toString().trim();
        return input.length() == 0;

    }
    public static void switchFragmentLoggedIn(Activity activity, Fragment fragment) {
        FragmentTransaction ft = ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_login_fragmentcontainer, fragment);
        ft.commit();

    }
    public static void switchFragmentLoggedInWithBackStack(Activity activity, Fragment fragment) {
        FragmentTransaction ft = ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_login_fragmentcontainer, fragment).addToBackStack(null);
        ft.commit();

    }

    public static void setupUI(final Activity activity, View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(activity);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(activity, innerView);
            }
        }
    }

    public static void showLog(Context activity, String tag, String message) {
        Log.v(tag, message);
    }

    public static void goBack(final Activity activity) {
        ((AppCompatActivity)activity).getSupportFragmentManager().popBackStack();
    }

    public static void showPopup(Activity activity, String header, String message, String okBtn, String cncelBtn, Bitmap icon) {
        LayoutInflater inflate = LayoutInflater.from(activity);
        View promptsView = inflate.inflate(R.layout.error_popup, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setView(promptsView);
        alertDialog.setCancelable(false);
        final AlertDialog alert2 = alertDialog.create();
        alert2.show();
        TextView tvHeader = promptsView.findViewById(R.id.text_popup_header);
        TextView tvMsg = promptsView.findViewById(R.id.text_popup_message);
        TextView tvCancel = promptsView.findViewById(R.id.text_popup_cancel);
        Button btnOk = promptsView.findViewById(R.id.button_popup_ok);
        ImageView ivIcon = promptsView.findViewById(R.id.img_popup_icon);
        tvHeader.setText(header);
        tvMsg.setText(message);
        tvCancel.setText(cncelBtn);
        btnOk.setText(okBtn);
        ivIcon.setImageBitmap(icon);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert2.dismiss();
            }
        });

    }

    public static void bankPopUp(Context activity, HashMap<String, String> map) {
        LayoutInflater inflate = LayoutInflater.from(activity);
        View promptsView = inflate.inflate(R.layout.bank_details_popup, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setView(promptsView);
        alertDialog.setCancelable(false);
        final AlertDialog alert2 = alertDialog.create();
        alert2.show();
        Button btnClose = promptsView.findViewById(R.id.button_bankpopup_close);
        TextView tvBank = promptsView.findViewById(R.id.text_bankpopup_bank);
        TextView tvAccountName = promptsView.findViewById(R.id.text_bankpopup_accntname);
        TextView tvBankAccountLabel = promptsView.findViewById(R.id.text_bankpopup_bankaccount_num_label);
        TextView tvBankAccountNum = promptsView.findViewById(R.id.text_bankpopup_bankaccount_num);
        TextView tvMobileNumberLabel = promptsView.findViewById(R.id.text_bankpopup_mobilenumber_label);
        TextView tvMobileNumber = promptsView.findViewById(R.id.text_bankpopup_mobilenumber);
        TextView tvAccountNameLabel = promptsView.findViewById(R.id.text_bankpopup_accntname_label);
        LinearLayout midContainer = promptsView.findViewById(R.id.linear_bankpopup_middlecontainer);
        LinearLayout lastContainer = promptsView.findViewById(R.id.linear_bankpopup_lastcontainer);
        if(map.get("bank").equalsIgnoreCase("coins")) {
            lastContainer.setOrientation(LinearLayout.VERTICAL);
        }
        if(!map.get("bank").equalsIgnoreCase("villa") && !map.get("bank").equalsIgnoreCase("pala") && !map.get("bank").equalsIgnoreCase("ml") && !map.get("bank").equalsIgnoreCase("ceb")) {
            Methods.showLog(activity, "BANK POP UP", "NOT " + map.get("bank"));
            midContainer.setVisibility(View.GONE);
            tvBank.setText(map.get("BankName"));
            tvBankAccountLabel.setText(map.get("AccountLabel"));
            tvBankAccountNum.setText(map.get("BankAccountNum"));
            tvAccountNameLabel.setText(map.get("AccountNameLabel"));
            tvAccountName.setText(map.get("AccountName"));
        } else {
            Methods.showLog(activity, "BANK POP UP", map.get("bank"));
            midContainer.setVisibility(View.VISIBLE);
            tvBank.setText(map.get("BankName"));
            tvBankAccountLabel.setText(map.get("AccountLabel"));
            tvBankAccountNum.setText(map.get("BankAccountNum"));
            tvAccountNameLabel.setText(map.get("AccountNameLabel"));
            tvAccountName.setText(map.get("AccountName"));
            tvMobileNumberLabel.setText(map.get("MobileNumberLabel"));
            tvMobileNumber.setText(map.get("MobileNumber"));
        }
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert2.dismiss();
            }
        });

    }

    public static void showPopup(Activity activity, String header, String message, String okBtn) {
        LayoutInflater inflate = LayoutInflater.from(activity);
        View promptsView = inflate.inflate(R.layout.error_popup, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setView(promptsView);
        alertDialog.setCancelable(false);
        final AlertDialog alert2 = alertDialog.create();
        alert2.show();
        TextView tvHeader = promptsView.findViewById(R.id.text_popup_header);
        TextView tvMsg = promptsView.findViewById(R.id.text_popup_message);
        TextView tvCancel = promptsView.findViewById(R.id.text_popup_cancel);
        Button btnOk = promptsView.findViewById(R.id.button_popup_ok);
        ImageView ivIcon = promptsView.findViewById(R.id.img_popup_icon);
        tvHeader.setText(header);
        tvMsg.setText(message);
        tvCancel.setVisibility(View.GONE);
        btnOk.setText(okBtn);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert2.dismiss();
            }
        });


    }


    public static void showNotif(Activity activity, String bookingid, String borrowername, String amountdue, String duedate, String creditOfficer) {
        LayoutInflater inflate = LayoutInflater.from(activity);
        View promptsView = inflate.inflate(R.layout.notif_popup, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setView(promptsView);
        alertDialog.setCancelable(false);
        final AlertDialog alert2 = alertDialog.create();
        alert2.show();
        TextView tvBookingId = promptsView.findViewById(R.id.text_notif_popup_msg_bookingid);
        TextView tvBorrowerName = promptsView.findViewById(R.id.text_notif_popup_msg_borrower);
        TextView tvCreditOfficerName = promptsView.findViewById(R.id.text_notif_popup_msg_creditofficer);
        TextView tvMsg = promptsView.findViewById(R.id.text_notif_popup_msg_message);

        Button btnOk = promptsView.findViewById(R.id.button_notif_popup_msg_ok);
        tvBookingId.setText(bookingid);
        tvBorrowerName.setText(borrowername +",");
        tvCreditOfficerName.setText(creditOfficer);
        String loanType = "";
        if(APIVariables.loanReason != null) {
            for (LoanApplication la : APIVariables.loanReason) {
                loanType = la.getReasonForLoan();
            }
        }

       // tvMsg.setText("Good day!  I would like to remind you of your due "+ duedate + " on your "+ loanType + ". Please pay your due IMMEDIATELY amounting to " + amountdue + " to avoid additional fees for late payment. Kindly disregard if payment has been made. Thank you.");
        tvMsg.setText("Good day!  I would like to remind you of your due "+ duedate + " on your "+ "Personal loan" + ". Please pay your due IMMEDIATELY amounting to " + amountdue + " to avoid additional fees for late payment. Kindly disregard if payment has been made. Thank you.");
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert2.dismiss();
            }
        });


    }
    public static void showNotif(Activity activity, String message) {
        LayoutInflater inflate = LayoutInflater.from(activity);
        View promptsView = inflate.inflate(R.layout.notif_popup, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setView(promptsView);
        alertDialog.setCancelable(false);
        final AlertDialog alert2 = alertDialog.create();
        alert2.show();
        TextView tvHeader = promptsView.findViewById(R.id.text_notif_popup_msg_header);
        TextView tvBookingId = promptsView.findViewById(R.id.text_notif_popup_msg_bookingid);
        TextView tvBorrowerName = promptsView.findViewById(R.id.text_notif_popup_msg_borrower);
        TextView tvCreditOfficerName = promptsView.findViewById(R.id.text_notif_popup_msg_creditofficer);
        TextView tvMsg = promptsView.findViewById(R.id.text_notif_popup_msg_message);
        TextView tvCOLabel = promptsView.findViewById(R.id.text_notif_popup_msg_creditofficer_label);
        TextView tvBookingIdLabel = promptsView.findViewById(R.id.text_notif_popup_msg_bookingid_label);
        TextView tvBorrowerNameLabel = promptsView.findViewById(R.id.text_notif_popup_msg_borrower_label);
        tvBookingIdLabel.setVisibility(View.GONE);
        tvBorrowerNameLabel.setVisibility(View.GONE);
        tvBookingId.setVisibility(View.GONE);
        tvBorrowerName.setVisibility(View.GONE);
        tvHeader.setVisibility(View.GONE);
        Button btnOk = promptsView.findViewById(R.id.button_notif_popup_msg_ok);
//        tvBookingId.setText(bookingid);
//        tvBorrowerName.setText(borrowername +",");
        tvCOLabel.setText("- Credit Officer");
        tvCreditOfficerName.setVisibility(View.GONE);
        tvMsg.setText(message);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert2.dismiss();
            }
        });


    }



    public static void gobackStepOnePopup(final Activity activity, String header, String message, String okBtn, String cncelBtn, final Fragment fragment) {
        LayoutInflater inflate = LayoutInflater.from(activity);
        View promptsView = inflate.inflate(R.layout.error_popup, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setView(promptsView);
        alertDialog.setCancelable(false);
        final AlertDialog alert2 = alertDialog.create();
        alert2.show();
        TextView tvHeader = promptsView.findViewById(R.id.text_popup_header);
        TextView tvMsg = promptsView.findViewById(R.id.text_popup_message);
        TextView tvCancel = promptsView.findViewById(R.id.text_popup_cancel);
        Button btnOk = promptsView.findViewById(R.id.button_popup_ok);
        ImageView ivIcon = promptsView.findViewById(R.id.img_popup_icon);
        tvHeader.setText(header);
        tvMsg.setText(message);
        tvCancel.setText(cncelBtn);
        btnOk.setText(okBtn);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert2.dismiss();
                Variables.stepThree = false;
                Variables.stepFour = false;
                Variables.stepFive = false;
                Variables.stepSix = false;
                Variables.stepSven = false;
                Variables.stepEight = false;

                new DBPullProcess(activity).deleteDB();
                FragmentTransaction ft = ((AppCompatActivity)activity).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_login_fragmentcontainer, fragment);
                ft.commit();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert2.dismiss();
            }
        });


    }

    public static ProgressDialog showProgress(Activity activity, String message) {

        ProgressDialog customProgressDialog =  new CustomProgressDialog(activity, message);
        customProgressDialog.setMessage(message);
        customProgressDialog.setCancelable(true);
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        customProgressDialog.setIndeterminate(false);

        return customProgressDialog;

    }
    public static ProgressDialog showProgress(Activity activity, String message, String submessage) {

        ProgressDialog customProgressDialog =  new CustomProgressDialog(activity, message, submessage);
        customProgressDialog.setMessage(message);
        customProgressDialog.setCancelable(false);
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        customProgressDialog.setIndeterminate(false);

        return customProgressDialog;

    }


    public static void customActionBar(final Activity activity, String title) {

        ActionBar actionBar =  ((AppCompatActivity) activity).getSupportActionBar();
        actionBar.setCustomView(R.layout.custom_actionbar_loggedin);
        View customActionbarItems = actionBar.getCustomView();

        TextView tvTitle = customActionbarItems.findViewById(R.id.text_actionbar_title);
//        final ImageView ivBurger = customActionbarItems.findViewById(R.id.img_actionbar_backOrBurger);
//        ivBurger.setBackground(activity.getResources().getDrawable(R.drawable.ic_menu));
        tvTitle.setText(title);
        ImageView ivHome = customActionbarItems.findViewById(R.id.img_actionbar_home);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Methods.switchFragment(activity, new Home());
            }
        });

        ImageView ivNotif = customActionbarItems.findViewById(R.id.img_actionbar_notif);

        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Methods.switchFragment(activity, new Home());
            }
        });
        for(LoanDetails loanDetails : APIVariables.loanDetails) {
            if(loanDetails.isOverdue()) {
                Picasso.get().load(R.drawable.ic_notif_notified).into(ivNotif);
                showPopup(activity, "WARNING!", "You have not paid your dues for 45 days. Your account is now subject to legal action. Please pay your dues immediately. Contact our Credit Officer for the exact amount.", "OK");
            } else {
                if(APIVariables.notice.getBookingId().equalsIgnoreCase("") && APIVariables.notice.getAmountDue().equalsIgnoreCase("") &&  APIVariables.notice.getDueDate().equalsIgnoreCase("")) {
                    Picasso.get().load(R.drawable.ic_notif).into(ivNotif);
                } else {
                    Picasso.get().load(R.drawable.ic_notif_notified).into(ivNotif);
                }

            }
        }
        ivNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(LoanDetails loanDetails : APIVariables.loanDetails) {
                    if(loanDetails.isOverdue()) {
                        showNotif(activity, APIVariables.notice.getBookingId(), APIVariables.notice.getBorrowerName(),APIVariables.notice.getAmountDue(), APIVariables.notice.getDueDate(), APIVariables.notice.getCreditOfficer());
                     } else {
                        if(APIVariables.notice.getBookingId().equalsIgnoreCase("") && APIVariables.notice.getAmountDue().equalsIgnoreCase("") &&  APIVariables.notice.getDueDate().equalsIgnoreCase("")) {
                            showNotif(activity, "No upcoming due date.");
                        } else {
                            showNotif(activity, APIVariables.notice.getBookingId(), APIVariables.notice.getBorrowerName(),APIVariables.notice.getAmountDue(), APIVariables.notice.getDueDate(), APIVariables.notice.getCreditOfficer());
                        }

                    }
                }

            }
        });


//
//        ivBurger.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (((MainActivity) activity).isDrawerOpen()) {
//                    ivBurger.setBackground(activity.getResources().getDrawable(R.drawable.ic_menu));
//                    ((MainActivity) activity).closeDrawer();
//                } else {
//                    ivBurger.setBackground(activity.getResources().getDrawable(R.drawable.ic_back_arrow));
//                    ((MainActivity) activity).openDrawer();
//                }
//
//
//            }
//        });
    }



    private static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e) {
            e.printStackTrace();
            Methods.showLog(activity, "HIDE SOFT KEYBOARD", "IS NULL.");
        }

    }



    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 60, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap base64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

    public static boolean isOnline(Activity activity) {

        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }

    // Scale a bitmap preserving the aspect ratio.
    public static Bitmap scale(Bitmap bitmap, int maxWidth, int maxHeight) {
        // Determine the constrained dimension, which determines both dimensions.
        int width;
        int height;
        float widthRatio = (float)bitmap.getWidth() / maxWidth;
        float heightRatio = (float)bitmap.getHeight() / maxHeight;
        // Width constrained.
        if (widthRatio >= heightRatio) {
            width = maxWidth;
            height = (int)(((float)width / bitmap.getWidth()) * bitmap.getHeight());
        }
        // Height constrained.
        else {
            height = maxHeight;
            width = (int)(((float)height / bitmap.getHeight()) * bitmap.getWidth());
        }
        Bitmap scaledBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        float ratioX = (float)width / bitmap.getWidth();
        float ratioY = (float)height / bitmap.getHeight();
        float middleX = width / 2.0f;
        float middleY = height / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        return scaledBitmap;
    }


}
