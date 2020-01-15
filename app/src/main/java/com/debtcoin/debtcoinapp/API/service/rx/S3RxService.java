package com.debtcoin.debtcoinapp.API.service.rx;

import com.debtcoin.debtcoinapp.API.model.PaymentDepositImage;
import com.debtcoin.debtcoinapp.API.model.ResultMessage;
import com.debtcoin.debtcoinapp.API.service.DebtcoinServiceGenerator;
import com.debtcoin.debtcoinapp.API.service.S3Service;

import io.reactivex.Maybe;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;

public class S3RxService {

    private S3Service s3Service;

    public S3RxService() {
        s3Service = DebtcoinServiceGenerator.createService(S3Service.class);
    }

    public S3RxService(String token) {
        s3Service = DebtcoinServiceGenerator.createServiceWithAuth(S3Service.class, token);
    }

    public Maybe<ResultMessage<PaymentDepositImage>> uploadPaymentFileToS3(@Part MultipartBody.Part paymentFile,
                                                                           @Part("username") RequestBody username) {
        return s3Service.uploadPaymentFileToS3(paymentFile, username);
    }
}
