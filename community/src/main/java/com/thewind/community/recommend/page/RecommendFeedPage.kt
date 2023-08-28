package com.thewind.community.recommend.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thewind.community.card.TitlePosterCard
import com.thewind.community.model.Poster
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
    val feeds = remember {
        listOf(Poster(), Poster())
    }
    Box(modifier = modifier.background(LocalColors.current.Bg2).statusBarsPadding()) {
        TitleHeader()
        LazyColumn(modifier = Modifier.padding(15.dp)){
            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
            items(feeds.size) { pos ->
                TitlePosterCard()
                Spacer(modifier = Modifier.height(15.dp))
            }

            item{
                // loadMore
            }
        }
    }

}