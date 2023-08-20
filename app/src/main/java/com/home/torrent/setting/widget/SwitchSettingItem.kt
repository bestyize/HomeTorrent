package com.home.torrent.setting.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.home.torrent.ui.theme.BrandRed

/**
 * @author: read
 * @date: 2023/8/17 上午12:54
 * @description:
 */

@Composable
fun SwitchSettingView(title: String, checked: Boolean, onClick: (Boolean) -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight()
                .wrapContentWidth()
                .align(Alignment.CenterStart)
        )
        Switch(
            checked = checked,
            onCheckedChange = {
                onClick.invoke(it)
            },
            colors = SwitchDefaults.colors(
                uncheckedBorderColor = Color.Transparent,
                uncheckedThumbColor = Color.White,
                checkedBorderColor = Color.Transparent,
                checkedThumbColor = Color.White,
                checkedTrackColor = BrandRed,
                checkedIconColor = Color.Transparent
            ),
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .align(Alignment.CenterEnd)
        )
    }
}