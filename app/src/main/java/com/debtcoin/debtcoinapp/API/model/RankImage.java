package com.debtcoin.debtcoinapp.API.model;

public class RankImage {

    private String name;
    private String path;
    private boolean newUpdate;

    public RankImage() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isNewUpdate() {
        return newUpdate;
    }

    public void setNewUpdate(boolean newUpdate) {
        this.newUpdate = newUpdate;
    }
}
