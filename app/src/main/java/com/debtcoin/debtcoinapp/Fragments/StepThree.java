package com.debtcoin.debtcoinapp.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.debtcoin.debtcoinapp.Database.DBPullProcess;
import com.debtcoin.debtcoinapp.Database.DBSaveProcess;
import com.debtcoin.debtcoinapp.Globals.Methods;
import com.debtcoin.debtcoinapp.Globals.Variables;
import com.debtcoin.debtcoinapp.Models.Personal;
import com.debtcoin.debtcoinapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepThree.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepThree#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepThree extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Unbinder unbinder;
    private Button btnNext;
    @BindView(R.id.edit_stepthree_name) EditText etFname;
    @BindView(R.id.edit_stepthree_dob) EditText etDob;
    @BindView(R.id.edit_stepthree_mobnumber) EditText etMobNumber;
    @BindView(R.id.edit_stepthree_address) EditText etAddress;
    @BindView(R.id.radiogrp_stepthree_doctype) RadioGroup rgDocType;
    @BindView(R.id.edit_stepthree_docnum) EditText etDocNum;
    @BindView(R.id.rbutton_stepthree_sss) RadioButton rbSSS;
    @BindView(R.id.rbutton_stepthree_prc) RadioButton rbPRC;
    @BindView(R.id.rbutton_stepthree_tin) RadioButton rbTIN;
    @BindView(R.id.img_stepthree_daterange) ImageView ivDateRange;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RelativeLayout container;
    private Calendar myCalendar;

    public StepThree() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StepThree.
     */
    // TODO: Rename and change types and number of parameters
    public static StepThree newInstance(String param1, String param2) {
        StepThree fragment = new StepThree();
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
        View view =inflater.inflate(R.layout.fragment_step_three, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.setDebug(true);
        TextView tvStep = view.findViewById(R.id.text_customactionbar_title);
        ImageView ivArrow = view.findViewById(R.id.img_customactionbar_img);
        tvStep.setText(getResources().getString(R.string.stepthree));
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Methods.gobackStepOnePopup(getActivity(), "Warning", "All user inputs will be cleared. Do you want to continue?", "Continue", "Cancel", new StepOne());

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

        ivDateRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btnNext = view.findViewById(R.id.button_next);


        container = view.findViewById(R.id.stepthree_container);
        Methods.setupUI(getActivity(),container);
        Methods.setupUI(getActivity(), rgDocType);
//        etFname.setText("asdasdasdasdsadsa");
//        etDob.setText("asdasdasdasdsadsa");
//        etAddress.setText("asdasdasdasdsadsa");
//        etMobNumber.setText("asdasdasdasdsadsa");
//        etDocNum.setText("asdasdasdasdsadsa");
        if(Variables.stepThree) {
            DBPullProcess pullPersonal = new DBPullProcess(getActivity());
            etFname.setText(pullPersonal.getPersonalInfo().getFname());
            etDob.setText(pullPersonal.getPersonalInfo().getDob());
            etAddress.setText(pullPersonal.getPersonalInfo().getAddress());
            etMobNumber.setText(pullPersonal.getPersonalInfo().getMobilenumber());
            etDocNum.setText(pullPersonal.getPersonalInfo().getDocNumber());
            String doctype = pullPersonal.getPersonalInfo().getDoctype();
           if(doctype.equalsIgnoreCase("sss")) {
               rbSSS.setChecked(true);
           } else if (doctype.equalsIgnoreCase("tin")) {
               rbTIN.setChecked(true);
           } else if (doctype.equalsIgnoreCase("prc")) {
               rbPRC.setChecked(true);
           }

        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = etFname.getText().toString();
                String dob = etDob.getText().toString();
                String number = etMobNumber.getText().toString();
                String address = etAddress.getText().toString();
                String docnumber = etDocNum.getText().toString();
                if(actionNext()) {
                    Personal personal = new Personal();
                    personal.setFname(fname);
                    personal.setDob(dob);
                    personal.setMobilenumber(number);
                    personal.setAddress(address);
                    personal.setDocNumber(docnumber);
                    int selectedID = rgDocType.getCheckedRadioButtonId();
                    RadioButton rbChecked = view.findViewById(selectedID);
                    personal.setDoctype(rbChecked.getText().toString());

                    DBSaveProcess db = new DBSaveProcess(getActivity());
                    if(db.savePersonalInfo(personal)) {
                        Variables.stepThree = true;
                        Methods.switchFragment(getActivity(), new StepFour());
                    }


                } else {
                    Methods.showPopup(getActivity(), "Oops!", "All fields are required.", "OK");
                }

            }
        });

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
       etDob.setText(sdf.format(myCalendar.getTime()));
    }
    private boolean actionNext() {
        boolean isComplete = false;
        boolean isFname = false;
        boolean isDob = false;
        boolean isDocNum = false;
        boolean isMobNum = false;
        boolean isAddress = false;
        boolean isDocType = false;
        if(etFname.getText().toString().trim().length() > 0) {
            isFname = true;

        } else {
            isFname = false;

        }
        if(etDob.getText().toString().trim().length() > 0) {
            isDob = true;

        } else {
            isDob = false;

        }
        if(etDocNum.getText().toString().trim().length() > 0) {
            isDocNum = true;

        } else {
            isDocNum = false;

        }
        if(etAddress.getText().toString().trim().length() > 0) {
            isAddress = true;

        } else {
            isAddress = false;

        }

        if(etMobNumber.getText().toString().trim().length() > 0 ) {
            isMobNum = true;
        } else {
            isMobNum = false;
        }

        if(rgDocType.getCheckedRadioButtonId() == -1 ) {
            isDocType = false;
        } else {
            isDocType = true;

        }
    return isFname && isAddress && isDob && isDocNum && isDocType && isMobNum;
    }
    @Override public void onDestroyView() {
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
