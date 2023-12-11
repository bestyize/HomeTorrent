package com.home.torrent.search.model

import com.home.torrent.model.TorrentInfo

internal data class TorrentSearchTabState(
    val src: Int,
    val keyword: String = "",
    val page: Int = 1,
    val dataList: List<TorrentInfo> = emptyList(),
    val loaded: Boolean = false
)