package com.debtcoin.debtcoinapp.API.model;

/**
 * Created by Roxel on 22/07/2018.
 */

public class Notice {

    private String content;
    private String bookingId;
    private String creditOfficer;
    private String amountDue;
    private String dueDate;
    private String borrowerName;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getCreditOfficer() {
        return creditOfficer;
    }

    public void setCreditOfficer(String creditOfficer) {
        this.creditOfficer = creditOfficer;
    }

    public String getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(String amountDue) {
        this.amountDue = amountDue;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }
}
