package com.home.torrent.service

import com.home.torrent.datasource.getSupportTorrentSources
import com.home.torrent.datasource.newTorrentService
import com.home.torrent.model.TorrentSource


fun requestTorrentSources(): List<TorrentSource> = getSupportTorrentSources()

fun searchTorrentList(src: Int, key: String, page: Int = 1) =
    newTorrentService(src).search(key, page)

fun requestMagnetUrl(src: Int, detailUrl: String) = newTorrentService(src).findMagnetUrl(detailUrl)