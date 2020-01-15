package com.debtcoin.debtcoinapp.API.model;

import java.util.List;

public class LoanApplication {

    private String applicantName;
    private float amount;
    private int terms;
    private String cashOutMethod;
    private String cashOutDate;
    private float totalPayable;
    private String reasonForLoan;
    private String reasonDetails;
    private String status; // (-1)Denied, (0)Pending, (1)Approved

    private UserAccount userAccount;

    private List<AmortizationSchedule> amortizationSchedules;
    private int id;

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getTerms() {
        return terms;
    }

    public void setTerms(int terms) {
        this.terms = terms;
    }

    public String getCashOutMethod() {
        return cashOutMethod;
    }

    public void setCashOutMethod(String cashOutMethod) {
        this.cashOutMethod = cashOutMethod;
    }

    public String getCashOutDate() {
        return cashOutDate;
    }

    public void setCashOutDate(String cashOutDate) {
        this.cashOutDate = cashOutDate;
    }

    public float getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(float totalPayable) {
        this.totalPayable = totalPayable;
    }

    public String getReasonForLoan() {
        return reasonForLoan;
    }

    public void setReasonForLoan(String reasonForLoan) {
        this.reasonForLoan = reasonForLoan;
    }

    public String getReasonDetails() {
        return reasonDetails;
    }

    public void setReasonDetails(String reasonDetails) {
        this.reasonDetails = reasonDetails;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public List<AmortizationSchedule> getAmortizationSchedules() {
        return amortizationSchedules;
    }

    public void setAmortizationSchedules(List<AmortizationSchedule> amortizationSchedules) {
        this.amortizationSchedules = amortizationSchedules;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }
}
