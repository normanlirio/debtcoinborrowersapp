package com.debtcoin.debtcoinapp.API.service;

import com.debtcoin.debtcoinapp.API.model.Payment;
import com.debtcoin.debtcoinapp.API.model.ResultMessage;

import io.reactivex.Maybe;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface PaymentService {

    @POST("/api/user/account/loan/payment/{username}")
    Maybe<ResultMessage<Payment>> makePayment(@Path("username") String username, @Body Payment payment);

    @GET("/api/user/account/loan/payment/{id}")
    Maybe<ResultMessage<Payment>> getPaymentById(@Path("id") long id);
}
