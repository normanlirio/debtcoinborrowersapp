package com.debtcoin.debtcoinapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.debtcoin.debtcoinapp.Globals.APIVariables;
import com.debtcoin.debtcoinapp.Globals.Methods;
import com.debtcoin.debtcoinapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DebtcoinRank.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DebtcoinRank#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DebtcoinRank extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    TextView tvFullName, tvRank, tvCreditLimit, tvReferrals;
    private ImageView ivRank;

    @BindView(R.id.relative_debtcoinrank_container)
    RelativeLayout rlContainer;
    public DebtcoinRank() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DebtcoinRank.
     */
    // TODO: Rename and change types and number of parameters
    public static DebtcoinRank newInstance(String param1, String param2) {
        DebtcoinRank fragment = new DebtcoinRank();
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
        return inflater.inflate(R.layout.fragment_debtcoin_rank, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Debtcoin Rank");
        Methods.customActionBar(getActivity(), "My Debtcoin Rank");

        tvFullName = view.findViewById(R.id.text_rank_fname);
        tvRank = view.findViewById(R.id.text_rank_rank);
        tvCreditLimit = view.findViewById(R.id.text_rank_creditlimit);
        tvReferrals = view.findViewById(R.id.text_rank_referrals);
        ivRank = view.findViewById(R.id.img_rank_badge);
        tvFullName.setText(APIVariables.name);
        tvRank.setText(APIVariables.rank);
        tvCreditLimit.setText(String.format(Locale.US, "Php %,.2f", APIVariables.creditLimit));
        tvReferrals.setText(String.valueOf(APIVariables.referrals));

//        switch (APIVariables.rank ) {
//            case "Bronze" :
//                ivRank.setImageResource(R.drawable.bronze);
//                break;
//            case "Silver" :
//                ivRank.setImageResource(R.drawable.silver);
//                break;
//            case "Gold" :
//                ivRank.setImageResource(R.drawable.gold);
//                break;
//
//        }

        final ProgressDialog pd = Methods.showProgress(getActivity(), "Loading...");
        pd.show();

        Picasso.get().load(APIVariables.currentRankLink).into(ivRank, new Callback() {
            @Override
            public void onSuccess() {
                pd.dismiss();
            }

            @Override
            public void onError(Exception e) {
                pd.dismiss();
            }
        });

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
