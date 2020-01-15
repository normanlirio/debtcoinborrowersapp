package com.debtcoin.debtcoinapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import com.debtcoin.debtcoinapp.Globals.Methods;
import com.debtcoin.debtcoinapp.Globals.Variables;
import com.debtcoin.debtcoinapp.Models.EmploymentInformation;
import com.debtcoin.debtcoinapp.Models.Financial;
import com.debtcoin.debtcoinapp.Models.Personal;
import com.debtcoin.debtcoinapp.Models.Referral;
import com.debtcoin.debtcoinapp.Util.AESCrypt;

import java.security.Key;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Lirio on 5/14/2018.
 */

public class DBSaveProcess extends DBHandler {
    private Context mContext;
    private SQLiteDatabase db;
    private Cursor res;
    private String sql = "";
    private String TAG = "";

    public DBSaveProcess(Context context) {
        super(context);
    }

    public boolean savePersonalInfo(Personal personal) {
        TAG = "Personal Info";
        boolean insertSuccess = false;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("fname", personal.getFname().trim());
        cv.put("doctype",personal.getDoctype().trim());
        cv.put("dob", personal.getDob().trim());
        cv.put("mobilenumber", personal.getMobilenumber().trim());
        cv.put("address", personal.getAddress().trim());
        cv.put("docnumber", personal.getDocNumber().trim());
        cv.put("email", Variables.email.trim());
        Log.v(TAG, personal.getFname());
        Log.v(TAG, personal.getDoctype());
        Log.v(TAG, personal.getDob());
        Log.v(TAG, personal.getMobilenumber());
        Log.v(TAG, personal.getAddress());
        Log.v(TAG, personal.getDocNumber());
        Log.v(TAG, "EMAIL " + Variables.email);
        int id = getID(Variables.email.trim());
        Variables.id = id;
        Variables.mobilenumber = personal.getMobilenumber().trim();
        try {
           if(id == -1) {
               Log.v("Personal Info", "saving to local database...");
               insertSuccess = db.insert("Information", null, cv ) > 0;
               if(insertSuccess) {
                   Log.v(TAG, "Saving success!");
               } else {
                   Log.v(TAG, "Something went wrong.");
               }

           } else {
               Log.v(TAG, "Updating... " + id);
               insertSuccess = db.update("Information", cv, "id=?", new String[]{Integer.toString(id)}) > 0;
           }
            return insertSuccess;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return insertSuccess;
    }

    private int getID(String num){
        int id = -1;
        db = this.getWritableDatabase();
        Cursor c = db.query("Information",new String[]{"ID"} ,"email =?",new String[]{num},null,null,null,null);
        if (c.moveToFirst()) {
            //if the row exist then return the id
            return c.getInt(c.getColumnIndex("ID"));
        }

        return id;
    }

    public boolean saveEmploymentInfo(EmploymentInformation employmentInformation) {
        String TAG = "Employment Info";
        boolean insertSuccess = false;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status", employmentInformation.getStatus());
        cv.put("empname", employmentInformation.getEmpname());
        cv.put("empnature", employmentInformation.getEmpnature());
        cv.put("position", employmentInformation.getPosition());
        cv.put("empaddress", employmentInformation.getEmpaddress());
        try {

            Log.v(TAG, "Updating table... " + Variables.email);

            insertSuccess = db.update("Information", cv, "email=?",new String[]{Variables.email} ) > 0;

            return insertSuccess;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return insertSuccess;
    }

    public boolean saveFinancialInfo(Financial financial) {
        boolean insertSuccess = false;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("bankName", financial.getBankName());
        cv.put("accountType",financial.getAccountType());
        cv.put("accountNumber",financial.getAccountNumber());

        try {
            insertSuccess = db.update("Information", cv, "email=?", new String[]{Variables.email} ) > 0;
            return insertSuccess;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return insertSuccess;
    }

    public boolean saveReferenceInfo(Referral referral) {
        boolean insertSuccess = false;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("refName", referral.getRefName());
        cv.put("refNameId",referral.getRefNameId());
        cv.put("refName2", referral.getRefName2());
        cv.put("refNameId2",referral.getRefNameId2());

        try {
            insertSuccess = db.update("Information", cv, "email=?", new String[]{Variables.email} ) > 0;
            return insertSuccess;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return insertSuccess;
    }

    public boolean saveCredentials(String username, String password) {
        boolean insertSuccess = false;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        try {
            cv.put("username", username);
            cv.put("password",AESCrypt.encrypt(password));
            insertSuccess = db.update("Information", cv, "email=?", new String[]{Variables.email} ) > 0;
            return insertSuccess;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            db.close();
        }

        return insertSuccess;
    }

    public boolean stepSeven(HashMap<String, Bitmap> image, String path) {
        TAG = "step seven save";
        Log.v(TAG, "DO IN BACKGROUND");
        boolean insertSuccess = false;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("filepathseven", path);
        cv.put("stepsevenimage", Methods.bitmapToBase64(image.get("filepath")));
        Log.v("Step 7 Save", "Email : " + Variables.email);
        try {
            insertSuccess = db.update("Information", cv, "email=?", new String[]{Variables.email} ) > 0;
            if(insertSuccess) {
                Log.v(TAG, "TRUE");
            } else  {
                Log.v(TAG, "FALSE");
            }

            return insertSuccess;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return insertSuccess;
    }

    public boolean stepEight(HashMap<String, Bitmap> image, String path) {
        TAG = "step seven save";
        Log.v(TAG, "DO IN BACKGROUND");
        boolean insertSuccess = false;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("filepatheight", path);
        cv.put("stepeightimage", Methods.bitmapToBase64(image.get("filepath")));
        Log.v("Step 8 Save", "Email : " + Variables.email);
        try {
            insertSuccess = db.update("Information", cv, "email=?", new String[]{Variables.email} ) > 0;
            if(insertSuccess) {
                Log.v(TAG, "TRUE");
            } else  {
                Log.v(TAG, "FALSE");
            }

            return insertSuccess;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return insertSuccess;
    }




}
