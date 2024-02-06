package com.home.torrent.search.model

import androidx.compose.runtime.Immutable
import com.home.torrent.model.TorrentInfo

@Immutable
internal data class TorrentSearchTabState(
    val src: Int,
    val page: Int = 1,
    val dataList: List<TorrentInfo> = emptyList(),
    val loaded: Boolean = false
)