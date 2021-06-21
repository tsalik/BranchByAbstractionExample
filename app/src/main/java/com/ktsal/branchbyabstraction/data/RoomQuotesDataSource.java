package com.ktsal.branchbyabstraction.data;


import androidx.room.Room;
import android.content.Context;

import com.ktsal.branchbyabstraction.domain.entity.Quote;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class RoomQuotesDataSource implements QuotesDataSource {

    private final RoomQuotesDatabase roomQuotesDatabase;

    public RoomQuotesDataSource(Context context) {
        Context appContext = context.getApplicationContext();
        roomQuotesDatabase = Room.databaseBuilder(appContext, RoomQuotesDatabase.class, QuotesDbHelperKt.DATABASE_NAME)
                .addMigrations(RoomQuotesDatabase.MIGRATION_1_2)
                .build();
    }

    @Override
    public Observable<List<Quote>> getSavedQuotes() {
        return roomQuotesDatabase.quotesDao()
                .getSavedQuotes()
                .flatMapObservable(new Function<List<QuoteEntity>, ObservableSource<? extends List<Quote>>>() {
                    @Override
                    public ObservableSource<? extends List<Quote>> apply(List<QuoteEntity> quoteEntities) throws Exception {
                        return Observable.fromIterable(quoteEntities)
                                .map(new Function<QuoteEntity, Quote>() {
                                    @Override
                                    public Quote apply(QuoteEntity quoteEntity) throws Exception {
                                        return new Quote(quoteEntity.quoteContent, quoteEntity.quoteSource);
                                    }
                                })
                                .toList()
                                .toObservable();
                    }
                });
    }

    @Override
    public Observable<Boolean> add(Quote quote) {
        final QuoteEntity quoteEntity = new QuoteEntity();
        quoteEntity.quoteContent = quote.getContent();
        quoteEntity.quoteSource = quote.getSource();
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                roomQuotesDatabase.quotesDao().insertQuote(quoteEntity);
                return true;
            }
        });
    }

}
