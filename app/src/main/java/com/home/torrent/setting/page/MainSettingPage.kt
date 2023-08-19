package com.home.torrent.setting.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.home.torrent.setting.widget.SwitchSettingView
import com.home.torrent.ui.theme.LightGrayBackground
import com.home.torrent.user.login.page.LoginPage
import com.home.torrent.user.vm.UserViewModel
import com.tencent.mmkv.MMKV

/**
 * @author: read
 * @date: 2023/8/17 上午12:36
 * @description:
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MainSettingPage() {

    val userVm = viewModel(modelClass = UserViewModel::class.java)
    val loginUserState = userVm.loginState.collectAsStateWithLifecycle()
    val openLoginPage = remember {
        mutableStateOf(false)
    }

    if (!openLoginPage.value && loginUserState.value == null) {
        AlertDialog(
            onDismissRequest = { openLoginPage.value = true },
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets(0.dp)),
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = true
            )
        ) {
            LoginPage()
        }
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(
                "设置", maxLines = 1, overflow = TextOverflow.Ellipsis
            )
        }, navigationIcon = {

        }, actions = {

        })
    }, content = { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
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
                    title = if (onlineState.value) "在线搜索" else "离线搜索",
                    checked = onlineState.value
                ) {
                    MMKV.defaultMMKV().encode("torrent_api_use_local", !it)
                    onlineState.value = it
                }
            }
        }
    })
}




