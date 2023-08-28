package com.thewind.community.model

/**
 * @author: read
 * @date: 2023/8/29 上午12:44
 * @description:
 */
data class Poster(
    val posterId: String? = null,
    val date: Long = 0L,
    val likeCount: Long = 0,
    val uid: Long = 0,
    val userName: String? = null,
    val userHeader: String? = null,
)