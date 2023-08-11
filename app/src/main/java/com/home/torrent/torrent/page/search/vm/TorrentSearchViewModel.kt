package com.home.torrent.torrent.page.search.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.torrent.model.TorrentInfo
import com.home.torrent.service.suspendRequestMagnetUrl
import com.home.torrent.service.suspendSearchTorrentList
import com.home.torrent.torrent.model.KeywordInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TorrentSearchViewModel : ViewModel() {

    val copyTorrentState: MutableStateFlow<TorrentInfo?> = MutableStateFlow(null)

    val keywordState: MutableStateFlow<KeywordInfo> = MutableStateFlow(KeywordInfo())

    suspend fun loadTorrentList(src: Int, key: String?, page: Int = 1): List<TorrentInfo> =
        suspendSearchTorrentList(src, key ?: "", page)

    fun copyTorrentUrl(data: TorrentInfo?) {
        if (data == null || !data.torrentUrl.isNullOrBlank() || !data.magnetUrl.isNullOrBlank() || data.detailUrl.isNullOrBlank()) {
            copyTorrentState.value = data
            return
        }
        viewModelScope.launch {
            copyTorrentState.value = data.copy(
                magnetUrl = suspendRequestMagnetUrl(
                    data.src, data.detailUrl!!
                )
            )
        }

    }

    fun updateKeyword(key: String) {
        // 点击搜索时触发一次更新
        keywordState.value = KeywordInfo(key, System.currentTimeMillis())
    }


}