package com.ktsal.branchbyabstraction

import android.content.ContentValues
import android.support.test.InstrumentationRegistry
import com.ktsal.branchbyabstraction.data.QuotesDbHelper
import com.ktsal.branchbyabstraction.data.QuotesEntry
import com.ktsal.branchbyabstraction.domain.entity.Quote
import com.squareup.sqlbrite2.BriteDatabase
import com.squareup.sqlbrite2.SqlBrite
import io.reactivex.android.schedulers.AndroidSchedulers


fun createTestDb(databaseVersion: Int): BriteDatabase {
    val sqlBrite = SqlBrite.Builder().build()
    val quotesDbHelper = QuotesDbHelper(InstrumentationRegistry.getTargetContext().applicationContext, databaseVersion)
    return sqlBrite.wrapDatabaseHelper(quotesDbHelper, AndroidSchedulers.mainThread())
}

fun addTestQuote(briteDatabase: BriteDatabase, quote: Quote) {
    val contentValues = ContentValues()
    contentValues.put(QuotesEntry.COLUMN_QUOTE_CONTENT, quote.content)
    contentValues.put(QuotesEntry.COLUMN_QUOTE_SOURCE, quote.source)
    briteDatabase.insert(QuotesEntry.TABLE_QUOTES, contentValues)
}

fun clearTestDb(briteDatabase: BriteDatabase) {
    briteDatabase.delete(QuotesEntry.TABLE_QUOTES, null)
}