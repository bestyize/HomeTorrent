package com.home.baseapp.app.toast

import android.widget.Toast
import com.home.baseapp.app.HomeApp


fun toast(msg: String?) {
    if (msg.isNullOrBlank()) return
    Toast.makeText(HomeApp.context, msg, Toast.LENGTH_SHORT).show()
}