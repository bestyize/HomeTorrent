package com.home.torrent.user.mine.page

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import coil.request.ImageRequest
import com.home.torrent.main.service.HomeAppConfigService
import com.home.torrent.setting.widget.SettingItemView
import com.home.torrent.user.login.page.LoginPage
import com.home.torrent.user.vm.UserViewModel
import com.thewind.account.bean.User
import com.thewind.resources.R
import com.thewind.utils.toDate
import com.thewind.widget.nav.LocalMainNavigation
import com.thewind.widget.theme.LocalColors
import com.thewind.widget.ui.CommonAlertDialog
import kotlinx.coroutines.launch

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
    val userVm = viewModel(
        modelClass = UserViewModel::class.java,
        viewModelStoreOwner = LocalContext.current as ComponentActivity
    )
    val minePageState by userVm.minePageState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    val router = LocalMainNavigation.current

    if (minePageState.showLogin) {
        BasicAlertDialog(
            onDismissRequest = {
                scope.launch {
                    userVm.closeLogin()
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets(0.dp)),
            properties = DialogProperties(
                usePlatformDefaultWidth = false, decorFitsSystemWindows = true
            )
        ) {
            LoginPage {
                scope.launch {
                    userVm.closeLogin()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalColors.current.Bg2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.statusBarsPadding())
        HeaderCard(user = minePageState.user, onLoginClick = {
            scope.launch {
                userVm.openLogin()
            }
        })
        Spacer(modifier = Modifier.height(20.dp))
        SettingItemView(title = stringResource(R.string.setting), icon = Icons.Default.Settings) {
            router.navigate("ht://setting")
        }

        if (minePageState.user != null) {

            if (minePageState.showLogout) {
                CommonAlertDialog(title = stringResource(R.string.notice),
                    content = stringResource(R.string.do_you_want_to_logout),
                    okText = stringResource(id = R.string.ok),
                    cancelText = stringResource(id = R.string.cancel),
                    onCancel = {
                        userVm.closeLogoutWaring()
                    },
                    onOk = {
                        userVm.logout()
                    })
            }
            Spacer(modifier = Modifier.height(20.dp))
            SettingItemView(
                title = stringResource(R.string.logout), icon = Icons.AutoMirrored.Filled.ExitToApp
            ) {
                userVm.showLogoutWaring()
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
                        title = title, icon = Icons.AutoMirrored.Filled.ArrowForward
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
            model = ImageRequest.Builder(LocalContext.current).data(user?.icon)
                .error(R.drawable.logo).build(),
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