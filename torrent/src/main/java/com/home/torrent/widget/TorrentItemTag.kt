package com.home.torrent.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.thewind.widget.theme.LocalColors

/**
 * @author: read
 * @date: 2023/9/12 上午12:47
 * @description:
 */
@Composable
internal fun TorrentItemTag(title: String, data: String) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .wrapContentWidth()
    ) {
        Text(
            text = title,
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal,
            color = LocalColors.current.Text1,
            textAlign = TextAlign.Left,
            lineHeight = 11.sp
        )
        Text(
            text = data,
            fontSize = 11.sp,
            lineHeight = 11.sp,
            fontWeight = FontWeight.Normal,
            color = LocalColors.current.Text1,
            textAlign = TextAlign.Left
        )
    }
}
