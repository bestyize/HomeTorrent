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
import com.thewind.widget.ui.list.lazy.PageLoadState
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


    private val loadingSet = mutableSetOf<Int>()

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

    fun forceReloadTabKeyword(tabIndex: Int) {
        val data = searchPageState.value
        val tabData = data.tabs.getOrNull(tabIndex) ?: return
        if (loadingSet.contains(tabIndex)) {
            Log.i(TAG, "forceReloadTabKeyword, tab = $tabIndex is loading")
            return
        }
        _searchPageState.value = data.copy(
            tabs = data.tabs.toMutableList().apply {
                this[tabIndex] = TorrentSearchTabState(src = tabData.src, keyword = data.keyword)
            }.toImmutableList()
        )
    }

    fun reloadTabKeywordWhenPageSwitch(tabIndex: Int) {
        val data = searchPageState.value
        val tabData = data.tabs.getOrNull(tabIndex) ?: return
        if (tabData.keyword != data.keyword) {
            forceReloadTabKeyword(tabIndex)
        }
    }

    suspend fun loadMore(src: Int, pageIndex: Int) {
        val data = _searchPageState.value
        val tabStates = _searchPageState.value.tabs
        val tabState = tabStates.find { it.src == src } ?: return
        if (loadingSet.contains(pageIndex)) return
        Log.i(TAG, "loadMore, pageIndex = $pageIndex, keyword = ${data.keyword}")
        when (tabState.loadState) {
            PageLoadState.INIT -> {
                loadingSet.add(pageIndex)
                val list = suspendSearchTorrent(
                    src = src, key = _searchPageState.value.keyword, page = tabState.page
                )
                _searchPageState.value = data.copy(
                    tabs = data.tabs.toMutableList().apply {
                        forEachIndexed { index, tabData ->
                            if (tabData.src == src) {
                                this[index] = tabData.copy(
                                    page = if (list.isEmpty()) 1 else 2,
                                    keyword = data.keyword,
                                    dataList = list,
                                    loadState = if (list.isEmpty()) PageLoadState.ALL_LOADED else PageLoadState.FINISH
                                )
                            }
                        }
                    }.toImmutableList()
                )
                loadingSet.remove(pageIndex)
            }

            PageLoadState.FINISH -> {
                loadingSet.add(pageIndex)
                val list = suspendSearchTorrent(
                    src = src, key = _searchPageState.value.keyword, page = tabState.page
                )
                _searchPageState.value = data.copy(
                    tabs = data.tabs.toMutableList().apply {
                        forEachIndexed { index, tabData ->
                            if (tabData.src == src) {
                                this[index] = tabData.copy(
                                    page = if (list.isEmpty()) tabData.page else tabData.page + 1,
                                    keyword = data.keyword,
                                    dataList = tabData.dataList.toMutableList().apply {
                                        addAll(list)
                                    },
                                    loadState = if (list.isEmpty()) PageLoadState.ALL_LOADED else PageLoadState.FINISH
                                )
                            }
                        }
                    }.toImmutableList()
                )
                loadingSet.remove(pageIndex)
            }

            PageLoadState.ALL_LOADED -> {

            }

            PageLoadState.ERROR -> {

            }
        }


    }

    fun updateKeyword(keyword: String) {
        _searchPageState.value = _searchPageState.value.copy(keyword = keyword)
    }

    suspend fun handleTorrentInfoClick(data: TorrentInfo) {
        val stateData = _searchPageState.value
        _searchPageState.value = stateData.copy(
            dialogState = stateData.dialogState.copy(
                type = SearchPageDialogType.OPTION, data = data
            )
        )

    }

    fun updateDialogState(
        data: TorrentInfo? = _searchPageState.value.dialogState.data, isMagnet: Boolean = true
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

    fun collectToCloud(torrent: TorrentInfo?) {
        viewModelScope.launch {
            torrent ?: return@launch
            val magnetUrl = if (torrent.magnetUrl.isNullOrBlank()) suspendRequestMagnetUrl(
                torrent.src, torrent.detailUrl!!
            ) else torrent.magnetUrl
            if (magnetUrl == null) {
                toast("收藏到云端失败！")
                return@launch
            }
            val hash =
                if (torrent.hash.isNullOrBlank()) transferMagnetUrlToHash(magnetUrl) else torrent.hash
            val dat = torrent.copy(
                magnetUrl = magnetUrl,
                hash = if (hash.isNullOrBlank()) magnetUrl else hash,
                torrentUrl = if (torrent.torrentUrl.isNullOrBlank()) transferMagnetUrlToTorrentUrl(
                    magnetUrl
                ) else torrent.torrentUrl
            )
            toast(TorrentCollectService.collectToCloud(dat).message)
            updateDialogState(null)
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