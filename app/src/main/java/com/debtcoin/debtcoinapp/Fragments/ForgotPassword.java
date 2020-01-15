package com.debtcoin.debtcoinapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.debtcoin.debtcoinapp.API.model.AccountReset;
import com.debtcoin.debtcoinapp.API.model.Mail;
import com.debtcoin.debtcoinapp.API.service.rx.AccountResetRxService;
import com.debtcoin.debtcoinapp.Activities.LoginActivity;
import com.debtcoin.debtcoinapp.Globals.APIVariables;
import com.debtcoin.debtcoinapp.Globals.Methods;
import com.debtcoin.debtcoinapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ForgotPassword.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ForgotPassword#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgotPassword extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Unbinder unbinder;

    private static final String TAG = "ForgotPassword";

    @BindView(R.id.edit_forgot_pass_email)
    EditText etEmail;
    @BindView(R.id.edit_forgot_pass_userid)
    EditText etUserId;
//    @BindView(R.id.checkbox_forgot_pass_forgotid)
//    CheckBox cbForgotId;
    @BindView(R.id.button_next)
    Button btnNext;
    @BindView(R.id.linear_forgot_pass_emailcontainer)
    LinearLayout llEmailContainer;
    @BindView(R.id.relative_forgot_pass_container)
    RelativeLayout rlContainer;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private boolean isForgotId = false;

    public ForgotPassword() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgotPassword.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgotPassword newInstance(String param1, String param2) {
        ForgotPassword fragment = new ForgotPassword();
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
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvStep = view.findViewById(R.id.text_customactionbar_title);
        ImageView ivArrow = view.findViewById(R.id.img_customactionbar_img);
        tvStep.setText(getResources().getString(R.string.login_forgot_pass));
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(getActivity(), LoginActivity.class);
                startActivity(login);
                getActivity().finish();
            }
        });
        Methods.setupUI(getActivity(), rlContainer);

        btnNext.setOnClickListener(v -> {

            final ProgressDialog pd = Methods.showProgress(getActivity(), "");
            pd.show();

            Mail mail = new Mail(etEmail.getText().toString());

            compositeDisposable.add(new AccountResetRxService()
                    .generateForgotPasswordConfirmationCode(mail)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(accountResetResultMessage -> {
                        pd.dismiss();
                        if (accountResetResultMessage.getResponseCode().equals("200")) {

                            AccountReset accountReset = accountResetResultMessage.getBody();
                            APIVariables.resetEmail = accountReset.getEmail();
                            APIVariables.resetToken = accountReset.getPasswordResetToken();

                            Methods.switchFragment(getActivity(), new ForgotVerification());
                        } else {

                            Methods.showPopup(getActivity(), "Error", accountResetResultMessage.getResponseMessage(), "OK");
                            Log.d(TAG, "onViewCreated: Error: " + accountResetResultMessage.getResponseMessage());
                        }
                    }, error -> {
                        pd.dismiss();
                        Methods.showPopup(getActivity(), "Error", error.getMessage(), "OK");
                        Log.e(TAG, "onViewCreated: ", error);
                    }));

                        //  Methods.switchFragment(getActivity(), new ForgotVerification());
        });
//        cbForgotId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    isForgotId = true;
//                    llEmailContainer.setVisibility(View.GONE);
//
//                } else {
//                    isForgotId = false;
//                    llEmailContainer.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//
//        if(isForgotId) {
//            btnNext.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Methods.switchFragment(getActivity(), new ForgotVerification());
//                }
//            });
//        } else {
//            btnNext.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Methods.showPopup(getActivity(), "", "We have sent an email containing your User ID.", "OK");
//                }
//            });
//        }
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

    @Override
    public void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear();
    }
}
