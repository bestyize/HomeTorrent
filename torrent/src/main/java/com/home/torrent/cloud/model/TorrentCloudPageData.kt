package com.home.torrent.cloud.model

import com.home.torrent.collect.model.TorrentInfoBean
import com.home.torrent.widget.TorrentClickOption

internal data class TorrentCloudPageData(
    val list:List<TorrentInfoBean> = emptyList(),
    val page: Int = 0,
    val showOptionDialog: Boolean = false,
    val showCopyDialog: Boolean = false,
    val selectedTorrent: TorrentInfoBean? = null,
    val clickOption: TorrentClickOption = TorrentClickOption.GET_MAGNET_URL
)
