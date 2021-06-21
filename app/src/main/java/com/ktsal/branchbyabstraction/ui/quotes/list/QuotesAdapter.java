package com.ktsal.branchbyabstraction.ui.quotes.list;


import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesViewHolder> {

    private List<QuoteItemView> quoteItemViews;

    @Override
    public QuotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new QuotesViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(QuotesViewHolder holder, int position) {
        holder.bind(getItemAt(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return quoteItemViews == null ? 0 : quoteItemViews.size();
    }

    public void updateItems(List<QuoteItemView> quoteItemViews) {
        this.quoteItemViews = quoteItemViews;
        notifyDataSetChanged();
    }

    private QuoteItemView getItemAt(int position) {
        return quoteItemViews.get(position);
    }

}
