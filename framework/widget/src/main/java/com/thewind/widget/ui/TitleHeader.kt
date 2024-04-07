package com.thewind.widget.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thewind.widget.theme.LocalColors

/**
 * @author: read
 * @date: 2023/8/29 上午12:51
 * @description:
 */

@Composable
@Preview
fun TitleHeader(
    title: String = "标题",
    color: Color = LocalColors.current.Text1,
    backgroundColor: Color = LocalColors.current.Bg1
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = backgroundColor)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = color,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(15.dp)
                .wrapContentSize()
                .align(Alignment.Center)
        )
    }
}