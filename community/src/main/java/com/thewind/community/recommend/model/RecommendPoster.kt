package com.thewind.community.recommend.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * @author: read
 * @date: 2023/8/29 上午12:44
 * @description:
 */

@Keep
@Parcelize
data class RecommendPoster (
    val id: Long = 0,
    val date: Long = 0L,
    val content: String? = null,
    val title: String? = null,
    val likeCount: Long = 0,
    val level: Int = 0,
    val commentCount: Long = 0,
    val uid: Long = 0,
    val userName: String? = null,
    val userHeader: String? = null
): Parcelable