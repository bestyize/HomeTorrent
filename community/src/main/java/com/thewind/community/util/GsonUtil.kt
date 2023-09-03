package com.thewind.community.util

import com.google.gson.GsonBuilder
import java.lang.reflect.Type
import java.net.URLEncoder

/**
 * @author: read
 * @date: 2023/8/20 上午2:17
 * @description:
 */

private val gson by lazy {
    GsonBuilder().disableHtmlEscaping().create()
}


internal fun Any?.toJson(): String {
    this ?: return "null"
    return gson.toJson(this)
}

internal fun <T> String?.toObject(clazz: Class<T>): T? {
    this ?: return null
    if (this.isBlank()) return null
    runCatching {
        return gson.fromJson(this, clazz)
    }
    return null
}

internal fun <T> String?.toArray(type: Type): T? {
    this ?: return null
    runCatching {
        return gson.fromJson(this, type)
    }
    return null
}


val String.urlEncode: String
    get() = URLEncoder.encode(this, "UTF-8")

