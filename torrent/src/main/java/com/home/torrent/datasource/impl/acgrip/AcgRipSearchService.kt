package com.home.torrent.datasource.impl.acgrip

import com.home.torrent.datasource.impl.base.TorrentSearchService
import com.home.torrent.model.TorrentInfo

internal class AcgRipSearchService : TorrentSearchService {
    override fun search(key: String, page: Int): List<TorrentInfo> {
        return AcgRipSearchModel.searchParse(key, page)
    }

    override fun findMagnetUrl(url: String): String {
        return AcgRipSearchModel.getMagnet(url)
    }
}