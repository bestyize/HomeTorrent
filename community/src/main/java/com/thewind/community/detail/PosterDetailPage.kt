package com.thewind.community.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.home.baseapp.app.HomeApp
import com.home.baseapp.app.toast.toast
import com.thewind.community.R
import com.thewind.community.card.PosterCard
import com.thewind.community.recommend.model.RecommendComment
import com.thewind.community.recommend.model.RecommendPoster
import com.thewind.widget.theme.LocalColors
import com.thewind.widget.ui.TitleHeader

/**
 * @author: read
 * @date: 2023/8/29 上午12:49
 * @description:
 */

@Composable
fun PosterDetailPage(
    modifier: Modifier = Modifier,
    poster: RecommendPoster,
    comments: List<RecommendComment>,
    onPublish: (String, Long) -> Unit
) {
    val openCommentState = remember {
        mutableStateOf(false)
    }

    val parentId = remember {
        mutableLongStateOf(-1L)
    }
    Column(modifier = modifier.background(LocalColors.current.Bg2)) {
        Spacer(modifier = Modifier.statusBarsPadding())
        TitleHeader(
            color = LocalColors.current.Text1,
            backgroundColor = LocalColors.current.Bg1,
            title = stringResource(
                R.string.detail
            )
        )
        Spacer(modifier = Modifier.height(5.dp))
        PosterCard(poster = poster, comments = comments, onMenuClick = {
            //openCommentState.value = true
        }, onShare = {}, onComment = {
            parentId.longValue = -1L
            openCommentState.value = true
        }, onLike = {}, onHeaderClick = {

        }, onCommentClick = { data ->
            openCommentState.value = true
            parentId.longValue = data.id
        })
    }

    if (openCommentState.value) {
        CommentPublishPage(onClose = {
            openCommentState.value = false
        }, onPublish = { data ->
            if (data.length < 10) {
                toast(HomeApp.context.resources.getString(R.string.word_less_than_10))
                return@CommentPublishPage
            }
            onPublish.invoke(data, parentId.longValue)
            openCommentState.value = false
        })
    }

}