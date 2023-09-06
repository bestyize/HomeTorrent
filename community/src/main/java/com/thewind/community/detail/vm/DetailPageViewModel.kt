package com.thewind.community.detail.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.baseapp.app.toast.toast
import com.thewind.community.detail.service.DetailPageService
import com.thewind.community.recommend.model.RecommendComment
import com.thewind.community.recommend.model.RecommendPoster
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * @author: read
 * @date: 2023/9/4 上午1:15
 * @description:
 */
class DetailPageViewModel : ViewModel() {

    val posterState: MutableStateFlow<RecommendPoster?> = MutableStateFlow(null)

    val commentState: MutableStateFlow<List<RecommendComment>> = MutableStateFlow(emptyList())

    fun loadPoster(id: Long) {
        viewModelScope.launch {
            posterState.value = DetailPageService.requestPosterDetail(id)
        }
    }

    fun setPoster(poster: RecommendPoster) {
        viewModelScope.launch {
            posterState.value = poster
        }
    }

    fun loadComments(posterId: Long?) {
        if (posterId == null || posterId == -1L) {
            return
        }
        viewModelScope.launch {
            commentState.value = DetailPageService.requestComments(posterId)
        }
    }

    fun publishComment(posterId: Long, content: String, parentId: Long = -1) {
        if (posterId == -1L) {
            return
        }
        viewModelScope.launch {
            val comment = DetailPageService.publishComment(
                posterId = posterId, content = content, parentId = parentId
            )
            if (comment == null) {
                toast("Error")
                return@launch
            }
            if (comment.parentId != -1L) {
                commentState.value = commentState.value.toMutableList().apply {
                    forEachIndexed { index, recommendComment ->
                        if (recommendComment.id == comment.parentId) {
                            recommendComment.copy(subCommentList = recommendComment.subCommentList?.toMutableList()
                                ?.apply {
                                    add(0, comment)
                                }).let {
                                this[index] = it
                            }
                        }

                    }

                }
            } else {
                commentState.value = commentState.value.toMutableList().apply {
                    add(0, comment)
                }
            }
        }
    }

    fun deleteComment(id: Long, parentId: Long = -1) {
        viewModelScope.launch {
            val success = DetailPageService.deleteComment(commentId = id)
            if (success) {
                commentState.value = commentState.value.toMutableList().apply {
                    if (parentId == -1L) {
                        removeIf { it.id == id }
                    } else {
                        forEachIndexed { index, recommendComment ->
                            if (recommendComment.id == parentId) {
                                recommendComment.copy(subCommentList = recommendComment.subCommentList?.toMutableList()
                                    ?.apply {
                                        removeIf { it.id == id }
                                    }).let {
                                    this[index] = it
                                }
                            }
                        }
                    }
                }
            }

        }
    }


}