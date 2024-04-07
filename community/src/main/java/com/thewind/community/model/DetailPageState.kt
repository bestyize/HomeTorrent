package com.thewind.community.model

import androidx.compose.runtime.Immutable
import com.thewind.community.recommend.model.RecommendComment
import com.thewind.community.recommend.model.RecommendPoster
import com.thewind.widget.ui.list.lazy.PageLoadState

@Immutable
data class DetailPageState(
    val poster: RecommendPoster? = null,
    val comments: List<RecommendComment> = emptyList(),
    val postId: Long = 0L,
    val loadState: PageLoadState = PageLoadState.INIT
)
