package com.thewind.community.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thewind.community.detail.vm.DetailPageViewModel
import com.thewind.community.util.toDate
import com.thewind.widget.theme.LocalColors

/**
 * @author: read
 * @date: 2023/8/29 上午12:31
 * @description:
 */

@Composable
@Preview
fun PosterCard(
    modifier: Modifier = Modifier,
    vm: DetailPageViewModel = viewModel(modelClass = DetailPageViewModel::class.java),
    onMenuClick:() -> Unit = {}
) {

    val poster = vm.posterState.collectAsStateWithLifecycle().value
    val comments = vm.commentState.collectAsStateWithLifecycle()
    vm.loadComments(poster?.id)

    Column(modifier = modifier.background(LocalColors.current.Bg2)) {

        TitlePosterCard(title = poster?.userName ?: "",
            subTitle = poster?.date.toDate(),
            header = poster?.userHeader,
            content = poster?.content ?: "",
            onMenuClick = onMenuClick)
        Spacer(modifier = Modifier.height(10.dp))
        CommentCardContainer(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(LocalColors.current.Bg1),
            dataList = comments.value
        )
    }
}