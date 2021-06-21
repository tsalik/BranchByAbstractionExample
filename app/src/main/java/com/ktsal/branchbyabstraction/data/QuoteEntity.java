package com.ktsal.branchbyabstraction.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import android.provider.BaseColumns;

import io.reactivex.annotations.NonNull;

@Entity(tableName = QuotesEntry.TABLE_QUOTES)
public class QuoteEntity {

    @PrimaryKey
    @ColumnInfo(name = BaseColumns._ID)
    @NonNull
    public Long id;

    @ColumnInfo(name = QuotesEntry.COLUMN_QUOTE_CONTENT)
    public String quoteContent;

    @ColumnInfo(name = QuotesEntry.COLUMN_QUOTE_SOURCE)
    public String quoteSource;

}
