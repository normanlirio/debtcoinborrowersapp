package com.debtcoin.debtcoinapp.API.service.rx;

import com.debtcoin.debtcoinapp.API.model.Banner;
import com.debtcoin.debtcoinapp.API.model.ResultMessage;
import com.debtcoin.debtcoinapp.API.service.BannerService;
import com.debtcoin.debtcoinapp.API.service.DebtcoinServiceGenerator;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by Roxel on 21/07/2018.
 */

public class BannerRxService {

    private BannerService bannerService;

    public BannerRxService() {
        bannerService = DebtcoinServiceGenerator.createService(BannerService.class);
    }

    public BannerRxService(String token) {
        bannerService = DebtcoinServiceGenerator.createServiceWithAuth(BannerService.class, token);
    }

    public Maybe<ResultMessage<List<Banner>>> getAllBanners() {
        return bannerService.getAllBanners();
    }
}
