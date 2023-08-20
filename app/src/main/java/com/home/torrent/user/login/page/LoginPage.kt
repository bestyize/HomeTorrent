package com.home.torrent.user.login.page

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.home.torrent.R
import com.home.torrent.ui.theme.BrandPink
import com.home.torrent.ui.theme.LightGrayBackground
import com.home.torrent.user.vm.UserViewModel
import com.home.torrent.util.toIntOrDefault

/**
 * @author: read
 * @date: 2023/8/18 上午12:40
 * @description:
 */

@Composable
@Preview
fun LoginPage(onClose: () -> Unit = {}) {

    val loginVm = viewModel(modelClass = UserViewModel::class.java)

    val pageState = remember {
        mutableStateOf(LoginPageAction.REGISTER)
    }

    val userName = remember {
        mutableStateOf("")
    }

    val password = remember {
        mutableStateOf("")
    }

    val email = remember {
        mutableStateOf("")
    }

    val verifyCode = remember {
        mutableStateOf("")
    }

    val maxWidthScale = when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> 0.5f
        Configuration.ORIENTATION_PORTRAIT -> 0.8f
        else -> 0.8f
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Icon(imageVector = Icons.Default.Close,
                contentDescription = "Close",
                modifier = Modifier
                    .align(
                        Alignment.CenterEnd
                    )
                    .padding(20.dp)
                    .clickable {
                        onClose.invoke()
                    })
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(maxWidthScale)
                .align(Alignment.Center)
                .wrapContentHeight()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "彩虹磁力",
                fontWeight = FontWeight.Bold,
                color = BrandPink,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth(maxWidthScale / 2)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(2.dp))
            if (pageState.value == LoginPageAction.LOGIN || pageState.value == LoginPageAction.REGISTER) {
                OutlinedTextField(value = userName.value, onValueChange = {
                    userName.value = it
                }, label = {
                    Text(
                        text = "用户名", color = BrandPink, fontWeight = FontWeight.Bold
                    )
                }, colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BrandPink,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = LightGrayBackground,
                    unfocusedContainerColor = LightGrayBackground
                ), modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(value = password.value, onValueChange = {
                password.value = it
            }, label = {
                Text(
                    text = if (pageState.value == LoginPageAction.MODIFY_PASSWORD) "新密码" else "密码",
                    color = BrandPink,
                    fontWeight = FontWeight.Bold
                )
            }, colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = BrandPink,
                focusedContainerColor = LightGrayBackground,
                unfocusedContainerColor = LightGrayBackground
            ), modifier = Modifier.fillMaxWidth()
            )

            if (pageState.value == LoginPageAction.REGISTER || pageState.value == LoginPageAction.MODIFY_PASSWORD) {
                Spacer(modifier = Modifier.height(15.dp))
                OutlinedTextField(value = email.value, onValueChange = {
                    email.value = it
                }, label = {
                    Text(
                        text = "邮箱", color = BrandPink, fontWeight = FontWeight.Bold
                    )
                }, colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = BrandPink,
                    focusedContainerColor = LightGrayBackground,
                    unfocusedContainerColor = LightGrayBackground
                ), modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(15.dp))
                OutlinedTextField(value = verifyCode.value, onValueChange = {
                    verifyCode.value = it
                }, label = {
                    Text(
                        text = "验证码", color = BrandPink, fontWeight = FontWeight.Bold
                    )
                }, trailingIcon = {
                    Text(text = "发送",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .clickable {
                                loginVm.sendVerifyCode(email.value)
                            })
                }, colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = BrandPink,
                    focusedContainerColor = LightGrayBackground,
                    unfocusedContainerColor = LightGrayBackground
                ), modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    when (pageState.value) {
                        LoginPageAction.LOGIN -> {
                            loginVm.login(
                                userName = userName.value,
                                email = email.value,
                                password = password.value
                            )
                        }

                        LoginPageAction.REGISTER -> {
                            loginVm.register(
                                userName = userName.value,
                                email = email.value,
                                password = password.value,
                                verifyCode = verifyCode.value.toIntOrDefault(0)
                            )
                        }

                        LoginPageAction.MODIFY_PASSWORD -> {
                            loginVm.modifyPassword(
                                email = email.value,
                                verifyCode = verifyCode.value.toIntOrDefault(0),
                                newPassword = password.value
                            )
                        }
                    }
                },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandPink)
            ) {
                val txt = when (pageState.value) {
                    LoginPageAction.REGISTER -> "注册"
                    LoginPageAction.LOGIN -> "登录"
                    LoginPageAction.MODIFY_PASSWORD -> "修改/找回密码"
                }
                Text(text = txt)
            }

            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                if (pageState.value == LoginPageAction.LOGIN || pageState.value == LoginPageAction.REGISTER) {
                    Text(if (pageState.value == LoginPageAction.REGISTER) "切换注册" else "切换登陆",
                        modifier = Modifier
                            .wrapContentWidth()
                            .align(Alignment.CenterEnd)
                            .clickable {
                                if (pageState.value == LoginPageAction.REGISTER) {
                                    pageState.value = LoginPageAction.LOGIN
                                } else {
                                    pageState.value = LoginPageAction.REGISTER
                                }
                            })
                }

                Text(text = "找回/修改密码",
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.CenterStart)
                        .clickable {
                            pageState.value = LoginPageAction.MODIFY_PASSWORD
                        })


            }
        }

        Text(
            text = "登录后可解锁更多功能",
            color = Color.Gray,
            modifier = Modifier
                .padding(bottom = 20.dp)
                .align(Alignment.BottomCenter)
        )
    }
}


private enum class LoginPageAction(val value: Int) {
    LOGIN(0), REGISTER(1), MODIFY_PASSWORD(2)
}
