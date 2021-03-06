package com.ktsal.branchbyabstraction.ui.app;


import android.app.Application;

import com.ktsal.branchbyabstraction.data.QuotesRepository;
import com.ktsal.branchbyabstraction.data.QuotesRepositoryProxy;
import com.ktsal.branchbyabstraction.data.RoomQuotesDataSource;
import com.ktsal.branchbyabstraction.ui.di.Injector;

public class QuotesApplication extends Application implements Injector {

    private QuotesRepository quotesRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        createDataBase();
    }

    private void createDataBase() {
        RoomQuotesDataSource roomQuotesDataSource = new RoomQuotesDataSource(this);
        quotesRepository = new QuotesRepositoryProxy(roomQuotesDataSource);
    }

    @Override
    public QuotesRepository injectQuotesRepository() {
        return quotesRepository;
    }

}
