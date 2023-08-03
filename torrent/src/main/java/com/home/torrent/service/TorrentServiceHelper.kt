package com.home.torrent.service

import com.home.torrent.datasource.getSupportTorrentSources
import com.home.torrent.datasource.newTorrentService
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