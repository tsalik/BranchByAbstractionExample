package com.ktsal.branchbyabstraction.data;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;


@Database(entities = {QuoteEntity.class}, version = QuotesDbHelperKt.DATABASE_VERSION)
public abstract class RoomQuotesDatabase extends RoomDatabase {

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    public abstract RoomQuotesDao quotesDao();

}
