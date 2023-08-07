package com.home.torrent.main.page

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.home.torrent.torrent.page.collect.TorrentCollectPage
import com.home.torrent.torrent.page.search.TorrentSearchPage
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun MainPage() {
    val tabs = listOf("首页", "收藏", "我的")
    val pagerState = rememberPagerState(initialPage = 0) {
        tabs.size
    }
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {

        HorizontalPager(state = pagerState) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF2F3F4)),
                contentAlignment = Alignment.Center
            ) {
                when (it) {
                    0 -> TorrentSearchPage()
                    1 -> TorrentCollectPage()
                    else -> Text(text = tabs[it])
                }
            }
        }

        TabRow(selectedTabIndex = 0,
            indicator = {},
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
                    color = if (isSelected) Color.Red else Color.Black,
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


private const val TAG = "[Main]MainPage"