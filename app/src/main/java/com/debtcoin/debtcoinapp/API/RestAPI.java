package com.debtcoin.debtcoinapp.API;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ace_bandong on 11/04/2016.
 */

public class RestAPI {

    private String HttpResponse = "";

    public RestAPI(){

    }

    protected String httpPostResponse(String SERVICE_URL, String JsonString) {
        this.httpPostMethod(SERVICE_URL, JsonString);
        return this.HttpResponse;
    }

    protected String httpGetResponse(String SERVICE_URL, String Token) {
        this.httpGetMethod(SERVICE_URL, Token);
        return this.HttpResponse;
    }
    public void httpPostMethod(String service_URL, String jsonString) {
        int ResponseCode = 0;

        try {
            URL e = new URL(service_URL);
            HttpURLConnection conn = (HttpURLConnection) e.openConnection();
            conn.setReadTimeout(120000);
            conn.setConnectTimeout(120000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "application/json");
         //  conn.setRequestProperty("Authorization", "Bearer " + Token);
            conn.connect();
            OutputStream out = conn.getOutputStream();
            out.write(jsonString.toString().getBytes());
            out.close();
            ResponseCode = conn.getResponseCode();
            BufferedReader _buff = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer response = new StringBuffer();

            String _InputLine;
            while ((_InputLine = _buff.readLine()) != null) {
                response.append(_InputLine);
            }

            this.HttpResponse = ResponseCode + "|" + response.toString();
        } catch (Exception var11) {
            this.HttpResponse = ResponseCode + "|" + var11.getMessage();
        }
    }

    private void httpGetMethod(String SERVICE_URL, String Token) {
        int ResponseCode = 0;
        String JsonString ="";

        try {
            URL e = new URL(SERVICE_URL);
            HttpURLConnection conn = (HttpURLConnection) e.openConnection();
            conn.setReadTimeout(120000);
            conn.setConnectTimeout(120000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Authorization", "Bearer " + Token);
            conn.connect();
            ResponseCode = conn.getResponseCode();
            BufferedReader _buff = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer response = new StringBuffer();

            String _InputLine;
            while ((_InputLine = _buff.readLine()) != null) {
                response.append(_InputLine);
            }

            this.HttpResponse = ResponseCode + "|" + response.toString();
        } catch (Exception var11) {
            this.HttpResponse = ResponseCode + "|" + var11.getMessage();
        }
    }
}
