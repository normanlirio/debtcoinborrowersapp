package com.debtcoin.debtcoinapp.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.debtcoin.debtcoinapp.Fragments.ArticlesPagerFragment;
import com.debtcoin.debtcoinapp.Fragments.ViewPagerHome;
import com.debtcoin.debtcoinapp.Models.Article;
import com.debtcoin.debtcoinapp.R;

import java.util.ArrayList;

/**
 * Created by fluxion inc on 02/07/2018.
 */

public class ViewPagerHomeAdapter extends FragmentStatePagerAdapter {
    private Context mContext;
    private FragmentManager fm;
    private ArrayList<String> list;
    //TODO
    //Change generics from Integer to String eg. ArrayList<String>
    public ViewPagerHomeAdapter(Context context, FragmentManager fm,  ArrayList<String> list) {
        super(fm);
        this.mContext = context;
        this.fm = fm;
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        Resources res = mContext.getResources();
        Fragment frag = null;
        int count = 0;
        while(count < list.size()) {
            if(position == count) {
                frag =  new ViewPagerHome().newInstance(list.get(position));
            }
            count++;
        }

        return frag;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
