package com.home.torrent.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

object WLog {
    @SuppressLint("SimpleDateFormat")
    private val sDataFormatter = SimpleDateFormat("yyyy-MM-dd hh:mm:ss:sss")
    fun i(tag: String, info: Any) {
        val msg =
            sDataFormatter.format(Date()) + " [WindMusic] [" + Thread.currentThread() + "] [I] [" + tag + "] " + info
        println(msg)
    }

    fun e(tag: String, info: Any) {
        val msg =
            sDataFormatter.format(Date()) + " [WindMusic] [" + Thread.currentThread() + "] [E] [" + tag + "] " + info
        println(msg)
    }

    fun d(tag: String, info: Any) {
        val msg =
            sDataFormatter.format(Date()) + " [WindMusic] [" + Thread.currentThread() + "] [D] [" + tag + "] " + info
        println(msg)
    }

    fun v(tag: String, info: Any) {
        val msg =
            sDataFormatter.format(Date()) + " [WindMusic] [" + Thread.currentThread() + "] [V] [" + tag + "] " + info
        println(msg)
    }
}