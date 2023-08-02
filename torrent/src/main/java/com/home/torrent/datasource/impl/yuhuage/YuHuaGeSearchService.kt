package com.home.torrent.datasource.impl.yuhuage

import com.home.torrent.datasource.impl.base.TorrentSearchService
import com.home.torrent.model.TorrentInfo


internal class YuHuaGeSearchService : TorrentSearchService {
    override fun search(key: String, page: Int): List<TorrentInfo> {
        return YuHuaGeSearchModel.searchParse(key, page)
    }

    override fun findMagnetUrl(url: String): String {
        return YuHuaGeSearchModel.getMagnet(url)
    }
}