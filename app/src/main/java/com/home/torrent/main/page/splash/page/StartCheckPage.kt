package com.home.torrent.main.page.splash.page

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.home.baseapp.app.HomeApp
import com.home.torrent.R
import com.home.torrent.main.model.HomeAppConfig
import com.home.torrent.main.service.HomeAppConfigService
import com.thewind.widget.ui.CommonAlertDialog
import kotlin.system.exitProcess

/**
 * @author: read
 * @date: 2023/8/22 下午11:33
 * @description:
 */
@Composable
internal fun StartCheckPage(onShow: () -> Unit = {}, onClose: () -> Unit = {}) {

    val appConfig = remember {
        mutableStateOf<HomeAppConfig?>(null)
    }

    val context = LocalContext.current

    appConfig.value?.let { config ->
        if (config.forbidden) {
            onShow.invoke()
            CommonAlertDialog(title = stringResource(R.string.warning),
                content = stringResource(R.string.not_valid_now),
                okText = stringResource(
                    id = R.string.ok,
                ),
                onOk = {
                    exitProcess(0)
                })
        } else if (config.newVersionCode > HomeApp.versionCode) {
            onShow.invoke()
            CommonAlertDialog(title = config.updateTitle,
                content = config.updateMessage,
                cancelText = if (config.updateForce) null else stringResource(R.string.cancel),
                okText = stringResource(
                    id = R.string.ok
                ),
                onOk = {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(config.updateUrl)))
                },
                onCancel = {
                    onClose.invoke()
                })
        }
    }

    LaunchedEffect(Unit) {
        appConfig.value = HomeAppConfigService.requestAppConfig()
    }
}