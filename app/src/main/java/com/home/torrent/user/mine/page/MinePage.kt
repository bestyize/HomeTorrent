package com.home.torrent.user.mine.page

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.home.torrent.R
import com.home.torrent.main.service.HomeAppConfigService
import com.home.torrent.setting.page.SettingActivity
import com.home.torrent.setting.widget.SettingItemView
import com.home.torrent.user.login.page.LoginPage
import com.home.torrent.user.vm.UserViewModel
import com.thewind.account.AccountManager
import com.thewind.account.bean.User
import com.thewind.utils.toDate
import com.thewind.widget.theme.LocalColors
import com.thewind.widget.ui.CommonAlertDialog

/**
 * @author: read
 * @date: 2023/8/20 下午2:03
 * @description:
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MinePage() {
    val activity = LocalContext.current as Activity
    val userVm = viewModel(modelClass = UserViewModel::class.java)
    val loginUserState = userVm.loginState.collectAsStateWithLifecycle()
    val openLoginPage = remember {
        mutableStateOf(!AccountManager.isLogin())
    }

    if (openLoginPage.value && loginUserState.value == null) {
        AlertDialog(
            onDismissRequest = { openLoginPage.value = false },
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets(0.dp)),
            properties = DialogProperties(
                usePlatformDefaultWidth = false, decorFitsSystemWindows = true
            )
        ) {
            LoginPage {
                openLoginPage.value = false
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalColors.current.Bg2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val vm = viewModel(modelClass = UserViewModel::class.java)
        val userState = vm.loginState.collectAsStateWithLifecycle()
        Spacer(modifier = Modifier.statusBarsPadding())
        HeaderCard(user = userState.value, onLoginClick = {
            openLoginPage.value = true
        })
        Spacer(modifier = Modifier.height(20.dp))
        SettingItemView(title = stringResource(R.string.setting), icon = Icons.Default.Settings) {
            activity.startActivity(Intent(activity, SettingActivity::class.java))
        }

        if (userState.value != null) {
            val logoutWaringDialogOpenState = remember {
                mutableStateOf(false)
            }
            if (logoutWaringDialogOpenState.value) {
                CommonAlertDialog(content = stringResource(R.string.do_you_want_to_logout),
                    onCancel = {
                        logoutWaringDialogOpenState.value = false
                    },
                    onOk = {
                        logoutWaringDialogOpenState.value = false
                        userVm.logout()
                    })
            }
            Spacer(modifier = Modifier.height(20.dp))
            SettingItemView(
                title = stringResource(R.string.logout), icon = Icons.Default.ExitToApp
            ) {
                logoutWaringDialogOpenState.value = true
            }
        }
        val noticeList = remember {
            HomeAppConfigService.appConfig.noticeList
        }

        if (!noticeList.isNullOrEmpty()) {
            noticeList.filter { !it.title.isNullOrBlank() }.forEach { option ->

                option.title?.let { title ->
                    Spacer(modifier = Modifier.height(20.dp))
                    SettingItemView(
                        title = title, icon = Icons.Default.ArrowForward
                    ) {
                        option.actionUrl.takeIf { !it.isNullOrBlank() }?.let {
                            activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
                        }

                    }
                }

            }
        }


    }

}


@OptIn(ExperimentalLayoutApi::class)
@Composable
@Preview
fun HeaderCard(user: User? = null, onLoginClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(LocalColors.current.Bg1, RoundedCornerShape(5.dp))
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = user?.icon?.takeIf { it.isNotBlank() },
            placeholder = painterResource(id = R.drawable.logo),
            alignment = Alignment.Center,
            modifier = Modifier
                .padding(10.dp)
                .clip(RoundedCornerShape(1000.dp))
                .size(48.dp),
            contentDescription = null
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterStart)
            ) {
                Text(text = user?.userName.takeIf { it?.isNotBlank() == true }
                    ?: stringResource(R.string.not_login),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = LocalColors.current.Text1)
                Spacer(modifier = Modifier.height(5.dp))
                FlowRow(modifier = Modifier.wrapContentSize()) {
                    user?.uid?.let {
                        Text(
                            text = stringResource(R.string.uid, user.uid),
                            fontSize = 12.sp,
                            color = LocalColors.current.Text1
                        )
                    }

                    user?.registerTime.toDate().takeIf { it.isNotBlank() }?.let {
                        Spacer(
                            modifier = Modifier
                                .height(5.dp)
                                .width(10.dp)
                        )
                        Text(
                            text = stringResource(
                                R.string.register_date, user?.registerTime.toDate()
                            ), fontSize = 12.sp, color = LocalColors.current.Text1
                        )
                    }

                }


            }
            if (user == null) {
                Text(text = stringResource(R.string.login_or_register),
                    color = LocalColors.current.Text1,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .align(Alignment.CenterEnd)
                        .clickable {
                            onLoginClick.invoke()
                        })
            }
        }

    }
}