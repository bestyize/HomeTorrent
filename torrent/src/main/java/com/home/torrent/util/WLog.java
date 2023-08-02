package com.home.torrent.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WLog {
    @SuppressLint("SimpleDateFormat")
    private final static SimpleDateFormat sDataFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:sss");

    public static void i(String tag, Object info) {
        String msg = sDataFormatter.format(new Date()) + " [WindMusic] [" + Thread.currentThread() + "] [I] [" + tag + "] " + info;
        System.out.println(msg);
    }

    public static void e(String tag, Object info) {
        String msg = sDataFormatter.format(new Date()) + " [WindMusic] [" + Thread.currentThread() + "] [E] [" + tag + "] " + info;
        System.out.println(msg);
    }

    public static void d(String tag, Object info) {
        String msg = sDataFormatter.format(new Date()) + " [WindMusic] [" + Thread.currentThread() + "] [D] [" + tag + "] " + info;
        System.out.println(msg);
    }

    public static void v(String tag, Object info) {
        String msg = sDataFormatter.format(new Date()) + " [WindMusic] [" + Thread.currentThread() + "] [V] [" + tag + "] " + info;
        System.out.println(msg);
    }
}
