package com.home.torrent.cloud.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.baseapp.app.toast.toast
import com.home.torrent.collect.model.TorrentInfoBean
import com.home.torrent.collect.service.TorrentCollectService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * @author: read
 * @date: 2023/8/21 上午1:00
 * @description:
 */
internal class CloudViewModel : ViewModel() {

    private val _cloudCollectListState: MutableStateFlow<List<TorrentInfoBean>> =
        MutableStateFlow(emptyList())

    private val _pageState: MutableStateFlow<Int> = MutableStateFlow(0)

    private val pageState = _pageState.asStateFlow()

    val cloudCollectListState = _cloudCollectListState.asStateFlow()


    private var loadFinish = false

    fun unCollectFromCloud(index: Int, hash: String?) {
        hash ?: return
        viewModelScope.launch {
            toast(TorrentCollectService.unCollectFromCloud(hash).message)
            _cloudCollectListState.value = _cloudCollectListState.value.toMutableList().apply {
                removeAt(index)
            }
        }
    }

    fun loadCloudCollectList(reset: Boolean = false) {
        if (reset) {
            loadFinish = false
        }
        if (loadFinish) return
        viewModelScope.launch {
            if (reset) {
                _pageState.value = 0
                _cloudCollectListState.value = emptyList()
            }
            TorrentCollectService.requestTorrentListFromServer(pageState.value).let {
                if (it.data.isNullOrEmpty()) {
                    if (it.code == -1) toast(it.message)
                    it.data?.let {
                        loadFinish = true
                    }
                } else {
                    _cloudCollectListState.value =
                        _cloudCollectListState.value.toMutableList().apply {
                            addAll(it.data)
                        }
                    _pageState.value = _pageState.value + 1
                }
            }
        }
    }

}