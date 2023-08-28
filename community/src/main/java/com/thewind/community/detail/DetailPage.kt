package com.thewind.community.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thewind.community.card.PosterCard
import com.thewind.widget.theme.LocalColors
import com.thewind.widget.ui.TitleHeader

/**
 * @author: read
 * @date: 2023/8/29 上午12:49
 * @description:
 */

@Composable
@Preview
fun DetailPage(modifier: Modifier = Modifier) {
    Column(modifier = modifier.background(LocalColors.current.Bg2)) {
        TitleHeader(color = LocalColors.current.Text1, backgroundColor = LocalColors.current.Bg1)
        Spacer(modifier = Modifier.height(5.dp))
        PosterCard()
    }

}