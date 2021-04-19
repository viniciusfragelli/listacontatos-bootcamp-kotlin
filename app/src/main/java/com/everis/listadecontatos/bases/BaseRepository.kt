package com.everis.listadecontatos.bases

import android.database.sqlite.SQLiteDatabase
import com.everis.listadecontatos.helpers.HelperDB

abstract class BaseRepository(
    var helperDB: HelperDB? = null
) {

    var readableDatabase: SQLiteDatabase? = null
        private set
        get() {
            return helperDB?.readableDatabase
        }

    var writableDatabase: SQLiteDatabase? = null
        private set
        get() {
            return helperDB?.writableDatabase
        }
}
