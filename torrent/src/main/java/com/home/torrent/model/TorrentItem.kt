package com.home.torrent.model

import com.google.gson.annotations.SerializedName

data class TorrentItem(
    @SerializedName("detail_url") val detailUrl: String? = null,
    @SerializedName("magnet_url") val magnetUrl: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("date") val date: String? = null,
    @SerializedName("size") val size: String? = null,
    @SerializedName("src") val src: Int = 0
)