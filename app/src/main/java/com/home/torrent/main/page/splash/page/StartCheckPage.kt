package com.home.torrent.main.page.splash.page

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.home.baseapp.app.HomeApp
import com.home.torrent.common.widget.CommonAlertDialog
import com.home.torrent.main.model.HomeAppConfig
import com.home.torrent.main.page.splash.service.SplashService
import kotlin.system.exitProcess

/**
 * @author: read
 * @date: 2023/8/22 下午11:33
 * @description:
 */
@Composable
fun StartCheckPage(onShow: () -> Unit = {}, onClose: () -> Unit = {}) {

    val appConfig = remember {
        mutableStateOf<HomeAppConfig?>(null)
    }

    val context = LocalContext.current

    appConfig.value?.let { config ->
        if (config.forbidden) {
            onShow.invoke()
            CommonAlertDialog(title = "温馨提示", content = "软件暂不可用", onOk = {
                exitProcess(0)
            }, cancelText = null)
        } else if (config.newVersionCode > HomeApp.versionCode) {
            onShow.invoke()
            CommonAlertDialog(title = config.updateTitle,
                content = config.updateMessage,
                cancelText = if (config.updateForce) null else "取消",
                onOk = {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(config.updateUrl)))
                },
                onCancel = {
                    onClose.invoke()
                })
        }
    }

    LaunchedEffect(key1 = "config") {
        appConfig.value = SplashService.requestAppConfig()
    }
}