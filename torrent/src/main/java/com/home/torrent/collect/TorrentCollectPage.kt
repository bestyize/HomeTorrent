package com.home.torrent.collect

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.home.torrent.R
import com.home.torrent.collect.vm.TorrentCollectViewModel
import com.home.torrent.widget.TorrentListView
import com.thewind.widget.theme.LocalColors
import com.thewind.widget.ui.TitleHeader

@Composable
fun TorrentCollectPage() {
    val vm = viewModel(modelClass = TorrentCollectViewModel::class.java)
    vm.init()
    val collectList = vm.torrentListState.collectAsStateWithLifecycle(emptyList())

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
                    .background(LocalColors.current.Bg2)
                    .align(
                        Alignment.BottomCenter
                    )
            )
        }

        TorrentListView(list = collectList.value,
            collectSet = collectList.value.toSet(),
            onClick = { data ->

            },
            onCollect = { data, collect ->
                if (collect) vm.collect(data) else vm.unCollect(data)

            })

    }


}