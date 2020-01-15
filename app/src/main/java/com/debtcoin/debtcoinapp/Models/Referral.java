package com.debtcoin.debtcoinapp.Models;

/**
 * Created by Lirio on 5/14/2018.
 */

public class Referral extends Personal {
    private String refName;
    private String refNameId;
    private String refName2;
    private String refNameId2;

    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    public String getRefNameId() {
        return refNameId;
    }

    public void setRefNameId(String refNameId) {
        this.refNameId = refNameId;
    }

    public String getRefName2() {
        return refName2;
    }

    public void setRefName2(String refName2) {
        this.refName2 = refName2;
    }

    public String getRefNameId2() {
        return refNameId2;
    }

    public void setRefNameId2(String refNameId2) {
        this.refNameId2 = refNameId2;
    }
}
