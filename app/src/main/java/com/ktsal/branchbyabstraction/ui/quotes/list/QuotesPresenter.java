package com.ktsal.branchbyabstraction.ui.quotes.list;


import com.ktsal.branchbyabstraction.data.QuotesRepository;
import com.ktsal.branchbyabstraction.domain.entity.Quote;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class QuotesPresenter {

    private final QuotesView quotesView;
    private final QuotesRepository quotesRepository;
    private final Scheduler subscriptionScheduler;
    private final Scheduler observationScheduler;

    public QuotesPresenter(QuotesView quotesView,
                           QuotesRepository quotesRepository,
                           Scheduler subscriptionScheduler,
                           Scheduler observationScheduler) {
        this.quotesView = quotesView;
        this.quotesRepository = quotesRepository;
        this.subscriptionScheduler = subscriptionScheduler;
        this.observationScheduler = observationScheduler;
    }

    public void getQuotes() {
        quotesRepository.getSavedQuotes()
                .subscribeOn(subscriptionScheduler)
                .observeOn(observationScheduler)
                .map(new Function<List<Quote>, List<QuoteItemView>>() {
                    @Override
                    public List<QuoteItemView> apply(List<Quote> quotes) throws Exception {
                        List<QuoteItemView> quoteItemViews = new ArrayList<>();
                        for (Quote quote : quotes) {
                            String source = " – " + quote.getSource();
                            quoteItemViews.add(new QuoteItemView(quote.getContent(), source));
                        }
                        return quoteItemViews;
                    }
                })
                .subscribe(new Consumer<List<QuoteItemView>>() {
                    @Override
                    public void accept(List<QuoteItemView> quoteItemViews) throws Exception {
                        if (quoteItemViews.isEmpty()) {
                            quotesView.showEmptyQuotes();
                        } else {
                            quotesView.showQuotes(quoteItemViews);
                        }
                    }
                });
    }

    public void addQuote() {
        quotesView.showAddQuote();
    }

}
