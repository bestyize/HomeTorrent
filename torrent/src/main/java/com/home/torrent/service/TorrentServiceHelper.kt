package com.home.torrent.service

import com.home.torrent.model.TorrentSource
import com.home.torrent.model.TorrentSrc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


private fun requestTorrentSourceSync(): List<TorrentSource> {
    return listOf(
        TorrentSource(src = TorrentSrc.SOLID_TORRENT.value, title = "Solid Torrent"),
        TorrentSource(src = TorrentSrc.SOLID_TORRENT.value, title = "1337"),
        TorrentSource(src = TorrentSrc.SOLID_TORRENT.value, title = "Acg Fun")
    )
}

suspend fun requestTorrentSourceList(): List<TorrentSource> = withContext(Dispatchers.IO) {
    requestTorrentSourceSync()
}