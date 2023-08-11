package com.home.torrent.torrent.page.collect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.home.torrent.model.TorrentInfo
import com.home.torrent.torrent.page.collect.vm.TorrentCollectViewModel
import com.home.torrent.torrent.page.widget.CopyAddressDialog
import com.home.torrent.torrent.page.widget.TorrentListView

@Composable
fun TorrentCollectPage() {
    val vm = viewModel(modelClass = TorrentCollectViewModel::class.java)
    val collectedTorrentState = vm.torrentState.collectAsStateWithLifecycle(emptyList())

    LaunchedEffect(key1 = "collect") {
        vm.loadAll()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White)
        ) {
            Text(
                text = "收藏",
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .align(Alignment.Center),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .background(Color.LightGray)
                    .align(
                        Alignment.BottomCenter
                    )
            )
        }

        val copyTorrent = remember {
            mutableStateOf<TorrentInfo?>(null)
        }

        copyTorrent.value?.let {
            CopyAddressDialog(it) {
                copyTorrent.value = null
            }
        }

        TorrentListView(dataListState = collectedTorrentState.value.toMutableList(),
            onClicked = { info ->
                copyTorrent.value = info
            },
            onCollectClicked = { info, collect ->
                if (collect) vm.collect(info) else vm.unCollect(info)
            })
    }


}