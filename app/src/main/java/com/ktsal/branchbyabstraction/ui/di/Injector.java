package com.ktsal.branchbyabstraction.ui.di;


import com.ktsal.branchbyabstraction.data.QuotesRepository;

public interface Injector {

    QuotesRepository injectQuotesRepository();

}
