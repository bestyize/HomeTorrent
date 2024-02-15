package com.thewind.community.recommend.model

import androidx.compose.runtime.Immutable

@Immutable
data class RecommendPageData(
    val list:List<RecommendPoster> = emptyList(),
    val loadFinish: Boolean = false,
    val currentPage: Int = 0,
    val publishState: Boolean = false
)
