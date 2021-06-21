package com.ktsal.branchbyabstraction.ui.quotes.add

import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.widget.EditText
import android.widget.Toast
import com.ktsal.branchbyabstraction.R
import com.ktsal.branchbyabstraction.ui.di.Injector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class AddQuoteFragment : DialogFragment(), AddQuoteView {

    private val quotesRepository by lazy { (activity.application as Injector).injectQuotesRepository() }
    private val addQuotePresenter by lazy { AddQuotePresenter(this, quotesRepository, Schedulers.io(), AndroidSchedulers.mainThread()) }
    private val contentEditText by lazy { dialog.findViewById<EditText>(R.id.contentEditText) }
    private val sourceEditText by lazy { dialog.findViewById<EditText>(R.id.sourceEditText) }
    private lateinit var onQuoteAddedListener: OnQuoteAddedListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnQuoteAddedListener) {
            onQuoteAddedListener = context
        } else {
            throw IllegalArgumentException("${context.toString()} must implement OnQuoteAddedListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(activity)
                .setTitle(getString(R.string.add_quote_dialog_title))
                .setPositiveButton(R.string.add_quote_dialog_positive) { _, _ -> }
                .setNegativeButton(R.string.add_quote_dialog_negative) { _, _ -> }
                .setView(R.layout.dialog_add_quote)
                .create()
        dialog.setOnShowListener { dialogInterface ->
            val positiveButton = (dialogInterface as AlertDialog).getButton(DialogInterface.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                val content = contentEditText?.text.toString()
                val source = sourceEditText?.text.toString()
                addQuotePresenter.addQuote(content, source)
            }
            val negativeButton = dialogInterface.getButton(DialogInterface.BUTTON_NEGATIVE)
            negativeButton.setOnClickListener { dismiss() }
        }
        return dialog
    }

    override fun success() {
        Toast.makeText(activity, R.string.add_quote_success_message, Toast.LENGTH_SHORT).show()
        onQuoteAddedListener.onQuoteAdded()
        dismiss()
    }

    override fun showEmptyQuoteError() {
        Toast.makeText(activity, R.string.add_quote_empty_error_message, Toast.LENGTH_SHORT).show()
    }

    interface OnQuoteAddedListener {

        fun onQuoteAdded()

    }

}