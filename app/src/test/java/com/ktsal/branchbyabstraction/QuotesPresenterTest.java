package com.ktsal.branchbyabstraction;


import com.ktsal.branchbyabstraction.data.QuotesRepository;
import com.ktsal.branchbyabstraction.domain.entity.Quote;
import com.ktsal.branchbyabstraction.ui.quotes.list.QuoteItemView;
import com.ktsal.branchbyabstraction.ui.quotes.list.QuotesPresenter;
import com.ktsal.branchbyabstraction.ui.quotes.list.QuotesView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuotesPresenterTest {

    private QuotesPresenter quotesPresenter;

    @Mock
    private QuotesView quotesView;

    @Mock
    private QuotesRepository quotesRepository;

    private Scheduler subscriptionScheduler = Schedulers.trampoline();
    private Scheduler observationScheduler = Schedulers.trampoline();

    @Before
    public void setupPresenter() {
        quotesPresenter = new QuotesPresenter(quotesView, quotesRepository, subscriptionScheduler, observationScheduler);
    }

    @Test
    public void givenEmptyQuotes_whenGetQuotes_thenViewShowsEmptyQuotes() {
        List<Quote> emptyQuotes = new ArrayList<>();
        when(quotesRepository.getSavedQuotes()).thenReturn(Observable.just(emptyQuotes));

        quotesPresenter.getQuotes();

        verify(quotesView).showEmptyQuotes();
    }

    @Test
    public void givenNonEmptyQuotes_whenGetQuotes_thenViewShowsQuotes() {
        List<Quote> quotes = new ArrayList<>();
        Quote iAmTheOneWhoKnocks = new Quote("I am the one who knocks", "Walter White");
        quotes.add(iAmTheOneWhoKnocks);
        when(quotesRepository.getSavedQuotes()).thenReturn(Observable.just(quotes));

        quotesPresenter.getQuotes();

        List<QuoteItemView> quoteItemViews = new ArrayList<>();
        QuoteItemView quoteItemView = new QuoteItemView("I am the one who knocks", " – Walter White");
        quoteItemViews.add(quoteItemView);
        verify(quotesView).showQuotes(quoteItemViews);
    }

}
