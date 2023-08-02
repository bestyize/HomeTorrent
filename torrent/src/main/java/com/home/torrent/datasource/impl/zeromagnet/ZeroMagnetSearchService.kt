package com.home.torrent.datasource.impl.zeromagnet

import com.home.torrent.datasource.impl.base.TorrentSearchService
import com.home.torrent.model.TorrentInfo

private const val TAG = "ZeroMagnetSearchService"

internal class ZeroMagnetSearchService : TorrentSearchService {
    override fun search(key: String, page: Int): List<TorrentInfo> {
        return ZeroMagnetSearchModel.searchParse(key, page)
    }

    override fun findMagnetUrl(url: String): String {
        return ZeroMagnetSearchModel.getMagnet(url)
    }
}