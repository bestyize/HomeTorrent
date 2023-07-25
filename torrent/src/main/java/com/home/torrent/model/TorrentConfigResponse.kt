package com.home.torrent.model

import com.google.gson.annotations.SerializedName

data class TorrentConfigResponse(
    @SerializedName("code") val code: Int = 0,
    @SerializedName("result") val result: List<TorrentSource>? = null
)