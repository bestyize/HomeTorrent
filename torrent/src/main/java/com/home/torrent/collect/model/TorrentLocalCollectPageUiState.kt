package com.home.torrent.collect.model

import com.home.torrent.model.TorrentInfo
import com.thewind.widget.ui.list.lazy.PageLoadState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class TorrentLocalCollectPageUiState(
    val torrentList: ImmutableList<TorrentInfo> = persistentListOf(),
    val dialogUiState: CollectPageDialogState = CollectPageDialogState(),
    val editDialogState: TorrentLocalCollectDialogUiState = TorrentLocalCollectDialogUiState(),
    val pageLoadState: PageLoadState = PageLoadState.INIT
)
