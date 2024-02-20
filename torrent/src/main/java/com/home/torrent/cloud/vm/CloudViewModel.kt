package com.home.torrent.cloud.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.baseapp.app.toast.toast
import com.home.torrent.cloud.model.TorrentCloudPageData
import com.home.torrent.collect.model.TorrentInfoBean
import com.home.torrent.collect.service.TorrentCollectService
import com.home.torrent.widget.TorrentClickOption
import com.thewind.widget.ui.list.lazy.PageLoadState
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
        showCopyDialog: Boolean, clickOption: TorrentClickOption = _cloudPageState.value.clickOption
    ) {
        _cloudPageState.value =
            _cloudPageState.value.copy(showCopyDialog = showCopyDialog, clickOption = clickOption)
    }

    suspend fun unCollectFromCloud(index: Int, hash: String?) {
        hash ?: return
        toast(TorrentCollectService.unCollectFromCloud(hash).message)
        _cloudPageState.value =
            _cloudPageState.value.copy(list = _cloudPageState.value.list.toMutableList().apply {
                removeAt(index)
            })
    }

    fun reloadAllCollectList() {
        _cloudPageState.value = TorrentCloudPageData()
    }

    suspend fun loadCloudCollectList() {

        val data = _cloudPageState.value

        when (data.pageLoadState) {
            PageLoadState.INIT, PageLoadState.FINISH -> {
                val resp = TorrentCollectService.requestTorrentListFromServer(data.page)
                _cloudPageState.value = if (resp.data.isNullOrEmpty()) {
                    data.copy(pageLoadState = PageLoadState.ALL_LOADED)
                } else {
                    data.copy(pageLoadState = PageLoadState.FINISH,
                        list = data.list.toMutableList().apply {
                            addAll(resp.data)
                        },
                        page = data.page + 1
                    )
                }

            }

            PageLoadState.ALL_LOADED -> {}

            PageLoadState.ERROR -> {}
        }
    }

}