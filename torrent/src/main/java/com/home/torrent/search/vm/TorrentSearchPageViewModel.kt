package com.home.torrent.search.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.home.torrent.model.TorrentSource
import com.home.torrent.search.model.TorrentSearchPageState
import com.home.torrent.service.requestTorrentSources
import com.home.torrentcenter.services.suspendSearchTorrent
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * @author: read
 * @date: 2023/9/12 上午1:42
 * @description:
 */
internal class TorrentSearchPageViewModel : ViewModel() {

    val sourceState: StateFlow<List<TorrentSource>> = MutableStateFlow(
        loadTorrentSourcesLocal()
    )

    val pagesState: MutableStateFlow<List<TorrentSearchPageState>> = MutableStateFlow(emptyList())

    val keywordState: MutableStateFlow<String> = MutableStateFlow("")

    val initFinishState: Flow<Boolean> = combine(sourceState, pagesState) { sources, pages ->
        return@combine !sources.isEmpty() && pages.size == sources.size
    }

    fun init() {
        viewModelScope.launch {
            val isFinish = initFinishState.first()
            if (!isFinish) {
                val list = mutableListOf<TorrentSearchPageState>()
                sourceState.value.forEach {
                    list.add(TorrentSearchPageState(src = it.src))
                }
                pagesState.value = list
            }
        }
    }

    fun updateGlobalKeyword(key: String, forceUpdate: Boolean = false) {
        if (keywordState.value == key && !forceUpdate) return
        keywordState.value = key
        pagesState.value = pagesState.value.toMutableList().apply {
            forEachIndexed { index, pageState ->
                this[index] = pageState.copy(keyword = key, page = 1, dataList = emptyList(), loaded = false)
            }
        }

    }

    fun loadMore(src: Int) {
        viewModelScope.launch {
            val pages = pagesState.value
            val pageData = pages.find { it.src == src } ?: return@launch
            val list = suspendSearchTorrent(src = src, key = pageData.keyword, page = pageData.page)
            pagesState.value = pages.toMutableList().apply {
                forEachIndexed { index, data ->
                    if (data.src == src) {
                        val page = if (list.isEmpty()) pageData.page else {
                            pageData.page + 1
                        }

                        val dataList = if (list.isEmpty()) pageData.dataList else {
                            pageData.dataList.toMutableList().apply {
                                addAll(list)
                            }
                        }
                        this[index] = pageData.copy(
                            page = page, dataList = dataList, loaded = list.isEmpty()
                        )
                    }
                }
            }

        }


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

private const val SP_LOCAL_TORRENT_SOURCES = "sp_local_torrent_sources"