package com.ktsal.branchbyabstraction.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class RoomQuotesDao {

    @Query("SELECT * FROM quotes")
    public abstract Single<List<QuoteEntity>> getSavedQuotes();

    @Insert
    public abstract void insertQuote(QuoteEntity quoteEntity);

}
