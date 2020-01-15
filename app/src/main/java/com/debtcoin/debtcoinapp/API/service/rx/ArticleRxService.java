package com.debtcoin.debtcoinapp.API.service.rx;

import com.debtcoin.debtcoinapp.API.model.Article;
import com.debtcoin.debtcoinapp.API.model.ResultMessage;
import com.debtcoin.debtcoinapp.API.service.ArticleService;
import com.debtcoin.debtcoinapp.API.service.DebtcoinServiceGenerator;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;

/**
 * Created by Roxel on 21/07/2018.
 */

public class ArticleRxService {

    private ArticleService articleService;

    public ArticleRxService(String token) {
        articleService = DebtcoinServiceGenerator.createServiceWithAuth(ArticleService.class, token);
    }

    public Maybe<ResultMessage<List<Article>>> getAllArticles() {
        return articleService.getAllArticles();
    }
}
