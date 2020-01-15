package com.debtcoin.debtcoinapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.debtcoin.debtcoinapp.API.model.Banner;
import com.debtcoin.debtcoinapp.API.service.rx.BannerRxService;
import com.debtcoin.debtcoinapp.Adapter.ViewPagerArticlesAdapter;
import com.debtcoin.debtcoinapp.Adapter.ViewPagerHomeAdapter;
import com.debtcoin.debtcoinapp.Globals.APIVariables;
import com.debtcoin.debtcoinapp.Globals.Methods;
import com.debtcoin.debtcoinapp.Globals.Variables;
import com.debtcoin.debtcoinapp.Models.Article;
import com.debtcoin.debtcoinapp.R;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "Home";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Unbinder unbinder;
    @BindView(R.id.linear_home_indicator)
    LinearLayout pageIndicator;
    @BindView(R.id.pager_home_pager)
    ViewPager pager;
    private PagerAdapter mPageAdapter;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    private OnFragmentInteractionListener mListener;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Methods.customActionBar(getActivity(), "Debtcoin PH");

        mPageAdapter = buildAdapter();
        pager.setAdapter(mPageAdapter);


        for(int i=0;i<mPageAdapter.getCount();i++){
            TextView page = new TextView(getActivity());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(6,31,6,4);
            page.setLayoutParams(layoutParams);
            page.setText("•");
            page.setTextSize(60f);
            page.setTag("page"+i);
            //assuming first card is always active
            if(i==0)
                page.setTextColor(ContextCompat.getColor(getActivity(),R.color.md_blue_grey_900));
            else
                page.setTextColor(ContextCompat.getColor(getActivity(),R.color.md_blue_grey_200));
            pageIndicator.addView(page);
        }

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //set all textview text color to idle color
                for(int i=0;i<mPageAdapter.getCount();i++){
                    TextView textView = pageIndicator.findViewWithTag("page"+i);
                    textView.setTextColor(ContextCompat.getColor(getActivity(),R.color.md_grey_400));
                }
                //set textview text color to active
                TextView textViewActive =  pageIndicator.findViewWithTag("page"+position);
                textViewActive.setTextColor(ContextCompat.getColor(getActivity(),R.color.debtcoin_light_blue));
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private PagerAdapter buildAdapter() {
        return new ViewPagerHomeAdapter(getActivity(), getFragmentManager(), filler());
    }

//    private ArrayList<String> filler() {
//        Banner banner = new Banner();
//        ArrayList<String> list = new ArrayList<>();
//        list.add("string");
//        return list;
//    }
    private ArrayList<String> filler() {
        ArrayList<String> list = new ArrayList<>();
//        list.add(R.drawable.mission);
//        list.add(R.drawable.vision);
        return APIVariables.bannerLinks;
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

    @Override
    public void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear();
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
