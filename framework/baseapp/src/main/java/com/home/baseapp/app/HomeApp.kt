package com.home.baseapp.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class HomeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        _context = this
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var _context: Context = Application()

        val context: Context
            get() = _context
    }
}