package com.home.torrent.torrent.page.collect.database.bean

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.home.torrent.model.TorrentInfo

internal const val TB_TORRENT_COLLECT = "tb_torrent_collect"

@Keep
@Entity(tableName = TB_TORRENT_COLLECT)
data class CollectTorrentInfo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("collect_time") val collectTime: Long = 0L,
    @ColumnInfo("title") val title: String? = null,
    @ColumnInfo("date") val date: String? = null,
    @ColumnInfo("size") val size: String? = null,
    @ColumnInfo("magnet_url") val magnetUrl: String? = null,
    @ColumnInfo("torrent_url") val torrentUrl: String? = null,
    @ColumnInfo("hash") val hash: String? = null,
    @ColumnInfo("src") val src: Int = 0,
    @ColumnInfo("detail_url") val detailUrl: String? = null
)

internal fun TorrentInfo.toCollectTorrentInfo(): CollectTorrentInfo {
    return CollectTorrentInfo(
        collectTime = System.currentTimeMillis(),
        title = title,
        date = date,
        size = size,
        magnetUrl = magnetUrl,
        torrentUrl = torrentUrl,
        src = src,
        detailUrl = detailUrl
    )
}

internal fun CollectTorrentInfo.toTorrentInfo(): TorrentInfo {
    return TorrentInfo(
        title = title,
        date = date,
        size = size,
        magnetUrl = magnetUrl,
        torrentUrl = torrentUrl,
        src = src,
        detailUrl = detailUrl
    )
}