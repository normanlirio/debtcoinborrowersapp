package com.debtcoin.debtcoinapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
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
import android.widget.Toast;

import com.debtcoin.debtcoinapp.API.model.ConfirmationCode;
import com.debtcoin.debtcoinapp.API.model.ResultMessage;
import com.debtcoin.debtcoinapp.API.service.DebtcoinServiceGenerator;
import com.debtcoin.debtcoinapp.API.service.EmailService;
import com.debtcoin.debtcoinapp.Activities.AgreementActivity;
import com.debtcoin.debtcoinapp.Globals.Methods;
import com.debtcoin.debtcoinapp.Globals.Variables;
import com.debtcoin.debtcoinapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepTwo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepTwo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepTwo extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btnNext;
    private static final int CODE_SIZE = 6;
    private boolean delete = false;
    private String text;
    private OnFragmentInteractionListener mListener;
    private EditText etCode1, etCode2, etCode3, etCode4, etCode5, etCode6;
    private RelativeLayout container;
    private String TAG = "Step 2";
    private TextView tvMsg;

    public StepTwo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StepTwo.
     */
    // TODO: Rename and change types and number of parameters
    public static StepTwo newInstance(String param1, String param2) {
        StepTwo fragment = new StepTwo();
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
        return inflater.inflate(R.layout.fragment_step_two, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvStep = view.findViewById(R.id.text_customactionbar_title);
        ImageView ivArrow = view.findViewById(R.id.img_customactionbar_img);
        tvStep.setText(getResources().getString(R.string.steptwo));
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity) getActivity()).getSupportFragmentManager().popBackStack();
            }
        });

        btnNext = view.findViewById(R.id.button_next);

        etCode1 = view.findViewById(R.id.edit_steptwo_codes_1);
        etCode2 = view.findViewById(R.id.edit_steptwo_codes_2);
        etCode3 = view.findViewById(R.id.edit_steptwo_codes_3);
        etCode4 = view.findViewById(R.id.edit_steptwo_codes_4);
        etCode5 = view.findViewById(R.id.edit_steptwo_codes_5);
        etCode6 = view.findViewById(R.id.edit_steptwo_codes_6);
        tvMsg = view.findViewById(R.id.text_steptwo_codemsg);

        etCode1.addTextChangedListener(new GenericTextWatcher(etCode1));
        etCode2.addTextChangedListener(new GenericTextWatcher(etCode2));
        etCode3.addTextChangedListener(new GenericTextWatcher(etCode3));
        etCode4.addTextChangedListener(new GenericTextWatcher(etCode4));
        etCode5.addTextChangedListener(new GenericTextWatcher(etCode5));
        etCode6.addTextChangedListener(new GenericTextWatcher(etCode6));

        keyListners();


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    confirmCode(code);
                }

            }
        });
        tvMsg.setText("Insertion of this Code confirms that you understand and agree to Debtcoin ");
        SpannableString tnc = makeLinkSpan("Terms and Conditions", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // respond to click
                Intent agreemnt = new Intent(getActivity(), AgreementActivity.class);
                agreemnt.putExtra("tab", "tnc");
                startActivity(agreemnt);
            }
        });
        tnc.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.md_white_1000)), 0, tnc.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvMsg.append(tnc);
        tvMsg.append(" and ");
        SpannableString privacy = makeLinkSpan("Privacy Policy", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // respond to click
                Intent agreemnt = new Intent(getActivity(), AgreementActivity.class);
                agreemnt.putExtra("tab", "privacy");
                startActivity(agreemnt);
            }
        });

        privacy.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.md_white_1000)), 0, privacy.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvMsg.append(privacy);
        tvMsg.setTextColor(getResources().getColor(R.color.md_white_1000));
        makeLinksFocusable(tvMsg);
        container = view.findViewById(R.id.steptwo_container);
        Methods.setupUI(getActivity(), container);
    }

    private void keyListners() {
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

    private SpannableString makeLinkSpan(CharSequence text, View.OnClickListener listener) {
        SpannableString link = new SpannableString(text);
        link.setSpan(new ClickableString(listener), 0, text.length(),
                SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        return link;
    }

    private void makeLinksFocusable(TextView tv) {
        MovementMethod m = tv.getMovementMethod();
        if ((m == null) || !(m instanceof LinkMovementMethod)) {
            if (tv.getLinksClickable()) {
                tv.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
    }

    public  class ClickableString extends ClickableSpan {
        private View.OnClickListener mListener;

        public ClickableString(View.OnClickListener listener) {
            mListener = listener;
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }
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

    private void confirmCode(String code) {
        EmailService emailService = DebtcoinServiceGenerator.createService(EmailService.class);
        ConfirmationCode confirmationCode = new ConfirmationCode();
        confirmationCode.setEmail(Variables.email);
        confirmationCode.setCode(code.toUpperCase());
        Log.v(TAG, "CODE : " + code);
        Call<ResultMessage<ConfirmationCode>> confirmCode = emailService.confirmCode(confirmationCode);
        final ProgressDialog pd = Methods.showProgress(getActivity(), "Confirming code... Please wait");
        pd.show();
        confirmCode.enqueue(new Callback<ResultMessage<ConfirmationCode>>() {
            @Override
            public void onResponse(Call<ResultMessage<ConfirmationCode>> call, Response<ResultMessage<ConfirmationCode>> response) {
                if (response.isSuccessful()) {
                    pd.dismiss();
                    ResultMessage<ConfirmationCode> resultMessage = response.body();
                    Log.v("", resultMessage.getResponseMessage());
                    ConfirmationCode confirmationCode1 = resultMessage.getBody();
                    try {
                        if(confirmationCode1 != null) {
                            if (confirmationCode1.isConfirmed()) {
                                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.frame_login_fragmentcontainer, new StepThree());
                                ft.commit();
                            }
                        } else {
                            Methods.showPopup(getActivity(), "OOPS!", "Invalid confirmation code, Please try again.", "OK");
                        }

                    } catch (NullPointerException npe) {
                        npe.printStackTrace();
                        Methods.showPopup(getActivity(), "OOPS!", "Invalid confirmation code, Please try again.", "OK");
                    }


                }
            }

            @Override
            public void onFailure(Call<ResultMessage<ConfirmationCode>> call, Throwable t) {
                pd.dismiss();
                Methods.showPopup(getActivity(), "Error", "Please check your internet connection", "OK");
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

                case R.id.edit_steptwo_codes_1:
                    if (text.length() == 1) {
                        etCode2.requestFocus();
                    }


                    break;
                case R.id.edit_steptwo_codes_2:
                    if (text.length() == 1) {
                        etCode3.requestFocus();
                    }

                    break;
                case R.id.edit_steptwo_codes_3:
                    if (text.length() == 1) {
                        etCode4.requestFocus();
                    }

                    break;
                case R.id.edit_steptwo_codes_4:
                    if (text.length() == 1) {
                        etCode5.requestFocus();
                    }

                    break;
                case R.id.edit_steptwo_codes_5:
                    if (text.length() == 1) {
                        etCode6.requestFocus();
                    }

                    break;
                case R.id.edit_steptwo_codes_6:
                    if (text.length() == 1) {

                    }

                    break;
            }
        }
    }
}
