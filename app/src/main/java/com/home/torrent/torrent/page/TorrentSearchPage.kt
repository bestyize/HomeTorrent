package com.home.torrent.torrent.page

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.home.torrent.model.TorrentInfo
import com.home.torrent.service.requestTorrentSources
import com.home.torrent.torrent.vm.TorrentSearchViewModel
import kotlinx.coroutines.launch


@Composable
@Preview
fun TorrentSearchPage() {
    Column(modifier = Modifier.fillMaxSize()) {
        TorrentSearchBar()
        TorrentSearchContentArea()
    }
}

@Composable
@Preview
fun TorrentSearchBar() {
    val vm = viewModel(modelClass = TorrentSearchViewModel::class.java)
    val query = vm.keywordState.collectAsStateWithLifecycle()
    val active = remember {
        mutableStateOf(true)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        TextField(
            value = query.value ?: "",
            onValueChange = {
                vm.updateKeyword(it)
            },
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
                            },
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(id = R.string.search),
                        modifier = Modifier.padding(start = 16.dp),
                    )
                }
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search),
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable {
                            vm.loadTorrentList(src = vm.currentSourceState.value, loadMore = true)
                        }
                )
            },
            colors = TextFieldDefaults.colors(
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
        requestTorrentSources()
    }
    val pageState = rememberPagerState(initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { tabs.size })

    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        ScrollableTabRow(
            selectedTabIndex = pageState.currentPage,
            edgePadding = 0.dp,
            divider = {},
            indicator = {
                Spacer(
                    modifier = Modifier
                        .tabIndicatorOffset(it[pageState.currentPage])
                        .height(3.dp)
                        .background(Color.Red, RoundedCornerShape(3.dp))
                )
            },
            modifier = Modifier.wrapContentWidth()
        ) {
            tabs.forEachIndexed { index, source ->
                val isSelected = index == pageState.currentPage
                Text(text = source.title,
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
                        })
            }
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .padding(vertical = 2.dp)
            .background(Color.LightGray))
        HorizontalPager(state = pageState) { pageIndex ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                TorrentListView(pageIndex)
            }
        }
    }
}

@Composable
fun TorrentListView(pageSrc: Int) {
    val vm = viewModel(modelClass = TorrentSearchViewModel::class.java)
    val page = remember { mutableStateOf(1) }
    val listState = vm.currPageTorrentListState.collectAsStateWithLifecycle(mutableListOf())
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            items(count = listState.value.size) { pos ->
                TorrentSearchItemView(pos, listState.value[pos])
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    if (listState.value.isNotEmpty()) {
                        Text(
                            text = "正在加载中",
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(vertical = 40.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                    )
                }

                LaunchedEffect(Unit) {
                    vm.loadTorrentList(src = pageSrc, page = page.value, loadMore = true)
                    page.value += 1
                }
            }
        }
    }
}

@Composable
fun TorrentSearchItemView(index: Int, data: TorrentInfo) {
    val vm = viewModel(modelClass = TorrentSearchViewModel::class.java)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.Center)
        ) {
            Text(
                text = "$index",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(10.dp)
                    .weight(1f),
                textAlign = TextAlign.Center,
            )

            Column(
                modifier = Modifier
                    .weight(5f)
                    .wrapContentHeight()
                    .padding(vertical = 10.dp)
            ) {
                Text(
                    text = data.title ?: "",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    textAlign = TextAlign.Left,
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(), verticalAlignment = Alignment.CenterVertically
                ) {
                    data.date?.let { date ->
                        Text(
                            text = "日期：",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Left,
                            lineHeight = 11.sp
                        )
                        Text(
                            text = date,
                            fontSize = 11.sp,
                            lineHeight = 11.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Left
                        )
                    }

                }
            }
            Icon(imageVector = Icons.Default.Send,
                contentDescription = "下载",
                tint = Color.LightGray,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        vm.copyTorrentUrl(data)
                    })

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .padding(start = 20.dp, end = 20.dp)
                .background(Color.LightGray)
                .align(Alignment.BottomCenter)
        )
    }

}


