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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.debtcoin.debtcoinapp.Database.DBPullProcess;
import com.debtcoin.debtcoinapp.Database.DBSaveProcess;
import com.debtcoin.debtcoinapp.Globals.Methods;
import com.debtcoin.debtcoinapp.Globals.Variables;
import com.debtcoin.debtcoinapp.Models.Referral;
import com.debtcoin.debtcoinapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepFive.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepFive#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepFive extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btnNext;
    private String _ref1, _refUserOrNum1, _ref2, _refUserOrNum2;
    private Unbinder unbinder;
    @BindView(R.id.edit_stepfive_ref1_name)
    EditText etRef1;

    @BindView(R.id.edit_stepfive_ref1_userornum)
    EditText etUserOrNum1;

    @BindView(R.id.edit_stepfive_ref2_name)
    EditText etRef2;

    @BindView(R.id.edit_stepfive_ref2_userornum)
    EditText etUserOrNum2;

    @BindView(R.id.stepfive_container)
    RelativeLayout rlContainer;

    private OnFragmentInteractionListener mListener;
    private String TAG = "Step 5";
    public StepFive() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StepFive.
     */
    // TODO: Rename and change types and number of parameters
    public static StepFive newInstance(String param1, String param2) {
        StepFive fragment = new StepFive();
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
        View view = inflater.inflate(R.layout.fragment_step_five, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Methods.setupUI(getActivity(), rlContainer);
        TextView tvStep = view.findViewById(R.id.text_customactionbar_title);
        ImageView ivArrow = view.findViewById(R.id.img_customactionbar_img);
        tvStep.setText(getResources().getString(R.string.stepfive));
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity)getActivity()).getSupportFragmentManager().popBackStack();
            }
        });
        if(Variables.stepFive) {
            Log.v(TAG, "Pulling data");
            DBPullProcess pullReference = new DBPullProcess(getActivity());
            if(pullReference !=  null) {
                etRef1.setText(pullReference.getReferenceInfo().getRefName());
                etUserOrNum1.setText(pullReference.getReferenceInfo().getRefNameId());
                etRef2.setText(pullReference.getReferenceInfo().getRefName2());
                etUserOrNum2.setText(pullReference.getReferenceInfo().getRefNameId2());
            }
        }
        btnNext = view.findViewById(R.id.button_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actions(etRef1.getText().toString(), etUserOrNum1.getText().toString(), etRef2.getText().toString(), etUserOrNum2.getText().toString()
                );
                Methods.switchFragment(getActivity(), new StepSix());
            }
        });
    }

    private void actions(String ref1, String refUserOrNum1, String ref2, String refUserOrNum2) {

        if(ref1.length() > 0 && refUserOrNum1.length() > 0) {
            _ref1 = ref1;
            _refUserOrNum1 = refUserOrNum1;
        } else {
            _ref1 = "";
            _refUserOrNum1 = "";

        }

        if(ref2.length() > 0 && refUserOrNum2.length() > 0) {
            _ref2 = ref2;
            _refUserOrNum2 = refUserOrNum2;
        } else {
            _ref2 = "";
            _refUserOrNum2 = "";

        }

        Referral referral = new Referral();
        referral.setRefName(_ref1);
        referral.setRefNameId(_refUserOrNum1);
        referral.setRefName2(_ref2);
        referral.setRefNameId2(_refUserOrNum2);
        DBSaveProcess saveReference = new DBSaveProcess(getActivity());
        if(saveReference.saveReferenceInfo(referral)) {
            Variables.stepFive = true;
        } else {
            Log.v("Step Five", "oooooops!");
        }



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
