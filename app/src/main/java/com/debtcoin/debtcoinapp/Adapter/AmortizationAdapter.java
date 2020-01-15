package com.debtcoin.debtcoinapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.debtcoin.debtcoinapp.Models.Amortization;
import com.debtcoin.debtcoinapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Lirio on 6/30/2018.
 */

public class AmortizationAdapter extends RecyclerView.Adapter<AmortizationAdapter.AmortizationViewHolder> {
    private ArrayList<Amortization> amortizations;
    private Context mContext;

    public AmortizationAdapter(Context context, ArrayList<Amortization> list) {
        this.amortizations = list;
        this.mContext = context;
    }

    @Override
    public AmortizationAdapter.AmortizationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_amortization, parent, false);
        return new AmortizationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AmortizationAdapter.AmortizationViewHolder holder, int position) {
        holder.tvTerms.setText(String.valueOf(amortizations.get(position).getTerms()));
        holder.tvDueDate.setText(amortizations.get(position).getDuedate());
        holder.tvAmount.setText(amortizations.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return amortizations.size();
    }

    public void setAmortizations(int terms, String duedate, double amount) {
        for(int i = 1; i <= terms; i++) {
            amortizations.add(new Amortization(i, getDate(i, duedate), String.format("%.2f",amount)));
        }


        notifyDataSetChanged();
    }

    private  String getDate(int count, String date){
        String dt = date;  // Start date

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

        Calendar c = Calendar.getInstance();
        try {
            Date d = sdf.parse(date);
            c.setTime(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        c.add(Calendar.DATE, 15 * count);
        // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        String output = sdf1.format(c.getTime());
        return output;
    }

    public void clearList() {
        amortizations.clear();
    }

    public class AmortizationViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTerms, tvDueDate, tvAmount;

        public AmortizationViewHolder(View itemView) {
            super(itemView);
            tvTerms = itemView.findViewById(R.id.text_applyloan_item_terms);
            tvDueDate = itemView.findViewById(R.id.text_applyloan_item_duedate);
            tvAmount = itemView.findViewById(R.id.text_applyloan_item_amount);
        }
    }
}
