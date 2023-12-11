package com.home.torrent.search.model

import com.home.torrent.model.TorrentSource

internal data class TorrentSearchPageState(
    val keyword: String = "",
    val source:List<TorrentSource> = emptyList(),
    val tabs: List<TorrentSearchTabState> = emptyList(),
    val dialogState: SearchPageDialogState = SearchPageDialogState()
)