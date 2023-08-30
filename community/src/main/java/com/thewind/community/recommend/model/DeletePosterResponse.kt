package com.thewind.community.recommend.model

import androidx.annotation.Keep

@Keep
data class DeletePosterResponse(
    val code: Int = 0,
    val message: String? = null)