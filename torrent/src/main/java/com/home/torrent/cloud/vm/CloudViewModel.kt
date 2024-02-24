package com.home.torrent.cloud.vm

import androidx.lifecycle.ViewModel
import com.home.baseapp.app.toast.toast
import com.home.torrent.cloud.model.TorrentCloudPageData
import com.home.torrent.collect.model.TorrentInfoBean
import com.home.torrent.collect.service.TorrentCollectService
import com.home.torrent.widget.TorrentClickOption
import com.thewind.widget.ui.list.lazy.PageLoadState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

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
                    data.copy(
                        pageLoadState = PageLoadState.FINISH,
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

    fun openModifyDialog() {
        val data = _cloudPageState.value
        _cloudPageState.value = data.copy(
            showCopyDialog = false,
            showOptionDialog = false,
            editDialogUiState = data.editDialogUiState.copy(show = true)
        )

    }

    suspend fun modifyTorrentTitle(newTitle: String) {
        val data = _cloudPageState.value
        val hash = data.selectedTorrent?.hash ?: return
        if (newTitle == data.selectedTorrent.title) return
        val resp = TorrentCollectService.modifyTorrentName(hash, newTitle)
        toast(resp.message)
        _cloudPageState.value = data.copy(list = data.list.toMutableList().apply {
            if (resp.data) {
                forEachIndexed { index, itemData ->
                    if (itemData.hash == hash) {
                        this[index] = itemData.copy(title = newTitle)
                    }
                }
            }

        }, editDialogUiState = data.editDialogUiState.copy(show = false))
    }


    fun closeModifyDialog() {
        val data = _cloudPageState.value
        _cloudPageState.value =
            data.copy(editDialogUiState = data.editDialogUiState.copy(show = false))
    }

}