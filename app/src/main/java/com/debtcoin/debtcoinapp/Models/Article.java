package com.debtcoin.debtcoinapp.Models;

import java.util.HashMap;

/**
 * Created by Lirio on 7/1/2018.
 */

public class Article {
    private String text;
    private String path;
    private HashMap<String, String> map;
   // private String id;

    public Article( String path,String text, HashMap<String, String> map) {
        this.text = text;
        this.path = path;
        this.map = map;
    }


//    public void setId(String id) {
//        this.id = id;
//    }


    public HashMap<String, String> getMap() {
        return map;
    }

    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
