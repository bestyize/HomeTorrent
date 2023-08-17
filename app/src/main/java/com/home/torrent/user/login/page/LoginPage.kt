package com.home.torrent.user.login.page

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.home.baseapp.app.toast.toast
import com.home.torrent.R
import com.home.torrent.ui.theme.BrandPink
import com.home.torrent.ui.theme.LightGrayBackground

/**
 * @author: read
 * @date: 2023/8/18 上午12:40
 * @description:
 */
@Composable
@Preview
fun LoginPage() {

    val isLogin = remember {
        mutableStateOf(false)
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
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                modifier = Modifier
                    .align(
                        Alignment.TopStart
                    )
                    .padding(20.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(0.6f)
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
                    .fillMaxWidth(0.6f)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(2.dp))
            OutlinedTextField(value = userName.value,
                onValueChange = {
                    userName.value = it
                },
                label = { Text(text = "用户名", color = BrandPink, fontWeight = FontWeight.Bold) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BrandPink,
                    focusedContainerColor = LightGrayBackground,
                    unfocusedContainerColor = LightGrayBackground
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(value = password.value,
                onValueChange = {
                    password.value = it
                },
                label = { Text(text = "密码", color = BrandPink, fontWeight = FontWeight.Bold) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BrandPink,
                    focusedContainerColor = LightGrayBackground,
                    unfocusedContainerColor = LightGrayBackground
                ),
                modifier = Modifier.fillMaxWidth()
            )

            if (!isLogin.value) {
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(value = email.value, onValueChange = {
                    email.value = it
                }, label = {
                    Text(
                        text = "邮箱", color = BrandPink, fontWeight = FontWeight.Bold
                    )
                }, colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BrandPink,
                    focusedContainerColor = LightGrayBackground,
                    unfocusedContainerColor = LightGrayBackground
                ), modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(value = verifyCode.value, onValueChange = {
                    verifyCode.value = it
                }, label = {
                    Text(
                        text = "验证码", color = BrandPink, fontWeight = FontWeight.Bold
                    )
                }, trailingIcon = {
                    Text(text = "发送", modifier = Modifier.clickable {
                        toast("已发送")
                    })
                }, colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BrandPink,
                    focusedContainerColor = LightGrayBackground,
                    unfocusedContainerColor = LightGrayBackground
                ), modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { /* Do something! */ },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = BrandPink)
            ) {
                Text(if (isLogin.value) "登录" else "注册")
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(if (isLogin.value) "切换注册" else "切换登陆",
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.End)
                    .clickable {
                        isLogin.value = !isLogin.value
                    })

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