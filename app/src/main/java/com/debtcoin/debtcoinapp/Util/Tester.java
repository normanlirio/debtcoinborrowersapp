package com.debtcoin.debtcoinapp.Util;

import com.debtcoin.debtcoinapp.Fragments.ApplyLoan;

/**
 * Created by fluxion inc on 12/07/2018.
 */

public class Tester {
    public static double amountOfLoantest(String amount, String cod){

        int coh = Integer.parseInt(cod);
        int aol = Integer.parseInt(amount);
        double result = 0;
        if(aol < 3001) {
            result = aol + (aol * 0.08 ) /2;
        }
        if(aol > 3000 && aol < 7001) {

        }
        if(aol > 7000 && aol <= 100000) {

        }
        return result;
    }
    public static void main(String[] args) {

        System.out.print( amountOfLoantest("3000", "15"));
    }
}
