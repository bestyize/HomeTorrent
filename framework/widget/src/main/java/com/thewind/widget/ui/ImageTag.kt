package com.thewind.widget.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thewind.widget.theme.LocalColors

/**
 * @author: read
 * @date: 2023/8/28 下午11:22
 * @description:
 */
@Composable
@Preview
fun ImageTag(
    modifier: Modifier = Modifier,
    title: String = "分享",
    icon: ImageVector = Icons.Default.Share
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon, contentDescription = "", modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = title, color = LocalColors.current.Text2, fontSize = 14.sp)
    }

}