package com.thewind.widget.ui.list.lazy

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PageLoadAllCard() {
    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(), contentAlignment = Alignment.Center
    ) {
        Text(text = "已全部加载", color = Color.Black, fontSize = 18.sp)
    }
}