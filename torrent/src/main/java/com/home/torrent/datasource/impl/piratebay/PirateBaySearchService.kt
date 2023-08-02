package com.home.torrent.datasource.impl.piratebay

import com.home.torrent.datasource.impl.base.TorrentSearchService
import com.home.torrent.model.TorrentInfo

internal class PirateBaySearchService: TorrentSearchService {
    override fun search(key: String, page: Int): List<TorrentInfo> {
        return PirateBaySearchModel.searchParse(key, page)
    }

    override fun findMagnetUrl(url: String): String {
        return PirateBaySearchModel.getMagnet(url)
    }
}