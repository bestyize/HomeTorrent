package com.thewind.widget.ui

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
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.thewind.widget.theme.LocalColors
import kotlinx.coroutines.launch


/**
 * @author: read
 * @date: 2024/2/25 上午12:11
 * @description:
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun CommonInputDialog(
    title: String = "Title",
    content: String = "很多内容呀",
    okText: String = "Ok",
    cancelText: String = "Cancel",
    onSubmit: (String) -> Unit = {},
    onCancel: () -> Unit = {}
) {


    val scope = rememberCoroutineScope()

    var contentText by remember {
        mutableStateOf(content)
    }

    BasicAlertDialog(
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
                .background(color = LocalColors.current.Bg1, shape = RoundedCornerShape(8.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = LocalColors.current.Text1,
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
                    .background(LocalColors.current.Bg2)
            )

            TextField(
                value = contentText,
                onValueChange = {
                    scope.launch {
                        contentText = it
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = LocalColors.current.Text1,
                    unfocusedTextColor = LocalColors.current.Text1,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = LocalColors.current.Bg3,
                    unfocusedContainerColor = LocalColors.current.Bg3,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )


            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(1.dp)
                    .background(LocalColors.current.Bg2)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                cancelText.let {
                    Text(text = cancelText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = LocalColors.current.Text1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .weight(1f)
                            .wrapContentHeight()
                            .clickable(indication = null,
                                interactionSource = remember { MutableInteractionSource() }) {
                                scope.launch {
                                    onCancel.invoke()
                                }
                            })
                }

                okText.let {
                    Spacer(
                        modifier = Modifier
                            .width(0.5.dp)
                            .background(LocalColors.current.Bg2)
                            .height(20.dp)
                    )
                    Text(text = okText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = LocalColors.current.Text1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                            .weight(1f)
                            .wrapContentHeight()
                            .clickable(indication = null,
                                interactionSource = remember { MutableInteractionSource() }) {
                                scope.launch {
                                    onSubmit.invoke(contentText)
                                }
                            })
                }

            }
        }
    }
}