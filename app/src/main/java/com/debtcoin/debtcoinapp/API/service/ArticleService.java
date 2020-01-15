package com.debtcoin.debtcoinapp.API.service;


import com.debtcoin.debtcoinapp.API.model.Article;
import com.debtcoin.debtcoinapp.API.model.ResultMessage;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Roxel on 21/07/2018.
 */

public interface ArticleService {

    @GET("/api/article/images/")
    Maybe<ResultMessage<List<Article>>> getAllArticles();
}
