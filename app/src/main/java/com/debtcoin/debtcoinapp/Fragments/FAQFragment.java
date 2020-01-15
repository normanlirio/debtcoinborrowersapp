package com.debtcoin.debtcoinapp.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
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
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.debtcoin.debtcoinapp.API.model.Message;
import com.debtcoin.debtcoinapp.API.service.rx.FaqRxService;
import com.debtcoin.debtcoinapp.Globals.APIVariables;
import com.debtcoin.debtcoinapp.Globals.Methods;
import com.debtcoin.debtcoinapp.Globals.Variables;
import com.debtcoin.debtcoinapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import com.debtcoin.debtcoinapp.Adapter.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FAQFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FAQFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FAQFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Unbinder unbinder;
    private String TAG = "FAQ";
    private OnFragmentInteractionListener mListener;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @BindView(R.id.expandable_faq_list)
     ExpandableListView expLV;

    @BindView(R.id.faq_container)
    RelativeLayout rlContainer;
    @BindView(R.id.linear_faq_listcontainer)
    LinearLayout svFAQ;



    private View footerView;


    Button btnSubmit;

    private HashMap<String, List<Integer>> listDataChild;
    private  List<String> listDataHeader;
    private EditText etUsername;
    private EditText etNum;
    private EditText etEmail;
    private EditText etFName;
    private EditText etMsg;

    public FAQFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FAQFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FAQFragment newInstance(String param1, String param2) {
        FAQFragment fragment = new FAQFragment();
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
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Methods.setupUI(getActivity(), rlContainer);

        prepareListData();

        ExpandableListFAQAdapter adapter = new ExpandableListFAQAdapter(getActivity(), listDataHeader, listDataChild);
        expLV.setAdapter(adapter);

        footerView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.listfooter, null, false);

        expLV.addFooterView(footerView);

        footerViewActions();


        View headerView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_header, null, false);

        expLV.addHeaderView(headerView);
        TextView tvStep = headerView.findViewById(R.id.text_customactionbar_title);
        ImageView ivArrow = headerView.findViewById(R.id.img_customactionbar_img);
        tvStep.setText("Back");
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity)getActivity()).getSupportFragmentManager().popBackStack();
            }
        });
        if(!Variables.isLoggedIn) {

        } else {
            Methods.customActionBar(getActivity(), "FAQs");
            tvStep.setVisibility(View.GONE);
            ivArrow.setVisibility(View.GONE);
            svFAQ.setPadding(15,15,15,15);
            etFName.setText(APIVariables.name);
            etUsername.setText(APIVariables.username);
            etEmail.setText(APIVariables.email);
            etNum.setText(APIVariables.mobileNumber);

        }


    }

    private void footerViewActions() {
       etFName = footerView.findViewById(R.id.edit_faq_fname);
        etEmail = footerView.findViewById(R.id.edit_faq_email);
        etNum = footerView.findViewById(R.id.edit_faq_number);
       etUsername = footerView.findViewById(R.id.edit_faq_uid);
         etMsg = footerView.findViewById(R.id.edit_faq_msg);
        Button btnSubmit = footerView.findViewById(R.id.button_faq_submit);
        ArrayList<EditText> etList = new ArrayList<>();
        etList.add(etFName);
        etList.add(etEmail);
        etList.add(etNum);
        etList.add(etMsg);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isOk = true;
                int index = 0;
                for(EditText t : etList) {
                    if(Methods.isEmpty(t)) {
                        isOk = false;
                        index = etList.indexOf(t);
                        break;
                    }
                }
                if(isOk) {
                    ProgressDialog pd = Methods.showProgress(getActivity(), "Sending message...");
                    pd.show();
                    Message message = new Message(
                            etFName.getText().toString(),
                            etEmail.getText().toString(),
                            etNum.getText().toString(),
                            etUsername.getText().toString(),
                            etMsg.getText().toString()
                    );

                    compositeDisposable.add(new FaqRxService().sendFaqMessage(message)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(messageResultMessage -> {

                                if (messageResultMessage.getResponseCode().equals("200")) {
                                    pd.dismiss();
                                    etList.add(etUsername);
                                    showPopup(getActivity(), "Your Message has been sent to our email. We will contact you as soon as we can.", "OK", etList);

                                } else {
                                    pd.dismiss();
                                    Toast.makeText(getActivity(), messageResultMessage.getResponseMessage(), Toast.LENGTH_SHORT).show();
                                    Methods.showPopup(getActivity(), "Oops!",  messageResultMessage.getResponseMessage(), "OK");
                                }
                            }, error -> {
                                pd.dismiss();
                                //Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                                Methods.showPopup(getActivity(), "Oops!", error.getMessage(), "OK");
                            }));


                } else {
                    switch (index) {
                        case 0 :
                            Methods.showPopup(getActivity(), "Oops!", "Full name is required", "OK");
                            break;
                        case 1 :
                            Methods.showPopup(getActivity(), "Oops!", "Email is required", "OK");
                            break;
                        case 2 :
                            Methods.showPopup(getActivity(), "Oops!", "Mobile Number is required", "OK");
                            break;
                        case 3 :
                            Methods.showPopup(getActivity(), "Oops!", "Message is required", "OK");
                            break;
                    }
                }
            }
        });
    }


    public void showPopup(Activity activity, String message, String okBtn, ArrayList<EditText> list) {
        LayoutInflater inflate = LayoutInflater.from(activity);
        View promptsView = inflate.inflate(R.layout.error_popup, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setView(promptsView);
        alertDialog.setCancelable(false);
        final AlertDialog alert2 = alertDialog.create();
        alert2.show();
        TextView tvHeader = promptsView.findViewById(R.id.text_popup_header);
        TextView tvMsg = promptsView.findViewById(R.id.text_popup_message);
        TextView tvCancel = promptsView.findViewById(R.id.text_popup_cancel);
        Button btnOk = promptsView.findViewById(R.id.button_popup_ok);
         ImageView ivIcon = promptsView.findViewById(R.id.img_popup_icon);
        ivIcon.setVisibility(View.GONE);
        tvHeader.setVisibility(View.GONE);

        tvMsg.setText(message);
        tvCancel.setVisibility(View.GONE);
        btnOk.setText(okBtn);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert2.dismiss();
//                for(EditText t : list) {
//                    t.setText("");
//
//                }
                etMsg.setText("");
            }
        });


    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<Integer>>();

        Resources res = getResources();
        listDataHeader.add(res.getString(R.string.whatisdebtcoin));
        listDataHeader.add(res.getString(R.string.whyapplication));
        listDataHeader.add(res.getString(R.string.howmuchqualify));
        listDataHeader.add(res.getString(R.string.howapplicantreceive));
        listDataHeader.add(res.getString(R.string.howrepay));
        listDataHeader.add(res.getString(R.string.howcontanct));

        List<Integer> _child1 = new ArrayList<>();
        _child1.add(R.id.linear_faq_child_1);

        List<Integer> _child2 = new ArrayList<>();
        _child2.add(R.id.linear_faq_child_2);

        List<Integer> _child3 = new ArrayList<>();
        _child3.add(R.id.linear_faq_child_3);

        List<Integer> _child4 = new ArrayList<>();
        _child4.add(R.id.linear_faq_child_4);

        List<Integer> _child5 = new ArrayList<>();
        _child5.add(R.id.linear_faq_child_5);

        List<Integer> _child6 = new ArrayList<>();
        _child6.add(R.id.linear_faq_child_6);

//        Log.v(TAG, "ID " + R.id.linear_faq_child_1);
//        Log.v(TAG, "ID " + R.id.linear_faq_child_2);
//        Log.v(TAG, "ID " + R.id.linear_faq_child_3);
//        Log.v(TAG, "ID " + R.id.linear_faq_child_4);
//        Log.v(TAG, "ID " + R.id.linear_faq_child_5);
//        Log.v(TAG, "ID " + R.id.linear_faq_child_6);

        listDataChild.put(listDataHeader.get(0), _child1);
        listDataChild.put(listDataHeader.get(1), _child2);
        listDataChild.put(listDataHeader.get(2), _child3);
        listDataChild.put(listDataHeader.get(3), _child4);
        listDataChild.put(listDataHeader.get(4), _child5);
        listDataChild.put(listDataHeader.get(5), _child6);

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

    @Override
    public void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear();
    }
}
