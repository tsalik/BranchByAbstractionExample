package com.ktsal.branchbyabstraction.ui.quotes.list

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.ktsal.branchbyabstraction.R
import com.ktsal.branchbyabstraction.ui.app.QuotesApplication
import com.ktsal.branchbyabstraction.ui.quotes.add.AddQuoteFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class QuotesListActivity : AppCompatActivity(), QuotesView, AddQuoteFragment.OnQuoteAddedListener {

    private val quotesRepository by lazy { (applicationContext as QuotesApplication).injectQuotesRepository() }
    private val quotesPresenter by lazy { QuotesPresenter(this, quotesRepository, Schedulers.io(), AndroidSchedulers.mainThread()) }
    private val quotesAdapter = QuotesAdapter()
    private val quotesRecyclerView: RecyclerView by lazy { findViewById(R.id.quotesRecyclerView) }
    private val addQuoteFab: FloatingActionButton by lazy { findViewById(R.id.addQuoteFab) }
    private val noQuotesMessage: TextView by lazy { findViewById(R.id.noQuotesMessage) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        quotesRecyclerView.adapter = quotesAdapter
        quotesRecyclerView.layoutManager =
            LinearLayoutManager(this)
        addQuoteFab.setOnClickListener { quotesPresenter.addQuote() }
        quotesPresenter.getQuotes()
    }

    override fun showEmptyQuotes() {
        noQuotesMessage.visibility = View.VISIBLE
    }

    override fun showQuotes(quoteItemViews: MutableList<QuoteItemView>?) {
        noQuotesMessage.visibility = View.GONE
        quotesAdapter.updateItems(quoteItemViews)
    }

    override fun showAddQuote() {
        val addQuoteFragment = AddQuoteFragment()
        addQuoteFragment.show(fragmentManager, "addQuoteFragment")
    }

    override fun onQuoteAdded() {
        quotesPresenter.getQuotes()
    }

}
