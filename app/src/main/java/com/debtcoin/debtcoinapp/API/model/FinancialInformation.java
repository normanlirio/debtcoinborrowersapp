package com.debtcoin.debtcoinapp.API.model;

import java.util.Date;

public class FinancialInformation {

    private String bankName;
    private String accountType;
    private String accountName;


    public FinancialInformation() {}

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }


}
