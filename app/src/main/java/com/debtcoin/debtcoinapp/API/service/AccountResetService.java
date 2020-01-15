package com.debtcoin.debtcoinapp.API.service;

import com.debtcoin.debtcoinapp.API.model.AccountReset;
import com.debtcoin.debtcoinapp.API.model.Mail;
import com.debtcoin.debtcoinapp.API.model.ResultMessage;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AccountResetService {

    @POST("/api/user/account/reset/email")
    Single<ResultMessage<AccountReset>> generateForgotPasswordConfirmationCode(@Body Mail mail);

    @POST("/api/user/account/reset/email/confirm")
    Single<ResultMessage<AccountReset>> confirmResetCode(@Body AccountReset accountReset);

    @POST("/api/user/account/reset/")
    Single<ResultMessage<Void>> changePassword(@Body AccountReset accountReset);
}
