package com.home.torrent.common.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.home.torrent.ui.theme.LightGrayBackground

/**
 * @author: read
 * @date: 2023/8/20 下午10:35
 * @description:
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun CommonAlertDialog(
    title: String = "提示",
    content: String = "",
    okText: String? = "确定",
    cancelText: String? = "取消",
    onOk: () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = { onCancel.invoke() },
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .windowInsetsPadding(WindowInsets(0.dp)),
        properties = DialogProperties(
            usePlatformDefaultWidth = false, decorFitsSystemWindows = true
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = Color.White, shape = RoundedCornerShape(8.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(1.dp)
                    .background(LightGrayBackground)
            )

            Text(
                text = content,
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(1.dp)
                    .background(LightGrayBackground)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                cancelText?.let {
                    Text(text = "取消",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .weight(1f)
                            .wrapContentHeight()
                            .clickable(indication = null,
                                interactionSource = remember { MutableInteractionSource() }) {
                                onCancel.invoke()
                            })
                }

                okText?.let {
                    Spacer(
                        modifier = Modifier
                            .width(0.5.dp)
                            .background(LightGrayBackground)
                            .height(20.dp)
                    )
                    Text(text = "确定",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .weight(1f)
                            .wrapContentHeight()
                            .clickable(indication = null,
                                interactionSource = remember { MutableInteractionSource() }) {
                                onOk.invoke()
                            })
                }

            }
        }
    }
}