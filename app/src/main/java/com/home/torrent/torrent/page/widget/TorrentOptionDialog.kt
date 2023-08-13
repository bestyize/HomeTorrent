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

/**
 * @author: read
 * @date: 2023/8/13 下午11:04
 * @description:
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TorrentClickOptionDialog(onClicked: (Int) -> Unit) {
    val options = listOf("获取磁力链接", "获取种子地址", "下载种子", "取消")

    ModalBottomSheet(onDismissRequest = {
        onClicked.invoke(options.indexOf("取消"))
    }, content = {
        options.forEachIndexed { index, title ->
            if (index == 0) {
                Spacer(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.8f)
                        .height(0.5.dp)
                        .background(Color.LightGray)
                )
            }
            Text(text = title,
                color = Color.Black,
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
                        onClicked.invoke(index)
                    })
            Spacer(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.8f)
                    .height(0.5.dp)
                    .background(if (index != options.size - 1) Color.LightGray else Color.Transparent)
            )

        }
    }, windowInsets = WindowInsets(0.dp), modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
    )
}