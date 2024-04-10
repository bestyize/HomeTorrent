package com.home.torrent.collect.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.baseapp.app.toast.toast
import com.home.torrent.collect.database.bean.CollectTorrentInfo
import com.home.torrent.collect.database.bean.toCollectTorrentInfo
import com.home.torrent.collect.database.bean.toTorrentInfo
import com.home.torrent.collect.database.torrentDb
import com.home.torrent.collect.model.CollectPageDialogState
import com.home.torrent.collect.model.CollectPageDialogType
import com.home.torrent.collect.model.TorrentLocalCollectPageUiState
import com.home.torrent.collect.service.TorrentCollectService
import com.home.torrent.model.TorrentInfo
import com.home.torrent.service.suspendRequestMagnetUrl
import com.home.torrent.service.transferMagnetUrlToHash
import com.home.torrent.service.transferMagnetUrlToTorrentUrl
import com.home.torrentcenter.services.suspendSearchMagnetUrl
import com.home.torrentcenter.services.suspendSearchTorrentUrl
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class TorrentCollectViewModel : ViewModel() {

    private val _localCollectPageState: MutableStateFlow<TorrentLocalCollectPageUiState> = MutableStateFlow(
        TorrentLocalCollectPageUiState()
    )

    val localCollectPageState = _localCollectPageState.asStateFlow()

    val torrentSetState: Flow<ImmutableSet<TorrentInfo>> = _localCollectPageState.map { it.torrentList.toImmutableSet() }

    val dialogState: MutableStateFlow<CollectPageDialogState> = MutableStateFlow(
        CollectPageDialogState()
    )

    init {
        viewModelScope.launch {
            loadAll()
        }
    }

    suspend fun collect(data: TorrentInfo) {
        val dat = if (data.magnetUrl.isNullOrBlank()) {
            data.copy(magnetUrl = suspendRequestMagnetUrl(data.src, data.detailUrl!!))
        } else data
        if (dat.magnetUrl.isNullOrBlank()) {
            toast("收藏失败")
            return
        }
        torrentDb.collectDao().insert(dat.toCollectTorrentInfo())
        toast("收藏成功!")
        loadAll()
    }

    suspend fun unCollect(data: TorrentInfo) {
        if (data.magnetUrl.isNullOrBlank()) {
            toast("取消收藏失败")
            return
        }
        torrentDb.collectDao().deleteByMagnetUrl(data.magnetUrl!!)
        toast("取消收藏成功")
        delay(500)
        loadAll()
    }

    private suspend fun loadAll() {
        val data = _localCollectPageState.value
        val list = torrentDb.collectDao().loadCollectedTorrent() ?: emptyList()
        _localCollectPageState.value = data.copy(torrentList = list.map { it.toTorrentInfo() }.toImmutableList())
    }

    suspend fun collectToCloud(data: TorrentInfo?) {
        data ?: return
        val magnetUrl = if (data.magnetUrl.isNullOrBlank()) suspendRequestMagnetUrl(
            data.src, data.detailUrl!!
        ) else data.magnetUrl
        if (magnetUrl == null) {
            toast("收藏到云端失败！")
            return
        }
        val hash =
            if (data.hash.isNullOrBlank()) transferMagnetUrlToHash(magnetUrl) else data.hash
        val dat = data.copy(
            magnetUrl = magnetUrl,
            hash = if (hash.isNullOrBlank()) magnetUrl else hash,
            torrentUrl = if (data.torrentUrl.isNullOrBlank()) transferMagnetUrlToTorrentUrl(
                magnetUrl
            ) else data.torrentUrl
        )
        toast(TorrentCollectService.collectToCloud(dat).message)
    }

    fun updateDialogState(data: TorrentInfo?, isMagnet: Boolean = true) {
        if (data == null || data.detailUrl.isNullOrBlank()) {
            dialogState.value = CollectPageDialogState()
            return
        }
        viewModelScope.launch {
            dialogState.value = dialogState.value.copy(
                type = CollectPageDialogType.ADDRESS, data = data.copy(
                    magnetUrl = when {
                        !data.magnetUrl.isNullOrBlank() -> data.magnetUrl
                        else -> suspendSearchMagnetUrl(
                            data.src, data.detailUrl!!
                        )
                    }, torrentUrl = when {
                        !data.torrentUrl.isNullOrBlank() -> data.torrentUrl
                        !data.magnetUrl.isNullOrBlank() -> transferMagnetUrlToTorrentUrl(data.magnetUrl!!)
                        else -> suspendSearchTorrentUrl(data.src, data.detailUrl!!)
                    }
                ), isMagnet = isMagnet
            )
        }

    }

    fun handleTorrentInfoClick(data: TorrentInfo) {
        dialogState.value = dialogState.value.copy(type = CollectPageDialogType.OPTION, data = data)
    }


    fun openEditDialog() {

    }


    fun modifyTitle(newTitle:String, hash: String) {


    }

    fun closeEditDialog() {

    }

}