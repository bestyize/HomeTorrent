package com.home.torrent.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.home.torrent.R
import com.home.torrent.model.TorrentInfo
import com.thewind.widget.theme.LocalColors
import com.thewind.widget.ui.list.lazy.PageLoadAllCard
import com.thewind.widget.ui.list.lazy.PageLoadErrorCard
import com.thewind.widget.ui.list.lazy.PageLoadState
import com.thewind.widget.ui.list.lazy.PageLoadingCard
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet

/**
 * @author: read
 * @date: 2023/9/12 上午1:00
 * @description:
 */

@Composable
internal fun TorrentListView(
    list: ImmutableList<TorrentInfo>,
    collectSet: ImmutableSet<TorrentInfo>,
    pageLoadState: PageLoadState = PageLoadState.ALL_LOADED,
    onClick: (TorrentInfo) -> Unit,
    onCollect: (TorrentInfo, Boolean) -> Unit,
    onLoad: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalColors.current.Bg1)
    ) {
        LazyColumn(content = {
            items(count = list.size) { itemIndex ->
                val data = list[itemIndex]
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .background(LocalColors.current.Bg2)
                        .align(Alignment.BottomCenter)
                )
                TorrentItemView(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(LocalColors.current.Bg1),
                    itemIndex = itemIndex,
                    data = data,
                    isCollected = collectSet.find { it.src == data.src && it.detailUrl == data.detailUrl && it.date == data.date } != null,
                    onClick = onClick,
                    onCollect = onCollect)
            }

            item {
                when (pageLoadState) {
                    PageLoadState.INIT, PageLoadState.FINISH -> {
                        PageLoadingCard(loadingText = stringResource(id = R.string.loading))
                        LaunchedEffect(key1 = Unit, block = {
                            onLoad.invoke()
                        })
                    }

                    PageLoadState.ALL_LOADED -> {
                        PageLoadAllCard(text = stringResource(id = R.string.loaded_all))
                    }

                    PageLoadState.ERROR -> {
                        PageLoadErrorCard(text = stringResource(id = R.string.load_failed))
                    }
                }
            }
        })
    }
}