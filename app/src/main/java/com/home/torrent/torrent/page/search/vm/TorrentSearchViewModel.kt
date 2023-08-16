package com.home.torrent.torrent.page.search.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.baseapp.app.toast.toast
import com.home.torrent.model.TorrentInfo
import com.home.torrent.model.TorrentSource
import com.home.torrent.service.transferMagnetUrlToTorrentUrl
import com.home.torrent.torrent.model.KeywordInfo
import com.home.torrentcenter.services.suspendRequestTorrentSource
import com.home.torrentcenter.services.suspendSearchMagnetUrl
import com.home.torrentcenter.services.suspendSearchTorrent
import com.home.torrentcenter.services.suspendSearchTorrentUrl
import com.thewind.downloader.model.DownloadTask
import com.thewind.downloader.task.DownloadManager.suspendSyncDownload
import com.thewind.downloader.task.DownloadState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TorrentSearchViewModel : ViewModel() {

    val copyTorrentState: MutableStateFlow<TorrentInfo?> = MutableStateFlow(null)

    val keywordState: MutableStateFlow<KeywordInfo> = MutableStateFlow(KeywordInfo())

    val torrentSourceState: MutableStateFlow<List<TorrentSource>> = MutableStateFlow(emptyList())

    fun loadSources() {
        viewModelScope.launch {
            torrentSourceState.value = suspendRequestTorrentSource()
        }
    }

    suspend fun loadTorrentList(src: Int, key: String?, page: Int = 1): List<TorrentInfo> =
        suspendSearchTorrent(src, key ?: "", page)

    fun copyTorrentUrl(data: TorrentInfo?) {
        if (data == null || data.detailUrl.isNullOrBlank()) {
            copyTorrentState.value = data
            return
        }
        viewModelScope.launch {
            copyTorrentState.value = data.copy(
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
            )
        }

    }

    fun updateKeyword(key: String) {
        // 点击搜索时触发一次更新
        keywordState.value = KeywordInfo(key, System.currentTimeMillis())
    }

    fun downloadTorrent(data: TorrentInfo?) {
        data ?: return
        if (data.detailUrl.isNullOrBlank()) {
            toast("数据无效")
            return
        }
        viewModelScope.launch {
            toast("开始下载")
            val state = suspendSyncDownload(
                DownloadTask(
                    url = when {
                        !data.torrentUrl.isNullOrBlank() -> data.torrentUrl
                        !data.magnetUrl.isNullOrBlank() -> transferMagnetUrlToTorrentUrl(data.magnetUrl!!)
                        else -> suspendSearchTorrentUrl(data.src, data.detailUrl!!)
                    },
                    key = data.hash.takeIf { !it.isNullOrBlank() } ?: transferMagnetUrlToTorrentUrl(data.magnetUrl?:"")
                )
            )
            when(state) {
                DownloadState.SUCCESS -> toast("下载成功")
                DownloadState.FAILED -> toast("下载失败")
            }
        }
    }


}