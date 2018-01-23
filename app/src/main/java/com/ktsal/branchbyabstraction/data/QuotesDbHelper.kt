package com.ktsal.branchbyabstraction.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns


object QuotesEntry : BaseColumns {
    const val TABLE_QUOTES = "quotes"
    const val COLUMN_QUOTE_CONTENT = "content"
    const val COLUMN_QUOTE_SOURCE = "source"
}

private const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${QuotesEntry.TABLE_QUOTES} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${QuotesEntry.COLUMN_QUOTE_CONTENT} TEXT, " +
                "${QuotesEntry.COLUMN_QUOTE_SOURCE} TEXT)"

const val DATABASE_NAME = "Quotes.db"

const val DATABASE_VERSION = 2

class QuotesDbHelper(context: Context, databaseVersion: Int): SQLiteOpenHelper(context, DATABASE_NAME, null, databaseVersion) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}


