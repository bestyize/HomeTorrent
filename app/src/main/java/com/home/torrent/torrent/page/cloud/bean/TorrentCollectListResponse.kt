package com.home.torrent.torrent.page.cloud.bean

import androidx.annotation.Keep

@Keep
data class TorrentCollectListResponse(
    val code: Int = 0, val message: String? = null, val data: List<TorrentInfoBean>? = null
)