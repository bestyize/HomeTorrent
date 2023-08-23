package com.home.torrent.torrent.page.collect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.home.torrent.R
import com.home.torrent.common.widget.TitleHeader
import com.home.torrent.model.TorrentInfo
import com.home.torrent.torrent.page.cloud.vm.CloudViewModel
import com.home.torrent.torrent.page.collect.vm.TorrentCollectViewModel
import com.home.torrent.torrent.page.widget.CopyAddressDialog
import com.home.torrent.torrent.page.widget.TorrentClickOption
import com.home.torrent.torrent.page.widget.TorrentClickOptionDialog
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
            TitleHeader(title = stringResource(R.string.local_collect))
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

        val optionMagnet = remember {
            mutableStateOf(true)
        }

        copyTorrent.value?.let {
            CopyAddressDialog(it, optionMagnet.value) {
                copyTorrent.value = null
                optionMagnet.value = true
            }
        }

        val optionShowState = remember {
            mutableStateListOf<Any?>(false, null)
        }

        if (optionShowState[0] as? Boolean == true) {
            val cloudVm = viewModel(modelClass = CloudViewModel::class.java)
            TorrentClickOptionDialog(onClicked = {
                when (it) {
                    TorrentClickOption.GET_MAGNET_URL -> {
                        optionMagnet.value = true
                        copyTorrent.value = optionShowState[1] as? TorrentInfo
                    }

                    TorrentClickOption.GET_TORRENT_URL -> {
                        optionMagnet.value = false
                        copyTorrent.value = optionShowState[1] as? TorrentInfo
                    }

                    TorrentClickOption.COLLECT_CLOUD -> {
                        vm.collectToCloud(optionShowState[1] as? TorrentInfo)
                        cloudVm.loadCloudCollectList(true)
                    }

                    else -> {

                    }
                }
                optionShowState[1] = null
                optionShowState[0] = false
            })
        }

        TorrentListView(dataListState = collectedTorrentState.value.toMutableList(),
            onClicked = { info ->
                optionShowState[1] = info
                optionShowState[0] = true
            },
            onCollectClicked = { info, collect ->
                if (collect) vm.collect(info) else vm.unCollect(info)
            })
    }


}