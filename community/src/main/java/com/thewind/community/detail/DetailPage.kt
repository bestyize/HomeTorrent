package com.thewind.community.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thewind.community.card.PosterCard
import com.thewind.community.detail.vm.DetailPageViewModel
import com.thewind.widget.theme.LocalColors
import com.thewind.widget.ui.TitleHeader

/**
 * @author: read
 * @date: 2023/8/29 上午12:49
 * @description:
 */

@Composable
@Preview
fun DetailPage(
    modifier: Modifier = Modifier,
    vm: DetailPageViewModel = viewModel(modelClass = DetailPageViewModel::class.java)
) {
    val openCommentState = remember {
        mutableStateOf(false)
    }
    Column(modifier = modifier.background(LocalColors.current.Bg2)) {
        Spacer(modifier = Modifier.statusBarsPadding())
        TitleHeader(color = LocalColors.current.Text1, backgroundColor = LocalColors.current.Bg1)
        Spacer(modifier = Modifier.height(5.dp))
        PosterCard(vm = vm, onMenuClick = {
            openCommentState.value = true
        })
    }

    if (openCommentState.value) {
        CommentPublishPage(vm = vm, onClose = {
            openCommentState.value = false
        })
    }

}