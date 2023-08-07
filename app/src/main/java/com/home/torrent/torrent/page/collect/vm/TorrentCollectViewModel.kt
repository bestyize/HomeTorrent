package com.home.torrent.torrent.page.collect.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.baseapp.app.toast.toast
import com.home.torrent.model.TorrentInfo
import com.home.torrent.service.suspendRequestMagnetUrl
import com.home.torrent.torrent.page.collect.database.bean.CollectTorrentInfo
import com.home.torrent.torrent.page.collect.database.bean.toCollectTorrentInfo
import com.home.torrent.torrent.page.collect.database.bean.toTorrentInfo
import com.home.torrent.torrent.page.collect.database.torrentDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TorrentCollectViewModel : ViewModel() {

    val collectedTorrent: MutableStateFlow<List<CollectTorrentInfo>> = MutableStateFlow(emptyList())

    val torrentState = collectedTorrent.map { it.map { it.toTorrentInfo() } }

    fun collect(data: TorrentInfo) {
        viewModelScope.launch {
            val dat = if (data.magnetUrl.isNullOrBlank()) {
                data.copy(detailUrl = suspendRequestMagnetUrl(data.src, data.detailUrl!!))
            } else data
            if (dat.magnetUrl.isNullOrBlank()) {
                toast("收藏失败")
                return@launch
            }
            torrentDb.collectDao().insert(dat.toCollectTorrentInfo())
            toast("收藏成功!")
            loadAll()
        }
    }

    fun unCollect(data: TorrentInfo) {
        viewModelScope.launch {
            if (data.magnetUrl.isNullOrBlank()) {
                toast("取消收藏失败")
                return@launch
            }
            torrentDb.collectDao().deleteByMagnetUrl(data.magnetUrl!!)
            toast("取消收藏成功")
            delay(500)
            loadAll()
        }
    }

    fun clearCollect() {
        viewModelScope.launch {
            torrentDb.collectDao().deleteAll()
            toast("清除成功")
        }
    }

    fun loadAll() {
        viewModelScope.launch {
            collectedTorrent.value = torrentDb.collectDao().loadCollectedTorrent() ?: emptyList()
        }
    }

}