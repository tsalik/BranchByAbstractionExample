package com.ktsal.branchbyabstraction.ui.quotes.list

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.ktsal.branchbyabstraction.R
import com.ktsal.branchbyabstraction.ui.app.QuotesApplication
import com.ktsal.branchbyabstraction.ui.quotes.add.AddQuoteFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class QuotesListActivity : AppCompatActivity(), QuotesView {

    private val quotesRepository by lazy { (applicationContext as QuotesApplication).injectQuotesRepository() }
    private val quotesPresenter by lazy { QuotesPresenter(this, quotesRepository, Schedulers.io(), AndroidSchedulers.mainThread()) }
    private val quotesAdapter = QuotesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        quotesRecyclerView.adapter = quotesAdapter
        quotesRecyclerView.layoutManager = LinearLayoutManager(this)
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

}
