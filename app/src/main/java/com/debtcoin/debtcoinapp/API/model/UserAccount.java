package com.debtcoin.debtcoinapp.API.model;

import java.util.Date;
import java.util.List;

public class UserAccount {

    private String username;
    private String password;
    private boolean hasPendingApplication;
    private boolean hasActiveLoan;

    private List<Payment> payments;
    private List<LoanDetails> loanDetails;
    private List<LoanApplication> loanApplication;
    private AccountDetails accountDetails;
    private Notice notice;

    public UserAccount() {}

    public UserAccount(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public List<LoanDetails> getLoanDetails() {
        return loanDetails;
    }

    public List<LoanApplication> getLoanApplication() {
        return loanApplication;
    }

    public AccountDetails getAccountDetails() {
        return accountDetails;
    }

    public boolean isHasPendingApplication() {
        return hasPendingApplication;
    }

    public void setHasPendingApplication(boolean hasPendingApplication) {
        this.hasPendingApplication = hasPendingApplication;
    }

    public boolean isHasActiveLoan() {
        return hasActiveLoan;
    }

    public void setHasActiveLoan(boolean hasActiveLoan) {
        this.hasActiveLoan = hasActiveLoan;
    }

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }
}
