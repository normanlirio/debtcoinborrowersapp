package com.debtcoin.debtcoinapp.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.debtcoin.debtcoinapp.Fragments.ArticlesPagerFragment;
import com.debtcoin.debtcoinapp.Models.Article;
import com.debtcoin.debtcoinapp.R;

import java.util.ArrayList;

/**
 * Created by Lirio on 7/1/2018.
 */

public class ViewPagerArticlesAdapter extends FragmentStatePagerAdapter {
    private Context mContext;
    private FragmentManager fm;
    private ArrayList<Article> list;

    public ViewPagerArticlesAdapter(Context context, FragmentManager fm,  ArrayList<Article> list) {
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
                 frag =  new ArticlesPagerFragment().newInstance(list.get(position).getPath(), list.get(position).getText(), list.get(position).getMap());
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
