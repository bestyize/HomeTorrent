package com.home.torrent.collect.model

import com.home.torrent.model.TorrentInfo

/**
 * @author: read
 * @date: 2023/9/15 上午12:49
 * @description:
 */
internal data class CollectPageDialogState(
    val type: CollectPageDialogType = CollectPageDialogType.NONE,
    val data: TorrentInfo? = null,
    val isMagnet: Boolean = true
)
