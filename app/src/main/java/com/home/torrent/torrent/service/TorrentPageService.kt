package com.home.torrent.torrent.service

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.home.baseapp.app.config.appHost
import com.home.torrent.model.TorrentInfo
import com.home.torrent.model.TorrentSource
import com.home.torrent.service.requestTorrentSources
import com.home.torrent.torrent.model.TorrentCollectResponse
import com.home.torrent.torrent.model.TorrentUnCollectResponse
import com.home.torrent.cloud.bean.TorrentCollectListResponse
import com.home.torrent.util.toJson
import com.home.torrent.util.toObject
import com.home.torrent.util.urlEncode
import com.tencent.mmkv.MMKV
import com.thewind.network.HttpUtil.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


private const val SP_LOCAL_TORRENT_SOURCES = "sp_local_torrent_sources"


object TorrentPageService {

    internal suspend fun collectToCloud(data: TorrentInfo) = withContext(Dispatchers.IO) {
        runCatching {
            get("$appHost/torrent/api/collect?data=${data.toJson().urlEncode}").takeIf { it.isNotBlank() }
                ?.toObject(TorrentCollectResponse::class.java)?.let {
                return@withContext it
            }

        }

        return@withContext TorrentCollectResponse(-1, "网络异常，收藏失败")
    }

    internal suspend fun unCollectFromCloud(hash: String) = withContext(Dispatchers.IO) {
        runCatching {
            get("$appHost/torrent/api/collect/delete?hash=$hash").takeIf { it.isNotBlank() }
                ?.toObject(TorrentUnCollectResponse::class.java)?.let {
                    return@withContext it
                }
        }
        return@withContext TorrentUnCollectResponse(-1, "网络异常，取消收藏失败")
    }

    internal suspend fun requestTorrentListFromServer(page: Int) = withContext(Dispatchers.IO) {
        runCatching {
            get("$appHost/torrent/api/collect/list/page?page=$page").takeIf { it.isNotBlank() }
                .toObject(TorrentCollectListResponse::class.java)?.let {
                    return@withContext it
                }
        }

        return@withContext TorrentCollectListResponse(code = -1, message = "网络错误，加载失败")
    }

}

internal fun loadTorrentSourcesLocal(): List<TorrentSource> {
    runCatching {
        val localData = MMKV.defaultMMKV().decodeString(SP_LOCAL_TORRENT_SOURCES) ?: ""
        val list: List<TorrentSource>? =
            Gson().fromJson(localData, object : TypeToken<List<TorrentSource>>() {}.type)
        if (!list.isNullOrEmpty()) return list
    }
    return requestTorrentSources()
}

internal fun saveTorrentSourcesToLocal(data: List<TorrentSource>?) {
    if (data.isNullOrEmpty()) return
    MMKV.defaultMMKV().encode(SP_LOCAL_TORRENT_SOURCES, Gson().toJson(data))
}