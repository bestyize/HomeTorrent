package com.home.baseapp.app.toast

import android.widget.Toast
import com.home.baseapp.app.HomeApp


fun toast(msg: String?) {
    Toast.makeText(HomeApp.context, msg, Toast.LENGTH_SHORT).show()
}