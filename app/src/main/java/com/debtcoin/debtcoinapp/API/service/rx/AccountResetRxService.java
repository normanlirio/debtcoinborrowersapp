package com.debtcoin.debtcoinapp.API.service.rx;

import com.debtcoin.debtcoinapp.API.model.AccountReset;
import com.debtcoin.debtcoinapp.API.model.Mail;
import com.debtcoin.debtcoinapp.API.model.ResultMessage;
import com.debtcoin.debtcoinapp.API.service.AccountResetService;
import com.debtcoin.debtcoinapp.API.service.DebtcoinServiceGenerator;

import io.reactivex.Single;

public class AccountResetRxService {

    private AccountResetService accountResetService;

    public AccountResetRxService() {
        accountResetService = DebtcoinServiceGenerator.createService(AccountResetService.class);
    }

    public Single<ResultMessage<AccountReset>> generateForgotPasswordConfirmationCode(Mail mail) {
        return accountResetService.generateForgotPasswordConfirmationCode(mail);
    }

    public Single<ResultMessage<AccountReset>> confirmResetCode(AccountReset accountReset) {
        return accountResetService.confirmResetCode(accountReset);
    }

    public Single<ResultMessage<Void>> changePassword(AccountReset accountReset) {
        return accountResetService.changePassword(accountReset);
    }
}
