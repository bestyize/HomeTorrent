package com.home.torrent.service

import com.home.torrent.datasource.getSupportTorrentSources
import com.home.torrent.datasource.newTorrentService
import com.home.torrent.def.magnetUrlToHash
import com.home.torrent.def.magnetUrlToTorrentUrl
import com.home.torrent.model.TorrentSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


fun requestTorrentSources(): List<TorrentSource> = getSupportTorrentSources()

suspend fun suspendRequestTorrentSources() = withContext(Dispatchers.IO) {
    getSupportTorrentSources()
}

fun searchTorrentList(src: Int, key: String, page: Int = 1) =
    newTorrentService(src).search(key, page)

suspend fun suspendSearchTorrentList(src: Int, key: String, page: Int = 1) =
    withContext(Dispatchers.IO) {
        searchTorrentList(src, key, page)
    }

fun requestMagnetUrl(src: Int, detailUrl: String) = newTorrentService(src).findMagnetUrl(detailUrl)

suspend fun suspendRequestMagnetUrl(src: Int, detailUrl: String) = withContext(Dispatchers.IO) {
    newTorrentService(src).findMagnetUrl(detailUrl)
}

fun requestTorrentUrl(src: Int, detailUrl: String) =
    newTorrentService(src).findTorrentUrl(detailUrl)

suspend fun suspendRequestTorrentUrl(src: Int, detailUrl: String) = withContext(Dispatchers.IO) {
    newTorrentService(src).findTorrentUrl(detailUrl)
}

fun transferMagnetUrlToTorrentUrl(magnetUrl: String): String {
    if (magnetUrl.isEmpty()) return ""
    if (magnetUrl.endsWith(".torrent")) return magnetUrl
    return magnetUrl.magnetUrlToTorrentUrl
}

fun transferMagnetUrlToHash(magnetUrl: String) = magnetUrl.magnetUrlToHash