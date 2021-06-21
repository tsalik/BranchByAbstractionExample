package com.ktsal.branchbyabstraction.data;


import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.annotation.NonNull;


@Database(entities = {QuoteEntity.class}, version = QuotesDbHelperKt.DATABASE_VERSION)
public abstract class RoomQuotesDatabase extends RoomDatabase {

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    public abstract RoomQuotesDao quotesDao();

}
