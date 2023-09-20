package com.thewind.utils

import java.text.SimpleDateFormat

/**
 * @author: read
 * @date: 2023/8/20 下午2:38
 * @description:
 */

private val sdf by lazy {
    SimpleDateFormat("yyyy-MM-dd")
}

fun Long?.toDate(): String {
    this ?: return ""
    return sdf.format(this)
}