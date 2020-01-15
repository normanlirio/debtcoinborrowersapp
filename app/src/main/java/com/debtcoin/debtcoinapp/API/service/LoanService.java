package com.debtcoin.debtcoinapp.API.service;

import com.debtcoin.debtcoinapp.API.model.LoanApplication;
import com.debtcoin.debtcoinapp.API.model.LoanDetails;
import com.debtcoin.debtcoinapp.API.model.ResultMessage;
import com.debtcoin.debtcoinapp.API.model.UserAccount;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LoanService {

    @POST("/api/user/account/loan/{username}")
    Single<ResultMessage<LoanApplication>> applyForLoan(@Path("username") String username,
                                                        @Body LoanApplication loanApplication);

    @GET("/api/user/account/loan/details/{username}")
    Single<ResultMessage<LoanDetails>> getLoanDetailsByUsername(@Path("username") String username);
}
