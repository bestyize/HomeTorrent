package com.home.torrent.collect.service

import com.home.baseapp.app.config.appHost
import com.home.torrent.collect.model.TorrentCollectListResponse
import com.home.torrent.collect.model.TorrentCollectResponse
import com.home.torrent.collect.model.TorrentUnCollectResponse
import com.home.torrent.model.TorrentInfo
import com.home.torrentcenter.tool.toJson
import com.home.torrentcenter.tool.toObject
import com.home.torrentcenter.tool.urlEncode
import com.thewind.network.HttpUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author: read
 * @date: 2023/9/14 上午1:45
 * @description:
 */
object TorrentCollectService {
    internal suspend fun collectToCloud(data: TorrentInfo) = withContext(Dispatchers.IO) {
        runCatching {
            HttpUtil.get("$appHost/torrent/api/collect?data=${data.toJson().urlEncode}").takeIf { it.isNotBlank() }
                ?.toObject(TorrentCollectResponse::class.java)?.let {
                    return@withContext it
                }

        }

        return@withContext TorrentCollectResponse(-1, "网络异常，收藏失败")
    }

    internal suspend fun unCollectFromCloud(hash: String) = withContext(Dispatchers.IO) {
        runCatching {
            HttpUtil.get("$appHost/torrent/api/collect/delete?hash=$hash").takeIf { it.isNotBlank() }
                ?.toObject(TorrentUnCollectResponse::class.java)?.let {
                    return@withContext it
                }
        }
        return@withContext TorrentUnCollectResponse(-1, "网络异常，取消收藏失败")
    }

    internal suspend fun requestTorrentListFromServer(page: Int) = withContext(Dispatchers.IO) {
        runCatching {
            HttpUtil.get("$appHost/torrent/api/collect/list/page?page=$page").takeIf { it.isNotBlank() }
                .toObject(TorrentCollectListResponse::class.java)?.let {
                    return@withContext it
                }
        }

        return@withContext TorrentCollectListResponse(code = -1, message = "网络错误，加载失败")
    }
}