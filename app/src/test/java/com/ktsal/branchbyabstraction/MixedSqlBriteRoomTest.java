package com.ktsal.branchbyabstraction;


import com.ktsal.branchbyabstraction.data.MixedSqlBriteRoomDataSource;
import com.ktsal.branchbyabstraction.data.QuotesDataSource;
import com.ktsal.branchbyabstraction.domain.entity.Quote;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MixedSqlBriteRoomTest {

    @Mock
    private QuotesDataSource oldImplementation;

    @Mock
    private QuotesDataSource newImplementation;

    private MixedSqlBriteRoomDataSource mixedSqlBriteRoomDataSource;

    @Before
    public void setup() {
        mixedSqlBriteRoomDataSource = new MixedSqlBriteRoomDataSource(oldImplementation, newImplementation);
    }

    @Test
    public void whenGetSavedQuotes_thenNewImplementationCalled() {
        List<Quote> quotes = new ArrayList<>();
        when(newImplementation.getSavedQuotes()).thenReturn(Observable.just(quotes));

        mixedSqlBriteRoomDataSource.getSavedQuotes();

        verify(newImplementation).getSavedQuotes();
        verify(oldImplementation, never()).getSavedQuotes();
    }

}
