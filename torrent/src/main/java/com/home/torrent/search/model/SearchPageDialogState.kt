package com.home.torrent.search.model

import com.home.torrent.model.TorrentInfo

data class SearchPageDialogState(val type: Int = 0, val data: TorrentInfo? = null)
