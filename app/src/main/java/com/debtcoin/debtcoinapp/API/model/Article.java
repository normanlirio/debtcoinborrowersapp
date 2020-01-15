package com.debtcoin.debtcoinapp.API.model;

public class Article {

    private String content;
    private String name;
    private String jobTitle;
    private String employer;
    private String path;
    private boolean newUpdate;

    public Article() {}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
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
