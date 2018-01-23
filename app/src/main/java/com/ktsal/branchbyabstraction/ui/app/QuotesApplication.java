package com.ktsal.branchbyabstraction.ui.app;


import android.app.Application;

import com.ktsal.branchbyabstraction.data.MixedSqlBriteRoomDataSource;
import com.ktsal.branchbyabstraction.data.QuotesDataSource;
import com.ktsal.branchbyabstraction.data.QuotesDbHelper;
import com.ktsal.branchbyabstraction.data.QuotesDbHelperKt;
import com.ktsal.branchbyabstraction.data.QuotesRepository;
import com.ktsal.branchbyabstraction.data.QuotesRepositoryProxy;
import com.ktsal.branchbyabstraction.data.RoomQuotesDataSource;
import com.ktsal.branchbyabstraction.data.SqlBriteQuotesDataSource;
import com.ktsal.branchbyabstraction.ui.di.Injector;
import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;

import io.reactivex.schedulers.Schedulers;

public class QuotesApplication extends Application implements Injector {

    private QuotesRepository quotesRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        createDataBase();
    }

    private void createDataBase() {
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        QuotesDbHelper quotesDbHelper = new QuotesDbHelper(this, QuotesDbHelperKt.DATABASE_VERSION);
        BriteDatabase briteDatabase = sqlBrite.wrapDatabaseHelper(quotesDbHelper, Schedulers.io());
        QuotesDataSource sqlBriteLocalDataSource = new SqlBriteQuotesDataSource(briteDatabase);
        RoomQuotesDataSource roomQuotesDataSource = new RoomQuotesDataSource(this);
        MixedSqlBriteRoomDataSource mixedSqlBriteRoomDataSource = new MixedSqlBriteRoomDataSource(sqlBriteLocalDataSource, roomQuotesDataSource);
        quotesRepository = new QuotesRepositoryProxy(mixedSqlBriteRoomDataSource);
    }

    @Override
    public QuotesRepository injectQuotesRepository() {
        return quotesRepository;
    }

}
