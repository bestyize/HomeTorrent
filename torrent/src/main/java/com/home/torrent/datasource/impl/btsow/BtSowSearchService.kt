package com.home.torrent.datasource.impl.btsow

import com.home.torrent.datasource.impl.base.TorrentSearchService
import com.home.torrent.model.TorrentInfo

internal class BtSowSearchService : TorrentSearchService {
    override fun search(key: String, page: Int): List<TorrentInfo> {
        return BtSowSearchModel.searchParse(key, page)
    }

    override fun findMagnetUrl(url: String): String {
        return BtSowSearchModel.getMagnet(url)
    }
}