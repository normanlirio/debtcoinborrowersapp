package com.debtcoin.debtcoinapp.Models;

/**
 * Created by Lirio on 6/30/2018.
 */

public class Amortization {
    private int terms;
    private String duedate;
    private String amount;

    public Amortization() {
    }

    public Amortization(int terms, String duedate, String amount) {
        this.terms = terms;
        this.duedate = duedate;
        this.amount = amount;
    }


    public int getTerms() {
        return terms;
    }

    public void setTerms(int terms) {
        this.terms = terms;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
