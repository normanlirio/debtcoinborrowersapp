package com.debtcoin.debtcoinapp.API.model;


import java.util.Date;

public class AmortizationSchedule {

    private int terms;
    private String dueDate;
    private float amount;

    private LoanApplication loanApplication;

    private Date created_date;

    private Date updated_date;

    private int id;

    public AmortizationSchedule() {}

    public AmortizationSchedule(int terms, String dueDate, float amount) {
        this.terms = terms;
        this.dueDate = dueDate;
        this.amount = amount;
    }

    public LoanApplication getLoanApplication() {
        return loanApplication;
    }

    public void setLoanApplication(LoanApplication loanApplication) {
        this.loanApplication = loanApplication;
    }

    public int getTerms() {
        return terms;
    }

    public void setTerms(int terms) {
        this.terms = terms;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
