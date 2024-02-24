package com.home.torrent.collect.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.home.torrent.R
import com.home.torrent.collect.model.CollectPageDialogType
import com.home.torrent.collect.vm.TorrentCollectViewModel
import com.home.torrent.widget.CopyAddressDialog
import com.home.torrent.widget.TorrentClickOption
import com.home.torrent.widget.TorrentClickOptionDialog
import com.home.torrent.widget.TorrentListView
import com.thewind.widget.theme.LocalColors
import com.thewind.widget.ui.TitleHeader
import com.thewind.widget.ui.list.lazy.PageLoadState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableSet

@Composable
fun TorrentCollectPage() {
    val vm = viewModel(modelClass = TorrentCollectViewModel::class.java)
    val collectList by vm.torrentListState.collectAsStateWithLifecycle(emptyList())


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

        TorrentListView(
            list = collectList.toImmutableList(),
            collectSet = collectList.toImmutableSet(),
            onClick = { data ->
                vm.handleTorrentInfoClick(data)
            },
            onCollect = { data, collect ->
                if (collect) vm.collect(data) else vm.unCollect(data)
            }
        )
    }

    TorrentCollectPageDialog()
}

@Composable
private fun TorrentCollectPageDialog(vm: TorrentCollectViewModel = viewModel(modelClass = TorrentCollectViewModel::class.java)) {
    val dialogState by vm.dialogState.collectAsStateWithLifecycle()
    if (dialogState.type != CollectPageDialogType.NONE) {
        when (dialogState.type) {
            CollectPageDialogType.OPTION -> TorrentClickOptionDialog(onClicked = {
                when (it) {
                    TorrentClickOption.GET_MAGNET_URL -> vm.updateDialogState(dialogState.data)
                    TorrentClickOption.GET_TORRENT_URL -> vm.updateDialogState(dialogState.data, false)
                    TorrentClickOption.COLLECT_CLOUD -> vm.collectToCloud(dialogState.data)
                    else -> vm.updateDialogState(null)
                }
            })

            CollectPageDialogType.ADDRESS -> CopyAddressDialog(
                address = (if (dialogState.isMagnet) dialogState.data?.magnetUrl else dialogState.data?.torrentUrl)
                    ?: ""
            ) {
                vm.updateDialogState(null)
            }

            else -> {}
        }
    }


}