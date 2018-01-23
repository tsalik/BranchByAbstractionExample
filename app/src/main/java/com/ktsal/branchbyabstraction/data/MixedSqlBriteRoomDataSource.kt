package com.ktsal.branchbyabstraction.data


class MixedSqlBriteRoomDataSource(private val newDataSource: QuotesDataSource)
    : QuotesDataSource by newDataSource