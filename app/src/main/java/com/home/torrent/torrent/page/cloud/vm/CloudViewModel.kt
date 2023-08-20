package com.home.torrent.torrent.page.cloud.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.baseapp.app.toast.toast
import com.home.torrent.torrent.page.cloud.bean.TorrentInfoBean
import com.home.torrent.torrent.service.TorrentPageService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * @author: read
 * @date: 2023/8/21 上午1:00
 * @description:
 */
class CloudViewModel : ViewModel() {

    private val _cloudCollectListState: MutableStateFlow<List<TorrentInfoBean>> = MutableStateFlow(emptyList())

    private val _pageState: MutableStateFlow<Int> = MutableStateFlow(0)

    val pageState = _pageState.asStateFlow()

    val cloudCollectListState = _cloudCollectListState.asStateFlow()

    fun unCollectFromCloud(index: Int, hash: String?) {
        hash ?: return
        viewModelScope.launch {
            toast(TorrentPageService.unCollectFromCloud(hash).message)
            _cloudCollectListState.value = _cloudCollectListState.value.toMutableList().apply {
                removeAt(index)
            }
        }
    }

    fun loadCloudCollectList(reset: Boolean = false) {
        viewModelScope.launch {
            if (reset) {
                _pageState.value = 0
                _cloudCollectListState.value = emptyList()
            }
            TorrentPageService.requestTorrentListFromServer(pageState.value).let {
                if (it.data.isNullOrEmpty()) {
                    toast(it.message)
                } else {
                    _cloudCollectListState.value = _cloudCollectListState.value.toMutableList().apply {
                        addAll(it.data)
                    }
                    _pageState.value = _pageState.value + 1
                }
            }
        }
    }

}