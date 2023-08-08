package com.home.torrent.torrent.page.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.home.baseapp.app.toast.toast
import com.home.torrent.model.TorrentInfo
import com.home.torrent.torrent.page.collect.vm.TorrentCollectViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CopyAddressDialog(copyTorrent: TorrentInfo, onCopy: () -> Unit) {
    val copyBtnState = remember {
        mutableStateOf(false)
    }
    if (copyBtnState.value) {
        copyBtnState.value = false
        LocalClipboardManager.current.setText(
            AnnotatedString(
                copyTorrent.magnetUrl ?: ""
            )
        )
        toast("复制成功")
        onCopy.invoke()
    }
    AlertDialog(onDismissRequest = { onCopy.invoke() }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White, RoundedCornerShape(10.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "温馨提示",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 15.dp)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(1.dp)
                        .padding(vertical = 10.dp)
                        .background(Color.LightGray)
                )
            }
            item {
                SelectionContainer {
                    Text(
                        text = copyTorrent.magnetUrl ?: "",
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .wrapContentHeight(),
                        fontSize = 14.sp,
                        color = Color.Blue
                    )
                }
            }

            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(1.dp)
                        .padding(vertical = 10.dp)
                        .background(Color.Gray)
                )
                Text(text = "复制",
                    modifier = Modifier
                        .padding(vertical = 15.dp)
                        .fillMaxWidth(0.8f)
                        .wrapContentHeight()
                        .clickable(indication = null,
                            interactionSource = remember { MutableInteractionSource() }) {
                            copyBtnState.value = true
                        },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            }

        }
    }

}


@Composable
fun TorrentSearchItemView(
    index: Int,
    data: TorrentInfo,
    onClicked: (data: TorrentInfo) -> Unit = {},
    onCollectClicked: (data: TorrentInfo, collect: Boolean) -> Unit = { _, _ -> }
) {
    val collectVm = viewModel(modelClass = TorrentCollectViewModel::class.java)


    val isCollectedState = remember {
        mutableStateOf(false)
    }
    LaunchedEffect("${data.hashCode()}") {
        isCollectedState.value =
            collectVm.collectedTorrent.value.find { it.src == data.src && it.detailUrl == data.detailUrl && it.date == data.date } != null
    }

    val isCollected = isCollectedState.value

    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(Color.White)
        .clickable {
            onClicked.invoke(data)
        }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.Center)
        ) {
            Text(
                text = "$index",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
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
                    color = Color.Black,
                    textAlign = TextAlign.Left,
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    data.date?.let { date ->
                        Text(
                            text = "日期：",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Left,
                            lineHeight = 11.sp
                        )
                        Text(
                            text = date,
                            fontSize = 11.sp,
                            lineHeight = 11.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Left
                        )
                    }
                    data.size?.let { size ->
                        Spacer(modifier = Modifier
                            .width(10.dp)
                            .height(1.dp))
                        Text(
                            text = "文件大小：",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Left,
                            lineHeight = 11.sp
                        )
                        Text(
                            text = size,
                            fontSize = 11.sp,
                            lineHeight = 11.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Left
                        )
                    }

                }
            }
            Icon(imageVector = if (isCollected) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "收藏",
                tint = if (isCollected) Color.Red else Color.LightGray,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        onCollectClicked.invoke(data, !isCollected)
                        isCollectedState.value = !isCollected
                    })

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .padding(start = 20.dp, end = 20.dp)
                .background(Color.LightGray)
                .align(Alignment.BottomCenter)
        )
    }

}

@Composable
fun TorrentListView(
    dataListState: MutableList<TorrentInfo>,
    bottomText: String? = null,
    onLoad: () -> Unit = {},
    onClicked: (data: TorrentInfo) -> Unit = {},
    onCollectClicked: (data: TorrentInfo, collect: Boolean) -> Unit = { _, _ -> }
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            items(count = dataListState.size) { pos ->
                TorrentSearchItemView(
                    index = pos,
                    data = dataListState[pos],
                    onClicked = onClicked,
                    onCollectClicked = onCollectClicked
                )
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    bottomText?.let {

                    }
                    if (dataListState.isNotEmpty() && !bottomText.isNullOrBlank()) {
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
                            .height(80.dp)
                    )
                }
                onLoad.invoke()
            }
        }
    }
}