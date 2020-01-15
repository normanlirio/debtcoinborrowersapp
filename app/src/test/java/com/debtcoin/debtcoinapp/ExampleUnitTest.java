package com.debtcoin.debtcoinapp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void test3kBelow() throws Exception {
        int coh = 15;
        int aol = 3000;
        double result = 0;
        double expected = 3320;
        if(aol < 3001) {
            result = aol + (aol * 0.08 ) /2;
            assertEquals("Result :", expected , result, 0.001);
        }
    }
}