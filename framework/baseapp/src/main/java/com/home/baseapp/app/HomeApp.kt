package com.home.baseapp.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class HomeApp : Application() {
    override fun onCreate() {
        _context = this
        super.onCreate()
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var _context: Context? = null

        val context: Context
            get() = _context!!
    }
}