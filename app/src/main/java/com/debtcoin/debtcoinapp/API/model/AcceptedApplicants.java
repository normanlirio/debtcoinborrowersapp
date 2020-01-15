package com.debtcoin.debtcoinapp.API.model;

/**
 * Created by fluxion inc on 08/06/2018.
 */

public class AcceptedApplicants {
    private int id;
    private int count;
    private boolean exceeded;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isExceeded() {
        return exceeded;
    }

    public void setExceeded(boolean exceeded) {
        this.exceeded = exceeded;
    }
}
