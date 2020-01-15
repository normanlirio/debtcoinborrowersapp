package com.debtcoin.debtcoinapp.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.debtcoin.debtcoinapp.API.model.AcceptedApplicants;
import com.debtcoin.debtcoinapp.API.model.AccountDetails;
import com.debtcoin.debtcoinapp.API.model.Article;
import com.debtcoin.debtcoinapp.API.model.Banner;
import com.debtcoin.debtcoinapp.API.model.LoanApplication;
import com.debtcoin.debtcoinapp.API.model.LoanDetails;
import com.debtcoin.debtcoinapp.API.model.Notice;
import com.debtcoin.debtcoinapp.API.model.RankImage;
import com.debtcoin.debtcoinapp.API.model.ResultMessage;
import com.debtcoin.debtcoinapp.API.model.User;
import com.debtcoin.debtcoinapp.API.model.UserAccount;
import com.debtcoin.debtcoinapp.API.service.DebtcoinServiceGenerator;
import com.debtcoin.debtcoinapp.API.service.UserService;
import com.debtcoin.debtcoinapp.API.service.rx.ArticleRxService;
import com.debtcoin.debtcoinapp.API.service.rx.BannerRxService;
import com.debtcoin.debtcoinapp.API.service.rx.RankImageRxService;
import com.debtcoin.debtcoinapp.API.service.rx.UserRxService;
import com.debtcoin.debtcoinapp.Fragments.ApplicantLimitFragment;
import com.debtcoin.debtcoinapp.Fragments.FAQFragment;
import com.debtcoin.debtcoinapp.Fragments.ForgotPassword;
import com.debtcoin.debtcoinapp.Fragments.ForgotVerification;
import com.debtcoin.debtcoinapp.Fragments.NewPassword;
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
import com.debtcoin.debtcoinapp.Globals.APIVariables;
import com.debtcoin.debtcoinapp.Globals.Methods;
import com.debtcoin.debtcoinapp.Globals.Variables;
import com.debtcoin.debtcoinapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements  FAQFragment.OnFragmentInteractionListener


{

    private static final String TAG = "LoginActivity";

    private boolean isLimit;
    private TextView tvViewFaq, tvRegister, tvForgotPassword;
    private EditText etUser, etPass;
    private Button btnSubmit;
    private RelativeLayout container;
    private int PERMISSION_ALL = 1;
    private String[] PERMISSIONS = {Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Unbinder unbinder;
    @BindView(R.id.frame_login_fragmentcontainer)
    RelativeLayout flContainer;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);
        Methods.setupUI(LoginActivity.this, flContainer);
        isLimit = false;

        tvViewFaq = findViewById(R.id.text_login_faq);
        tvRegister = findViewById(R.id.text_login_register);
        etUser = findViewById(R.id.edit_login_user);
        etPass = findViewById(R.id.edit_login_pass);
        btnSubmit = findViewById(R.id.button_login_login);
        container = findViewById(R.id.frame_login_fragmentcontainer);
        tvForgotPassword = findViewById(R.id.text_login_forgotpass);
//        etUser.setText("admin123");
//        etPass.setText("654321");
        if(!Methods.isOnline(LoginActivity.this)) {
            Methods.showPopup(LoginActivity.this, "Error", "This app requires an internet connection.", "OK");
        }
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           // Methods.switchFragment(LoginActivity.this, new StepThree());
            getApplicantCount();
            }
        });



        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        // Retrofit Test
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Methods.isEmpty(etUser) && Methods.isEmpty(etPass)) {

                    Methods.showPopup(LoginActivity.this, "", "Both fields are required.", "OK");
                } else if(Methods.isEmpty(etUser)) {
                    Methods.showPopup(LoginActivity.this, "", "Username is required.", "OK");
                } else if( Methods.isEmpty(etPass)) {
                    Methods.showPopup(LoginActivity.this, "", "Password is required.", "OK");
                } else {
                    final ProgressDialog pd = Methods.showProgress(LoginActivity.this, "Signing In...");
                    pd.show();
                    Log.v("LOGIN", "API Connect..");
                    UserAccount userAccount = new UserAccount(etUser.getText().toString(), etPass.getText().toString());

                    compositeDisposable.add(new UserRxService().loginUser(userAccount)
                            .subscribeOn(Schedulers.io())
                            .flatMap(userToken -> {
                                if (userToken.getResponseCode().equals("200")) {
                                    Log.d(TAG, "onClick: response Code: " + userToken.getResponseCode());
                                    Log.d(TAG, "onClick: Token: " + userToken.getToken());
                                    Log.d(TAG, "onClick: Expires: " + userToken.getExpires());
                                    Log.d(TAG, "onClick: Message: " + userToken.getMessage());

                                    Variables.token = userToken.getToken();
                                } else if (userToken.getResponseCode().equals("401")) {
                                        return Maybe.error(new Exception(userToken.getMessage()));
                                }   else  {
                                    Log.d(TAG, "onClick: response Code: " + userToken.getResponseCode());
                                    Log.d(TAG, "onClick: Message: " + userToken.getMessage());

                                    return Maybe.error(new Exception(userToken.getMessage()));

                                }

                                return new UserRxService(Variables.token)
                                        .getUserByUsername(etUser.getText().toString());
                            })
                            .flatMap(userResultMessage -> {
                                Log.d(TAG, "onClick: response Code: " + userResultMessage.getResponseCode());

                                if (userResultMessage.getResponseCode().equals("200")) {

                                    User user = userResultMessage.getBody();
                                    AccountDetails accountDetails = user.getUserAccount().getAccountDetails();

                                    APIVariables.userId = user.getId();
                                    APIVariables.name = user.getFullName();
                                    APIVariables.rank = accountDetails.getRank();
                                    APIVariables.creditLimit = accountDetails.getCreditLimit();
                                    APIVariables.referrals = accountDetails.getReferrals();
                                    APIVariables.username = user.getUserAccount().getUsername();
                                    APIVariables.email = user.getEmail();
                                    APIVariables.mobileNumber = user.getMobileNumber();
                                    APIVariables.isPending = user.isPending();
                                    APIVariables.loanDetails =  user.getUserAccount().getLoanDetails();
                                    APIVariables.notice = user.getUserAccount().getNotice();
                                    APIVariables.loanReason = user.getUserAccount().getLoanApplication();
//                                    if(user.getUserAccount().getLoanApplication() == null) {
//                                        for(LoanApplication la : user.getUserAccount().getLoanApplication()) {
//                                            Log.v("LOAN APPLICATION", la.getApplicantName());
//                                        }
//                                    }


                                    // TODO notices
                                    APIVariables.notification = user.getUserAccount().getNotice().getContent();

                                    List<LoanDetails> loanDetailsList = user.getUserAccount().getLoanDetails();
                                    LoanDetails loanDetails = user.getUserAccount().getLoanDetails().get(loanDetailsList. size() - 1);

                                    APIVariables.currentPaymentDueDate = loanDetails.getDueDate();

                                    Log.d(TAG, "onClick: " + APIVariables.userId);
                                    Log.d(TAG, "onClick: " + APIVariables.name);
                                    Log.d(TAG, "onClick: " + APIVariables.rank);
                                    Log.d(TAG, "onClick: " + APIVariables.creditLimit);
                                    Log.d(TAG, "onClick: " + APIVariables.referrals);
                                    if(user.getStatus().equals("0")) {
//                                        Methods.showPopup(LoginActivity.this, "Oops!", "Your account is still under verification.", "OK");
                                        return Maybe.error(new Exception("Your account is still under verification."));
                                    } else if(user.getStatus().equals("-1")) {
                                        return Maybe.error(new Exception("Your account is denied."));
                                    } else {

                                    }

                                } else {
                                    Log.d(TAG, "onClick: response Code: " + userResultMessage.getResponseCode());
                                    Log.d(TAG, "onClick: Message: " + userResultMessage.getResponseMessage());
                                    return Maybe.error(new Exception(userResultMessage.getResponseMessage()));
                                }

                                return new BannerRxService(Variables.token).getAllBanners();
                            })
                            .flatMap(bannerListResultMessage -> {


                                if (bannerListResultMessage.getResponseCode().equals("200")) {

                                    List<Banner> banners = bannerListResultMessage.getBody();
                                    for (Banner banner : banners) {
                                        APIVariables.bannerLinks.add(banner.getPath());
                                        Log.d(TAG, "loadLinks: Banner Path: " + banner.getPath());
                                    }
                                } else {
                                    return Maybe.error(new Exception(bannerListResultMessage.getResponseMessage()));
                                }

                                return new RankImageRxService(Variables.token).getAllRankImages();
                            })
                            .flatMap(rankImageListResultMessage -> {


                                if (rankImageListResultMessage.getResponseCode().equals("200")) {

                                    List<RankImage> rankImages = rankImageListResultMessage.getBody();
                                    for (RankImage rankImage : rankImages) {
                                        if (APIVariables.rank.equals(rankImage.getName())) {
                                            APIVariables.currentRankLink = rankImage.getPath();
                                            Log.d(TAG, "loadLinks: CurrentRank Path: " + rankImage.getPath());
                                        }

                                        APIVariables.rankImageLinks.add(rankImage.getPath());
                                        Log.d(TAG, "loadLinks: RankImage Path: " + rankImage.getPath());
                                    }
                                } else {
                                    return Maybe.error(new Exception(rankImageListResultMessage.getResponseMessage()));
                                }

                                return new ArticleRxService(Variables.token).getAllArticles();
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(articleListResultMessage -> {

                                pd.dismiss();

                                if (articleListResultMessage.getResponseCode().equals("200")) {

                                    List<Article> articles = articleListResultMessage.getBody();
                                    for (Article article : articles) {
                                        APIVariables.articles.add(article);
                                        Log.d(TAG, "loadLinks: Article Path: " + article.getPath());
                                    }

                                    Intent login = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(login);
                                    finish();
                                } else {
                                    Methods.showPopup(LoginActivity.this, "Oops!", "Something went wrong.","OK");
                                }

                            }, error -> {
                                pd.hide();
                                Log.e(TAG, "onClick: ", error);
                                Log.e(TAG, error.getMessage());
                                String message = error.getMessage();
                                if(message.contains("verification")) {
                                    Methods.showPopup(LoginActivity.this, "Oops!", "Your account is still under verification." ,"OK");
                                } else if(message.contains("Invalid username")){
                                    Methods.showPopup(LoginActivity.this, "Oops!", "Invalid username or password." ,"OK");
                                }else if(message.contains("account is denied")){
                                    Methods.showPopup(LoginActivity.this, "Oops!", "Your account is denied." ,"OK");
                                } else {
                                    Methods.showPopup(LoginActivity.this, "Oops!", "Something went wrong." ,"OK");
                                }

                            }));
                }
            }
        });

        tvViewFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(LoginActivity.this, "This feature coming soon.", Toast.LENGTH_SHORT).show();
               Methods.switchFragment(LoginActivity.this, new FAQFragment());
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
               startActivity(intent);
               finish();
            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

        compositeDisposable.clear();
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void getApplicantCount() {
        pd = Methods.showProgress(LoginActivity.this, "Loading...");
        pd.show();
        UserService userService = DebtcoinServiceGenerator.createService(UserService.class);
        Call<ResultMessage<AcceptedApplicants>> applicants = userService.getApplicantCount();
        applicants.enqueue(new Callback<ResultMessage<AcceptedApplicants>>() {
            @Override
            public void onResponse(Call<ResultMessage<AcceptedApplicants>> call, Response<ResultMessage<AcceptedApplicants>> response) {
                ResultMessage<AcceptedApplicants> resultMessage;
                if (response.isSuccessful()) {
                    pd.dismiss();
                    resultMessage = response.body();
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    if (resultMessage.getBody().isExceeded()) {
                        intent.putExtra("hasExceeded", true);
                       //
                    } else {
                        intent.putExtra("hasExceeded", false);
                     //
                    }
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResultMessage<AcceptedApplicants>> call, Throwable t) {
                pd.dismiss();
                t.printStackTrace();
                Methods.showPopup(LoginActivity.this , "Error", "Please check your internet connection", "OK");
            }
        });
    }



}
