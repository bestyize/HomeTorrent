package com.home.torrent.torrent.page.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.home.baseapp.app.HomeApp
import com.home.torrent.R
import com.home.torrent.ui.theme.LocalColors

/**
 * @author: read
 * @date: 2023/8/13 下午11:04
 * @description:
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TorrentClickOptionDialog(
    onClicked: (TorrentClickOption) -> Unit,
    options: Array<TorrentClickOption> = TorrentClickOption.values()
) {

    ModalBottomSheet(
        onDismissRequest = {
            onClicked.invoke(TorrentClickOption.CANCEL)
        },
        content = {
            Spacer(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.8f)
                    .height(0.5.dp)
                    .background(LocalColors.current.Bg2)
            )
            options.forEach { option ->
                Text(text = option.value,
                    color = LocalColors.current.Text1,
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth(0.8f)
                        .wrapContentHeight()
                        .align(Alignment.CenterHorizontally)
                        .clickable(indication = null, interactionSource = remember {
                            MutableInteractionSource()
                        }) {
                            onClicked.invoke(option)
                        })
                Spacer(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.8f)
                        .height(0.5.dp)
                        .background(if (option != TorrentClickOption.CANCEL) LocalColors.current.Bg2 else Color.Transparent)
                )

            }
        },
        windowInsets = WindowInsets(0.dp),
        containerColor = LocalColors.current.Bg1,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    )
}

internal enum class TorrentClickOption(val value: String) {
    GET_MAGNET_URL(HomeApp.context.getString(R.string.get_magnet_url)), GET_TORRENT_URL(
        HomeApp.context.getString(
            R.string.get_torrent_url
        )
    ),
    COLLECT_CLOUD(HomeApp.context.getString(R.string.collect_to_cloud)), CANCEL(
        HomeApp.context.getString(R.string.cancel)
    )
}