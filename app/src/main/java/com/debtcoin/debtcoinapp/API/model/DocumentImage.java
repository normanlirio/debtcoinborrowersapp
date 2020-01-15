package com.debtcoin.debtcoinapp.API.model;

import java.util.Date;

public class DocumentImage {

    private String governmentIdFilePath;
    private String selfieIdFilePath;

    private User user;

    private Date created_date;

    private Date updated_date;

    private int id;

    public DocumentImage() {}

    public String getGovernmentIdFilePath() {
        return governmentIdFilePath;
    }

    public void setGovernmentIdFilePath(String governmentIdFilePath) {
        this.governmentIdFilePath = governmentIdFilePath;
    }

    public String getSelfieIdFilePath() {
        return selfieIdFilePath;
    }

    public void setSelfieIdFilePath(String selfieIdFilePath) {
        this.selfieIdFilePath = selfieIdFilePath;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
