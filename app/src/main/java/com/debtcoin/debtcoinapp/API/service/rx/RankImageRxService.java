package com.debtcoin.debtcoinapp.API.service.rx;

import com.debtcoin.debtcoinapp.API.model.RankImage;
import com.debtcoin.debtcoinapp.API.model.ResultMessage;
import com.debtcoin.debtcoinapp.API.service.DebtcoinServiceGenerator;
import com.debtcoin.debtcoinapp.API.service.RankImageService;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * Created by Roxel on 21/07/2018.
 */

public class RankImageRxService {

    private RankImageService rankImageService;

    public RankImageRxService(String token) {
        rankImageService = DebtcoinServiceGenerator.createServiceWithAuth(RankImageService.class, token);
    }

    public Maybe<ResultMessage<List<RankImage>>> getAllRankImages() {
        return rankImageService.getAllRankImages();
    }
}
