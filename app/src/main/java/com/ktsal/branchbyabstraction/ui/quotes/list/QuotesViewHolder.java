package com.ktsal.branchbyabstraction.ui.quotes.list;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ktsal.branchbyabstraction.R;

class QuotesViewHolder extends RecyclerView.ViewHolder {

    private TextView quoteContentTextView;
    private TextView quoteSourceTextView;

    QuotesViewHolder(ViewGroup root) {
        super(LayoutInflater.from(root.getContext()).inflate(R.layout.viewholder_quote, root, false));
        quoteContentTextView = itemView.findViewById(R.id.quoteContentTextView);
        quoteSourceTextView = itemView.findViewById(R.id.quoteSourceTextView);
    }

    void bind(QuoteItemView quoteItemView) {
        quoteContentTextView.setText(quoteItemView.getContent());
        quoteSourceTextView.setText(quoteItemView.getSource());
    }

}
