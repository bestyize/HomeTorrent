package com.home.torrent.collect.model

import androidx.annotation.Keep

/**
 * @author: read
 * @date: 2023/8/20 下午11:08
 * @description:
 */
@Keep
internal data class TorrentCollectResponse(val code: Int = 0, val message: String? = null)