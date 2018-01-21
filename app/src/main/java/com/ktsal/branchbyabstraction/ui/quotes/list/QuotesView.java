package com.ktsal.branchbyabstraction.ui.quotes.list;


import java.util.List;

public interface QuotesView {

    void showEmptyQuotes();

    void showQuotes(List<QuoteItemView> quoteItemViews);

    void showAddQuote();

}
