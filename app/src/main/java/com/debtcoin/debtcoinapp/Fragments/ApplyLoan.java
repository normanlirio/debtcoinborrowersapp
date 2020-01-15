package com.debtcoin.debtcoinapp.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.debtcoin.debtcoinapp.API.model.AmortizationSchedule;
import com.debtcoin.debtcoinapp.API.model.LoanApplication;
import com.debtcoin.debtcoinapp.Adapter.AmortizationAdapter;
import com.debtcoin.debtcoinapp.Globals.APIVariables;
import com.debtcoin.debtcoinapp.Globals.Methods;
import com.debtcoin.debtcoinapp.Globals.Variables;
import com.debtcoin.debtcoinapp.Models.Amortization;
import com.debtcoin.debtcoinapp.R;
import com.debtcoin.debtcoinapp.Util.GsonParser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ApplyLoan.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ApplyLoan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplyLoan extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int DAYS_PER_TERM = 15;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Unbinder unbinder;
    private double interest;
    private double amountResult;
    private OnFragmentInteractionListener mListener;
    @BindView(R.id.recycler_applyloan_amortization)
    RecyclerView rvAmortization;
    @BindView(R.id.spinner_applyloan_daystopay)
    Spinner spDays;
    @BindView(R.id.spinner_applyloan_cashouthmethod)
    Spinner spCashOut;
    @BindView(R.id.text_applyloan_next)
    TextView tvNext;
    @BindView(R.id.apply_loan_container)
    RelativeLayout rlContainer;

    @BindView(R.id.edit_applyloan_amount)
    EditText etLoanAmount;

    @BindView(R.id.edit_applyloan_cashoutdate)
    EditText etCashOutDate;

    @BindView(R.id.edit_applyloan_total)
    EditText etTotalPayable;

    @BindView(R.id.img_applyloan_daterange)
    ImageView ivDate;

    @BindView(R.id.checkbox_applyloan_accept)
    CheckBox cbAccept;

    @BindView(R.id.button_applyloan_compute)
    Button btnCompute;

    private Calendar myCalendar;
    private AmortizationAdapter amortizationAdapter;
    private ArrayAdapter daystopay;
    private int terms;
    private ArrayList<AmortizationSchedule> amortizationSchedule;
    private boolean hasChanged = false;
    protected static String currentAmount ="";
    protected static String currentDaysToPay ="";

    public ApplyLoan() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApplyLoan.
     */
    // TODO: Rename and change types and number of parameters
    public static ApplyLoan newInstance(String param1, String param2) {
        ApplyLoan fragment = new ApplyLoan();
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
        View view = inflater.inflate(R.layout.fragment_apply_loan, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Methods.setupUI(getActivity(), rlContainer);
        Methods.customActionBar(getActivity(), "Apply for a Loan");
        rvAmortization.setLayoutManager(new LinearLayoutManager(getActivity()));
        amortizationAdapter = new AmortizationAdapter(getActivity(), filler());
        rvAmortization.setAdapter(amortizationAdapter);
        daystopay = new ArrayAdapter(getActivity(), R.layout.layout_spinner_applyloan, getResources().getStringArray(R.array.dropdown_daystopay));
        ArrayAdapter cashout = new ArrayAdapter(getActivity(), R.layout.layout_spinner_applyloan, getResources().getStringArray(R.array.dropdown_banks));

        spDays.setAdapter(daystopay);
        spCashOut.setAdapter(cashout);
        if(Variables.hasPressedNext) {
            Log.v("HAS PRESSED NEXT", "Terms : "  + Variables.terms + " Amount : " + Variables.amount);
            amortizationAdapter.setAmortizations(Variables.terms, Variables.date, Variables.amount);
        } else {
            Log.v("HAS PRESSED NEXT", "FALSE");
        }

        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isComplete() && !etTotalPayable.getText().toString().equalsIgnoreCase("")) {
                    if (cbAccept.isChecked()) {
                        if (Integer.parseInt(etLoanAmount.getText().toString()) > APIVariables.creditLimit) {
                            Methods.showPopup(getActivity(), "Oops!", "Amount loan should not exceed credit limit.\nCurrent credit limit is : " + String.format(Locale.US, "Php %,.2f", APIVariables.creditLimit), "OK");
                        } else {
                            if(ApplyLoan.currentAmount.equalsIgnoreCase(etLoanAmount.getText().toString()) && ApplyLoan.currentDaysToPay.equalsIgnoreCase(spDays.getSelectedItem().toString())){
                                Variables.terms = terms;
                                Variables.hasPressedNext = true;
                                Variables.amount = amountResult;
                                Variables.date = etCashOutDate.getText().toString();
                                LoanApplication loanApplication = new LoanApplication();
                                loanApplication.setApplicantName(APIVariables.name);
                                loanApplication.setAmount(Float.parseFloat(etLoanAmount.getText().toString()));
                                loanApplication.setTerms(Integer.parseInt(spDays.getSelectedItem().toString()));
                                loanApplication.setCashOutMethod(spCashOut.getSelectedItem().toString());
                                loanApplication.setCashOutDate(etCashOutDate.getText().toString());
                                loanApplication.setTotalPayable(Float.parseFloat(etTotalPayable.getText().toString()));
                                loanApplication.setAmortizationSchedules(setAmortizationSchedule());

                                Fragment fragment = new ApplyLoanDetails();
                                Bundle args = new Bundle();

                                String loanApplicationJsonString = GsonParser.parse().toJson(loanApplication);
                                args.putString("loanApplication", loanApplicationJsonString);
                                fragment.setArguments(args);

                                Methods.switchFragmentLoggedInWithBackStack(getActivity(), fragment);
                            } else {
                                Methods.showPopup(getActivity(), "", "Changing the days or the amount entered will require pressing the Compute button again.", "OK");
                            }

                        }

                    } else {
                        Methods.showPopup(getActivity(), "Oops!", "User acceptance is required.", "OK");
                    }
                } else {
                    Methods.showPopup(getActivity(), "Oops!", "All fields are required.", "OK");
                }

            }
        });

        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() + 24 * 60 * 60 * 1000);

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000));
                datePickerDialog.show();
            }
        });


        etTotalPayable.setEnabled(false);

        btnCompute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!spDays.getSelectedItem().toString().equalsIgnoreCase("") && isComplete()) {
                    if(Integer.parseInt(etLoanAmount.getText().toString()) <= APIVariables.creditLimit) {
                        amortizationAdapter.clearList();
                        int daysToPay = Integer.parseInt(spDays.getSelectedItem().toString());
                        double amountLoan = Double.parseDouble(etLoanAmount.getText().toString());
                        double amount =  computeAmortization(daysToPay / DAYS_PER_TERM, amountLoan);
                        // for displaying adapter again when user pressed the previous button
                        ApplyLoan.currentDaysToPay = spDays.getSelectedItem().toString();
                        ApplyLoan.currentAmount = etLoanAmount.getText().toString();
                        terms = daysToPay / DAYS_PER_TERM;
                        amountResult = amount;
                        //end
                        amortizationAdapter.setAmortizations(daysToPay / DAYS_PER_TERM, etCashOutDate.getText().toString(), amount);
                        etTotalPayable.setText(String.format("%.2f",amount * (daysToPay / DAYS_PER_TERM)));
                    } else {
                        Methods.showPopup(getActivity(), "Oops!", "Amount loan should not exceed credit limit.\nCurrent credit limit is : " + String.format(Locale.US, "Php %,.2f", APIVariables.creditLimit), "OK");
                    }
                } else {
                    Methods.showPopup(getActivity(), "", "All fields are required", "OK");
                }

            }
        });

        etLoanAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String etLoanAmountText = etLoanAmount.getText().toString();
                if(etLoanAmountText.length() <= 5) {

                    currentAmount = etLoanAmountText;
                    if (!etLoanAmountText.equalsIgnoreCase("")) {
                        if (Integer.parseInt(etLoanAmount.getText().toString()) >= 20000) {
                            daystopay = new ArrayAdapter(getActivity(), R.layout.layout_spinner_applyloan, getResources().getStringArray(R.array.dropdown_daystopay20k));
                            spDays.setAdapter(daystopay);
                        } else {
                            daystopay = new ArrayAdapter(getActivity(), R.layout.layout_spinner_applyloan, getResources().getStringArray(R.array.dropdown_daystopay));
                            spDays.setAdapter(daystopay);
                        }
                    } else {
                        daystopay = new ArrayAdapter(getActivity(), R.layout.layout_spinner_applyloan, getResources().getStringArray(R.array.dropdown_daystopay));
                        spDays.setAdapter(daystopay);
                    }
                } else {
                    Methods.showPopup(getActivity(), "Oops!", "Amount too large.", "Close");
                }

            }
        });

        spDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                hasChanged = false;
            }
        });


        setRetainInstance(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    private double computeAmortization(int terms, double loan) {
        double interest = getInterest(loan);
        return (loan / terms) + (loan * interest) / 2;
    }


    private double getInterest(double loan) {
        double interest;
        if (loan < 5001) {
            interest = 0.0967;
        } else if (loan < 15001) {
            interest = 0.0856;
        } else if (loan < 30001) {
            interest = 0.0719;
        } else {
            interest = 0.0649;
        }
        return interest;
    }



    private boolean isComplete() {
        boolean isComplete = true;
        ArrayList<EditText> list = new ArrayList<>();
        list.add(etLoanAmount);
        list.add(etCashOutDate);
        for (EditText t : list) {
            if (Methods.isEmpty(t)) {
                isComplete = false;
                break;
            }
        }

        return isComplete;
    }



    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etCashOutDate.setText(sdf.format(myCalendar.getTime()));
    }

    private ArrayList<Amortization> filler() {
        ArrayList<Amortization> list = new ArrayList<>();

        return list;
    }
    private ArrayList<AmortizationSchedule> setAmortizationSchedule() {
        amortizationSchedule = new ArrayList<>();
        for(int i = 1; i <= terms; i++) {

            amortizationSchedule.add(new AmortizationSchedule(i, getDate(i, etCashOutDate.getText().toString()), Float.parseFloat(Double.toString(amountResult))));
        }
      return amortizationSchedule;
    }

    private  String getDate(int count, String date){
        String dt = date;  // Start date

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

        Calendar c = Calendar.getInstance();
        try {
            Date d = sdf.parse(date);
            c.setTime(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        c.add(Calendar.DATE, 15 * count);
        // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        String output = sdf1.format(c.getTime());
        return output;
    }


    private ArrayList<AmortizationSchedule> amortizationSchedules() {
        ArrayList<AmortizationSchedule> amortizationSchedules = new ArrayList<>();
        amortizationSchedules.add(new AmortizationSchedule(15, "01/01/2019", 50_000));
        amortizationSchedules.add(new AmortizationSchedule(45, "01/01/2019", 30_000));
        amortizationSchedules.add(new AmortizationSchedule(60, "01/01/2019", 30_000));
        amortizationSchedules.add(new AmortizationSchedule(30, "01/01/2019", 25_000));
        amortizationSchedules.add(new AmortizationSchedule(15, "01/01/2019", 20_000));
        amortizationSchedules.add(new AmortizationSchedule(45, "01/01/2019", 10_000));
        return amortizationSchedules;
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
}
