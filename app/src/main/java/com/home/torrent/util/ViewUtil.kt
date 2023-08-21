package com.home.torrent.util

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.WindowManager
import androidx.fragment.app.DialogFragment

/**
 * @author: read
 * @date: 2023/8/22 上午12:44
 * @description:
 */
fun DialogFragment.fillWidth() {
    dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog?.window?.attributes?.gravity = Gravity.BOTTOM
    dialog?.window?.setLayout(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.WRAP_CONTENT
    )
    dialog?.window?.setWindowAnimations(android.R.style.Animation_InputMethod)
}

fun DialogFragment.fillFullScreen() {
    dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog?.window?.attributes?.gravity = Gravity.TOP
    dialog?.window?.setLayout(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.MATCH_PARENT
    )
    dialog?.window?.setWindowAnimations(android.R.style.Animation_InputMethod)
}