package com.thewind.network

import android.util.Log
import com.home.baseapp.app.HomeApp
import com.thewind.account.AccountManager
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.HttpURLConnection
import java.net.Proxy
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.Locale


/**
 * @author: read
 * @date: 2023/8/16 下午11:59
 * @description:
 */


object HttpUtil {
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
            headerMap.putAll(getAccountHeader())
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

    /**
     * 做POST请求
     *
     * @param link      请求地址
     * @param params    请求体，类似于keyword=十年&num=100这样的格式
     * @param headerMap 请求头
     * @return 请求结果
     */
    fun post(
        link: String?, params: String?, headerMap: MutableMap<String, String> = mutableMapOf()
    ): String? {
        if (link == null || !link.startsWith("http")) {
            return ""
        }
        var response: String? = null
        try {
            val url = URL(link)
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.doInput = true
            conn.doOutput = true
            conn.connectTimeout = 10000
            conn.readTimeout = 10000
            headerMap.putAll(commonHeaders)
            headerMap.putAll(getAccountHeader())
            for (key in headerMap.keys) {
                conn.setRequestProperty(key, headerMap[key])
            }
            val writer = PrintWriter(conn.outputStream)
            writer.print(params)
            writer.flush()
            val reader = BufferedReader(InputStreamReader(conn.inputStream, StandardCharsets.UTF_8))
            var line: String?
            val sb = StringBuilder()
            while (reader.readLine().also { line = it } != null) {
                sb.append(line)
            }
            writer.close()
            reader.close()
            conn.disconnect()
            response = sb.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    fun getHeader(link: String?): Map<String, List<String>> {
        val headerMap: Map<String, List<String>> = HashMap()
        if (link == null || !link.startsWith("http")) {
            return headerMap
        }
        try {
            val url = URL(link)
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "GET"
            conn.readTimeout = 10000
            conn.connectTimeout = 10000
            val headerField = conn.headerFields
            conn.disconnect()
            return headerField
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return headerMap
    }

    fun get(link: String?, headerMap: MutableMap<String, String>, proxy: Proxy?): String {
        if (link == null || !link.startsWith("http")) {
            return ""
        }
        val sb = StringBuilder()
        try {
            val url = URL(link)
            val conn = url.openConnection(proxy) as HttpURLConnection
            conn.requestMethod = "GET"
            conn.readTimeout = 3000
            conn.connectTimeout = 3000
            headerMap.putAll(commonHeaders)
            headerMap.putAll(getAccountHeader())
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
            Log.e(TAG, "get, error = $e")
        }
        return sb.toString()
    }

    fun buildGetUrl(baseUrl: String, map: Map<String?, Any?>?): String {
        return baseUrl + buildGetBody(map)
    }

    fun buildGetBody(map: Map<String?, Any?>?): String {
        if (map == null) {
            return ""
        }
        val sb = StringBuilder()
        sb.append("?")
        val keySet = map.keys
        for (str in keySet) {
            sb.append(str)
            sb.append("=")
            sb.append(map[str])
            sb.append("&")
        }
        sb.deleteCharAt(sb.length - 1)
        return sb.toString()
    }
}


private const val TAG = "HttpUtil"

private val commonHeaders by lazy {
    mapOf(
        "package" to HomeApp.context.applicationInfo.packageName,
        "version" to HomeApp.versionCode.toString(),
        "debug" to HomeApp.isDebug.toString(),
        "lng" to Locale.getDefault().language
    )
}

private fun getAccountHeader(): Map<String, String> {
    AccountManager.getUser()?.let {
        return mapOf(
            "uid" to it.uid.toString(), "password" to it.password
        )
    }
    return mapOf()
}

