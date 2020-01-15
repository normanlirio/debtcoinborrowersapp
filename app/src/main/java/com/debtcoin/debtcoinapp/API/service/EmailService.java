package com.debtcoin.debtcoinapp.API.service;

import com.debtcoin.debtcoinapp.API.model.ConfirmationCode;
import com.debtcoin.debtcoinapp.API.model.Mail;
import com.debtcoin.debtcoinapp.API.model.ResultMessage;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Roxel on 15/05/2018.
 */

public interface EmailService {
    @POST("/api/email")
    public Call<ResultMessage<ConfirmationCode>> getConfirmationCode(@Body Mail email);

    @PUT("/api/email")
    public Call<ResultMessage<ConfirmationCode>> confirmCode(@Body ConfirmationCode confirmationCode);

}
