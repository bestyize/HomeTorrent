package com.thewind.community.recommend.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.baseapp.app.toast.toast
import com.thewind.community.recommend.model.RecommendPoster
import com.thewind.community.recommend.service.RecommendPageService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * @author: read
 * @date: 2023/8/31 上午1:12
 * @description:
 */
class RecommendPageViewModel : ViewModel() {

    val posterListState: MutableStateFlow<List<RecommendPoster>> = MutableStateFlow(emptyList())

    val loadFinishState: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private var currentPage = 0


    fun loadPoster(fullRefresh: Boolean) {
        viewModelScope.launch {
            if (fullRefresh) {
                currentPage = 0
                loadFinishState.value = true
            }
            val list = RecommendPageService.requestRecommendFeeds(page = currentPage)
            if (list.isEmpty()) {
                toast("load finish")
                loadFinishState.value = true
                return@launch
            }
            posterListState.value = if (fullRefresh) {
                list
            } else {
                posterListState.value.toMutableList().apply {
                    addAll(list)
                }
            }
            currentPage++

        }
    }

    fun publishPoster(title: String = "", content: String) {
        viewModelScope.launch {
            val poster = RecommendPageService.publishPoster(title, content)
            if (poster == null) {
                toast("failed")
                return@launch
            }
            posterListState.value = posterListState.value.toMutableList().apply {
                add(0, poster)
            }
        }
    }

    suspend fun deletePoster(posterId: Long) = RecommendPageService.deletePoster(posterId)


    suspend fun loadComment(posterId: Long) = RecommendPageService.requestComments(posterId)


    suspend fun publishComment(posterId: Long, content: String, parentId: Long = -1) =
        RecommendPageService.publishComment(posterId, content, parentId)

    suspend fun deleteComment(commentId: Long) = RecommendPageService.deleteComment(commentId)

}