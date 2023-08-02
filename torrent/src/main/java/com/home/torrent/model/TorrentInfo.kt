package com.home.torrent.model

import com.google.gson.annotations.SerializedName

data class TorrentInfo(
    @SerializedName("detail_url") val detailUrl: String? = null,
    @SerializedName("magnet_url") val magnetUrl: String? = null,
    @SerializedName("torrent_url") val torrentUrl: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("date") val date: String? = null,
    @SerializedName("size") val size: String? = null,
    @SerializedName("src") val src: Int = 0,
    @SerializedName("download_count") val downloadCount: String? = null,
    @SerializedName("leacher_count") val leacherCount: String? = null,
    @SerializedName("sender_count") val senderCount: String? = null
)