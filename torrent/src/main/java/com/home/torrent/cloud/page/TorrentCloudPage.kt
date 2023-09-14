package com.home.torrent.cloud.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.home.torrent.R
import com.home.torrent.cloud.vm.CloudViewModel
import com.home.torrent.collect.model.TorrentInfoBean
import com.home.torrent.widget.CopyAddressDialog
import com.home.torrent.widget.TorrentClickOption
import com.home.torrent.widget.TorrentClickOptionDialog
import com.home.torrent.widget.TorrentItemTag
import com.home.torrentcenter.tool.toDate
import com.thewind.widget.theme.LocalColors
import com.thewind.widget.ui.TitleHeader

/**
 * @author: read
 * @date: 2023/8/21 上午12:44
 * @description:
 */

private val clickOptions = arrayOf(
    TorrentClickOption.GET_MAGNET_URL, TorrentClickOption.GET_TORRENT_URL, TorrentClickOption.CANCEL
)

@Composable
@Preview
fun TorrentCloudPage() {

    val vm = viewModel(modelClass = CloudViewModel::class.java)
    val dataList = vm.cloudCollectListState.collectAsStateWithLifecycle()


    val showOptionDialog = remember {
        mutableStateOf(false)
    }

    val showCopyDialog = remember {
        mutableStateOf(false)
    }

    val selectedTorrent = remember {
        mutableStateOf<TorrentInfoBean?>(null)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TitleHeader(title = stringResource(R.string.cloud_collect))
            CloudTorrentListView(dataList = dataList.value, onLoad = {
                vm.loadCloudCollectList()
            }, onUnCollect = { index, hash ->
                vm.unCollectFromCloud(index, hash)
            }, onItemClick = {
                selectedTorrent.value = it
                showOptionDialog.value = true
            })
        }
        Box(modifier = Modifier
            .padding(bottom = 80.dp, end = 15.dp)
            .align(Alignment.BottomEnd)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(100.dp))
            .background(
                color = LocalColors.current.Brand_pink, shape = RoundedCornerShape(100.dp)
            )
            .padding(10.dp)
            .clickable {
                vm.loadCloudCollectList(true)
            }) {
            Icon(
                Icons.Filled.Refresh,
                tint = LocalColors.current.Text_white,
                contentDescription = "",
                modifier = Modifier.size(24.dp)
            )
        }
    }

    val clickOptionType = remember {
        mutableStateOf(TorrentClickOption.GET_MAGNET_URL)
    }

    if (showOptionDialog.value) {
        TorrentClickOptionDialog(options = clickOptions, onClicked = {
            clickOptionType.value = it
            when (it) {
                TorrentClickOption.GET_MAGNET_URL -> {
                    showCopyDialog.value = true
                }

                TorrentClickOption.GET_TORRENT_URL -> {
                    showCopyDialog.value = true
                }

                else -> {}
            }
            showOptionDialog.value = false
        })
    }

    val address =
        if (clickOptionType.value == TorrentClickOption.GET_MAGNET_URL) selectedTorrent.value?.magnetUrl else selectedTorrent.value?.torrentUrl
    if (showCopyDialog.value) {
        CopyAddressDialog(address = address ?: "", onCopy = {
            showCopyDialog.value = false
        })
    }
}

@Composable
private fun CloudTorrentListView(
    dataList: List<TorrentInfoBean> = emptyList(),
    onLoad: () -> Unit = {},
    onUnCollect: (Int, String?) -> Unit = { _, _ -> },
    onItemClick: (TorrentInfoBean) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(LocalColors.current.Bg2)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(count = dataList.size, key = {
                "$it-${dataList[it].hash}"
            }) { index ->
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .background(LocalColors.current.Bg2)
                        .align(Alignment.BottomCenter)
                )
                CloudTorrentItemView(data = dataList[index], index = index, onClick = {
                    onItemClick.invoke(it)
                }, onDelete = {
                    onUnCollect.invoke(index, it)
                })
                if (index == dataList.size - 1) {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
            item {
                onLoad.invoke()
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview
private fun CloudTorrentItemView(
    data: TorrentInfoBean = TorrentInfoBean(),
    index: Int = 0,
    onClick: (TorrentInfoBean) -> Unit = {},
    onDelete: (String?) -> Unit = {}
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(LocalColors.current.Bg1)
        .clickable {
            onClick.invoke(data)
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
                        TorrentItemTag(title = "日期：", data = it)
                    }
                    data.size?.let {
                        Spacer(
                            modifier = Modifier
                                .width(10.dp)
                                .height(1.dp)
                        )
                        TorrentItemTag(title = "大小：", data = it)
                    }
                    data.collectDate.let {
                        Spacer(
                            modifier = Modifier
                                .width(10.dp)
                                .height(1.dp)
                        )
                        TorrentItemTag(title = "收藏日期：", data = it.toDate())
                    }
                }
            }
            Icon(imageVector = Icons.Default.Delete,
                contentDescription = "收藏",
                tint = Color.LightGray,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        onDelete.invoke(data.hash)
                    })
        }
    }

}