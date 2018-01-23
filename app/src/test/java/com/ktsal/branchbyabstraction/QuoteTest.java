package com.ktsal.branchbyabstraction;


import com.ktsal.branchbyabstraction.domain.entity.Quote;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class QuoteTest {

    @Test
    public void givenQuoteHasContentAndSource_thenQuoteIsNotEmpty() {
        Quote quote = new Quote("content", "source");

        assertTrue(quote.isNotEmpty());
    }

    @Test
    public void givenQuoteHasNeitherContentNorSource_thenQuoteIsEmpty() {
        Quote emptyQuote = new Quote("", "");

        assertTrue(emptyQuote.isEmpty());
    }

    @Test
    public void givenQuoteHasContentButNotSource_thenQuoteIsEmpty() {
        Quote quote = new Quote("content", "");

        assertTrue(quote.isEmpty());
    }

    @Test
    public void givenQuoteNoContentButSource_thenQuoteIsEmpty() {
        Quote quote = new Quote("", "source");

        assertTrue(quote.isEmpty());
    }

}
