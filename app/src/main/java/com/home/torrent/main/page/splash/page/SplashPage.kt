package com.home.torrent.main.page.splash.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.home.torrent.R
import com.home.torrent.common.widget.TitleHeader
import com.home.torrent.ui.theme.BrandPink
import com.home.torrent.ui.theme.TransportGray
import kotlinx.coroutines.delay

/**
 * @author: read
 * @date: 2023/8/22 上午12:45
 * @description:
 */

@Composable
@Preview
fun SplashPage(onClose: () -> Unit = {}) {

    val countDownState = remember {
        mutableStateOf(true)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val timeLeft = remember {
            mutableIntStateOf(3)
        }
        SkipButton(
            modifier = Modifier.align(Alignment.TopEnd),
            skipText = stringResource(R.string.skip) + " ${timeLeft.intValue}",
            onClose = {
                if (countDownState.value) {
                    onClose.invoke()
                }
            }
        )
        if (timeLeft.intValue == 0) {
            onClose.invoke()
        }
        LaunchedEffect(key1 = "count_down") {
            while (timeLeft.intValue > 0) {
                delay(1000)
                if (countDownState.value) {
                    timeLeft.intValue = timeLeft.intValue - 1
                }

            }
        }

        Column(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier.width(180.dp)
            )
            TitleHeader(stringResource(R.string.app_name), color = BrandPink, backgroundColor = Color.Transparent)
        }

    }

    StartCheckPage(onClose = {
        countDownState.value = true
    }, onShow = {
        countDownState.value = false
    })
}

@Composable
private fun SkipButton(
    modifier: Modifier = Modifier, skipText: String = stringResource(R.string.skip), onClose: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .padding(15.dp)
            .wrapContentSize()
            .background(color = TransportGray, shape = RoundedCornerShape(115.dp))
            .clickable(indication = null, interactionSource = remember {
                MutableInteractionSource()
            }, onClick = {
                onClose.invoke()
            })
    ) {
        Text(
            text = skipText,
            fontSize = 14.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
        )
    }
}