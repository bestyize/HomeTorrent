package com.home.torrent.torrent.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.torrent.model.TorrentInfo
import com.home.torrent.model.TorrentSource
import com.home.torrent.service.suspendRequestTorrentSources
import com.home.torrent.service.suspendSearchTorrentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class TorrentSearchViewModel : ViewModel() {

    val sourcesState: MutableStateFlow<List<TorrentSource>> = MutableStateFlow(emptyList())

    val torrentListState: MutableStateFlow<MutableMap<Int, MutableList<TorrentInfo>>> =
        MutableStateFlow(
            mutableMapOf()
        )

    val copyTorrentState: MutableStateFlow<TorrentInfo?> = MutableStateFlow(null)

    val currentSourceState: MutableStateFlow<Int> = MutableStateFlow(0)

    val currPageTorrentListState: Flow<MutableList<TorrentInfo>> = combine(torrentListState, currentSourceState) { list, curr ->
        return@combine list[curr] ?: mutableListOf()
    }

    val keywordState: MutableStateFlow<String?> = MutableStateFlow(null)

    fun loadTorrentSources() {
        viewModelScope.launch {
            sourcesState.value = suspendRequestTorrentSources()
        }
    }

    fun loadTorrentList(src: Int, page: Int = 1, loadMore: Boolean = true) {
        viewModelScope.launch {
            val newData = suspendSearchTorrentList(src, keywordState.value ?: "", page)
            val old = torrentListState.value[src] ?: mutableListOf()
            val new = if (loadMore) old + newData else newData
            torrentListState.value = torrentListState.value.toMutableMap().apply {
                this[src] = new.toMutableList()
            }

        }
    }

    fun copyTorrentUrl(data: TorrentInfo) {
        copyTorrentState.value = data
    }

    fun updateCurrentSource(src: Int) {
        currentSourceState.value = src
    }

    fun updateKeyword(key: String?) {
        keywordState.value = key
    }


}