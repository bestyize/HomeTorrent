package com.home.torrent.search.model

import androidx.compose.runtime.Immutable
import com.home.torrent.model.TorrentSource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
internal data class TorrentSearchPageState(
    val keyword: String = "",
    val source:ImmutableList<TorrentSource> = persistentListOf(),
    val tabs: ImmutableList<TorrentSearchTabState> = persistentListOf(),
    val dialogState: SearchPageDialogState = SearchPageDialogState()
)