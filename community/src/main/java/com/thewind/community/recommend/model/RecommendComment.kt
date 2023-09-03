package com.thewind.community.recommend.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize


/**
 * @author: read
 * @date: 2023/8/28 下午11:29
 * @description:
 */

@Keep
@Parcelize
data class RecommendComment (
    val id: Long = 0,
    val content: String? = null,
    val date: Long = 0L,
    val posterId: Long = 0L,
    val likeCount: Int = 0,
    val uid: Long = 0,
    val userName: String? = null,
    val userHeader: String? = null,
    val parentId: Long = -1L,
    val isSubComment: Boolean = false,
    val subCommentList: List<RecommendComment>? = null
): Parcelable