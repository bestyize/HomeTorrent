package com.home.torrent.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.home.baseapp.app.toast.toast
import com.home.torrent.R
import com.thewind.widget.theme.LocalColors


/**
 * @author : 亦泽
 * @date : 2023/9/14
 * @email : zhangrui10@bilibili.com
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CopyAddressDialog(address: String, onCopy: () -> Unit) {
    val copyBtnState = remember {
        mutableStateOf(false)
    }
    if (copyBtnState.value) {
        copyBtnState.value = false
        LocalClipboardManager.current.setText(
            AnnotatedString(
                address
            )
        )
        toast(stringResource(R.string.copy_success))
        onCopy.invoke()
    }
    AlertDialog(onDismissRequest = { onCopy.invoke() }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(LocalColors.current.Bg1, RoundedCornerShape(10.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = stringResource(R.string.notice),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = LocalColors.current.Text1,
                    modifier = Modifier.padding(vertical = 15.dp)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(1.dp)
                        .padding(vertical = 10.dp)
                        .background(LocalColors.current.Bg2)
                )
            }
            item {
                SelectionContainer {
                    Text(
                        text = address,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .wrapContentHeight(),
                        fontSize = 14.sp,
                        color = Color.Blue
                    )
                }
            }

            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(1.dp)
                        .padding(vertical = 10.dp)
                        .background(LocalColors.current.Bg2)
                )
                Text(text = stringResource(R.string.copy),
                    modifier = Modifier
                        .padding(vertical = 15.dp)
                        .fillMaxWidth(0.8f)
                        .wrapContentHeight()
                        .clickable(indication = null,
                            interactionSource = remember { MutableInteractionSource() }) {
                            copyBtnState.value = true
                        },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = LocalColors.current.Text1
                )
            }

        }
    }

}
