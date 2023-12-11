package com.home.torrent.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.home.torrent.R
import com.home.torrent.model.TorrentInfo
import com.thewind.widget.theme.LocalColors

/**
 * @author: read
 * @date: 2023/9/12 上午12:37
 * @description:
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun TorrentItemView(
    modifier: Modifier = Modifier,
    itemIndex: Int,
    data: TorrentInfo,
    isCollected: Boolean,
    onClick: (TorrentInfo) -> Unit,
    onCollect: (TorrentInfo, Boolean) -> Unit
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable(interactionSource = remember {
                    MutableInteractionSource()
                }, indication = null, onClick = {
                    onClick.invoke(data)
                })
        ) {
            Text(
                text = "$itemIndex",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = LocalColors.current.Text1,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(10.dp)
                    .weight(1f),
                textAlign = TextAlign.Center,
            )
            Column(
                modifier = Modifier
                    .weight(5f)
                    .wrapContentHeight()
                    .padding(vertical = 10.dp)
            ) {
                Text(
                    text = data.title ?: "",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = LocalColors.current.Text1,
                    textAlign = TextAlign.Left,
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    data.date?.let {
                        TorrentItemTag(title = stringResource(R.string.date), data = it)
                    }
                    data.size?.let {
                        Spacer(
                            modifier = Modifier
                                .width(10.dp)
                                .height(1.dp)
                        )
                        TorrentItemTag(title = stringResource(R.string.size), data = it)
                    }
                    data.downloadCount?.let {
                        Spacer(
                            modifier = Modifier
                                .width(10.dp)
                                .height(1.dp)
                        )
                        TorrentItemTag(title = stringResource(R.string.download), data = it)
                    }
                    data.lastDownloadDate?.let {
                        Spacer(
                            modifier = Modifier
                                .width(10.dp)
                                .height(1.dp)
                        )
                        TorrentItemTag(title = stringResource(R.string.nearly), data = it)
                    }
                    data.leacherCount?.let {
                        Spacer(
                            modifier = Modifier
                                .width(10.dp)
                                .height(1.dp)
                        )
                        TorrentItemTag(title = stringResource(R.string.leacher), data = it)
                    }
                    data.senderCount?.let {
                        Spacer(
                            modifier = Modifier
                                .width(10.dp)
                                .height(1.dp)
                        )
                        TorrentItemTag(title = stringResource(R.string.sender), data = it)
                    }

                }
            }
            Icon(imageVector = if (isCollected) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = stringResource(id = R.string.collect),
                tint = if (isCollected) Color.Red else Color.LightGray,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .clickable(indication = null, interactionSource = remember {
                        MutableInteractionSource()
                    }) {
                        onCollect.invoke(data, !isCollected)
                    }
            )
        }
    }
}