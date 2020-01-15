package com.debtcoin.debtcoinapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.debtcoin.debtcoinapp.Globals.Methods;
import com.debtcoin.debtcoinapp.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lirio on 6/18/2018.
 */

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.BankAdapterViewHolder> {
    private Context mContext;
    private int[] list;
    private String page;

    public BankAdapter(Context context, int[] list, String page) {
        this.mContext = context;
        this.list = list;
        this.page = page;
    }

    @Override
    public BankAdapter.BankAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewTypee) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bank, parent, false);

        return new BankAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BankAdapter.BankAdapterViewHolder holder, final int position) {
        Bitmap bm = BitmapFactory.decodeResource(mContext.getResources(), list[position]);
        holder.ivBank.setImageBitmap(bm);
       // if (page.equalsIgnoreCase("faq")) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap<String, String> map = new HashMap<>();
                    switch (position) {
                        case 0:
                            map.put("bank", "bdo");
                            map.put("BankName", "BDO Deposit / Fund Transfer:");
                            map.put("AccountLabel", "BDO Account:");
                            map.put("BankAccountNum", "00-393-018-1724");
                            map.put("AccountNameLabel", "Account Name:");
                            map.put("AccountName", "Crisostomo B. Ferrer");
                            map.put("MobileNumberLabel", "");
                            map.put("MobileNumber", "");
                            Methods.bankPopUp(mContext, map);
                            break;
                        case 1:
                            map.put("bank", "bpi");
                            map.put("BankName", "BPI Deposit / Fund Transfer:");
                            map.put("AccountLabel", "BPI Account:");
                            map.put("BankAccountNum", "4999-0662-89");
                            map.put("AccountNameLabel", "Account Name:");
                            map.put("AccountName", "Crisostomo B. Ferrer");
                            map.put("MobileNumberLabel", "");
                            map.put("MobileNumber", "");
                            Methods.bankPopUp(mContext, map);
                            break;
                        case 2:
                            map.put("bank", "lbp");
                            map.put("BankName", "LBP Deposit / Fund Transfer:");
                            map.put("AccountLabel", "LBP Account:");
                            map.put("BankAccountNum", "0287-2603-34");
                            map.put("AccountNameLabel", "Account Name:");
                            map.put("AccountName", "Crisostomo B. Ferrer");
                            map.put("MobileNumberLabel", "");
                            map.put("MobileNumber", "");
                            Methods.bankPopUp(mContext, map);
                            break;
                        case 3:
                            map.put("bank", "ml");
                            map.put("BankName", "MLhuillier Deposit:");
                            map.put("AccountLabel", "Receiver Name:");
                            map.put("BankAccountNum", "Crisostomo B. Ferrer");
                            map.put("AccountNameLabel", "Address:");
                            map.put("AccountName", "Sampaloc, Manila");
                            map.put("MobileNumberLabel", "Mobile Number:");
                            map.put("MobileNumber", "0998-984-4714");
                            Methods.bankPopUp(mContext, map);
                            break;
                        case 4:
                            map.put("bank", "coins");
                            map.put("BankName", "Coins.ph:");
                            map.put("AccountLabel", "Name:");
                            map.put("BankAccountNum", "Crisostomo B. Ferrer");
                            map.put("AccountNameLabel", "Wallet Address:");
                            map.put("AccountName", "3PzEbZW3PX88bdK9VL4ssfzGCQbn91Eqab");
                            map.put("MobileNumberLabel", "");
                            map.put("MobileNumber", "");
                            Methods.bankPopUp(mContext, map);
                            break;
                        case 5:
                            map.put("bank", "ceb");
                            map.put("BankName", "Cebuana Lhuillier Deposit:");
                            map.put("AccountLabel", "Receiver Name:");
                            map.put("BankAccountNum", "Crisostomo B. Ferrer");
                            map.put("AccountNameLabel", "Address:");
                            map.put("AccountName", "Sampaloc, Manila");
                            map.put("MobileNumberLabel", "Mobile Number:");
                            map.put("MobileNumber", "0998-984-4714");
                            Methods.bankPopUp(mContext, map);
                            break;
                        case 6:
                            map.put("bank", "pala");
                            map.put("BankName", "Palawan Express Deposit:");
                            map.put("AccountLabel", "Receiver Name:");
                            map.put("BankAccountNum", "Crisostomo B. Ferrer");
                            map.put("AccountNameLabel", "Address:");
                            map.put("AccountName", "Sampaloc, Manila");
                            map.put("MobileNumberLabel", "Mobile Number:");
                            map.put("MobileNumber", "0998-984-4714");
                            Methods.bankPopUp(mContext, map);
                            break;
                        case 7:
                            map.put("bank", "pala");
                            map.put("BankName", "Villarica Pawnshop Deposit:");
                            map.put("AccountLabel", "Receiver Name:");
                            map.put("BankAccountNum", "Crisostomo B. Ferrer");
                            map.put("AccountNameLabel", "Address:");
                            map.put("AccountName", "Sampaloc, Manila");
                            map.put("MobileNumberLabel", "Mobile Number:");
                            map.put("MobileNumber", "0998-984-4714");
                            Methods.bankPopUp(mContext, map);
                            break;


                    }
                }
            });
       // }
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public class BankAdapterViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivBank;

        public BankAdapterViewHolder(View itemView) {
            super(itemView);
            ivBank = itemView.findViewById(R.id.img_bank);
        }
    }
}
