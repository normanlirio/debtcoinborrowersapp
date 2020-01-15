package com.debtcoin.debtcoinapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.debtcoin.debtcoinapp.API.model.Employment;
import com.debtcoin.debtcoinapp.API.model.FinancialInformation;
import com.debtcoin.debtcoinapp.API.model.Reference;
import com.debtcoin.debtcoinapp.API.model.ResultMessage;
import com.debtcoin.debtcoinapp.API.model.User;
import com.debtcoin.debtcoinapp.API.model.UserAccount;
import com.debtcoin.debtcoinapp.API.service.DebtcoinServiceGenerator;
import com.debtcoin.debtcoinapp.API.service.UserService;
import com.debtcoin.debtcoinapp.API.service.rx.UserRxService;
import com.debtcoin.debtcoinapp.Activities.LoginActivity;
import com.debtcoin.debtcoinapp.Database.DBPullProcess;
import com.debtcoin.debtcoinapp.Globals.Methods;
import com.debtcoin.debtcoinapp.Globals.Variables;
import com.debtcoin.debtcoinapp.R;
import com.debtcoin.debtcoinapp.Util.AESCrypt;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepTen.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepTen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepTen extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "STEP TEN";
    private Unbinder unbinder;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private UserService userService;
    private String filePath7;
    private String filePath8;

    @BindView(R.id.stepten_container)
    RelativeLayout rlContainer;
    @BindView(R.id.text_stepten_fname)
    TextView tvFname;
    @BindView(R.id.text_stepten_doctype)
    TextView tvDocType;
    @BindView(R.id.text_stepten_docnum)
    TextView tvDocNum;
    @BindView(R.id.text_stepten_dob)
    TextView tvDob;
    @BindView(R.id.text_stepten_mobnum)
    TextView tvMobNum;
    @BindView(R.id.text_stepten_presentaddress)
    TextView tvPAddress;
    @BindView(R.id.text_stepten_status)
    TextView tvStatus;
    @BindView(R.id.text_stepten_emporbusinessname)
    TextView tvEmpOrBusi;
    @BindView(R.id.text_stepten_nature)
    TextView tvNature;
    @BindView(R.id.text_stepten_jobpos)
    TextView tvJobPos;
    @BindView(R.id.text_stepten_empaddress)
    TextView tvEmpAddress;
    @BindView(R.id.text_stepten_ref1name)
    TextView tvRef1Name;
    @BindView(R.id.text_stepten_ref2name)
    TextView tvRef2Name;
    @BindView(R.id.text_stepten_ref1numorbusi)
    TextView tvNumOrUser1;
    @BindView(R.id.text_stepten_ref2numorbusi)
    TextView tvNumOrUser2;
    @BindView(R.id.text_stepten_bankname)
    TextView tvBankName;
    @BindView(R.id.text_stepten_accnttype)
    TextView tvAccountType;
    @BindView(R.id.text_stepten_accntnum)
    TextView tvAccountNum;
    @BindView(R.id.img_stepten_sevenimage)
    ImageView ivStepSevenImage;
    @BindView(R.id.img_stepten_eightimage)
    ImageView ivStepEightImage;
    @BindView(R.id.button_next)
    Button btnConfirm;
    @BindView(R.id.relative_stepten_chilccontainer)
    RelativeLayout rlChild;
    @BindView(R.id.thankyou_container)
    RelativeLayout rlThankyou;
    @BindView(R.id.button_thankyou_back)
    Button btnDone;
    @BindView(R.id.text_stepten_user)
    TextView tvUser;
    private String password;

    CompositeDisposable compositeDisposable = new CompositeDisposable();


    private OnFragmentInteractionListener mListener;

    public StepTen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StepTen.
     */
    // TODO: Rename and change types and number of parameters
    public static StepTen newInstance(String param1, String param2) {
        StepTen fragment = new StepTen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_ten, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Methods.setupUI(getActivity(), rlContainer);
        TextView tvStep = view.findViewById(R.id.text_customactionbar_title);
        ImageView ivArrow = view.findViewById(R.id.img_customactionbar_img);
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity) getActivity()).getSupportFragmentManager().popBackStack();
            }
        });
        tvStep.setText(getResources().getString(R.string.stepten));
        btnConfirm.setText("Confirm and Submit");
        summary();
    }

    private void summary() {
        DBPullProcess information = new DBPullProcess(getActivity());

        tvFname.setText(information.getPersonalInfo().getFname());
        tvDocType.setText(information.getPersonalInfo().getDoctype());
        tvDocNum.setText(information.getPersonalInfo().getDocNumber());
        tvDob.setText(information.getPersonalInfo().getDob());
        tvMobNum.setText(information.getPersonalInfo().getMobilenumber());
        tvPAddress.setText(information.getPersonalInfo().getAddress());

        tvStatus.setText(information.getEmploymentInfo().getStatus());
        tvEmpOrBusi.setText(information.getEmploymentInfo().getEmpname());
        tvNature.setText(information.getEmploymentInfo().getEmpnature());
        tvJobPos.setText(information.getEmploymentInfo().getPosition());
        tvEmpAddress.setText(information.getEmploymentInfo().getEmpaddress());
        String ref1 = information.getReferenceInfo().getRefName();
        String refId1 = information.getReferenceInfo().getRefNameId();
        String ref2 = information.getReferenceInfo().getRefName2();
        String refId2 = information.getReferenceInfo().getRefNameId2();
        HashMap<String, String> optionalFields = new HashMap<>();
        if (ref1.length() > 0) {
            tvRef1Name.setText(ref1);

        } else {
            tvRef1Name.setText("NA");

        }

        if (refId1.length() > 0) {
            tvNumOrUser1.setText(refId1);
        } else {
            tvNumOrUser1.setText("NA");
        }

        if (ref2.length() > 0) {
            tvRef2Name.setText(ref2);

        } else {
            tvRef2Name.setText("NA");
        }

        if (refId2.length() > 0) {
            tvNumOrUser2.setText(refId2);
        } else {
            tvNumOrUser2.setText("NA");
        }

        String bankName = information.getFinancialInfo().getBankName();
        String accntType = information.getFinancialInfo().getAccountType();
        String accntNum = information.getFinancialInfo().getAccountNumber();

        if (bankName.length() > 0) {
            tvBankName.setText(bankName);
        } else {
            tvBankName.setText("NA");
        }

        if (accntType.length() > 0) {
            tvAccountType.setText(accntType);
        } else {
            tvAccountType.setText("NA");
        }

        if (accntNum.length() > 0) {
            tvAccountNum.setText(accntNum);
        } else {
            tvAccountNum.setText("NA");
        }

        Log.v(TAG, "FILE PATH FOR STEP 7 : " + information.getStepSevenInfo().get("filepath"));
        Log.v(TAG, "FILE PATH FOR STEP 8 : " + information.getStepEightInfo().get("filepath"));
        filePath7 = information.getStepSevenInfo().get("filepath");
        filePath8 = information.getStepEightInfo().get("filepath");
        ivStepSevenImage.setImageBitmap(Methods.base64ToBitmap(information.getStepSevenInfo().get("base64")));
        ivStepSevenImage.setScaleType(ImageView.ScaleType.FIT_XY);
        ivStepEightImage.setImageBitmap(Methods.base64ToBitmap(information.getStepEightInfo().get("base64")));
        ivStepEightImage.setScaleType(ImageView.ScaleType.FIT_XY);
        optionalFields.put("ref1", ref1);
        optionalFields.put("ref2", ref2);
        optionalFields.put("ref1id", refId1);
        optionalFields.put("ref2id", refId2);
        optionalFields.put("bankName", bankName);
        optionalFields.put("accntType", accntType);
        optionalFields.put("accntNum", accntNum);

        tvUser.setText(information.getCredentials().get("user"));
        password = information.getCredentials().get("pass");
        Log.v(TAG, "PASSWORD : " + password);
        signUp(optionalFields);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void signUp(HashMap<String, String> optional) {
//        final UserService userService = DebtcoinServiceGenerator.createService(UserService.class);
        User user = new User();
        user.setFullName(tvFname.getText().toString());
        user.setDocIdType(tvDocType.getText().toString());
        user.setDocIdNum(tvDocNum.getText().toString());
        user.setMobileNumber(tvMobNum.getText().toString());
        user.setDateOfBirth(tvDob.getText().toString());

        user.setPresentAddress(tvPAddress.getText().toString());
        user.setEmail(Variables.email);

        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(tvUser.getText().toString());
        userAccount.setPassword(decryptAESCrypt(password));

        Employment employment = new Employment();
        employment.setStatus(tvStatus.getText().toString());
        employment.setEmployerName(tvEmpOrBusi.getText().toString());
        employment.setNature(tvNature.getText().toString());
        employment.setPosition(tvJobPos.getText().toString());
        employment.setAddress(tvEmpAddress.getText().toString());

        Reference reference = new Reference();
        reference.setRefOneName(optional.get("ref1"));
        reference.setRefOneMobileNum(optional.get("ref1id"));
        reference.setRefTwoName(optional.get("ref2"));
        reference.setRefTwoMobileNum(optional.get("ref2id"));

        FinancialInformation financialInformation = new FinancialInformation();
        financialInformation.setBankName(optional.get("bankName"));
        financialInformation.setAccountType(optional.get("accntType"));
        financialInformation.setAccountName(optional.get("accntNum"));

        user.setEmployment(employment);
        user.setReference(reference);
        user.setFinancialInformation(financialInformation);
        user.setUserAccount(userAccount);

        Log.d(TAG, "signUp getFullName: "+ user.getFullName());
        Log.d(TAG, "signUp getDocIdNum: "+ user.getDocIdNum());
        Log.d(TAG, "signUp getDocIdType: "+ user.getDocIdType());
        Log.d(TAG, "signUp getMobileNumber: "+ user.getMobileNumber());
        Log.d(TAG, "signUp getPresentAddress: "+ user.getPresentAddress());
        Log.d(TAG, "signUp getEmail: "+ user.getEmail());

        Log.d(TAG, "signUp getEmployerName: " + employment.getEmployerName());
        Log.d(TAG, "signUp getNature: " + employment.getNature());
        Log.d(TAG, "signUp getPosition: " + employment.getPosition());
        Log.d(TAG, "signUp getStatus: " + employment.getStatus());
        Log.d(TAG, "signUp getAddress: " + employment.getAddress());

        Log.d(TAG, "signUp getAccountName: " + financialInformation.getAccountName());
        Log.d(TAG, "signUp getAccountType: " + financialInformation.getAccountType());
        Log.d(TAG, "signUp getAccountType: " + financialInformation.getBankName());




        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Call<ResultMessage<User>> userCall = userService.signUp(user);
//                final ProgressDialog pd = Methods.showProgress(getActivity(), "SENDING APPLICATION...");
//
//                Log.d(TAG, "onClick: PASSWORD: " + userAccount.getPassword());
//                pd.show();
//                userCall.enqueue(new Callback<ResultMessage<User>>() {
//                    @Override
//                    public void onResponse(Call<ResultMessage<User>> call, Response<ResultMessage<User>> response) {
//                        ResultMessage<User> responseResult = response.body();
//                        if (response.isSuccessful()) {
//                            pd.dismiss();
//                            if (responseResult != null) {
//                                Log.d(TAG, "onResponse: " + responseResult.getResponseCode());
//                                Log.d(TAG, "onResponse: " + responseResult.getResponseMessage());
//                            } else {
//                                Log.v(TAG, "NULL Response");
//                            }
//                            Log.v(TAG, response.message());
//                            Log.v(TAG, "SUCCESS CALL.");
//                        } else {
//                            Log.v(TAG, response.message());
//                            Log.v(TAG, "FAILED!!!");
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResultMessage<User>> call, Throwable t) {
//                        Log.v(TAG, "onFailure: " + t.getMessage());
//                        Log.v(TAG, "Failed to send application.");
//                        pd.dismiss();
//                      //  Methods.showPopup(getActivity(), "Error", "Please check your internet connection", "OK");
//                    }
//                });

//                signUpImage(filePath7, filePath8);
                signUpUserRx(user, filePath7, filePath8);

            }
        });
    }

    public void signUpImage(String filepath, String filepath2) {
        userService = DebtcoinServiceGenerator.createService(UserService.class);
        File file = null;
        File file2 = null;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            file = new File(getRealPathFromURIPath(Uri.parse(filepath)));
            file2 = new File(getRealPathFromURIPath(Uri.parse(filepath2)));
        } else {
            file = new File(filepath);
            file2 = new File(filepath2);
        }

        RequestBody reqFile = null;
        RequestBody reqFile2 = null;
        MultipartBody.Part body = null;
        MultipartBody.Part body2 = null;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            reqFile2 = RequestBody.create(MediaType.parse("image/*"), file2);
            Log.v(TAG, "FILE :" + file.getName() + ", FILE2 :" + file2.getName());
            body = MultipartBody.Part.createFormData("govIdImage", file.getName(), reqFile);
            body2 = MultipartBody.Part.createFormData("selfieIdImage", file2.getName(), reqFile2);
        } else {
            reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            reqFile2 = RequestBody.create(MediaType.parse("image/*"), file2);
            body = MultipartBody.Part.createFormData("govIdImage", file.getName(), reqFile);
            body2 = MultipartBody.Part.createFormData("selfieIdImage", file2.getName(), reqFile2);
        }
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), Variables.email);

        Call<Void> signUpImageCall = userService.signUpImages(body, body2, email);
        final ProgressDialog pd = Methods.showProgress(getActivity(), "SENDING APPLICATION...");
        pd.show();
        signUpImageCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.isSuccessful()) {
                    pd.dismiss();
                    Log.v(TAG, "Successfully uploaded images");
                    rlChild.setVisibility(View.GONE);
                    rlThankyou.setVisibility(View.VISIBLE);
                    btnDone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Variables.stepThree = false;
                            Variables.stepFour = false;
                            Variables.stepFive = false;
                            Variables.stepSix = false;
                            Variables.stepSven = false;
                            Variables.stepEight = false;
                            Variables.stepNine = false;
                            new DBPullProcess(getActivity()).deleteDB();
                            Intent startNewActivity = new Intent(getActivity(), LoginActivity.class);
                            getActivity().startActivity(startNewActivity);
                            getActivity().finish();
                        }
                    });
                } else {
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                pd.dismiss();
                Log.v(TAG, "onFailure: " + t.getMessage());
                t.printStackTrace();
                t.getCause();
                t.getLocalizedMessage();

                Log.v(TAG, "Failed to upload images.");
                Methods.showPopup(getActivity(), "Request timeout", "Failed to upload images.", "OK");
            }
        });
    }

    private void signUpUserRx(User user, String filepath, String filepath2) {

        final ProgressDialog pd = Methods.showProgress(getActivity(), "Sending Application...", "This may take several minutes to complete.");
        pd.setCancelable(false);
        pd.show();

        compositeDisposable.add(new UserRxService().signUpUser(user)
                .subscribeOn(Schedulers.io())
                .flatMap(userResultMessage -> {

                    if(userResultMessage.getResponseCode().equals("200")) {
                        User userModel = userResultMessage.getBody();
                        Log.d(TAG, "loginUserRx: name: " + userModel.getFullName());

                        File file;
                        File file2;
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                            file = new File(getRealPathFromURIPath(Uri.parse(filepath)));
                            file2 = new File(getRealPathFromURIPath(Uri.parse(filepath2)));
                        } else {
                            file = new File(filepath);
                            file2 = new File(filepath2);
                        }

                        RequestBody reqFile;
                        RequestBody reqFile2;
                        MultipartBody.Part body;
                        MultipartBody.Part body2;
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                            reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                            reqFile2 = RequestBody.create(MediaType.parse("image/*"), file2);
                            Log.v(TAG, "FILE :" + file.getName() + ", FILE2 :" + file2.getName());
                            body = MultipartBody.Part.createFormData("govIdImage", file.getName(), reqFile);
                            body2 = MultipartBody.Part.createFormData("selfieIdImage", file2.getName(), reqFile2);
                        } else {
                            reqFile = RequestBody.create(MediaType.parse("image/*"), file);
                            reqFile2 = RequestBody.create(MediaType.parse("image/*"), file2);
                            body = MultipartBody.Part.createFormData("govIdImage", file.getName(), reqFile);
                            body2 = MultipartBody.Part.createFormData("selfieIdImage", file2.getName(), reqFile2);
                        }

                        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), userModel.getEmail());

                        return new UserRxService().signUpUserImages(body, body2, email);
                    } else {

                        return Maybe.error(new Exception(userResultMessage.getResponseMessage()));
                    }

                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(documentImageResultMessage -> {

                    if (documentImageResultMessage.getResponseCode().equals("200")) {
                        pd.dismiss();
                        Log.v(TAG, "Successfully uploaded images");
                        rlChild.setVisibility(View.GONE);
                        rlThankyou.setVisibility(View.VISIBLE);
                        btnDone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Variables.stepThree = false;
                                Variables.stepFour = false;
                                Variables.stepFive = false;
                                Variables.stepSix = false;
                                Variables.stepSven = false;
                                Variables.stepEight = false;
                                Variables.stepNine = false;
                                new DBPullProcess(getActivity()).deleteDB();
                                Intent startNewActivity = new Intent(getActivity(), LoginActivity.class);
                                getActivity().startActivity(startNewActivity);
                                getActivity().finish();
                            }
                        });
                    } else {
                        pd.dismiss();

//                        pd.dismiss();
//                        Methods.showPopup(getActivity(), "Request failed", userResultMessage.getResponseMessage(), "OK");

                        Log.v(TAG, "Failed to upload images.");
                        Methods.showPopup(getActivity(), "Request timeout", "Failed to upload images.", "OK");
                    }

                    Log.d(TAG, "signUpUserRx: process complete!");
                }, error -> {
                    pd.dismiss();

                    Log.e(TAG, "loginUserRx: ", error);
                    Log.e(TAG, error.getMessage());
                    Methods.showPopup(getActivity(), "Oops!", "Something went wrong.", "OK");

                    Log.e(TAG, "signUpUserRx: ", error);
                }));

    }


    private String getRealPathFromURIPath(Uri contentURI) {
        Cursor cursor = getActivity().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private String decryptAESCrypt(String str) {
        String decrypted = "";
        try {
            decrypted = AESCrypt.decrypt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return decrypted;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear();
    }
}
