package com.home.torrent.datasource.impl.solidtorrent

import com.home.torrent.datasource.impl.base.TorrentSearchService
import com.home.torrent.model.TorrentInfo

internal class SolidTorrentSearchService : TorrentSearchService {
    override fun search(key: String, page: Int): List<TorrentInfo> {
        return SolidTorrentSearchModel.searchParse(key, page)
    }

    override fun findMagnetUrl(url: String): String {
        return SolidTorrentSearchModel.getMagnet(url)
    }
}