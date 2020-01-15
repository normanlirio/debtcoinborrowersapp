package com.debtcoin.debtcoinapp.API.service.rx;

import com.debtcoin.debtcoinapp.API.model.Payment;
import com.debtcoin.debtcoinapp.API.model.ResultMessage;
import com.debtcoin.debtcoinapp.API.service.DebtcoinServiceGenerator;
import com.debtcoin.debtcoinapp.API.service.PaymentService;

import io.reactivex.Maybe;

public class PaymentRxService {

    private PaymentService paymentService;

    public PaymentRxService() {
        paymentService = DebtcoinServiceGenerator.createService(PaymentService.class);
    }

    public PaymentRxService(String token) {
        paymentService = DebtcoinServiceGenerator.createServiceWithAuth(PaymentService.class, token);
    }

    public Maybe<ResultMessage<Payment>> makePayment(String username, Payment payment) {
        return paymentService.makePayment(username, payment);
    }

    public Maybe<ResultMessage<Payment>> getPaymentById(long id) {
        return paymentService.getPaymentById(id);
    }


}
