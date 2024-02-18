package com.home.torrent.search.page

import androidx.compose.runtime.Composable
import com.home.torrent.model.TorrentInfo
import com.home.torrent.search.model.TorrentSearchTabState
import com.home.torrent.widget.TorrentListView
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableList

/**
 * @author: read
 * @date: 2023/9/12 上午1:29
 * @description:
 */

@Composable
internal fun TorrentSearchTab(
    tabState: TorrentSearchTabState,
    onLoad: () -> Unit,
    collectSet: ImmutableSet<TorrentInfo>,
    onCollect: (TorrentInfo, Boolean) -> Unit,
    onClick: (TorrentInfo) -> Unit
) {
    TorrentListView(
        list = tabState.dataList.toImmutableList(),
        collectSet = collectSet,
        pageLoadState = tabState.loadState,
        onClick = onClick,
        onCollect = onCollect,
        onLoad = onLoad
    )
}
