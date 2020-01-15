package com.debtcoin.debtcoinapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.debtcoin.debtcoinapp.Database.DBPullProcess;
import com.debtcoin.debtcoinapp.Database.DBSaveProcess;
import com.debtcoin.debtcoinapp.Globals.Methods;
import com.debtcoin.debtcoinapp.Globals.Variables;
import com.debtcoin.debtcoinapp.Models.EmploymentInformation;
import com.debtcoin.debtcoinapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepFour.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepFour#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepFour extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btnNext;

    private OnFragmentInteractionListener mListener;
    private RelativeLayout container;
    @BindView(R.id.spinner_stepfour_empstat) Spinner spEmpStat;
    @BindView(R.id.edit_stepfour_empname_businname) EditText etName;
    @BindView(R.id.edit_stepfour_employeradd) EditText etAddress;
    @BindView(R.id.edit_stepfour_nature) EditText etNature;
    @BindView(R.id.edit_stepfour_pos) EditText etPos;
    private Unbinder unbinder;
    private String TAG = "Step 4";

    public StepFour() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StepFour.
     */
    // TODO: Rename and change types and number of parameters
    public static StepFour newInstance(String param1, String param2) {
        StepFour fragment = new StepFour();
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
        View view = inflater.inflate(R.layout.fragment_step_four, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvStep = view.findViewById(R.id.text_customactionbar_title);
        ImageView ivArrow = view.findViewById(R.id.img_customactionbar_img);
        tvStep.setText(getResources().getString(R.string.stepfour));
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity)getActivity()).getSupportFragmentManager().popBackStack();
            }
        });
        btnNext = view.findViewById(R.id.button_next);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), R.layout.layout_spinner_stepfour, getResources().getStringArray(R.array.employmentstatus));
        spEmpStat.setAdapter(arrayAdapter);

//        etAddress.setText("asfdfhfgkghjghjghj");
//        etName.setText("asfdfhfgkghjghjghj");
//        etNature.setText("asfdfhfgkghjghjghj");
//        etPos.setText("asfdfhfgkghjghjghj");
        if(Variables.stepFour) {
            Log.v(TAG, "Pulling data");
            DBPullProcess pullEmployment = new DBPullProcess(getActivity());
            if(pullEmployment != null) {
                etAddress.setText(pullEmployment.getEmploymentInfo().getEmpaddress());
                etName.setText(pullEmployment.getEmploymentInfo().getEmpname());
                etNature.setText(pullEmployment.getEmploymentInfo().getEmpnature());
                etPos.setText(pullEmployment.getEmploymentInfo().getPosition());
                int empStatPosition = arrayAdapter.getPosition(pullEmployment.getEmploymentInfo().getStatus());
                spEmpStat.setSelection(empStatPosition);
            }

        }
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String empstat = spEmpStat.getSelectedItem().toString();
                String empbusi = etName.getText().toString();
                String pos = etPos.getText().toString();
                String nature = etNature.getText().toString();
                String address = etAddress.getText().toString();
                Log.v(TAG, "EmpStat : " + empstat + ", Emp Busi : " + empbusi + ", Position :" + pos + ", Nature: " + nature + ", Address: "+ address);
                if(actionNext(nature, empbusi, pos, address)) {
                    EmploymentInformation employmentInformation = new EmploymentInformation();
                    employmentInformation.setStatus(empstat);
                    employmentInformation.setEmpname(empbusi);
                    employmentInformation.setPosition(pos);
                    employmentInformation.setEmpaddress(address);
                    employmentInformation.setEmpnature(nature);

                    DBSaveProcess db = new DBSaveProcess(getActivity());
                    if(db.saveEmploymentInfo(employmentInformation)) {
                        Variables.stepFour = true;
                        Methods.switchFragment(getActivity(), new StepFive());
                    } else {
                        Log.v("Step Four", "ooooops!");
                    }


                } else {
                    Methods.showPopup(getActivity(), "Oops!", "All fields are required.", "OK");
                }

            }
        });

        container = view.findViewById(R.id.stepfour_container);
        Methods.setupUI(getActivity(),container);
    }


    private boolean actionNext(String nature, String empbusi, String pos, String address) {
        boolean isComplete =false;
        if(nature.length() > 0) {
            isComplete = true;

        } else {
            isComplete = false;

        }
        if(empbusi.length() > 0) {
            isComplete = true;

        } else {
            isComplete = false;

        }
        if(pos.length() > 0) {
            isComplete = true;

        } else {
            isComplete = false;

        }
        if(address.length() > 0) {
            isComplete = true;

        } else {
            isComplete = false;

        }

        return isComplete;
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
