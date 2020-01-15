package com.debtcoin.debtcoinapp.API.model;

import java.util.Date;

public class PaymentDepositImage {

    private String path;

    private Date createdDate;
    private Date updatedDate;

    public PaymentDepositImage() {}

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
