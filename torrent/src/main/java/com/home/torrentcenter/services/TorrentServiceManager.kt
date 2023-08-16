package com.home.torrentcenter.services

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.home.torrent.model.TorrentInfo
import com.home.torrent.model.TorrentSource
import com.home.torrent.service.requestMagnetUrl
import com.home.torrent.service.requestTorrentSources
import com.home.torrent.service.searchTorrentList
import com.home.torrentcenter.tool.urlEncode
import com.tencent.mmkv.MMKV
import com.thewind.network.HttpUtil.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


private val useLocal by lazy {
    MMKV.defaultMMKV().decodeBool("torrent_api_use_local")
}

private val host: String
    get() {
        return MMKV.defaultMMKV().decodeString("api_host") ?: "https://thewind.xyz"
    }

fun requestTorrentSource(): List<TorrentSource> {

    runCatching {
        if (useLocal) {
            return requestTorrentSources()
        }
        val resp = get("$host/torrent/api/sources")
        val list: List<TorrentSource>? =
            Gson().fromJson(resp, object : TypeToken<List<TorrentSource>>() {}.type)
        return list ?: emptyList()
    }
    return emptyList()

}

suspend fun suspendRequestTorrentSource() = withContext(Dispatchers.IO) {
    requestTorrentSource()
}

fun searchTorrent(src: Int, key: String, page: Int = 1): List<TorrentInfo> {
    runCatching {
        if (useLocal) {
            return searchTorrentList(src, key, page)
        }
        val resp = get("$host/torrent/api/search?src=$src&key=${key.urlEncode}&page=$page")
        val list: List<TorrentInfo>? =
            Gson().fromJson(resp, object : TypeToken<List<TorrentInfo>>() {}.type)
        return list ?: emptyList()
    }

    return emptyList()


}


suspend fun suspendSearchTorrent(src: Int, key: String, page: Int = 1) =
    withContext(Dispatchers.IO) {
        searchTorrent(src, key, page)
    }

fun searchMagnetUrl(src: Int, detailUrl: String): String {
    runCatching {
        if (useLocal) {
            return requestMagnetUrl(src, detailUrl)
        }
        return get("$host/torrent/api/magnet/url?src=$src&detailUrl=${detailUrl.urlEncode}")
    }
    return ""
}

suspend fun suspendSearchMagnetUrl(src: Int, detailUrl: String) = withContext(Dispatchers.IO) {
    searchMagnetUrl(src, detailUrl)
}

fun searchTorrentUrl(src: Int, detailUrl: String): String {
    runCatching {
        if (useLocal) {
            return requestMagnetUrl(src, detailUrl)
        }
        return get("$host/torrent/api/torrent/url?src=$src&detailUrl=${detailUrl.urlEncode}")
    }
    return ""
}

suspend fun suspendSearchTorrentUrl(src: Int, detailUrl: String) = withContext(Dispatchers.IO) {
    searchTorrentUrl(src, detailUrl)
}