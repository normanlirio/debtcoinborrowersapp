package com.debtcoin.debtcoinapp.API.service.rx;


import com.debtcoin.debtcoinapp.API.model.LoanApplication;
import com.debtcoin.debtcoinapp.API.model.LoanDetails;
import com.debtcoin.debtcoinapp.API.model.ResultMessage;
import com.debtcoin.debtcoinapp.API.service.DebtcoinServiceGenerator;
import com.debtcoin.debtcoinapp.API.service.LoanService;

import io.reactivex.Single;

public class LoanRxService {

    private LoanService loanService;

    public LoanRxService() {
        loanService = DebtcoinServiceGenerator.createService(LoanService.class);
    }

    public LoanRxService(String token) {
        loanService = DebtcoinServiceGenerator.createServiceWithAuth(LoanService.class, token);
    }

    public Single<ResultMessage<LoanApplication>> applyForLoan(String username, LoanApplication loanApplication) {
        return loanService.applyForLoan(username, loanApplication);
    }

    public Single<ResultMessage<LoanDetails>> getLoanDetailsByUsername(String username) {
        return loanService.getLoanDetailsByUsername(username);
    }
}
