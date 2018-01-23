package com.ktsal.branchbyabstraction.data

import com.ktsal.branchbyabstraction.domain.entity.Quote
import io.reactivex.Observable


class MixedSqlBriteRoomDataSource(private val oldDataSource: QuotesDataSource,
                                  private val newDataSource: QuotesDataSource)
    : QuotesDataSource by oldDataSource {

    override fun getSavedQuotes(): Observable<MutableList<Quote>> {
        return newDataSource.savedQuotes
    }

    override fun add(quote: Quote): Observable<Boolean> {
        return newDataSource.add(quote)
    }

}