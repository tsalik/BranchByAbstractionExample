package com.ktsal.branchbyabstraction.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class RoomQuotesDao {

    @Query("SELECT * FROM quotes")
    public abstract Single<List<QuoteEntity>> getSavedQuotes();

}
