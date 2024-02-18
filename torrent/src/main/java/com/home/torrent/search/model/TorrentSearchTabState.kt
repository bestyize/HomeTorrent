package com.home.torrent.search.model

import androidx.compose.runtime.Immutable
import com.home.torrent.model.TorrentInfo
import com.thewind.widget.ui.list.lazy.PageLoadState

@Immutable
internal data class TorrentSearchTabState(
    val src: Int,
    val page: Int = 1,
    val dataList: List<TorrentInfo> = emptyList(),
    val keyword: String = "",
    val loadState: PageLoadState = PageLoadState.INIT
)