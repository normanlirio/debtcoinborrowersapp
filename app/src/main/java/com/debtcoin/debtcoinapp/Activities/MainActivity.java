package com.debtcoin.debtcoinapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.debtcoin.debtcoinapp.API.model.Article;
import com.debtcoin.debtcoinapp.API.model.Banner;
import com.debtcoin.debtcoinapp.API.model.LoanDetails;
import com.debtcoin.debtcoinapp.API.model.Payment;
import com.debtcoin.debtcoinapp.API.model.RankImage;
import com.debtcoin.debtcoinapp.API.model.UserAccount;
import com.debtcoin.debtcoinapp.API.service.rx.ArticleRxService;
import com.debtcoin.debtcoinapp.API.service.rx.BannerRxService;
import com.debtcoin.debtcoinapp.API.service.rx.PaymentRxService;
import com.debtcoin.debtcoinapp.API.service.rx.RankImageRxService;
import com.debtcoin.debtcoinapp.API.service.rx.UserRxService;
import com.debtcoin.debtcoinapp.Fragments.ApplyLoan;
import com.debtcoin.debtcoinapp.Fragments.ApplyLoanConfirmation;
import com.debtcoin.debtcoinapp.Fragments.ApplyLoanDetails;
import com.debtcoin.debtcoinapp.Fragments.Articles;
import com.debtcoin.debtcoinapp.Fragments.ArticlesPagerFragment;
import com.debtcoin.debtcoinapp.Fragments.DebtcoinRank;
import com.debtcoin.debtcoinapp.Fragments.DebtcoinWorks;
import com.debtcoin.debtcoinapp.Fragments.FAQFragment;
import com.debtcoin.debtcoinapp.Fragments.Home;
import com.debtcoin.debtcoinapp.Fragments.MakePayment;
import com.debtcoin.debtcoinapp.Fragments.MakePaymentConfirmation;
import com.debtcoin.debtcoinapp.Fragments.Overview;
import com.debtcoin.debtcoinapp.Fragments.ViewPagerHome;
import com.debtcoin.debtcoinapp.Globals.APIVariables;
import com.debtcoin.debtcoinapp.Globals.Methods;
import com.debtcoin.debtcoinapp.Globals.Variables;
import com.debtcoin.debtcoinapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Maybe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FAQFragment.OnFragmentInteractionListener, Overview.OnFragmentInteractionListener, DebtcoinWorks.OnFragmentInteractionListener,
        DebtcoinRank.OnFragmentInteractionListener, ApplyLoan.OnFragmentInteractionListener, Articles.OnFragmentInteractionListener, MakePayment.OnFragmentInteractionListener, ApplyLoanDetails.OnFragmentInteractionListener,
        ApplyLoanConfirmation.OnFragmentInteractionListener, ArticlesPagerFragment.OnFragmentInteractionListener, Home.OnFragmentInteractionListener,
        ViewPagerHome.OnFragmentInteractionListener, MakePaymentConfirmation.OnFragmentInteractionListener
{

    private static final String TAG = "MainActivity";

    private Unbinder unbinder;
    private  DrawerLayout drawer;
    private  int count = 0;

    private TextView tvNavheaderFname, tvNavheaderRank, tvNavheaderCreditlimit, tvNavheaderReferrals;
    private ImageView ivRank;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private boolean isUploaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        unbinder = ButterKnife.bind(this);
      drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Methods.setupUI(MainActivity.this, drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        // TODO START NOTE: Normz di ko alam kung tama ginawa ko but it works! haha paki polish nalang thanks!
        View navView = navigationView.getHeaderView(0);

        tvNavheaderFname = navView.findViewById(R.id.text_navheader_fname);
        tvNavheaderRank = navView.findViewById(R.id.text_navheader_rank);
        tvNavheaderCreditlimit = navView.findViewById(R.id.text_navheader_creditlimit);
        tvNavheaderReferrals = navView.findViewById(R.id.text_navheader_referrals);
        ivRank = navView.findViewById(R.id.img_navheader_rank);

        tvNavheaderFname.setText(APIVariables.name);
        tvNavheaderCreditlimit.setText(String.format(Locale.US, "Php %,.2f", APIVariables.creditLimit));
        tvNavheaderRank.setText(APIVariables.rank);
        tvNavheaderReferrals.setText(String.valueOf(APIVariables.referrals));
//        switch (APIVariables.rank.toUpperCase() ) {
//            case "Bronze" :
//                ivRank.setImageResource(R.drawable.bronze);
//                break;
//            case "Silver" :
//                ivRank.setImageResource(R.drawable.silver);
//                break;
//            case "Gold" :
//                ivRank.setImageResource(R.drawable.gold);
//                break;
//
//        }

        Picasso.get().load(APIVariables.currentRankLink).into(ivRank);
        // TODO END NOTE

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        drawer.setScrimColor(getResources().getColor(android.R.color.transparent));
        Methods.customActionBar(MainActivity.this, "Debtcoin ph");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                hideSoftKeyboard();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideSoftKeyboard();
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.custom_actionbar_loggedin);
        View customActionbarItems = actionBar.getCustomView();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.md_white_1000));
        ImageView ivHome = customActionbarItems.findViewById(R.id.img_actionbar_home);
        ImageView ivNotif = customActionbarItems.findViewById(R.id.img_actionbar_notif);

        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Methods.switchFragment(MainActivity.this, new Home());
            }
        });


     //   Toast.makeText(MainActivity.this, APIVariables.notification, Toast.LENGTH_SHORT).show();
        if(savedInstanceState == null) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment frag = new Home();
            ft.replace(R.id.frame_login_fragmentcontainer, frag);
            ft.commit();
        }

//        tvTitle = customActionbarItems.findViewById(R.id.text_actionbar_title);
//         ivBurger = customActionbarItems.findViewById(R.id.img_actionbar_backOrBurger);
//        ivBurger.setBackground(getResources().getDrawable(R.drawable.ic_menu));
//        tvTitle.setText("Debtcoin PH");
//      //  drawer.setScrimColor(getResources().getColor(android.R.color.transparent));
//        ivBurger.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (drawer.isDrawerOpen(GravityCompat.START)) {
//                    ivBurger.setBackground(getResources().getDrawable(R.drawable.ic_menu));
//                    drawer.closeDrawer(GravityCompat.START);
//                } else {
//                    ivBurger.setBackground(getResources().getDrawable(R.drawable.ic_back_arrow));
//                    drawer.openDrawer(GravityCompat.START);
//                }
//
//
//            }
//        });
//


    }


    private void hideSoftKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

        compositeDisposable.clear();
    }

    public void openDrawer() {
        drawer.openDrawer(GravityCompat.START);
    }

    public void closeDrawer() {
        drawer.closeDrawer(GravityCompat.START);
    }

    public boolean isDrawerOpen(){
        return drawer.isDrawerOpen(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {

        if(count == 1 ){
            finish();
        } else {
            count++;
            Toast.makeText(MainActivity.this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
              count = 0;

            }
        }, 4000);


    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_overview :
                Variables.hasPressedNext = false;
                Methods.switchFragmentLoggedIn(MainActivity.this, new Overview());
                closeDrawer();
                break;
            case R.id.nav_apply_loan :
                Variables.hasPressedNext = false;
                checkPendingLoanApplication();
                break;
            case R.id.nav_make_payment :
                if (APIVariables.isOverdue) {
                    Methods.showPopup(MainActivity.this, "WARNING!", "You have not paid your dues for 45 days. Your account is now subject to legal action. Please pay your dues immediately. Contact our Credit Officer for the exact amount.", "OK");

                } else {
                    Variables.hasPressedNext = false;
                    checkPendingPayment();
                }


                break;
            case R.id.nav_articles :
                Variables.hasPressedNext = false;
                Methods.switchFragmentLoggedIn(MainActivity.this, new Articles());
                closeDrawer();
                break;
            case R.id.nav_how_works :
                Variables.hasPressedNext = false;
                Methods.switchFragmentLoggedIn(MainActivity.this, new DebtcoinWorks());
                closeDrawer();
                break;
            case R.id.nav_myrank :
                Variables.hasPressedNext = false;
                Methods.switchFragmentLoggedIn(MainActivity.this, new DebtcoinRank());
                closeDrawer();
                break;
            case R.id.nav_faqs :
                Variables.hasPressedNext = false;
                Methods.switchFragmentLoggedIn(MainActivity.this, new FAQFragment());
                closeDrawer();
                Variables.isLoggedIn = true;
                break;
            case R.id.nav_logout :
                Variables.isLoggedIn = false;
                closeDrawer();
                APIVariables.clearAll();
                Variables.clearAll();
                Intent loginPage = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginPage);
                finish();
                break;
        }


        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void checkPendingLoanApplication() {

        final ProgressDialog pd = Methods.showProgress(MainActivity.this, "");
        pd.show();

        compositeDisposable.add(new UserRxService(Variables.token)
                .getUserAccountByUsername(APIVariables.username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userAccountResultMessage -> {

                    pd.dismiss();

                    if (userAccountResultMessage.getResponseCode().equals("200")) {
                        UserAccount userAccount = userAccountResultMessage.getBody();
                        if (userAccount.isHasPendingApplication()) {
                            Methods.showPopup(MainActivity.this, "", getResources().getString(R.string.apply_loan_existing), "CLOSE");
                        } else if (userAccount.isHasActiveLoan()) {
                            Methods.showPopup(MainActivity.this, "", "You still have an active loan.", "CLOSE");
                        } else if (!userAccount.getLoanDetails().get(userAccount.getLoanDetails().size() - 1).isCompleted()) {
                            Methods.showPopup(MainActivity.this, "", "Please complete your unpaid loan.", "CLOSE");
                        } else {
                            Methods.switchFragmentLoggedIn(MainActivity.this, new ApplyLoan());
                            closeDrawer();
                        }
                    } else {
                        Methods.showPopup(MainActivity.this, "", userAccountResultMessage.getResponseMessage(), "CLOSE");
                    }

                }, error -> {
                    pd.dismiss();
                    Log.d(TAG, "checkPendingLoanApplication: " + error.getMessage());
                }));
    }

    private void checkPendingPayment() {

        final ProgressDialog pd = Methods.showProgress(MainActivity.this, "");
        pd.show();

        compositeDisposable.add(new UserRxService(Variables.token)
                .getUserAccountByUsername(APIVariables.username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userAccountResultMessage -> {

                    pd.dismiss();

                    if (userAccountResultMessage.getResponseCode().equals("200")) {

                        UserAccount userAccount = userAccountResultMessage.getBody();
                        List<Payment> payments = userAccount.getPayments();

                        List<Payment> sortedPayments = new ArrayList<>();

                        int lastIndex = payments.size() - 1;

                        Log.d(TAG, "checkPendingPayment: PAYMENTS SIZE: " + payments.size());

//                        Toast.makeText(this, "" + Variables.isImageUploaded, Toast.LENGTH_SHORT).show();

                        if (payments.size() > 0) {
                            APIVariables.isOverdue = userAccount.getLoanDetails().get(userAccount.getLoanDetails().size() - 1).isOverdue();
                            Variables.paymentId = payments.get(lastIndex).getId() ;
                            Variables.isImageUploaded = payments.get(lastIndex).isImageUploaded();

                            Log.d(TAG, "checkPendingPayment: PAYMENT ID: " + Variables.paymentId);

                            Log.d(TAG, "checkPendingPayment: isConfirmed: " + payments.get(lastIndex).isConfirmed());

                            if (payments.get(lastIndex).isConfirmed()) {
                                if (userAccount.isHasPendingApplication()) {
                                    Methods.showPopup(MainActivity.this, "", "You still have pending loan application.", "CLOSE");
                                } else if (!userAccount.isHasActiveLoan()) {
                                    Methods.showPopup(MainActivity.this, "", "You don't have active loan.", "CLOSE");
                                } else {
                                    Methods.switchFragmentLoggedIn(MainActivity.this, new MakePayment());
                                    closeDrawer();
                                }
                            } else {
//                                Log.v("MainActivity", "Makepayment");
//                                Variables.paymentId = payments.get(lastIndex).getId() ;
//                                Variables.isImageUploaded = payments.get(lastIndex).isImageUploaded();
//                                Toast.makeText(this, "" + payments.get(lastIndex).isImageUploaded(), Toast.LENGTH_SHORT).show();
//                                if(payments.get(lastIndex).isImageUploaded()) {
//
//                                } else {
//                                    Variables.isImageUploaded = isUploaded;
//                                    Methods.switchFragmentLoggedIn(MainActivity.this, new MakePayment());
//                                    closeDrawer();
//                                }
                                Methods.showPopup(MainActivity.this, "", "You still have pending payments.", "CLOSE");

                            }
                        } else {

                            if (userAccount.isHasPendingApplication()) {
                                Methods.showPopup(MainActivity.this, "", "You still have pending loan application.", "CLOSE");
                            } else if (!userAccount.isHasActiveLoan()) {
                                Methods.showPopup(MainActivity.this, "", "You don't have active loan.", "CLOSE");
                            } else {
                                Methods.switchFragmentLoggedIn(MainActivity.this, new MakePayment());
                                closeDrawer();
                            }

                        }
                    } else {
                        Methods.showPopup(MainActivity.this, "", userAccountResultMessage.getResponseMessage(), "CLOSE");
                    }

                }, error -> {
                    pd.dismiss();
                    Log.d(TAG, "checkPendingPayment: " + error.getMessage());
                }));
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }



}
