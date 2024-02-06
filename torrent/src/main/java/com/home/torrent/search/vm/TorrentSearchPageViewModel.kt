package com.home.torrent.search.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.home.baseapp.app.toast.toast
import com.home.torrent.collect.service.TorrentCollectService
import com.home.torrent.model.TorrentInfo
import com.home.torrent.model.TorrentSource
import com.home.torrent.search.model.SearchPageDialogState
import com.home.torrent.search.model.SearchPageDialogType
import com.home.torrent.search.model.TorrentSearchPageState
import com.home.torrent.search.model.TorrentSearchTabState
import com.home.torrent.service.requestTorrentSources
import com.home.torrent.service.suspendRequestMagnetUrl
import com.home.torrent.service.transferMagnetUrlToHash
import com.home.torrent.service.transferMagnetUrlToTorrentUrl
import com.home.torrentcenter.services.suspendSearchMagnetUrl
import com.home.torrentcenter.services.suspendSearchTorrent
import com.home.torrentcenter.services.suspendSearchTorrentUrl
import com.tencent.mmkv.MMKV
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * @author: read
 * @date: 2023/9/12 上午1:42
 * @description:
 */
internal class TorrentSearchPageViewModel : ViewModel() {

    private val _searchPageState: MutableStateFlow<TorrentSearchPageState> = MutableStateFlow(
        TorrentSearchPageState(source = loadTorrentSourcesLocal())
    )

    val searchPageState = _searchPageState.asStateFlow()

    init {
        viewModelScope.launch {
            val isFinish =
                _searchPageState.value.source.isNotEmpty() && _searchPageState.value.source.size == _searchPageState.value.tabs.size
            if (!isFinish) {
                val data = _searchPageState.value
                _searchPageState.value = data.copy(tabs = data.source.map {
                    TorrentSearchTabState(src = it.src)
                }.toImmutableList())
            }
        }
    }

    fun reloadKeyword() {
        viewModelScope.launch {
            val data = searchPageState.value
            _searchPageState.value = data.copy(
                tabs = data.tabs.toMutableList().apply {
                    forEachIndexed { index, pageState ->
                        this[index] =
                            pageState.copy(
                                page = 1,
                                dataList = emptyList(),
                                loaded = false
                            )
                    }
                }.toImmutableList()
            )
        }
    }

    fun loadMore(src: Int) {
        viewModelScope.launch {
            val pages = _searchPageState.value.tabs
            val pageData = pages.find { it.src == src } ?: return@launch
            val list = suspendSearchTorrent(
                src = src,
                key = _searchPageState.value.keyword,
                page = pageData.page
            )
            val newTabs = pages.toMutableList().apply {
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
            _searchPageState.value = _searchPageState.value.copy(tabs = newTabs.toImmutableList())

        }

    }

    fun updateKeyword(keyword: String) {
        viewModelScope.launch {
            _searchPageState.value = _searchPageState.value.copy(keyword = keyword)
        }
    }

    fun handleTorrentInfoClick(data: TorrentInfo) {
        viewModelScope.launch {
            val stateData = _searchPageState.value
            _searchPageState.value = stateData.copy(
                dialogState = stateData.dialogState.copy(
                    type = SearchPageDialogType.OPTION,
                    data = data
                )
            )
        }

    }

    fun updateDialogState(
        data: TorrentInfo? = _searchPageState.value.dialogState.data,
        isMagnet: Boolean = true
    ) {
        if (data == null || data.detailUrl.isNullOrBlank()) {
            _searchPageState.value =
                _searchPageState.value.copy(dialogState = SearchPageDialogState())
            return
        }
        viewModelScope.launch {
            val dialogState = _searchPageState.value.dialogState
            _searchPageState.value = _searchPageState.value.copy(
                dialogState = dialogState.copy(
                    type = SearchPageDialogType.ADDRESS, data = data.copy(
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
            )
        }

    }

    fun collectToCloud(data: TorrentInfo?) {
        viewModelScope.launch {
            data ?: return@launch
            val magnetUrl = if (data.magnetUrl.isNullOrBlank()) suspendRequestMagnetUrl(
                data.src, data.detailUrl!!
            ) else data.magnetUrl
            if (magnetUrl == null) {
                toast("收藏到云端失败！")
                return@launch
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
    }

}


internal fun loadTorrentSourcesLocal(): ImmutableList<TorrentSource> {
    runCatching {
        val localData = MMKV.defaultMMKV().decodeString(SP_LOCAL_TORRENT_SOURCES) ?: ""
        val list: List<TorrentSource>? =
            Gson().fromJson(localData, object : TypeToken<List<TorrentSource>>() {}.type)
        if (!list.isNullOrEmpty()) return list.toImmutableList()
    }
    return requestTorrentSources().toImmutableList()
}

private fun saveTorrentSourcesToLocal(data: List<TorrentSource>?) {
    if (data.isNullOrEmpty()) return
    MMKV.defaultMMKV().encode(SP_LOCAL_TORRENT_SOURCES, Gson().toJson(data))
}

private const val SP_LOCAL_TORRENT_SOURCES = "sp_local_torrent_sources"

private const val TAG = "[Torrent]TorrentSearchPageViewModel"