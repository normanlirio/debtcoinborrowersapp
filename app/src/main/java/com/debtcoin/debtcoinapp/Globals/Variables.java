package com.debtcoin.debtcoinapp.Globals;

import com.debtcoin.debtcoinapp.API.model.Payment;

/**
 * Created by Lirio on 5/14/2018.
 */

public class Variables {
    public static boolean stepThree = false;
    public static boolean stepFour = false;
    public static boolean stepFive = false;
    public static boolean stepSix = false;
    public static boolean stepSven = false;
    public static boolean stepEight = false;
    public static boolean stepNine = false;
    public static boolean isLoggedIn = false;
    public static int id = 0;
    public static String mobilenumber;
    public static String email;
    public static String token;
    public static int terms = 0;
    public static String date = "";
    public static double amount = 0;
    public static boolean hasPressedNext;
    public static boolean hasReadNotification;
    public static boolean isImageUploaded = true;
    public static long paymentId;
    public static Payment payment;
    public static String bankName;

    public static void clearAll() {
        id = 0;
        mobilenumber = "";
        email = "";
        token = "";
        terms = 0;
        date = "";
        amount = 0;
        hasPressedNext = false;
    }
}
