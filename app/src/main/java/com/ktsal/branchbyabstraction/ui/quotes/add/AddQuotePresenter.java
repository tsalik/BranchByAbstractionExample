package com.ktsal.branchbyabstraction.ui.quotes.add;


import com.ktsal.branchbyabstraction.data.QuotesRepository;
import com.ktsal.branchbyabstraction.domain.entity.Quote;

import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;

public class AddQuotePresenter {

    private final AddQuoteView addQuoteView;
    private final QuotesRepository quotesRepository;
    private final Scheduler subscribeOnScheduler;
    private final Scheduler observeOnScheduler;

    public AddQuotePresenter(AddQuoteView addQuoteView, QuotesRepository quotesRepository,
                             Scheduler subscribeOnScheduler, Scheduler observeOnScheduler) {
        this.addQuoteView = addQuoteView;
        this.quotesRepository = quotesRepository;
        this.subscribeOnScheduler = subscribeOnScheduler;
        this.observeOnScheduler = observeOnScheduler;
    }

    public void addQuote(String content, String source) {
        Quote quote = new Quote(content, source);
        if (quote.getNotEmpty()) {
            quotesRepository.add(quote)
                    .subscribeOn(subscribeOnScheduler)
                    .observeOn(observeOnScheduler)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean success) throws Exception {
                            if (success) {
                                addQuoteView.success();
                            }
                        }
                    });
        } else {
            addQuoteView.showEmptyQuoteError();
        }
    }
}
