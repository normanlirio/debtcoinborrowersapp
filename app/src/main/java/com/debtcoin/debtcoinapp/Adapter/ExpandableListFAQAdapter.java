package com.debtcoin.debtcoinapp.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.debtcoin.debtcoinapp.Globals.Methods;
import com.debtcoin.debtcoinapp.R;
import com.debtcoin.debtcoinapp.Util.EqualSpacingItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fluxion inc on 13/06/2018.
 */

public class ExpandableListFAQAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private ArrayList<String> list;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<Integer>> _listDataChild;

    public ExpandableListFAQAdapter(Context context, List<String> listDataHeader,HashMap<String, List<Integer>> listChildData) {
        this.mContext = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }


    @Override
    public int getGroupCount() {
       return this._listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int i) {
        return this._listDataHeader.get(i);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
        String headerTitle = (String) getGroup(i);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView tvHeader = convertView.findViewById(R.id.text_group_header);
        ImageView ivIcon = convertView.findViewById(R.id.img_group_ic);

        if(b) {
            ivIcon.setImageResource(R.drawable.ic_arrow_down);

        } else {
            ivIcon.setImageResource(R.drawable.ic_arrow_right);
        }
        tvHeader.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPos, int childPos, boolean b, View view, ViewGroup viewGroup) {
        int childText = (int) getChild(groupPos, childPos);
        if(view == null) {
                    LayoutInflater infalInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = infalInflater.inflate(R.layout.faq_whatisdebtcoin, null);
        }
       LinearLayout llChild1 = view.findViewById(R.id.linear_faq_child_1);
        LinearLayout llChild2 = view.findViewById(R.id.linear_faq_child_2);
        LinearLayout llChild3 = view.findViewById(R.id.linear_faq_child_3);
        LinearLayout llChild4 = view.findViewById(R.id.linear_faq_child_4);
        LinearLayout llChild5 = view.findViewById(R.id.linear_faq_child_5);
        LinearLayout llChild6 = view.findViewById(R.id.linear_faq_child_6);
        RecyclerView rvBanks = view.findViewById(R.id.recycler_faq_child_banks);
        rvBanks.setLayoutManager(new GridLayoutManager(mContext, 4));
        int[] list = new int[] {R.drawable.bdo, R.drawable.bpi, R.drawable.landbank, R.drawable.ml, R.drawable.coins, R.drawable.cebuana, R.drawable.palawan, R.drawable.villarica};
        BankAdapter adapter = new BankAdapter(mContext, list, "faq");
        rvBanks.setAdapter(adapter);
    //    rvBanks.addItemDecoration(new EqualSpacingItemDecoration(3, EqualSpacingItemDecoration.VERTICAL));
       switch (groupPos) {
           case 0 :
               llChild1.setVisibility(View.VISIBLE);
               llChild2.setVisibility(View.GONE);
               llChild3.setVisibility(View.GONE);
               llChild4.setVisibility(View.GONE);
               llChild5.setVisibility(View.GONE);
               llChild6.setVisibility(View.GONE);
               Methods.showLog(mContext, "EXPANDABLE", "GROUP " + groupPos + " CHILD " + childPos);
               break;
           case 1 :
               llChild2.setVisibility(View.VISIBLE);
               llChild1.setVisibility(View.GONE);
               llChild3.setVisibility(View.GONE);
               llChild4.setVisibility(View.GONE);
               llChild5.setVisibility(View.GONE);
               llChild6.setVisibility(View.GONE);
               Methods.showLog(mContext, "EXPANDABLE", "GROUP " + groupPos + " CHILD " + childPos);
               break;
           case 2 :
               llChild3.setVisibility(View.VISIBLE);
               llChild1.setVisibility(View.GONE);
               llChild2.setVisibility(View.GONE);
               llChild4.setVisibility(View.GONE);
               llChild5.setVisibility(View.GONE);
               llChild6.setVisibility(View.GONE);
               Methods.showLog(mContext, "EXPANDABLE", "GROUP " + groupPos + " CHILD " + childPos);
               break;
           case 3 :
               llChild4.setVisibility(View.VISIBLE);
               llChild1.setVisibility(View.GONE);
               llChild2.setVisibility(View.GONE);
               llChild3.setVisibility(View.GONE);
               llChild5.setVisibility(View.GONE);
               llChild6.setVisibility(View.GONE);
               Methods.showLog(mContext, "EXPANDABLE", "GROUP " + groupPos + " CHILD " + childPos);
               break;
           case 4 :
               llChild5.setVisibility(View.VISIBLE);
               llChild1.setVisibility(View.GONE);
               llChild2.setVisibility(View.GONE);
               llChild3.setVisibility(View.GONE);
               llChild4.setVisibility(View.GONE);
               llChild6.setVisibility(View.GONE);
               Methods.showLog(mContext, "EXPANDABLE", "GROUP " + groupPos + " CHILD " + childPos);
               break;
           case 5 :
               llChild6.setVisibility(View.VISIBLE);
               llChild1.setVisibility(View.GONE);
               llChild2.setVisibility(View.GONE);
               llChild3.setVisibility(View.GONE);
               llChild4.setVisibility(View.GONE);
               llChild5.setVisibility(View.GONE);
               Methods.showLog(mContext, "EXPANDABLE", "GROUP " + groupPos + " CHILD " + childPos);
               break;
           default:
               Methods.showLog(mContext, "EXPANDABLE","DEFAULT");
       }

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }


}
