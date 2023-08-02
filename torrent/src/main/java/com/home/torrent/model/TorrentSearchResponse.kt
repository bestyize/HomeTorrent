package com.home.torrent.model

import com.google.gson.annotations.SerializedName

data class TorrentSearchResponse(
    @SerializedName("code") val code: Int = 0,
    @SerializedName("list") val list: List<TorrentInfo> = listOf()
)