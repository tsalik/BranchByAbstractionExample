package com.ktsal.branchbyabstraction.data;


import android.content.ContentValues;
import android.database.Cursor;

import com.ktsal.branchbyabstraction.domain.entity.Quote;
import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.QueryObservable;
import com.squareup.sqlbrite2.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class SqlBriteQuotesDataSource implements QuotesDataSource {

    private final BriteDatabase briteDatabase;

    public SqlBriteQuotesDataSource(BriteDatabase briteDatabase) {
        this.briteDatabase = briteDatabase;
    }

    @Override
    public Observable<List<Quote>> getSavedQuotes() {
        QueryObservable queryObservable = briteDatabase.createQuery(QuotesEntry.TABLE_QUOTES, "SELECT * from quotes");
        return queryObservable
                .map(new Function<SqlBrite.Query, List<Quote>>() {
                    @Override
                    public List<Quote> apply(SqlBrite.Query query) throws Exception {
                        Cursor cursor = query.run();
                        List<Quote> quotes = new ArrayList<>();
                        while (cursor.moveToNext()) {
                            String content = cursor.getString(cursor.getColumnIndex(QuotesEntry.COLUMN_QUOTE_CONTENT));
                            String source = cursor.getString(cursor.getColumnIndex(QuotesEntry.COLUMN_QUOTE_SOURCE));
                            Quote quote = new Quote(content, source);
                            quotes.add(quote);
                        }
                        return quotes;
                    }
                });
    }

    @Override
    public Observable<Boolean> add(Quote quote) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuotesEntry.COLUMN_QUOTE_CONTENT, quote.getContent());
        contentValues.put(QuotesEntry.COLUMN_QUOTE_SOURCE, quote.getSource());
        long rowId = briteDatabase.insert(QuotesEntry.TABLE_QUOTES, contentValues);
        return Observable.just(rowId != -1);
    }

}
