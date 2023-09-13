package com.home.torrent.cloud.bean

import androidx.annotation.Keep
import com.home.torrent.collect.model.TorrentInfoBean

@Keep
data class TorrentCollectListResponse(
    val code: Int = 0, val message: String? = null, val data: List<TorrentInfoBean>? = null
)