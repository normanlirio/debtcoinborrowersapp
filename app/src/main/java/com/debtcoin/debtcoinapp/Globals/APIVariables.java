package com.debtcoin.debtcoinapp.Globals;

import com.debtcoin.debtcoinapp.API.model.Article;
import com.debtcoin.debtcoinapp.API.model.LoanApplication;
import com.debtcoin.debtcoinapp.API.model.LoanDetails;
import com.debtcoin.debtcoinapp.API.model.Notice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roxel on 06/07/2018.
 */

public class APIVariables {

    public static Long userId;

    public static String username;
    public static String name;
    public static String rank;
    public static float creditLimit;
    public static int referrals;
    public static String email;
    public static String mobileNumber;
    public static boolean isPending;
    public static String notification;
    public static String currentPaymentDueDate;

    // For forgot password
    public static String resetCode;
    public static String resetEmail;
    public static String resetToken;

    public static ArrayList<String> bannerLinks = new ArrayList<>();
    public static ArrayList<Article> articles = new ArrayList<>();
    public static ArrayList<String> rankImageLinks = new ArrayList<>();
    public static ArrayList<String> loanDetailsNotices = new ArrayList<>();
    public static List<LoanDetails> loanDetails = new ArrayList<>();
    public static String currentRankLink;
    public static boolean isOverdue = false;
    public static Notice notice;
    public static List<LoanApplication> loanReason;

    public static void clearForgotPasswordVars() {
        resetCode = "";
        resetEmail = "";
        resetToken = "";
    }


    public static void clearAll(){
        userId = 0L;
        username = "";
        rank = "";
        creditLimit = 0L;
        referrals = 0;
        email = "";
        mobileNumber = "";
        loanDetailsNotices.clear();
        articles.clear();
        bannerLinks.clear();
        rankImageLinks.clear();
        
    }
}
