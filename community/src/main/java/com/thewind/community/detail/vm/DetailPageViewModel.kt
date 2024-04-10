package com.thewind.community.detail.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.baseapp.app.toast.toast
import com.thewind.community.detail.service.DetailPageService
import com.thewind.community.model.DetailPageState
import com.thewind.community.recommend.model.RecommendComment
import com.thewind.community.recommend.model.RecommendPoster
import com.thewind.widget.ui.list.lazy.PageLoadState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * @author: read
 * @date: 2023/9/4 上午1:15
 * @description:
 */
class DetailPageViewModel : ViewModel() {

    private val _detailPageState: MutableStateFlow<DetailPageState> = MutableStateFlow(
        DetailPageState()
    )

    val detailPageState = _detailPageState.asStateFlow()

    suspend fun loadPoster(id: Long) {
        val poster = DetailPageService.requestPosterDetail(id)
        _detailPageState.value = _detailPageState.value.copy(
            postId = id,
            poster = poster ?: _detailPageState.value.poster,
            loadState = if (poster == null) PageLoadState.ERROR else PageLoadState.FINISH
        )
    }

    fun setPoster(poster: RecommendPoster) {
        _detailPageState.value = _detailPageState.value.copy(postId = poster.id, poster = poster, loadState = PageLoadState.FINISH)
    }

    suspend fun loadComments(posterId: Long?) {
        if (posterId == null || posterId == -1L) {
            return
        }
        _detailPageState.value =
            _detailPageState.value.copy(comments = DetailPageService.requestComments(posterId))
    }

    suspend fun publishComment(posterId: Long, content: String, parentId: Long = -1) {
        if (posterId == -1L) {
            return
        }
        val comment = DetailPageService.publishComment(
            posterId = posterId, content = content, parentId = parentId
        )
        if (comment == null) {
            toast("Error")
            return
        }
        val comments = _detailPageState.value.comments
        _detailPageState.value =
            _detailPageState.value.copy(comments = if (comment.parentId != -1L) {
                comments.toMutableList().apply {
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
                comments.toMutableList().apply {
                    add(0, comment)
                }
            })
    }

    fun deleteComment(id: Long, parentId: Long = -1) {
        viewModelScope.launch {
            val success = DetailPageService.deleteComment(commentId = id)
            if (success) {
                val comments = _detailPageState.value.comments
                _detailPageState.value =
                    _detailPageState.value.copy(comments = comments.toMutableList().apply {
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
                    })
            }

        }
    }


}