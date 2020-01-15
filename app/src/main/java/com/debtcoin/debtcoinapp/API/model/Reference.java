package com.debtcoin.debtcoinapp.API.model;

import java.util.Date;

public class Reference {

    private String refOneName;
    private String refOneMobileNum;
    private String refTwoName;
    private String refTwoMobileNum;

    public Reference() {}

    public String getRefOneName() {
        return refOneName;
    }

    public void setRefOneName(String refOneName) {
        this.refOneName = refOneName;
    }

    public String getRefOneMobileNum() {
        return refOneMobileNum;
    }

    public void setRefOneMobileNum(String refOneMobileNum) {
        this.refOneMobileNum = refOneMobileNum;
    }

    public String getRefTwoName() {
        return refTwoName;
    }

    public void setRefTwoName(String refTwoName) {
        this.refTwoName = refTwoName;
    }

    public String getRefTwoMobileNum() {
        return refTwoMobileNum;
    }

    public void setRefTwoMobileNum(String refTwoMobileNum) {
        this.refTwoMobileNum = refTwoMobileNum;
    }

}
