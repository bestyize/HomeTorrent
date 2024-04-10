package com.thewind.community.detail

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.home.baseapp.app.HomeApp
import com.home.baseapp.app.toast.toast
import com.thewind.community.card.PosterCard
import com.thewind.community.detail.vm.DetailPageViewModel
import com.thewind.community.recommend.model.RecommendPoster
import com.thewind.resources.R
import com.thewind.widget.theme.LocalColors
import com.thewind.widget.ui.TitleHeader
import com.thewind.widget.ui.list.lazy.PageLoadState
import com.thewind.widget.ui.list.lazy.PageLoadingCard
import kotlinx.coroutines.launch

/**
 * @author: read
 * @date: 2023/8/29 上午12:49
 * @description:
 */

@Composable
@Preview
fun PosterDetailPage(
    posterId: Long = 0L
) {

    val vm = viewModel(
        modelClass = DetailPageViewModel::class.java,
        viewModelStoreOwner = LocalContext.current as ComponentActivity
    )
    var openCommentState by remember {
        mutableStateOf(false)
    }

    var parentId by remember {
        mutableLongStateOf(-1L)
    }
    val detailPageState by vm.detailPageState.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .background(LocalColors.current.Bg1)
            .statusBarsPadding()
    ) {
        TitleHeader(
            color = LocalColors.current.Text1,
            backgroundColor = LocalColors.current.Bg1,
            title = stringResource(
                R.string.detail
            )
        )
        Spacer(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(color = LocalColors.current.Bg2)
        )

        when (detailPageState.loadState) {
            PageLoadState.INIT -> PageLoadingCard(loadingText = stringResource(id = R.string.loading))
            PageLoadState.ERROR,
            PageLoadState.ALL_LOADED, PageLoadState.FINISH -> PosterCard(
                poster = detailPageState.poster,
                comments = detailPageState.comments,
                onMenuClick = {
                    //openCommentState.value = true
                },
                onShare = {},
                onComment = {
                    parentId = -1L
                    openCommentState = true
                },
                onLike = {},
                onHeaderClick = {

                },
                onCommentClick = { data ->
                    openCommentState = true
                    parentId = data.id
                })

            else -> Box(modifier = Modifier.size(0.dp))
        }

    }

    if (openCommentState) {
        CommentPublishPage(onClose = {
            openCommentState = false
        }, onPublish = { data ->
            if (data.length < 10) {
                toast(HomeApp.context.resources.getString(R.string.word_less_than_10))
                return@CommentPublishPage
            }
            scope.launch {
                vm.publishComment(
                    posterId = posterId,
                    content = data,
                    parentId = parentId
                )
                openCommentState = false
            }

        })
    }

    LaunchedEffect(key1 = Unit) {
        vm.loadPoster(posterId)
        vm.loadComments(posterId)
    }

}