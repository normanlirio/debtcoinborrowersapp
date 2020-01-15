package com.debtcoin.debtcoinapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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
import com.debtcoin.debtcoinapp.API.model.Mail;
import com.debtcoin.debtcoinapp.API.model.ResultMessage;
import com.debtcoin.debtcoinapp.API.service.DebtcoinServiceGenerator;
import com.debtcoin.debtcoinapp.API.service.EmailService;
import com.debtcoin.debtcoinapp.Activities.LoginActivity;
import com.debtcoin.debtcoinapp.Database.DBHandler;
import com.debtcoin.debtcoinapp.Globals.Methods;
import com.debtcoin.debtcoinapp.Globals.Variables;
import com.debtcoin.debtcoinapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepOne.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepOne#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepOne extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button btnNext;
    private RelativeLayout container;
    @BindView(R.id.edit_stepone_email)
    EditText etEmail;
    private Unbinder unbinder;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String TAG = "STEP ONE";
    private OnFragmentInteractionListener mListener;

    public StepOne() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StepOne.
     */
    // TODO: Rename and change types and number of parameters
    public static StepOne newInstance(String param1, String param2) {
        StepOne fragment = new StepOne();
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
       View view = inflater.inflate(R.layout.fragment_step_one, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvStep = view.findViewById(R.id.text_customactionbar_title);
        ImageView ivArrow = view.findViewById(R.id.img_customactionbar_img);
        tvStep.setText(getResources().getString(R.string.stepone));
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(getActivity(), LoginActivity.class);
                startActivity(login);
                getActivity().finish();
            }
        });
        new DBHandler(getActivity());

        btnNext = view.findViewById(R.id.button_next);
       // etEmail.setText("a@gaaaa2asa.com");
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                if(email.length() > 0 && isValidEmail(email) ) {
                    Variables.email = email;
                   // Methods.switchFragment(getActivity(), new StepThree());
                   sendConfirmationCodeToEmail(etEmail.getText().toString().trim());
                } else {
                   Methods.showPopup(getActivity(), "Warning", "Please input a valid email", "OK");
                }

            }
        });

        container = view.findViewById(R.id.stepone_container);
        Methods.setupUI(getActivity(),container);
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void sendConfirmationCodeToEmail(String email) {

        EmailService service = DebtcoinServiceGenerator.createService(EmailService.class);
        Mail mail = new Mail();
        mail.setTo(email);
      //  Log.v(TAG, "EMAIL : " + email);
        Call<ResultMessage<ConfirmationCode>> callSync = service.getConfirmationCode(mail);
        final ProgressDialog pd = Methods.showProgress(getActivity(), "Sending code via email...","Please wait") ;
        pd.show();
        callSync.enqueue(new Callback<ResultMessage<ConfirmationCode>>() {
            @Override
            public void onResponse(Call<ResultMessage<ConfirmationCode>> call, Response<ResultMessage<ConfirmationCode>> response) {
                pd.dismiss();
                try {

                    ResultMessage<ConfirmationCode> confirmationCodeResultMessage = response.body();
                    String responseCode = confirmationCodeResultMessage.getResponseCode();
                    if(responseCode.equalsIgnoreCase("200")) {
                        ConfirmationCode confirmationCode = confirmationCodeResultMessage.getBody();
                        Log.d(TAG, "onResponse: confirmation code: " + confirmationCode.getCode());
                        Methods.switchFragment(getActivity(), new StepTwo());
                    } else if (responseCode.equalsIgnoreCase("422")) {
                        Methods.showPopup(getActivity(), "Error", confirmationCodeResultMessage.getResponseMessage(), "OK");
                    }
                    Log.d(TAG, "RESPONSE MSG: " + confirmationCodeResultMessage.getResponseMessage());
                  //  Log.d(TAG, "RESPONSE CODE: " + confirmationCodeResultMessage.getResponseCode());



                }catch (NullPointerException npe) {
                    npe.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResultMessage<ConfirmationCode>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                pd.dismiss();
                Methods.showPopup(getActivity(), "Error", "Please check your internet connection", "OK");
            }
        });

    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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


}
