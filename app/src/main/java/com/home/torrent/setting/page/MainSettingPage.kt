package com.home.torrent.setting.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.home.torrent.R
import com.home.torrent.common.widget.TitleHeader
import com.home.torrent.setting.widget.SwitchSettingView
import com.home.torrent.ui.theme.LightGrayBackground
import com.tencent.mmkv.MMKV

/**
 * @author: read
 * @date: 2023/8/17 上午12:36
 * @description:
 */

@Composable
@Preview
fun MainSettingPage() {

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TitleHeader(stringResource(R.string.setting))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .background(LightGrayBackground)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                    )
                    val onlineState = remember {
                        mutableStateOf(!MMKV.defaultMMKV().decodeBool("torrent_api_use_local"))
                    }
                    SwitchSettingView(
                        title = if (onlineState.value) stringResource(R.string.online_search) else stringResource(
                            R.string.offline_search
                        ),
                        checked = onlineState.value
                    ) {
                        MMKV.defaultMMKV().encode("torrent_api_use_local", !it)
                        onlineState.value = it
                    }
                }
            }
        }

    }
}




