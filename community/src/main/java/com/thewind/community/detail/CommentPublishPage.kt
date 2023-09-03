package com.thewind.community.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thewind.community.R
import com.thewind.community.detail.vm.DetailPageViewModel
import com.thewind.community.recommend.page.PublishTitle
import com.thewind.widget.theme.LocalColors

/**
 * @author: read
 * @date: 2023/9/4 上午2:13
 * @description:
 */

@Preview
@Composable
fun CommentPublishPage(
    onClose: () -> Unit = {}, vm: DetailPageViewModel = viewModel(
        modelClass = DetailPageViewModel::class.java
    )
) {
    val posterId = vm.posterState.value?.id ?: -1L
    val content = remember {
        mutableStateOf("")
    }
    Dialog(
        onDismissRequest = { onClose.invoke() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(modifier = Modifier.fillMaxSize().background(color = LocalColors.current.Bg1)) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(LocalColors.current.Bg2)
            )
            PublishTitle(modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
                isEnable = content.value.isNotBlank(),
                onBack = {
                    onClose.invoke()
                },
                onPublish = {
                    vm.publishComment(posterId = posterId, content = content.value)
                    onClose.invoke()
                })
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