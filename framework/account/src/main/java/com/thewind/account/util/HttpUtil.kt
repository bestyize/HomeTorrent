package com.thewind.account.util

import android.util.Log
import com.home.baseapp.app.HomeApp
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * @author: read
 * @date: 2023/8/21 上午12:00
 * @description:
 */
internal object HttpUtil {
    fun get(link: String?, headerMap: MutableMap<String, String> = mutableMapOf()): String {
        if (link == null || !link.startsWith("http")) {
            return ""
        }
        val sb = StringBuilder()
        try {
            val url = URL(link)
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.readTimeout = 5000
            conn.connectTimeout = 5000
            headerMap.putAll(commonHeaders)
            for (key in headerMap.keys) {
                conn.addRequestProperty(key, headerMap[key])
            }
            val reader = BufferedReader(InputStreamReader(conn.inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                sb.append(line)
            }
            reader.close()
            conn.disconnect()
        } catch (e: Exception) {
            Log.e(TAG, "get, error , url = $link, errMsg = $e")
        }
        return sb.toString()
    }
}

private const val TAG = "HttpUtil"

private val commonHeaders by lazy {
    mapOf(
        "package" to HomeApp.context.applicationInfo.packageName,
        "version" to HomeApp.versionCode.toString(),
        "debug" to HomeApp.isDebug.toString()
    )
}