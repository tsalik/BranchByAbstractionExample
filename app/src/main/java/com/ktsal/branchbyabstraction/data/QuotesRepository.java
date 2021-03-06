package com.ktsal.branchbyabstraction.data;


import com.ktsal.branchbyabstraction.domain.entity.Quote;

import java.util.List;

import io.reactivex.Observable;

public interface QuotesRepository {

    Observable<List<Quote>> getSavedQuotes();

    Observable<Boolean> add(Quote quote);

}
