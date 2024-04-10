package com.home.torrent.main.page

import android.content.res.Resources
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.home.torrent.cloud.page.TorrentCloudPage
import com.home.torrent.collect.page.TorrentCollectPage
import com.home.torrent.search.page.TorrentSearchPage
import com.home.torrent.user.mine.page.MinePage
import com.thewind.community.recommend.page.RecommendFeedPage
import com.thewind.resources.R
import com.thewind.widget.theme.LocalColors
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun MainPage(
    resources: Resources = LocalContext.current.resources, tabs: List<String> = remember {
        listOf(
            resources.getString(R.string.main_page),
            resources.getString(R.string.collect),
            resources.getString(R.string.cloud),
            resources.getString(R.string.recommend),
            resources.getString(R.string.my)
        )
    }
) {

    val pagerState = rememberPagerState(initialPage = 0) {
        tabs.size
    }
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalColors.current.Bg1)
            .statusBarsPadding()
    ) {

        HorizontalPager(state = pagerState) {
            Box(
                modifier = Modifier
                    .padding(bottom = 60.dp)
                    .fillMaxSize()
                    .background(LocalColors.current.Bg2), contentAlignment = Alignment.Center
            ) {
                when (it) {
                    0 -> TorrentSearchPage()
                    1 -> TorrentCollectPage()
                    2 -> TorrentCloudPage()
                    3 -> RecommendFeedPage()
                    else -> MinePage()
                }
            }
        }

        TabRow(
            selectedTabIndex = 0,
            indicator = {},
            containerColor = LocalColors.current.Bg3,
            divider = {},
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .shadow(elevation = 3.dp)
                .height(60.dp)
                .fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelected = pagerState.currentPage == index
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    color = if (isSelected) LocalColors.current.Brand_pink else LocalColors.current.Text1,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    fontSize = if (isSelected) 18.sp else 17.sp,
                    modifier = Modifier.clickable(onClick = {
                        scope.launch {
                            pagerState.scrollToPage(index)
                        }
                    },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() })
                )
            }
        }

    }
}
