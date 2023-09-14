package com.home.torrent.search.model

import com.home.torrent.model.TorrentInfo

internal data class SearchPageDialogState(
    val type: SearchPageDialogType = SearchPageDialogType.NONE,
    val data: TorrentInfo? = null,
    val isMagnet: Boolean = true
)
