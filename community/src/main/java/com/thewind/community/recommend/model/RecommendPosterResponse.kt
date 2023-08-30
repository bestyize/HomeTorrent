package com.thewind.community.recommend.model

import androidx.annotation.Keep

@Keep
data class RecommendPosterResponse(
    val code: Int = 0,
    val message: String? = null,
    val data: List<RecommendPoster>? = null
)