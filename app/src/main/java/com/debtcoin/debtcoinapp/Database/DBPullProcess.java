package com.debtcoin.debtcoinapp.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

import com.debtcoin.debtcoinapp.Globals.Methods;
import com.debtcoin.debtcoinapp.Globals.Variables;
import com.debtcoin.debtcoinapp.Models.EmploymentInformation;
import com.debtcoin.debtcoinapp.Models.Financial;
import com.debtcoin.debtcoinapp.Models.Personal;
import com.debtcoin.debtcoinapp.Models.Referral;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by Lirio on 5/14/2018.
 */

public class DBPullProcess extends DBHandler {
    private Context mContext;
    private SQLiteDatabase db;
    private Cursor res;
    public DBPullProcess(Context context) {
        super(context);
    }

    public Personal getPersonalInfo() {
        Personal personal = new Personal();
        db = this.getWritableDatabase();
        String sql = "Select fname, doctype, mobilenumber, dob, address, docnumber from Information where email = '" + Variables.email + "'";
        try {
            res = db.rawQuery(sql, null);
            while(res.moveToNext()) {
              //  Log.v("GET PINFO", res.getString(res.getColumnIndex("fname")));
                personal.setFname(res.getString(res.getColumnIndex("fname")));
                personal.setDoctype(res.getString(res.getColumnIndex("doctype")));
                personal.setMobilenumber(res.getString(res.getColumnIndex("mobilenumber")));
                personal.setDob(res.getString(res.getColumnIndex("dob")));
                personal.setAddress(res.getString(res.getColumnIndex("address")));
                personal.setDocNumber(res.getString(res.getColumnIndex("docnumber")));
            }
            return personal;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            res.close();
            db.close();
        }
        return personal;
    }

    public EmploymentInformation getEmploymentInfo() {
        EmploymentInformation employmentInformation = new EmploymentInformation();
        db = this.getWritableDatabase();
        String sql = "Select status, empname, empnature, position, empaddress from Information where email = '" + Variables.email + "'";
        try {
            res = db.rawQuery(sql, null);
            while(res.moveToNext()) {
                employmentInformation.setStatus(res.getString(res.getColumnIndex("status")));
                employmentInformation.setEmpname(res.getString(res.getColumnIndex("empname")));
                employmentInformation.setEmpnature(res.getString(res.getColumnIndex("empnature")));
                employmentInformation.setPosition(res.getString(res.getColumnIndex("position")));
                employmentInformation.setEmpaddress(res.getString(res.getColumnIndex("empaddress")));

            }
            return employmentInformation;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            res.close();
            db.close();
        }
        return employmentInformation;
    }

    public Referral getReferenceInfo() {
        Referral referral = new Referral();
        db = this.getWritableDatabase();
        String sql = "Select refName, refNameId, refName2, refNameId2 from Information where email = '" + Variables.email + "'";
        try {
            res = db.rawQuery(sql, null);
            while(res.moveToNext()) {
                referral.setRefName(res.getString(res.getColumnIndex("refName")));
                referral.setRefNameId(res.getString(res.getColumnIndex("refNameId")));
                referral.setRefName2(res.getString(res.getColumnIndex("refName2")));
                referral.setRefNameId2(res.getString(res.getColumnIndex("refNameId2")));


            }
            return referral;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            res.close();
            db.close();
        }
        return referral;
    }

    public Financial getFinancialInfo() {
        Financial financial = new Financial();
        db = this.getWritableDatabase();
        String sql = "Select bankName, accountNumber, accountType from Information where email = '" + Variables.email + "'";
        try {
            res = db.rawQuery(sql, null);
            while(res.moveToNext()) {
                financial.setBankName(res.getString(res.getColumnIndex("bankName")));
                financial.setAccountNumber(res.getString(res.getColumnIndex("accountNumber")));
                financial.setAccountType(res.getString(res.getColumnIndex("accountType")));

            }
            return financial;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            res.close();
            db.close();
        }
        return financial;
    }

    public HashMap<String, String> getStepSevenInfo() {
        HashMap<String, String> map = new HashMap<>();
        String base64 = "";
        db = this.getWritableDatabase();
        String sql = "Select stepsevenimage, filepathseven from Information where email = '" + Variables.email + "'";
        Cursor res1 = db.rawQuery(sql, null);
        try {

            while(res1.moveToNext()) {
                map.put("base64", res1.getString(0));
                map.put("filepath", res1.getString(1));
            }

            return map;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            res1.close();
            db.close();
        }
        return map;
    }


    public HashMap<String, String> getStepEightInfo() {
        HashMap<String, String> map = new HashMap<>();
        String filepath = "";
        db = this.getWritableDatabase();
        String sql = "Select stepeightimage, filepatheight from Information where email = '" + Variables.email + "'";
        Cursor res2 = db.rawQuery(sql, null);
        try {

            while(res2.moveToNext()) {
                map.put("base64", res2.getString(res2.getColumnIndex("stepeightimage")));
                map.put("filepath", res2.getString(res2.getColumnIndex("filepatheight")));
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            res2.close();
            db.close();
        }
        return map;
    }

    public HashMap<String, String> getCredentials() {
        HashMap<String, String> map = new HashMap<>();
        db = this.getWritableDatabase();
        String sql = "Select username, password from Information where email = '" + Variables.email + "'";
        try {
            res = db.rawQuery(sql, null);
            while(res.moveToNext()) {
                map.put("user", res.getString(res.getColumnIndex("username")));
                map.put("pass", res.getString(res.getColumnIndex("password")));

            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            res.close();
            db.close();
        }
        return map;
    }


    public void deleteDB() {
        try {

            if(SQLiteDatabase.deleteDatabase(new File("/data/data/com.debtcoin.debtcoinapp/databases/debtcoin.db"))) {
             //   Log.v("DB DELETE", "DB Deleted.");
            }

        } catch (Exception e) {
           // Log.v("DB DELETE", "DB failed deletion.");
            e.printStackTrace();
        }
    }
}
