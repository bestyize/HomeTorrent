package com.thewind.community.recommend.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.baseapp.app.HomeApp
import com.home.baseapp.app.toast.toast
import com.thewind.account.AccountManager
import com.thewind.community.R
import com.thewind.community.recommend.model.RecommendPageData
import com.thewind.community.recommend.service.RecommendPageService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * @author: read
 * @date: 2023/8/31 上午1:12
 * @description:
 */
class RecommendPageViewModel : ViewModel() {

    private val _recommendPageState: MutableStateFlow<RecommendPageData> = MutableStateFlow(
        RecommendPageData()
    )

    val recommendPageData = _recommendPageState.asStateFlow()


    fun loadPoster(fullRefresh: Boolean) {
        viewModelScope.launch {
            var currentPage = _recommendPageState.value.currentPage
            if (fullRefresh) {
                currentPage = 0
            }
            val list = RecommendPageService.requestRecommendFeeds(page = currentPage)
            if (list.isEmpty()) {
                toast("load finish")
                _recommendPageState.value =
                    _recommendPageState.value.copy(currentPage = currentPage, loadFinish = true)
                return@launch
            }
            val newList = if (fullRefresh) {
                list
            } else {
                _recommendPageState.value.list.toMutableList().apply {
                    addAll(list)
                }
            }
            _recommendPageState.value = _recommendPageState.value.copy(
                currentPage = currentPage + 1,
                list = newList,
                loadFinish = true
            )

        }
    }

    fun publishPoster(title: String = "", content: String) {
        viewModelScope.launch {
            val poster = RecommendPageService.publishPoster(title, content)
            if (poster == null) {
                toast(HomeApp.context.getString(R.string.failed))
                return@launch
            }
            _recommendPageState.value =
                _recommendPageState.value.copy(list = _recommendPageState.value.list.toMutableList()
                    .apply {
                        add(0, poster)
                    })
        }
    }

    fun deletePoster(posterId: Long) {
        viewModelScope.launch {
            val success = RecommendPageService.deletePoster(posterId)
            if (success) {
                _recommendPageState.value = _recommendPageState.value.copy(
                    list = _recommendPageState.value.list.toMutableList().apply {
                        removeIf { it.id == posterId }
                    })
            } else {
                toast(HomeApp.context.getString(R.string.failed))
            }
        }
    }

    fun updatePublishPageState(open: Boolean) {
        val user = AccountManager.getUser() ?: return
        if (user.level > 0) {
            _recommendPageState.value = _recommendPageState.value.copy(publishState = open)
        } else {
            toast(HomeApp.context.getString(R.string.no_publish_permission))
        }

    }

}