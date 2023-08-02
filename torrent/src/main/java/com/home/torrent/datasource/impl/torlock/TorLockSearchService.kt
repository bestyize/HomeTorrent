package com.home.torrent.datasource.impl.torlock

import com.home.torrent.datasource.impl.base.TorrentSearchService
import com.home.torrent.model.TorrentInfo

internal class TorLockSearchService : TorrentSearchService {
    override fun search(key: String, page: Int): List<TorrentInfo> {
        return TorLockSearchModel.searchParse(key, page)
    }

    override fun findMagnetUrl(url: String): String {
        return TorLockSearchModel.getMagnet(url)
    }
}