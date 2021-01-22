package com.everis.listadecontatos.application

import android.app.Application
import com.everis.listadecontatos.helpers.HelperDB

class ContatoApplication : Application() {

    var helperDB: HelperDB? = null
        private set

    override fun onCreate() {
        super.onCreate()
        helperDB = HelperDB(this)
    }
}