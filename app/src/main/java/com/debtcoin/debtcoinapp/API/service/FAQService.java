package com.debtcoin.debtcoinapp.API.service;

import com.debtcoin.debtcoinapp.API.model.Message;
import com.debtcoin.debtcoinapp.API.model.ResultMessage;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Roxel on 07/07/2018.
 */

public interface FAQService {

    @POST("/api/faq/messages")
    Single<ResultMessage<Message>> sendFaqMessage(@Body Message message);
}
