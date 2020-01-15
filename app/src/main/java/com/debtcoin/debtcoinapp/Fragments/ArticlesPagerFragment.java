package com.debtcoin.debtcoinapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.debtcoin.debtcoinapp.Globals.Methods;
import com.debtcoin.debtcoinapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import uk.co.deanwild.flowtextview.FlowTextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ArticlesPagerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ArticlesPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticlesPagerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "ID";
    private static final String ARG_PARAM2 = "Paragraph";

    private static final String TAG = "ArticlesPagerFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String id;
    private String paragraph;
    private HashMap<String, String> hashMap;
    @BindView(R.id.ftv)
    FlowTextView ftv;
    @BindView(R.id.img)
    ImageView ivImg;
    @BindView(R.id.text_articlespager_name)
    TextView tvName;
    @BindView(R.id.text_articlespager_company)
    TextView tvCompany;
    @BindView(R.id.text_articlespager_job)
    TextView tvJob;

    private Unbinder unbinder;

    private OnFragmentInteractionListener mListener;

    public ArticlesPagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArticlesPagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ArticlesPagerFragment newInstance(String param1, String param2, HashMap<String, String> map) {
        ArticlesPagerFragment fragment = new ArticlesPagerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putSerializable("credentials", map);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments() != null) {
                paragraph = getArguments().getString(ARG_PARAM2);
                id = getArguments().getString(ARG_PARAM1);
                hashMap = (HashMap<String, String>) getArguments().getSerializable("credentials");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_articles_pager, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        ftv.setTextColor(getResources().getColor(R.color.md_white_1000));
        ftv.setText(paragraph);

        tvName.setText(hashMap.get("Name"));
        tvCompany.setText(hashMap.get("Company"));
        tvJob.setText(hashMap.get("Job"));
//        ivImg.setBackgroundResource(id);

        final ProgressDialog pd = Methods.showProgress(getActivity(), "Loading...");
        pd.show();

        Log.d(TAG, "onViewCreated: PATH: " + id);

        Picasso.get().load(id).into(ivImg, new Callback() {
            @Override
            public void onSuccess() {
                pd.dismiss();
            }

            @Override
            public void onError(Exception e) {
                pd.dismiss();
            }
        });



        float scaledTextSize = getResources().getDisplayMetrics().scaledDensity * 20.0f;
        if ((getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            // on a normal screen device ...
          //  ftv.setTextSize(20.0f);

            tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX,  scaledTextSize);
            tvCompany.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaledTextSize);
            tvJob.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaledTextSize);
            Log.v("TEXT SIZE", "NORMAL ARTICLES: " + ftv.getTextsize());
            Log.v("TEXT SIZE", "NORMAL ARTICLES: " + scaledTextSize);
        }else  if ((getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_LARGE) {
            // on a large screen device ...
         //   ftv.setTextSize(22.0f);
            tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaledTextSize);
            tvCompany.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaledTextSize);
            tvJob.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaledTextSize);
            Log.v("TEXT SIZE", "LARGE ARTICLES: " + ftv.getTextsize());
            Log.v("TEXT SIZE", "LARGE ARTICLES: " + scaledTextSize);

        } else  if ((getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            // on a xlarge screen device ...
         //   ftv.setTextSize(24.0f);
            tvName.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaledTextSize);
            tvCompany.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaledTextSize);
            tvJob.setTextSize(TypedValue.COMPLEX_UNIT_PX, scaledTextSize);
            Log.v("TEXT SIZE", "XLARGE ARTICLES: " + ftv.getTextsize());
            Log.v("TEXT SIZE", "XLARGE ARTICLES: " + scaledTextSize);
        } else {
          //  ftv.setTextSize(21.0f);
            Log.v("TEXT SIZE", "ARTICLES: " + ftv.getTextsize());
            Log.v("TEXT SIZE", "ARTICLES: " + scaledTextSize);
            tvName.setTextSize(21.0f);
            tvCompany.setTextSize(21.0f);
            tvJob.setTextSize(21.0f);
        }
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
