package com.debtcoin.debtcoinapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.debtcoin.debtcoinapp.API.model.LoanDetails;
import com.debtcoin.debtcoinapp.API.model.Notice;
import com.debtcoin.debtcoinapp.API.service.rx.LoanRxService;
import com.debtcoin.debtcoinapp.Activities.MainActivity;
import com.debtcoin.debtcoinapp.Adapter.OverviewNoticesAdapter;
import com.debtcoin.debtcoinapp.Globals.APIVariables;
import com.debtcoin.debtcoinapp.Globals.Methods;
import com.debtcoin.debtcoinapp.Globals.Variables;
import com.debtcoin.debtcoinapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Overview.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Overview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Overview extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Unbinder unbinder;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static final String TAG = "Overview";

    TextView tvCurrentLoanAmount, tvTerms, tvTotalPayableAmount, tvNumOfPayments, tvNumOfPaymentsMade, tvTotalAmountDue;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.relative_overview_container)
    RelativeLayout rlContainer;
    @BindView(R.id.recycler_overview_notice)
    RecyclerView rvNotices;
    @BindView(R.id.text_overview_amntdue_notice)
    TextView tvAmountDueNotice;
    @BindView(R.id.text_overview_notice_label)
    TextView tvNoticeLabel;

    public Overview() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Overview.
     */
    // TODO: Rename and change types and number of parameters
    public static Overview newInstance(String param1, String param2) {
        Overview fragment = new Overview();
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
        View view =  inflater.inflate(R.layout.fragment_overview, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Methods.customActionBar(getActivity(), "Overview");
        tvNoticeLabel.setVisibility(View.GONE);
        tvCurrentLoanAmount = view.findViewById(R.id.text_overview_amount);
        tvTerms = view.findViewById(R.id.text_overview_terms);
        tvTotalPayableAmount = view.findViewById(R.id.text_overview_payable_amount);
        tvNumOfPayments = view.findViewById(R.id.text_overview_num_payments_days);
        tvNumOfPaymentsMade = view.findViewById(R.id.text_overview_num_payments_made);
        tvTotalAmountDue = view.findViewById(R.id.text_overview_amount_due);

        final ProgressDialog pd = Methods.showProgress(getActivity(), "Loading...");
        pd.show();

        compositeDisposable.add(new LoanRxService(Variables.token)
                .getLoanDetailsByUsername(APIVariables.username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loanDetailsResultMessage -> {
                    pd.dismiss();

                    if (loanDetailsResultMessage.getResponseCode().equals("200")) {

                        LoanDetails loanDetails = loanDetailsResultMessage.getBody();
                        tvCurrentLoanAmount.setText(String.format(Locale.US, "Php %,.2f", loanDetails.getCurrentLoanAmount()));
                        tvTerms.setText(String.valueOf(loanDetails.getTerms()));
                        tvTotalPayableAmount.setText(String.format(Locale.US, "Php %,.2f", loanDetails.getTotalPayableAmount()));
                        tvNumOfPayments.setText(String.valueOf(loanDetails.getNumOfPayments()));
                        tvNumOfPaymentsMade.setText(String.valueOf(loanDetails.getNumOfPaymentsMade()));
                        tvTotalAmountDue.setText(String.format(Locale.US, "Php %,.2f", loanDetails.getTotalAmountDue()));
                        //TODO :
                        tvAmountDueNotice.setText("Payment Due Date: " + loanDetails.getDueDate());
                        APIVariables.currentPaymentDueDate = loanDetails.getDueDate();

                        Log.d(TAG, "onViewCreated: Loan details Due Date: " +loanDetails.getDueDate());

//                        List<Notice> notices = loanDetails.getNotices();
//
//                        Log.d(TAG, "onViewCreated: Size of Notices: " + notices.size());
//
//                        if (notices.size() > 0) {
//                            Log.d(TAG, "onViewCreated: Notices");
//                            ArrayList<String> noticesStr = new ArrayList<>();
//
//                            for (Notice notice : notices) {
//                                Log.d(TAG, "onViewCreated: Notice: " + notice.getContent());
//                                noticesStr.add(notice.getContent());
//                            }
//
//                            OverviewNoticesAdapter adapter = new OverviewNoticesAdapter(getActivity(),
//                                    noticesStr);
//                            rvNotices.setAdapter(adapter);
//                        }



                    } else {
                        Toast.makeText(getActivity(), loanDetailsResultMessage.getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
                    pd.dismiss();
                    Log.e(TAG, "onViewCreated: ", error);
                }));
        rvNotices.setLayoutManager(new LinearLayoutManager(getActivity()));

//        OverviewNoticesAdapter adapter = new OverviewNoticesAdapter(getActivity(), APIVariables.loanDetailsNotices);
//        rvNotices.setAdapter(adapter);

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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear();
    }
}
