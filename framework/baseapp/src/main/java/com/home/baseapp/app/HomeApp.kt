package com.home.baseapp.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Build
import com.tencent.mmkv.MMKV

class HomeApp : Application() {
    override fun onCreate() {
        _context = this
        super.onCreate()
        init()
    }


    private fun init() {
        MMKV.initialize(this)
    }


    companion object {

        @SuppressLint("StaticFieldLeak")
        private var _context: Context? = null

        val context: Context
            get() = _context!!

        val versionCode by lazy {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                context.applicationContext?.packageManager?.getPackageInfo(
                    context.packageName,
                    0
                )?.longVersionCode ?: 100
            } else {
                context.applicationContext?.packageManager?.getPackageInfo(
                    context.packageName,
                    0
                )?.versionCode ?: 100
            }
        }

        val isDebug by lazy { context.applicationInfo.flags.and(ApplicationInfo.FLAG_DEBUGGABLE) != 0 }
    }
}