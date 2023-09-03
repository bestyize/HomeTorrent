package com.thewind.community.recommend.page

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.home.baseapp.app.toast.toast
import com.thewind.community.R
import com.thewind.community.recommend.vm.RecommendPageViewModel
import com.thewind.widget.theme.LocalColors

/**
 * @author: read
 * @date: 2023/9/3 下午10:55
 * @description:
 */

@Preview
@Composable
fun PublishPage(onClose: () -> Unit = {}) {
    Dialog(
        onDismissRequest = { onClose.invoke() }, properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        val vm = viewModel(modelClass = RecommendPageViewModel::class.java)
        val content = remember {
            mutableStateOf("")
        }
        val title = remember {
            mutableStateOf("")
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(LocalColors.current.Bg1)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                PublishTitle(modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                    isEnable = content.value.isNotBlank(),
                    onBack = {
                        onClose.invoke()
                    },
                    onPublish = {
                        vm.publishPoster(title = title.value, content = content.value)
                        onClose.invoke()
                    })
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(LocalColors.current.Bg2)
                )
                TextField(
                    value = title.value,
                    onValueChange = {
                        title.value = it
                    },
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 16.sp, fontWeight = FontWeight.Bold
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = LocalColors.current.Text1,
                        unfocusedTextColor = LocalColors.current.Text1,
                        focusedContainerColor = LocalColors.current.Bg1,
                        unfocusedContainerColor = LocalColors.current.Bg1,
                        disabledContainerColor = LocalColors.current.Bg1,
                        unfocusedIndicatorColor = LocalColors.current.Bg1,
                        focusedIndicatorColor = LocalColors.current.Bg1
                    ),
                    placeholder = {
                        Text(text = stringResource(R.string.title_optional))
                    },
                    maxLines = 15,
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 60.dp)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(LocalColors.current.Bg2)
                )
                TextField(
                    value = content.value,
                    onValueChange = {
                        content.value = it
                    },
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 14.sp
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = LocalColors.current.Text1,
                        unfocusedTextColor = LocalColors.current.Text1,
                        focusedContainerColor = LocalColors.current.Bg1,
                        unfocusedContainerColor = LocalColors.current.Bg1,
                        disabledContainerColor = LocalColors.current.Bg1,
                        unfocusedIndicatorColor = LocalColors.current.Bg1,
                        focusedIndicatorColor = LocalColors.current.Bg1
                    ),
                    placeholder = {
                        Text(text = stringResource(R.string.content_require))
                    },
                    maxLines = 15,
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 240.dp)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(LocalColors.current.Bg2)
                )
            }
        }

    }

}

@Preview
@Composable
private fun PublishTitle(
    modifier: Modifier = Modifier,
    isEnable: Boolean = true,
    onBack: () -> Unit = {},
    onPublish: () -> Unit = {}
) {
    Box(
        modifier = modifier
    ) {
        Icon(imageVector = Icons.Default.ArrowBack,
            tint = LocalColors.current.Text1,
            contentDescription = "",
            modifier = Modifier
                .align(
                    Alignment.CenterStart
                )
                .clickable {
                    onBack.invoke()
                })

        Text(
            text = stringResource(R.string.publish_poster),
            color = LocalColors.current.Text1,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.Center)
        )
        val lessWord = stringResource(R.string.content_less_than_10_word)
        Text(text = stringResource(R.string.publish),
            color = LocalColors.current.Wh0,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(5.dp)
                .align(Alignment.CenterEnd)
                .background(LocalColors.current.Brand_pink, shape = RoundedCornerShape(5.dp))
                .padding(vertical = 4.dp, horizontal = 10.dp)
                .clickable {
                    if (!isEnable) {
                        toast(lessWord)
                    } else {
                        onPublish.invoke()
                    }
                })

    }
}

