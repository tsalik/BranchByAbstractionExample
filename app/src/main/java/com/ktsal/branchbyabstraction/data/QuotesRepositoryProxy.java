package com.ktsal.branchbyabstraction.data;


import com.ktsal.branchbyabstraction.domain.entity.Quote;

import java.util.List;

import io.reactivex.Observable;

public class QuotesRepositoryProxy implements QuotesRepository {

    private final QuotesDataSource quotesDataSource;

    public QuotesRepositoryProxy(QuotesDataSource quotesDataSource) {
        this.quotesDataSource = quotesDataSource;
    }

    @Override
    public Observable<List<Quote>> getSavedQuotes() {
        return quotesDataSource.getSavedQuotes();
    }

    @Override
    public Observable<Boolean> add(Quote quote) {
        return quotesDataSource.add(quote);
    }

}
