package com.debtcoin.debtcoinapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.debtcoin.debtcoinapp.API.model.AccountReset;
import com.debtcoin.debtcoinapp.API.service.rx.AccountResetRxService;
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
 * {@link ForgotVerification.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ForgotVerification#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgotVerification extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btnNext;
    private EditText etCode1, etCode2, etCode3, etCode4, etCode5, etCode6;
    private static final String TAG = "ForgotVerification";

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private OnFragmentInteractionListener mListener;
    private Unbinder unbinder;

    @BindView(R.id.relative_forgotverification_container)
    RelativeLayout rlContainer;

    public ForgotVerification() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgotVerification.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgotVerification newInstance(String param1, String param2) {
        ForgotVerification fragment = new ForgotVerification();
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
        View view = inflater.inflate(R.layout.fragment_forgot_verification, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Methods.setupUI(getActivity(), rlContainer);
        TextView tvStep = view.findViewById(R.id.text_customactionbar_title);
        ImageView ivArrow = view.findViewById(R.id.img_customactionbar_img);
        tvStep.setText("Verification");
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Methods.goBack(getActivity());
            }
        });

        btnNext = view.findViewById(R.id.button_next);

        etCode1 = view.findViewById(R.id.edit_forgot_codes_1);
        etCode2 = view.findViewById(R.id.edit_forgot_codes_2);
        etCode3 = view.findViewById(R.id.edit_forgot_codes_3);
        etCode4 = view.findViewById(R.id.edit_forgot_codes_4);
        etCode5 = view.findViewById(R.id.edit_forgot_codes_5);
        etCode6 = view.findViewById(R.id.edit_forgot_codes_6);

        etCode1.addTextChangedListener(new GenericTextWatcher(etCode1));
        etCode2.addTextChangedListener(new GenericTextWatcher(etCode2));
        etCode3.addTextChangedListener(new GenericTextWatcher(etCode3));
        etCode4.addTextChangedListener(new GenericTextWatcher(etCode4));
        etCode5.addTextChangedListener(new GenericTextWatcher(etCode5));
        etCode6.addTextChangedListener(new GenericTextWatcher(etCode6));

        keyListners();

        btnNext.setOnClickListener(v -> {

            String code1 = etCode1.getText().toString().trim();
            String code2 = etCode2.getText().toString().trim();
            String code3 = etCode3.getText().toString().trim();
            String code4 = etCode4.getText().toString().trim();
            String code5 = etCode5.getText().toString().trim();
            String code6 = etCode6.getText().toString().trim();

            Log.v(TAG, code1+code2+code3+code4+code5+code6);
            String code = code1+code2+code3+code4+code5+code6;
            if(code.length() < 6) {
                Methods.showPopup(getActivity(), "Error", "Confirmation code should be at least 6 characters", "OK");
            } else if (code.length() == 6 /*&& Methods.isOnline(getActivity())*/) {
                // Methods.switchFragment(getActivity(), new StepThree());

                final ProgressDialog pd = Methods.showProgress(getActivity(), "");
                pd.show();

                AccountReset accountReset = new AccountReset();
                accountReset.setCode(code);
                accountReset.setEmail(APIVariables.resetEmail);
                accountReset.setPasswordResetToken(APIVariables.resetToken);

                compositeDisposable.add(new AccountResetRxService()
                        .confirmResetCode(accountReset)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(accountResetResultMessage -> {
                            pd.dismiss();

                            if (accountResetResultMessage.getResponseCode().equals("200")) {
                                APIVariables.resetCode = accountResetResultMessage.getBody().getCode();
                                Methods.switchFragment(getActivity(), new NewPassword());

                            } else {
                                Log.d(TAG, "onViewCreated: Error: " + accountResetResultMessage.getResponseMessage());
                                Methods.showPopup(getActivity(), "Error", accountResetResultMessage.getResponseMessage(), "OK");
                            }

                        }, error -> {
                            pd.dismiss();
                            Methods.showPopup(getActivity(), "Error", error.getMessage(), "OK");
                            Log.e(TAG, "onViewCreated: ", error);
                        }));
            }

         //   Methods.switchFragment(getActivity(), new NewPassword());
        });

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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

    private void keyListners() {
        Log.v("KEY LISTENER", "PASOK!");
        etCode2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DEL){
                    //on backspace
                    if(etCode2.getText().toString().length() < 1) {
                        etCode2.setText("");
                        etCode1.requestFocus();
                    }

                    //    Toast.makeText(getActivity(), "Backspace is pressed.", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        etCode3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                int count = 0;
                if(keyCode == KeyEvent.KEYCODE_DEL){
                    //on backspace

                    if(etCode3.getText().toString().length() < 1) {
                        etCode3.setText("");
                        etCode2.requestFocus();
                    }
                    //   Toast.makeText(getActivity(), "Backspace is pressed.", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        etCode4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DEL){
                    //on backspace
                    if(etCode4.getText().toString().length() < 1) {
                        etCode4.setText("");
                        etCode3.requestFocus();
                    }
                    //  Toast.makeText(getActivity(), "Backspace is pressed.", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        etCode5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DEL){
                    //on backspace
                    if(etCode5.getText().toString().length() < 1) {
                        etCode5.setText("");
                        etCode4.requestFocus();
                    }
                    // Toast.makeText(getActivity(), "Backspace is pressed.", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        etCode6.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DEL){
                    //on backspace
                    if(etCode6.getText().toString().length() < 1) {
                        etCode6.setText("");
                        etCode5.requestFocus();
                    }
                    //  Toast.makeText(getActivity(), "Backspace is pressed.", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

    }

    public class GenericTextWatcher implements TextWatcher {
        private View view;

        public GenericTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String text = s.toString();

            switch (view.getId()) {

                case R.id.edit_forgot_codes_1:
                    if (text.length() == 1) {
                        etCode2.requestFocus();
                    }


                    break;
                case R.id.edit_forgot_codes_2:
                    if (text.length() == 1) {
                        etCode3.requestFocus();
                    }

                    break;
                case R.id.edit_forgot_codes_3:
                    if (text.length() == 1) {
                        etCode4.requestFocus();
                    }

                    break;
                case R.id.edit_forgot_codes_4:
                    if (text.length() == 1) {
                        etCode5.requestFocus();
                    }

                    break;
                case R.id.edit_forgot_codes_5:
                    if (text.length() == 1) {
                        etCode6.requestFocus();
                    }

                    break;
                case R.id.edit_forgot_codes_6:
                    if (text.length() == 1) {

                    }

                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear();
    }
}
