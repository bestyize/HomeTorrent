package com.home.torrent.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class TorrentSource(
    @SerializedName("src") val src: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("sub_title") val subTitle: String? = null,
    @SerializedName("desc") val desc: String? = null,
    @SerializedName("official_url") var officialUrl: String? = null,
    @SerializedName("level") val level: Int = 0,
    var isSelected: Boolean = false
) : Parcelable