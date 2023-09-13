package com.home.torrent.collect.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.baseapp.app.toast.toast
import com.home.torrent.collect.database.bean.CollectTorrentInfo
import com.home.torrent.collect.database.bean.toCollectTorrentInfo
import com.home.torrent.collect.database.bean.toTorrentInfo
import com.home.torrent.model.TorrentInfo
import com.home.torrent.service.suspendRequestMagnetUrl
import com.home.torrent.service.transferMagnetUrlToHash
import com.home.torrent.service.transferMagnetUrlToTorrentUrl
import com.home.torrent.collect.database.torrentDb
import com.home.torrent.collect.service.TorrentCollectService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TorrentCollectViewModel : ViewModel() {

    private val collectedTorrent: MutableStateFlow<List<CollectTorrentInfo>> = MutableStateFlow(emptyList())

    val torrentListState: Flow<List<TorrentInfo>> = collectedTorrent.map { it.map { it.toTorrentInfo() }}

    val torrentSetState: Flow<Set<TorrentInfo>> = torrentListState.map { it.toSet() }



    fun init() {
        loadAll()
    }

    fun collect(data: TorrentInfo) {
        viewModelScope.launch {
            val dat = if (data.magnetUrl.isNullOrBlank()) {
                data.copy(magnetUrl = suspendRequestMagnetUrl(data.src, data.detailUrl!!))
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

    fun collectToCloud(data: TorrentInfo?) {
        data ?: return
        viewModelScope.launch {
            val magnetUrl = if (data.magnetUrl.isNullOrBlank()) suspendRequestMagnetUrl(
                data.src,
                data.detailUrl!!
            ) else data.magnetUrl
            if (magnetUrl == null) {
                toast("收藏到云端失败！")
                return@launch
            }
            val hash = if (data.hash.isNullOrBlank()) transferMagnetUrlToHash(magnetUrl) else data.hash
            val dat = data.copy(
                magnetUrl = magnetUrl,
                hash = if (hash.isNullOrBlank()) magnetUrl else hash,
                torrentUrl = if (data.torrentUrl.isNullOrBlank()) transferMagnetUrlToTorrentUrl(
                    magnetUrl
                ) else data.torrentUrl
            )
            toast(TorrentCollectService.collectToCloud(dat).message)
        }
    }

}