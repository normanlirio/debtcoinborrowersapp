package com.debtcoin.debtcoinapp.API.service;

import com.debtcoin.debtcoinapp.API.model.AcceptedApplicants;
import com.debtcoin.debtcoinapp.API.model.DocumentImage;
import com.debtcoin.debtcoinapp.API.model.ResultMessage;
import com.debtcoin.debtcoinapp.API.model.User;
import com.debtcoin.debtcoinapp.API.model.UserAccount;
import com.debtcoin.debtcoinapp.API.model.UserToken;

import io.reactivex.Maybe;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface UserService {
    @POST("/login")
    Call<Void> login(@Body UserAccount userAccount);

    @POST("/api/users/sign-up")
    Call<ResultMessage<User>> signUp(@Body User user);

    @Multipart
    @POST("/api/s3/fileupload")
    Call<Void> signUpImages(@Part MultipartBody.Part govIdImage, @Part MultipartBody.Part selfieIdImage, @Part("email") RequestBody email);

    @GET("/api/users/applicants/limit")
    Call<ResultMessage<AcceptedApplicants>> getApplicantCount();

    @POST("/api/users/sign-up")
    Maybe<ResultMessage<User>> signUpUser(@Body User user);

    @Multipart
    @POST("/api/s3/fileupload")
    Maybe<ResultMessage<DocumentImage>> signUpUserImages(@Part MultipartBody.Part govIdImage, @Part MultipartBody.Part selfieIdImage, @Part("email") RequestBody email);

    @POST("/login")
    Maybe<UserToken> loginUser(@Body UserAccount userAccount);

    @GET("/api/users/{username}")
    Maybe<ResultMessage<User>> getUserByUsername(@Path("username") String username);

    @GET("/api/user/account/{username}")
    Single<ResultMessage<UserAccount>> getUserAccountByUsername(@Path("username") String username);

    @POST("/api/user/account/check/username")
    Single<ResultMessage<String>> checkUsername(@Body UserAccount userAccount);
}
