package com.home.torrent.torrent.service

import com.home.torrent.service.requestMagnetUrl
import com.home.torrent.service.requestTorrentSources
import com.home.torrent.service.searchTorrentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


internal suspend fun suspendLoadTorrentSources() = withContext(Dispatchers.IO) {
    requestTorrentSources()
}

internal suspend fun suspendLoadTorrentList(src: Int, keyword: String, page: Int) = withContext(Dispatchers.IO) {
    searchTorrentList(src, keyword, page)
}

internal suspend fun suspendLoadMagnetUrl(src: Int, detailUrl: String) = withContext(Dispatchers.IO) {
    requestMagnetUrl(src, detailUrl)
}