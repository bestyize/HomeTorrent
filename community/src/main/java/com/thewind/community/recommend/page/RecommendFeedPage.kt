package com.thewind.community.recommend.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thewind.community.card.TitlePosterCard
import com.thewind.community.recommend.vm.RecommendPageViewModel
import com.thewind.community.util.toDate
import com.thewind.widget.theme.LocalColors
import com.thewind.widget.ui.TitleHeader

/**
 * @author: read
 * @date: 2023/8/29 上午1:01
 * @description:
 */

@Composable
@Preview
fun RecommendFeedPage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(LocalColors.current.Bg2)
            .statusBarsPadding()
    ) {
        TitleHeader(title = "推荐", color = LocalColors.current.Text1, backgroundColor = LocalColors.current.Bg1)
        val vm = viewModel(modelClass = RecommendPageViewModel::class.java)

        val posterListState = vm.posterListState.collectAsStateWithLifecycle()

        val finishState = vm.loadFinishState.collectAsStateWithLifecycle()

        LazyColumn(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
        ) {
            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
            items(posterListState.value.size, key = { "${posterListState.value[it].id}" }) { pos ->
                val data = posterListState.value[pos]
                TitlePosterCard(title = data.userName ?:"", subTitle = data.date.toDate(), header = data.userHeader, content = data.content ?: "")
                Spacer(modifier = Modifier.height(15.dp))
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
                if (!finishState.value) {
                    vm.loadPoster(false)
                }
            }
        }
    }

}