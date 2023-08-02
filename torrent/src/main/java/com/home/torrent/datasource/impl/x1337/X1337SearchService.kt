package com.home.torrent.datasource.impl.x1337

import com.home.torrent.datasource.impl.base.TorrentSearchService
import com.home.torrent.model.TorrentInfo

internal class X1337SearchService : TorrentSearchService {
    override fun search(key: String, page: Int): List<TorrentInfo> {
        return X1337SearchModel.searchParse(key, page)
    }

    override fun findMagnetUrl(url: String): String {
        return X1337SearchModel.getMagnet(url)
    }
}
