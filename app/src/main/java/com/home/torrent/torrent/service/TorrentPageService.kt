package com.home.torrent.torrent.service

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.home.torrent.model.TorrentSource
import com.home.torrent.service.requestTorrentSources
import com.tencent.mmkv.MMKV



private const val SP_LOCAL_TORRENT_SOURCES = "sp_local_torrent_sources"

internal fun loadTorrentSourcesLocal(): List<TorrentSource> {
    runCatching {
        val localData = MMKV.defaultMMKV().decodeString(SP_LOCAL_TORRENT_SOURCES) ?: ""
        val list:List<TorrentSource>? =  Gson().fromJson(localData, object : TypeToken<List<TorrentSource>>(){}.type)
        if (!list.isNullOrEmpty()) return list
    }
    return requestTorrentSources()
}

internal fun saveTorrentSourcesToLocal(data: List<TorrentSource>?) {
    if (data.isNullOrEmpty()) return
    MMKV.defaultMMKV().encode(SP_LOCAL_TORRENT_SOURCES, Gson().toJson(data))
}
