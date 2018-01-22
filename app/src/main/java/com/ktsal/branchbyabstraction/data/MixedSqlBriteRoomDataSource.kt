package com.ktsal.branchbyabstraction.data


class MixedSqlBriteRoomDataSource(private val oldDataSource: QuotesDataSource)
    : QuotesDataSource by oldDataSource