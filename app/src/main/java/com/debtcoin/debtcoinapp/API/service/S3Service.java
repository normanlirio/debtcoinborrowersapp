package com.debtcoin.debtcoinapp.API.service;

import com.debtcoin.debtcoinapp.API.model.PaymentDepositImage;
import com.debtcoin.debtcoinapp.API.model.ResultMessage;

import io.reactivex.Maybe;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface S3Service {

    @Multipart
    @POST("/api/s3/payment/fileupload")
    Maybe<ResultMessage<PaymentDepositImage>> uploadPaymentFileToS3(@Part MultipartBody.Part paymentFile,
                                                                    @Part("username") RequestBody username);
}
