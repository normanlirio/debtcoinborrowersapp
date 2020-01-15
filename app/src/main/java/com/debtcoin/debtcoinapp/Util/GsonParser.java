package com.debtcoin.debtcoinapp.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Roxel on 07/07/2018.
 */

public class GsonParser {

    private static Gson gson;

    public static Gson parse() {
        if (null == gson) {
            GsonBuilder builder = new GsonBuilder();
            gson = builder.create();
        }
        return gson;
    }
}
