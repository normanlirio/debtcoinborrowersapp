package com.debtcoin.debtcoinapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.debtcoin.debtcoinapp.API.model.LoanApplication;
import com.debtcoin.debtcoinapp.API.service.rx.LoanRxService;
import com.debtcoin.debtcoinapp.Globals.APIVariables;
import com.debtcoin.debtcoinapp.Globals.Methods;
import com.debtcoin.debtcoinapp.Globals.Variables;
import com.debtcoin.debtcoinapp.R;
import com.debtcoin.debtcoinapp.Util.GsonParser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ApplyLoanDetails.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ApplyLoanDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplyLoanDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Unbinder unbinder;
    private OnFragmentInteractionListener mListener;

    @BindView(R.id.text_applyloandetails_next)
    TextView tvNext;
    @BindView(R.id.text_applyloandetails_prev)
    TextView tvBack;
    @BindView(R.id.spinner_applyloandetails_reason)
    Spinner spReasons;
    @BindView(R.id.edit_applyloandetails_details)
    EditText etDetails;
    @BindView(R.id.relative_applyloan_details_container)
    RelativeLayout rlContainer;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    LoanApplication loanApplication;
    public ApplyLoanDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApplyLoanDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static ApplyLoanDetails newInstance(String param1, String param2) {
        ApplyLoanDetails fragment = new ApplyLoanDetails();
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

            String loanApplicationJsonString = getArguments().getString("loanApplication");
            if (loanApplicationJsonString != null) {
                loanApplication = GsonParser.parse().fromJson(loanApplicationJsonString, LoanApplication.class);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_apply_loan_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Methods.customActionBar(getActivity(), "Apply for a Loan");
        Methods.setupUI(getActivity(), rlContainer);

        ArrayAdapter reasons = new ArrayAdapter(getActivity(), R.layout.layout_spinner_applyloan, getResources().getStringArray(R.array.dropdown_reasons));
        spReasons.setAdapter(reasons);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Variables.hasPressedNext = false;
                if(!Methods.isEmpty(etDetails)) {
                    ProgressDialog progressDialog = Methods.showProgress(getActivity(), "Sending Loan Application...");
                    progressDialog.show();
                    loanApplication.setReasonForLoan(spReasons.getSelectedItem().toString());
                    loanApplication.setReasonDetails(etDetails.getText().toString());

                    compositeDisposable.add(new LoanRxService(Variables.token)
                            .applyForLoan(APIVariables.username, loanApplication)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(loanApplicationResultMessage -> {
                                if (loanApplicationResultMessage.getResponseCode().equals("200")) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), loanApplicationResultMessage.getResponseMessage(), Toast.LENGTH_SHORT).show();
                                    Methods.switchFragmentLoggedIn(getActivity(), new ApplyLoanConfirmation());
                                } else {
                                    Methods.showPopup(getActivity(), "Oops!", loanApplicationResultMessage.getResponseMessage(), "OK");
                                    Methods.showPopup(getActivity(), "Oops!", "Something went wrong", "OK");
                                    Log.e("Apply loan", loanApplicationResultMessage.getResponseMessage());
                                }

                            }, error -> {
                                progressDialog.dismiss();
                                Methods.showPopup(getActivity(), "Oops!", "Something went wrong", "OK");
                                Log.e("Apply loan", error.getMessage());
                            }));


                } else {
                    Methods.showPopup(getActivity(), "Oops!", "All fields are required.", "OK");
                }



            }
        });

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Methods.goBack(getActivity());
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
