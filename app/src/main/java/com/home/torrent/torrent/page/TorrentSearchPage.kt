package com.home.torrent.torrent.page

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.home.torrent.R
import com.home.torrent.torrent.vm.TorrentSearchViewModel
import kotlinx.coroutines.launch


@Composable
@Preview
fun TorrentSearchPage() {
    val vm = viewModel(modelClass = TorrentSearchViewModel::class.java)

    val pageState = vm.sourcesState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        TorrentSearchBar()
        TorrentSearchContentArea()
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun TorrentSearchBar() {
    val query = remember {
        mutableStateOf("")
    }
    val active = remember {
        mutableStateOf(true)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth().background(Color.White)
    ) {
        TextField(value = query.value,
            onValueChange = {},
            placeholder = { Text(text = stringResource(id = R.string.search_more_torrent)) },
            leadingIcon = {
                if (active.value) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "点击搜索更多",
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .clickable {
                                active.value = false
                                query.value = ""
                            },
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.search),
                        modifier = Modifier.padding(start = 16.dp),
                    )
                }
            },colors = TextFieldDefaults.colors(
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(100.dp))
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
fun TorrentSearchContentArea() {
    val tabs = remember {
        listOf(
            "TT", "ZZ","TT", "ZZ","TT", "ZZ","TT", "ZZ"
        )
    }
    val pageState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { tabs.size })

    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        ScrollableTabRow(
            selectedTabIndex = pageState.currentPage,
            edgePadding = 0.dp,
            divider = {},
            indicator = {
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(it[pageState.currentPage])
                        .height(3.dp)
                        .background(Color.Red, RoundedCornerShape(3.dp))
                )
            },
            modifier = Modifier.wrapContentWidth()
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelected = index == pageState.currentPage
                Text(
                    text = title,
                    color = if (isSelected) Color.Red else Color.Black,
                    fontSize = if (isSelected) 16.sp else 15.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .clickable {
                            scope.launch {
                                pageState.scrollToPage(index)
                            }
                        }
                )
            }
        }
        HorizontalPager(state = pageState) { pageIndex ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = pageIndex.toString())
            }

        }
    }
}

