package com.home.torrent.collect.model

import androidx.annotation.Keep

@Keep
data class TorrentUnCollectResponse(val code: Int = 0, val message: String? = null)
