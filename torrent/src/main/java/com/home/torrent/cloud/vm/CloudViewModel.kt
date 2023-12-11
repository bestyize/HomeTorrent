package com.home.torrent.cloud.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.baseapp.app.toast.toast
import com.home.torrent.cloud.model.TorrentCloudPageData
import com.home.torrent.collect.model.TorrentInfoBean
import com.home.torrent.collect.service.TorrentCollectService
import com.home.torrent.widget.TorrentClickOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * @author: read
 * @date: 2023/8/21 上午1:00
 * @description:
 */
internal class CloudViewModel : ViewModel() {

    private val _cloudPageState: MutableStateFlow<TorrentCloudPageData> = MutableStateFlow(
        TorrentCloudPageData()
    )

    val cloudPageState = _cloudPageState.asStateFlow()

    private var loadFinish = false

    fun handleItemClick(
        showOptionDialog: Boolean,
        selectedTorrent: TorrentInfoBean? = _cloudPageState.value.selectedTorrent,
        clickOption: TorrentClickOption = _cloudPageState.value.clickOption
    ) {
        _cloudPageState.value = _cloudPageState.value.copy(
            showOptionDialog = showOptionDialog,
            selectedTorrent = selectedTorrent,
            clickOption = clickOption
        )
    }

    fun updateCopyDialogState(
        showCopyDialog: Boolean,
        clickOption: TorrentClickOption = _cloudPageState.value.clickOption
    ) {
        _cloudPageState.value =
            _cloudPageState.value.copy(showCopyDialog = showCopyDialog, clickOption = clickOption)
    }

    fun unCollectFromCloud(index: Int, hash: String?) {
        hash ?: return
        viewModelScope.launch {
            toast(TorrentCollectService.unCollectFromCloud(hash).message)
            _cloudPageState.value =
                _cloudPageState.value.copy(list = _cloudPageState.value.list.toMutableList().apply {
                    removeAt(index)
                })
        }
    }

    fun loadCloudCollectList(reset: Boolean = false) {
        if (reset) {
            loadFinish = false
        }
        if (loadFinish) return
        viewModelScope.launch {
            var data = _cloudPageState.value
            if (reset) {
                data = TorrentCloudPageData()
            }
            TorrentCollectService.requestTorrentListFromServer(data.page).let {
                if (it.data.isNullOrEmpty()) {
                    if (it.code == -1) toast(it.message)
                } else {
                    _cloudPageState.value = _cloudPageState.value.copy(
                        page = _cloudPageState.value.page + 1,
                        list = data.list.toMutableList().apply {
                            addAll(it.data)
                        })
                }
            }
            loadFinish = true
        }
    }

}