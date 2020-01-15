package com.debtcoin.debtcoinapp.API.service.rx;

import com.debtcoin.debtcoinapp.API.model.Message;
import com.debtcoin.debtcoinapp.API.model.ResultMessage;
import com.debtcoin.debtcoinapp.API.service.DebtcoinServiceGenerator;
import com.debtcoin.debtcoinapp.API.service.FAQService;

import io.reactivex.Single;

/**
 * Created by Roxel on 07/07/2018.
 */

public class FaqRxService {

    private FAQService faqService;

    public FaqRxService() {
        faqService = DebtcoinServiceGenerator.createService(FAQService.class);
    }

    public Single<ResultMessage<Message>> sendFaqMessage(Message message) {
        return faqService.sendFaqMessage(message);
    }
}
