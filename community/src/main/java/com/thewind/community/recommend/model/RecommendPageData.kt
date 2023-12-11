package com.thewind.community.recommend.model

data class RecommendPageData(
    val list:List<RecommendPoster> = emptyList(),
    val loadFinish: Boolean = false,
    val currentPage: Int = 0,
    val publishState: Boolean = false
)
