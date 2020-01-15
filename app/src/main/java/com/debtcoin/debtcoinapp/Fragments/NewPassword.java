package com.debtcoin.debtcoinapp.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.debtcoin.debtcoinapp.API.model.AccountReset;
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
 * {@link NewPassword.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewPassword#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewPassword extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText password;
    private EditText confirmPassword;
    private Button btnNext;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private static final String TAG = "NewPassword";
    private OnFragmentInteractionListener mListener;
    private Unbinder unbinder;

    @BindView(R.id.relative_newpassword_container)
    RelativeLayout rlContainer;
    public NewPassword() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewPassword.
     */
    // TODO: Rename and change types and number of parameters
    public static NewPassword newInstance(String param1, String param2) {
        NewPassword fragment = new NewPassword();
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
        View view =  inflater.inflate(R.layout.fragment_new_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Methods.setupUI(getActivity(), rlContainer);
        TextView tvStep = view.findViewById(R.id.text_customactionbar_title);
        ImageView ivArrow = view.findViewById(R.id.img_customactionbar_img);
        tvStep.setText("New Password");
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Methods.goBack(getActivity());
            }
        });
        password = view.findViewById(R.id.edit_forgot_pass);
        confirmPassword = view.findViewById(R.id.edit_forgot_pass_confirm);
        btnNext = view.findViewById(R.id.button_next);

        btnNext.setOnClickListener(v -> {

            String passwordStr = password.getText().toString();
            String confirmPasswordStr = confirmPassword.getText().toString();

            if(password.length() > 0 && password.length() < 6) {
                Methods.showPopup(getActivity(), "Error", "Password should be at least 6 characters", "OK");
            } else if(!passwordStr.equals(confirmPasswordStr)) {
                Methods.showPopup(getActivity(), "Error", "Password must be matched", "OK");
            } else {

                final ProgressDialog pd = Methods.showProgress(getActivity(), "");
                pd.show();

                AccountReset accountReset = new AccountReset();
                accountReset.setCode(APIVariables.resetCode);
                accountReset.setEmail(APIVariables.resetEmail);
                accountReset.setPasswordResetToken(APIVariables.resetToken);
                accountReset.setNewPassword(passwordStr);

                compositeDisposable.add(new AccountResetRxService()
                        .changePassword(accountReset)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(resultMessage -> {

                            pd.dismiss();

                            if (resultMessage.getResponseCode().equals("200")) {
                              //  Toast.makeText(getActivity(), resultMessage.getResponseMessage(), Toast.LENGTH_SHORT).show();
                                showPopup(getActivity(), resultMessage.getResponseMessage(), "Continue");
                                APIVariables.clearForgotPasswordVars();
                            } else {
                                Methods.showPopup(getActivity(), "Error", resultMessage.getResponseMessage(), "OK");
                            }

                        }, error -> {
                            Log.e(TAG, "onViewCreated: ", error);
                            Methods.showPopup(getActivity(), "Error", error.getMessage(), "OK");
                        }));

            }
        });
    }

    private void showPopup(Activity activity, String message, String okBtn) {
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
        tvHeader.setVisibility(View.GONE);
        ivIcon.setVisibility(View.GONE);
        tvMsg.setText(message);
        tvCancel.setVisibility(View.GONE);
        btnOk.setText(okBtn);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert2.dismiss();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });


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
