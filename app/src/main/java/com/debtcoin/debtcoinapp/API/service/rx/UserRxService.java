package com.debtcoin.debtcoinapp.API.service.rx;

import com.debtcoin.debtcoinapp.API.model.DocumentImage;
import com.debtcoin.debtcoinapp.API.model.ResultMessage;
import com.debtcoin.debtcoinapp.API.model.User;
import com.debtcoin.debtcoinapp.API.model.UserAccount;
import com.debtcoin.debtcoinapp.API.model.UserToken;
import com.debtcoin.debtcoinapp.API.service.DebtcoinServiceGenerator;
import com.debtcoin.debtcoinapp.API.service.UserService;

import io.reactivex.Maybe;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;

public class UserRxService {

    private UserService userService;

    public UserRxService() {
        userService = DebtcoinServiceGenerator.createService(UserService.class);
    }

    public UserRxService(String token) {
        userService = DebtcoinServiceGenerator.createServiceWithAuth(UserService.class, token);
    }

    public Maybe<ResultMessage<User>> signUpUser(User user) {
        return userService.signUpUser(user);
    }

    public Maybe<ResultMessage<DocumentImage>> signUpUserImages(@Part MultipartBody.Part govIdImage,
                                                                @Part MultipartBody.Part selfieIdImage,
                                                                @Part("email") RequestBody email) {

        return userService.signUpUserImages(govIdImage, selfieIdImage, email);
    }

    public Maybe<UserToken> loginUser(UserAccount userAccount) {
        return userService.loginUser(userAccount);
    }

    public Maybe<ResultMessage<User>> getUserByUsername(String username) {
        return userService.getUserByUsername(username);
    }

    public Single<ResultMessage<UserAccount>> getUserAccountByUsername(String username) {
        return userService.getUserAccountByUsername(username);
    }

    public Single<ResultMessage<String>> checkUsername(UserAccount userAccount) {
        return userService.checkUsername(userAccount);
    }
}
