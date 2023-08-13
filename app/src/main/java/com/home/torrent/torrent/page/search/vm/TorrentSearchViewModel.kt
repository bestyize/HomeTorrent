package com.home.torrent.torrent.page.search.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.baseapp.app.toast.toast
import com.home.torrent.model.TorrentInfo
import com.home.torrent.service.suspendRequestMagnetUrl
import com.home.torrent.service.suspendRequestTorrentUrl
import com.home.torrent.service.suspendSearchTorrentList
import com.home.torrent.service.transferMagnetUrlToTorrentUrl
import com.home.torrent.torrent.model.KeywordInfo
import com.thewind.downloader.model.DownloadTask
import com.thewind.downloader.task.DownloadState
import com.thewind.downloader.task.suspendSyncDownload
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TorrentSearchViewModel : ViewModel() {

    val copyTorrentState: MutableStateFlow<TorrentInfo?> = MutableStateFlow(null)

    val keywordState: MutableStateFlow<KeywordInfo> = MutableStateFlow(KeywordInfo())

    suspend fun loadTorrentList(src: Int, key: String?, page: Int = 1): List<TorrentInfo> =
        suspendSearchTorrentList(src, key ?: "", page)

    fun copyTorrentUrl(data: TorrentInfo?) {
        if (data == null || data.detailUrl.isNullOrBlank()) {
            copyTorrentState.value = data
            return
        }
        viewModelScope.launch {
            copyTorrentState.value = data.copy(
                magnetUrl = when {
                    !data.magnetUrl.isNullOrBlank() -> data.magnetUrl
                    else -> suspendRequestMagnetUrl(
                        data.src, data.detailUrl!!
                    )
                }, torrentUrl = when {
                    !data.torrentUrl.isNullOrBlank() -> data.torrentUrl
                    !data.magnetUrl.isNullOrBlank() -> transferMagnetUrlToTorrentUrl(data.magnetUrl!!)
                    else -> suspendRequestTorrentUrl(data.src, data.detailUrl!!)
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

            val state = suspendSyncDownload(
                DownloadTask(
                    url = when {
                        !data.torrentUrl.isNullOrBlank() -> data.torrentUrl
                        !data.magnetUrl.isNullOrBlank() -> transferMagnetUrlToTorrentUrl(data.magnetUrl!!)
                        else -> suspendRequestTorrentUrl(data.src, data.detailUrl!!)
                    },
                    key = transferMagnetUrlToTorrentUrl(data.magnetUrl?:"")
                )
            )
            when(state) {
                DownloadState.SUCCESS -> toast("下载成功")
                DownloadState.FAILED -> toast("下载失败")
            }
        }
    }


}