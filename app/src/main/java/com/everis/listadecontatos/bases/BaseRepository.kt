package com.everis.listadecontatos.bases

import android.database.sqlite.SQLiteDatabase
import com.everis.listadecontatos.helpers.HelperDB

open class BaseRepository(
    val helperDB: HelperDB? = null
) {
    val readableDatabase: SQLiteDatabase?
        get() {
            return helperDB?.readableDatabase
        }

    val writableDatabase: SQLiteDatabase?
        get() {
            return helperDB?.writableDatabase
        }

}