package com.debtcoin.debtcoinapp.API.service;

import com.debtcoin.debtcoinapp.API.model.Banner;
import com.debtcoin.debtcoinapp.API.model.ResultMessage;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by Roxel on 21/07/2018.
 */

public interface BannerService {

    @GET("/api/banner/images/")
    Maybe<ResultMessage<List<Banner>>> getAllBanners();
}
