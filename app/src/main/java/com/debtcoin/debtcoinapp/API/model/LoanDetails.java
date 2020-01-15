package com.debtcoin.debtcoinapp.API.model;

import java.util.Date;
import java.util.List;

public class LoanDetails {

    private String name;
    private float currentLoanAmount;
    private int terms;
    private float totalPayableAmount;
    private int numOfPayments;
    private int numOfPaymentsMade;
    private float totalAmountDue;
    private boolean completed;
    private String dueDate;
    private String notification;
    private String bookingId;
    private boolean overdue;

    private UserAccount userAccount;

    private List<LoanDetailsAmortizationSchedule> loanDetailsAmortizationSchedules;

    private Date created_date;

    private Date updated_date;

    private int id;

    public LoanDetails() {}

    public UserAccount getUserAccount() {
        return userAccount;
    }



    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public float getCurrentLoanAmount() {
        return currentLoanAmount;
    }

    public void setCurrentLoanAmount(float currentLoanAmount) {
        this.currentLoanAmount = currentLoanAmount;
    }

    public int getTerms() {
        return terms;
    }

    public void setTerms(int terms) {
        this.terms = terms;
    }

    public float getTotalPayableAmount() {
        return totalPayableAmount;
    }

    public void setTotalPayableAmount(float totalPayableAmount) {
        this.totalPayableAmount = totalPayableAmount;
    }

    public int getNumOfPayments() {
        return numOfPayments;
    }

    public void setNumOfPayments(int numOfPayments) {
        this.numOfPayments = numOfPayments;
    }

    public int getNumOfPaymentsMade() {
        return numOfPaymentsMade;
    }

    public void setNumOfPaymentsMade(int numOfPaymentsMade) {
        this.numOfPaymentsMade = numOfPaymentsMade;
    }

    public float getTotalAmountDue() {
        return totalAmountDue;
    }

    public void setTotalAmountDue(float totalAmountDue) {
        this.totalAmountDue = totalAmountDue;
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public List<LoanDetailsAmortizationSchedule> getLoanDetailsAmortizationSchedules() {
        return loanDetailsAmortizationSchedules;
    }

    public void setLoanDetailsAmortizationSchedules(List<LoanDetailsAmortizationSchedule> loanDetailsAmortizationSchedules) {
        this.loanDetailsAmortizationSchedules = loanDetailsAmortizationSchedules;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }
}
