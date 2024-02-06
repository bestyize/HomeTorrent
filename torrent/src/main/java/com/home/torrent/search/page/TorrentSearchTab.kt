package com.home.torrent.search.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.home.torrent.model.TorrentInfo
import com.home.torrent.search.model.TorrentSearchTabState
import com.home.torrent.widget.TorrentListView
import com.thewind.widget.theme.LocalColors
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toImmutableList

/**
 * @author: read
 * @date: 2023/9/12 上午1:29
 * @description:
 */

@Composable
internal fun TorrentSearchTab(
    pageState: TorrentSearchTabState,
    onLoad: () -> Unit,
    collectSet: ImmutableSet<TorrentInfo>,
    onCollect: (TorrentInfo, Boolean) -> Unit,
    onClick: (TorrentInfo) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalColors.current.Bg1)
    ) {
        TorrentListView(
            list = pageState.dataList.toImmutableList(),
            collectSet = collectSet,
            onClick = onClick,
            onCollect = onCollect,
            onLoad = onLoad,
            bottomText = when {
                pageState.loaded && pageState.dataList.isEmpty() -> "暂无结果，点此刷新"
                pageState.loaded -> "已全部加载"
                else -> "加载中..."
            },
            loadFinished = pageState.loaded,
            onBottomTextClick = {
                if (pageState.dataList.isEmpty()) {
                    onLoad.invoke()
                }
            }
        )
    }
}
