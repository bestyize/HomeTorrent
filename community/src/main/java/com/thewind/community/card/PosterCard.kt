package com.thewind.community.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thewind.community.recommend.model.RecommendComment
import com.thewind.community.recommend.model.RecommendPoster
import com.thewind.community.util.toDate
import com.thewind.widget.theme.LocalColors

/**
 * @author: read
 * @date: 2023/8/29 上午12:31
 * @description:
 */

@Composable
fun PosterCard(
    modifier: Modifier = Modifier,
    poster: RecommendPoster? = null,
    comments: List<RecommendComment> = emptyList(),
    onMenuClick: () -> Unit = {},
    onShare: () -> Unit = {},
    onComment: () -> Unit = {},
    onLike: () -> Unit = {},
    onHeaderClick: (RecommendComment) -> Unit = {},
    onCommentClick: (RecommendComment) -> Unit = {}
) {

    Column(modifier = modifier.background(LocalColors.current.Bg2)) {

        TitlePosterCard(
            title = poster?.userName ?: "",
            subTitle = poster?.date.toDate(),
            header = poster?.userHeader,
            content = poster?.content ?: "",
            onMenuClick = onMenuClick,
            onShare = onShare,
            onComment = onComment,
            onLike = onLike
        )
        Spacer(modifier = Modifier.height(10.dp))
        CommentCardContainer(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(LocalColors.current.Bg1),
            dataList = comments,
            onHeaderClick = onHeaderClick,
            onCommentClick = onCommentClick
        )
    }
}