package com.home.torrent.cloud.model

import com.home.torrent.collect.model.TorrentInfoBean
import com.home.torrent.widget.TorrentClickOption
import com.thewind.widget.ui.list.lazy.PageLoadState

internal data class TorrentCloudPageData(
    val list:List<TorrentInfoBean> = emptyList(),
    val page: Int = 1,
    val pageLoadState: PageLoadState = PageLoadState.INIT,
    val showOptionDialog: Boolean = false,
    val showCopyDialog: Boolean = false,
    val selectedTorrent: TorrentInfoBean? = null,
    val clickOption: TorrentClickOption = TorrentClickOption.GET_MAGNET_URL,
    val editDialogUiState: TorrentCloudEditDialogUiState = TorrentCloudEditDialogUiState()
)
