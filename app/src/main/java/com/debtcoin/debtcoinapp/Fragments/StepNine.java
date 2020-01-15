package com.debtcoin.debtcoinapp.Fragments;

import android.app.ProgressDialog;
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

import com.debtcoin.debtcoinapp.API.model.UserAccount;
import com.debtcoin.debtcoinapp.API.service.rx.UserRxService;
import com.debtcoin.debtcoinapp.Database.DBPullProcess;
import com.debtcoin.debtcoinapp.Database.DBSaveProcess;
import com.debtcoin.debtcoinapp.Globals.Methods;
import com.debtcoin.debtcoinapp.Globals.Variables;
import com.debtcoin.debtcoinapp.R;
import com.debtcoin.debtcoinapp.Util.AESCrypt;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepNine.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepNine#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepNine extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String TAG = "Step 9";

    private Unbinder unbinder;
    private OnFragmentInteractionListener mListener;

    @BindView(R.id.stepnine_container)
    RelativeLayout rlContainer;
    @BindView(R.id.button_next)
    Button btnNext;
    @BindView(R.id.edit_stepnine_pass)
    EditText etPass;
    @BindView(R.id.edit_stepnine_uname)
    EditText etUser;

    public StepNine() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StepNine.
     */
    // TODO: Rename and change types and number of parameters
    public static StepNine newInstance(String param1, String param2) {
        StepNine fragment = new StepNine();
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
        View view = inflater.inflate(R.layout.fragment_step_nine, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Methods.setupUI(getActivity(), rlContainer);
        TextView tvStep = view.findViewById(R.id.text_customactionbar_title);
        ImageView ivArrow = view.findViewById(R.id.img_customactionbar_img);
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity)getActivity()).getSupportFragmentManager().popBackStack();
            }
        });
        tvStep.setText(getResources().getString(R.string.stepnine));

        btnNext = view.findViewById(R.id.button_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actions();

            }
        });
        if(Variables.stepNine) {
            Log.v(TAG, "Pulling data");
            DBPullProcess db = new DBPullProcess(getActivity());
            HashMap<String, String> map = db.getCredentials();

            try {
                etUser.setText(map.get("user"));
                etPass.setText(AESCrypt.decrypt(map.get("pass")));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void actions() {
        String user = etUser.getText().toString();
        String pass = etPass.getText().toString();
        if(user.length() > 0 && pass.length() > 0) {
            if(pass.length() > 0 && pass.length() < 6) {
                Methods.showPopup(getActivity(), "Error", "Password should be at least 6 characters", "OK");
            } else if (user.length() > 0 && user .length()< 6) {
                Methods.showPopup(getActivity(), "Error", "Username should be at least 6 characters", "OK");
            }    else {
                final ProgressDialog pd = Methods.showProgress(getActivity(), "Validating username");
                pd.show();

                //Log.d(TAG, "PASSWORD: " + pass);

                UserAccount userAccount = new UserAccount(user, pass);

                new UserRxService()
                        .checkUsername(userAccount)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(resultMessage -> {

                            pd.dismiss();

                            if (resultMessage.getResponseCode().equals("200")) {

                                DBSaveProcess db = new DBSaveProcess(getActivity());
                                db.saveCredentials(user, pass);
                                Variables.stepNine = true;

                                Methods.switchFragment(getActivity(), new StepTen());

                            } else {
                                Methods.showPopup(getActivity(), "Error", resultMessage.getResponseMessage(), "OK");
                            }

                        }, error -> {
                            pd.dismiss();
                            Log.e(TAG, "actions: ", error);
                            Methods.showPopup(getActivity(), "Error", "Something went wrong.", "OK");
                        });
            }

        } else {
            Methods.showPopup(getActivity(), "Oops!", "All fields are required.", "OK");
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
