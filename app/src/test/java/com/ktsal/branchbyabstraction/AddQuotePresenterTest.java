package com.ktsal.branchbyabstraction;


import com.ktsal.branchbyabstraction.data.QuotesRepository;
import com.ktsal.branchbyabstraction.domain.entity.Quote;
import com.ktsal.branchbyabstraction.ui.quotes.add.AddQuotePresenter;
import com.ktsal.branchbyabstraction.ui.quotes.add.AddQuoteView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddQuotePresenterTest {

    @Mock
    private AddQuoteView addQuoteView;

    @Mock
    private QuotesRepository quotesRepository;

    private AddQuotePresenter addQuotePresenter;

    private Scheduler subscriptionScheduler = Schedulers.trampoline();

    private Scheduler observationScheduler = Schedulers.trampoline();

    @Before
    public void setupPresenter() {
        addQuotePresenter = new AddQuotePresenter(addQuoteView, quotesRepository,
                subscriptionScheduler, observationScheduler);
    }

    @Test
    public void givenQuoteNotEmptyAndAddedInRepo_whenAddQuote_thenViewShowsQuoteAdded() {
        when(quotesRepository.add(new Quote("A brave new world", "Iron Maiden")))
                .thenReturn(Observable.just(true));

        addQuotePresenter.addQuote("A brave new world", "Iron Maiden");

        verify(addQuoteView).success();
    }

    @Test
    public void givenQuoteEmpty_whenAddQuote_thenViewShowsQuoteEmptyError() {

        addQuotePresenter.addQuote("", "");

        verify(addQuoteView).showEmptyQuoteError();
    }

}
