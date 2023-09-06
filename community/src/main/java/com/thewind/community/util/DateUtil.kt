package com.thewind.community.util

import java.text.SimpleDateFormat

/**
 * @author: read
 * @date: 2023/8/28 下午11:51
 * @description:
 */
private val sdf by lazy {
    SimpleDateFormat("yyyy-MM-dd HH:mm")
}

internal fun Long?.toDate(): String {
    this ?: return ""
    return sdf.format(this)
}