package com.home.torrent.model

import com.google.gson.annotations.SerializedName

data class TorrentSearchResponse(
    @SerializedName("code") val code: Int = 0,
    @SerializedName("result") val list: List<TorrentItem>
)