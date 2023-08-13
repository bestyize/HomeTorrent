package com.home.torrent.torrent.page.search

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.home.torrent.R
import com.home.torrent.model.TorrentInfo
import com.home.torrent.model.TorrentSource
import com.home.torrent.service.requestTorrentSources
import com.home.torrent.torrent.page.collect.vm.TorrentCollectViewModel
import com.home.torrent.torrent.page.search.vm.TorrentSearchViewModel
import com.home.torrent.torrent.page.widget.CopyAddressDialog
import com.home.torrent.torrent.page.widget.TorrentClickOptionDialog
import com.home.torrent.torrent.page.widget.TorrentListView
import kotlinx.coroutines.launch


@Composable
@Preview
fun TorrentSearchPage() {
    val vm = viewModel(modelClass = TorrentSearchViewModel::class.java)
    val query = remember {
        mutableStateOf(vm.keywordState.value.key)
    }
    val collectVm = viewModel(modelClass = TorrentCollectViewModel::class.java)
    collectVm.loadAll()
    Column(modifier = Modifier.fillMaxSize()) {
        TorrentSearchBar(query, vm)
        TorrentSearchContentArea(query, vm, collectVm)
    }
}

@Composable
fun TorrentSearchBar(query: MutableState<String>, vm: TorrentSearchViewModel) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        TextField(value = query.value,
            onValueChange = {
                query.value = it
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                vm.updateKeyword(query.value)
            }),
            maxLines = 3,
            placeholder = { Text(text = stringResource(id = R.string.search_more_torrent)) },
            leadingIcon = {
                if (query.value.isBlank()) {
                    Icon(imageVector = Icons.Default.Search,
                        contentDescription = "搜索",
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .clickable {
                                vm.updateKeyword("")
                            })
                } else {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "清除",
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

            },
            trailingIcon = {
                Text(text = "搜索",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(end = 18.dp)
                        .wrapContentHeight()
                        .clickable(indication = null, interactionSource = remember {
                            MutableInteractionSource()
                        }) {
                            vm.updateKeyword(query.value)
                        })
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
fun TorrentSearchContentArea(
    query: MutableState<String>, vm: TorrentSearchViewModel, collectVm: TorrentCollectViewModel
) {

    val tabs = remember {
        requestTorrentSources()
    }
    val pageState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { tabs.size })

    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        ScrollableTabRow(selectedTabIndex = pageState.currentPage,
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
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .padding(vertical = 2.dp)
                .background(Color.LightGray)
        )

        TorrentPagerArea(
            tabs = tabs, vm = vm, pageState = pageState, query = query, collectVm = collectVm
        )

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TorrentPagerArea(
    tabs: List<TorrentSource>,
    vm: TorrentSearchViewModel,
    collectVm: TorrentCollectViewModel,
    pageState: PagerState,
    query: MutableState<String>
) {
    val dataLists = tabs.indices.toList().map {
        remember("$it-data") {
            mutableStateListOf<TorrentInfo>()
        }
    }
    val pageLists = tabs.indices.toList().map {
        remember("$it-page") {
            mutableIntStateOf(1)
        }
    }
    val keywordLists = tabs.indices.toList().map {
        remember("$it-keyword") {
            mutableStateOf("")
        }
    }
    val keyword = vm.keywordState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = "pager-torrent") {
        snapshotFlow { pageState.currentPage }.collect { page ->
            Log.i(TAG, "TorrentSearchContentArea, page:$page selected, query = $query")
            if (keywordLists[page].value != query.value || keyword.value.key.isEmpty()) {
                vm.updateKeyword(query.value)
            }

        }
    }

    val copyTorrent = vm.copyTorrentState.collectAsStateWithLifecycle()

    val optionMagnet = remember {
        mutableStateOf(true)
    }

    copyTorrent.value?.let {
        CopyAddressDialog(it, optionMagnet.value) {
            vm.copyTorrentUrl(null)
            optionMagnet.value = true
        }
    }

    val optionShowState = remember {
        mutableStateListOf<Any?>(false, null)
    }

    if (optionShowState[0] as? Boolean == true) {
        TorrentClickOptionDialog {
            when (it) {
                0 -> {
                    optionMagnet.value = true
                    vm.copyTorrentUrl(optionShowState[1] as? TorrentInfo)
                }

                1 -> {
                    optionMagnet.value = false
                    vm.copyTorrentUrl(optionShowState[1] as? TorrentInfo)
                }
            }
            optionShowState[1] = null
            optionShowState[0] = false
        }
    }


    HorizontalPager(state = pageState) { pageIndex ->

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            val fullRefresh = keyword.value.key != keywordLists[pageIndex].value
            keywordLists[pageIndex].value = keyword.value.key
            if (fullRefresh) {
                dataLists[pageIndex].clear()
                pageLists[pageIndex].intValue = 1
            }
            val key = keyword.value.key
            val src = tabs[pageIndex].src
            val page = pageLists[pageIndex]
            val dataList = dataLists[pageIndex]
            val noMoreToast = remember {
                mutableStateOf(false)
            }
            val update = remember {
                mutableStateOf(false)
            }
            if (update.value) {
                LaunchedEffect("$src") {
                    val newData = vm.loadTorrentList(
                        src = src, key = key, page = page.intValue
                    )
                    if (newData.isEmpty()) {
                        noMoreToast.value = true
                        return@LaunchedEffect
                    }
                    noMoreToast.value = false
                    dataList.addAll(
                        newData
                    )
                    page.intValue++
                }
                update.value = false
            }

            TorrentListView(dataListState = dataList,
                bottomText = if (noMoreToast.value) "已全部加载" else "正在为您加载中...",
                onLoad = {
                    update.value = true
                },
                onClicked = { info ->
//                    vm.copyTorrentUrl(info)
                    optionShowState[1] = info
                    optionShowState[0] = true
                },
                onCollectClicked = { info, collect ->
                    if (collect) collectVm.collect(info) else collectVm.unCollect(info)
                })
        }
    }
}


private const val TAG = "[Main]TorrentSearchPage"


