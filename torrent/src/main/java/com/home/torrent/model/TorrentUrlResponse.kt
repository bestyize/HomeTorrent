package com.home.torrent.model

import com.google.gson.annotations.SerializedName

data class TorrentUrlResponse(
    @SerializedName("code") val code: Int = 0,
    @SerializedName("result") val result: String? = null
)
