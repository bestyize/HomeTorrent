package com.thewind.community.model

/**
 * @author: read
 * @date: 2023/8/28 下午11:29
 * @description:
 */
data class Comment(
    val id: String? = null,
    val content: String? = null,
    val date: Long = 0L,
    val posterId: String? = null,
    val uid: Long = 0,
    val userName: String? = null,
    val userHeader: String? = null,
    val parentId: String? = null,
    val subCommentList: List<Comment>? = null,
    val isSubComment: Boolean = false
)