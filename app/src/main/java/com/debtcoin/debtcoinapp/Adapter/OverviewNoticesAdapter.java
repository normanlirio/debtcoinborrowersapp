package com.debtcoin.debtcoinapp.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.debtcoin.debtcoinapp.R;

import java.util.ArrayList;

/**
 * Created by Lirio on 7/22/2018.
 */

public class OverviewNoticesAdapter extends RecyclerView.Adapter<OverviewNoticesAdapter.OverviewNoticesViewHolder> {
    private Activity activity;
    private ArrayList<String> notices;
    public OverviewNoticesAdapter(Activity activity, ArrayList<String> list) {
        this.notices = list;
        this.activity = activity;

    }
    @Override
    public OverviewNoticesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_overview_notices, parent, false);
        return new OverviewNoticesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OverviewNoticesViewHolder holder, int position) {
            holder.tvNotice.setText(notices.get(position));
    }

    @Override
    public int getItemCount() {
        return notices.size();
    }

    public class OverviewNoticesViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNotice;
        public OverviewNoticesViewHolder(View itemView) {
            super(itemView);
            tvNotice = itemView.findViewById(R.id.text_items_overview_notices_notice);
        }
    }
}
