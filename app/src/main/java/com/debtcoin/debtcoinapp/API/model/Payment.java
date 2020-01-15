package com.debtcoin.debtcoinapp.API.model;

import java.util.Date;

public class Payment {

    private String payorName;
    private String userId;
    private String bookingId;
    private String dueDate;
    private String bankName;
    private String reference;
    private String date;
    private String time;
    private float amountPaid;
    private boolean confirmed;
    private boolean imageUploaded;

    private Date created_date;

    private Date updated_date;

    private int id;

    private PaymentDepositImage paymentDepositImage;

    public Payment() {}
    public Payment(String payorName, String userId, String bookingId, String dueDate, String bankName, String reference, String date, String time, float amountPaid) {
        this.payorName = payorName;
        this.userId = userId;
        this.bookingId = bookingId;
        this.dueDate = dueDate;
        this.bankName = bankName;
        this.reference = reference;
        this.date = date;
        this.time = time;
        this.amountPaid = amountPaid;
    }

    
    public Payment(String payorName, String userId, String bookingId, String dueDate, String bankName, String reference, String date, String time, float amountPaid, boolean imageUploaded) {
        this.payorName = payorName;
        this.userId = userId;
        this.bookingId = bookingId;
        this.dueDate = dueDate;
        this.bankName = bankName;
        this.reference = reference;
        this.date = date;
        this.time = time;
        this.amountPaid = amountPaid;
        this.imageUploaded = imageUploaded;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(float amountPaid) {
        this.amountPaid = amountPaid;
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

    public String getPayorName() {
        return payorName;
    }

    public void setPayorName(String payorName) {
        this.payorName = payorName;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public PaymentDepositImage getPaymentDepositImage() {
        return paymentDepositImage;
    }

    public void setPaymentDepositImage(PaymentDepositImage paymentDepositImage) {
        this.paymentDepositImage = paymentDepositImage;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isImageUploaded() {
        return imageUploaded;
    }

    public void setImageUploaded(boolean imageUploaded) {
        this.imageUploaded = imageUploaded;
    }
}
