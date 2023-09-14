package com.home.torrent.widget

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.home.torrent.model.TorrentInfo
import com.thewind.widget.theme.LocalColors

/**
 * @author: read
 * @date: 2023/9/12 上午1:00
 * @description:
 */

@Composable
internal fun TorrentListView(
    modifier: Modifier = Modifier,
    list: List<TorrentInfo>,
    collectSet: Set<TorrentInfo>,
    onClick: (TorrentInfo) -> Unit,
    onCollect: (TorrentInfo, Boolean) -> Unit,
    onLoad: () -> Unit = {},
    bottomText: String? = null,
    loadFinished: Boolean = true
) {
    Box(modifier = modifier) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
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
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .align(Alignment.BottomCenter)
                )
                bottomText?.let {
                    Text(
                        text = bottomText,
                        fontSize = 16.sp,
                        color = Color.Red,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(vertical = 40.dp),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .align(Alignment.BottomCenter)
                )

                if (!loadFinished) {
                    LaunchedEffect(key1 = list.size) {
                        onLoad.invoke()
                    }
                }
            }
        }
    }
}