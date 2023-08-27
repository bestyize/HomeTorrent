package com.home.torrent.torrent.page.cloud.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.home.baseapp.app.toast.toast
import com.home.torrent.R
import com.home.torrent.common.widget.TitleHeader
import com.home.torrent.torrent.page.cloud.bean.TorrentInfoBean
import com.home.torrent.torrent.page.cloud.vm.CloudViewModel
import com.home.torrent.torrent.page.widget.TorrentClickOption
import com.home.torrent.torrent.page.widget.TorrentClickOptionDialog
import com.home.torrent.torrent.page.widget.TorrentItemTag
import com.home.torrent.util.toDate
import com.thewind.widget.theme.LocalColors

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
internal fun CloudPage() {

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

                TorrentClickOption.CANCEL -> {}

                else -> {}
            }
            showOptionDialog.value = false
        })
    }

    val address =
        if (clickOptionType.value == TorrentClickOption.GET_MAGNET_URL) selectedTorrent.value?.magnetUrl else selectedTorrent.value?.torrentUrl
    if (showCopyDialog.value) {

        CopyAddressDialog(address = address ?: "", onClose = {
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
                        .height(if (index == 0) 5.dp else 1.dp)
                        .fillMaxWidth(0.8f)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CopyAddressDialog(
    address: String = "", onClose: () -> Unit
) {
    val copyBtnState = remember {
        mutableStateOf(false)
    }
    if (copyBtnState.value) {
        copyBtnState.value = false
        LocalClipboardManager.current.setText(
            AnnotatedString(
                address
            )
        )
        toast("复制成功")
        onClose.invoke()
    }
    AlertDialog(onDismissRequest = { onClose.invoke() }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(LocalColors.current.Bg1, RoundedCornerShape(10.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "温馨提示",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = LocalColors.current.Text1,
                    modifier = Modifier.padding(vertical = 15.dp)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(1.dp)
                        .padding(vertical = 10.dp)
                        .background(LocalColors.current.Bg2)
                )
            }
            item {
                SelectionContainer {
                    Text(
                        text = address,
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
                        .background(LocalColors.current.Bg2)
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
                    color = LocalColors.current.Text1
                )
            }

        }
    }

}
