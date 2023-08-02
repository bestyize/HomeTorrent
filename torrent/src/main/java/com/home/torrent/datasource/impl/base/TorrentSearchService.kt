package com.home.torrent.datasource.impl.base

import com.home.torrent.model.TorrentInfo


interface TorrentSearchService {

    fun search(key: String, page: Int = 1) : List<TorrentInfo>

    fun findMagnetUrl(url: String) : String
}