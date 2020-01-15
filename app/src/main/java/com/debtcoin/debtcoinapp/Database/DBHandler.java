package com.debtcoin.debtcoinapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Lirio on 5/14/2018.
 */

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "debtcoin.db";
    private final static String DB_PATH = "/data/data/com.debtcoin.debtcoinapp/databases/";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase db;
    private Context mContext;

    public DBHandler(Context context)  {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
        boolean dbexist = checkDatabase();
        if(dbexist) {
            openDatabase();
        } else {
            try {

                createDatabase();
                System.out.println("Will create Database...");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void createDatabase() throws IOException {
        boolean dbExist = checkDatabase();
        SQLiteDatabase dbRead = null;
        if(dbExist) {
            System.out.println("DB EXISTS");
        } else {
            dbRead = this.getReadableDatabase();
            dbRead.close();
            try {
                copyDatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");

            }
        }
    }

    private void copyDatabase() throws IOException {

        InputStream myDBInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        Log.v("DB PATH", outFileName);
        OutputStream outputFileName = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while((length = myDBInput.read(buffer)) > 0) {
            outputFileName.write(buffer, 0 , length);
        }
        outputFileName.flush();
        outputFileName.close();
        System.out.println("DB COPIED!!");
        myDBInput.close();
    }

    public boolean checkDatabase() {
        String stringPath = DB_PATH + DB_NAME;
        boolean dbPath = false;
        try {
            File dbfile = new File(stringPath);
            dbPath = dbfile.exists();
        } catch(SQLiteException e) {
            System.out.print("DOES NOT EXIIIIIIIIIIIIST");
        }


        return dbPath;
    }

    public void openDatabase() throws SQLiteException {
        String path = DB_PATH + DB_NAME;
        Log.v("DB PATH", path);
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if(db != null) {
            db.close();
        }
        super.close();
    }
}
