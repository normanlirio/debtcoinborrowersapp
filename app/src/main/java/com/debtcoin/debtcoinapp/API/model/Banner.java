package com.debtcoin.debtcoinapp.API.model;

public class Banner {

    private String title;
    private String content;
    private String path;
    private boolean newUpdate;

    private int id;

    public Banner(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
