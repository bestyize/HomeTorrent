package com.home.torrent.collect.model

import androidx.annotation.Keep
import com.home.torrent.model.TorrentInfo

/**
 * @author: read
 * @date: 2023/8/21 上午1:02
 * @description:
 */
@Keep
data class TorrentInfoBean(
    var id: Long = 0,
    var uid: Long = 0,
    var hash: String? = null,
    var detailUrl: String? = null,
    var magnetUrl: String? = null,
    var torrentUrl: String? = null,
    var title: String? = null,
    var date: String? = null,
    var size: String? = null,
    var src: Int = 0,
    var collectDate: Long = 0
)

internal fun TorrentInfo.toInfoBean(): TorrentInfoBean {
    val info = this
    return TorrentInfoBean().apply {
        this.hash = info.hash
        this.detailUrl = info.detailUrl
        this.magnetUrl = info.magnetUrl
        this.torrentUrl = info.torrentUrl
        this.title = info.title
        this.date = info.date
        this.src = info.src
        this.size = info.size
    }
}