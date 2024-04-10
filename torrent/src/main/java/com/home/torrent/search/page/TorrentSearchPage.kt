package com.home.torrent.search.page

import androidx.activity.ComponentActivity
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.home.torrent.collect.vm.TorrentCollectViewModel
import com.home.torrent.search.model.SearchPageDialogType
import com.home.torrent.search.vm.TorrentSearchPageViewModel
import com.home.torrent.widget.CopyAddressDialog
import com.home.torrent.widget.TorrentClickOption
import com.home.torrent.widget.TorrentClickOptionDialog
import com.home.torrent.widget.TorrentSearchBar
import com.thewind.widget.theme.LocalColors
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * @author: read
 * @date: 2023/9/12 上午1:18
 * @description:
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TorrentSearchPage() {
    val vm = viewModel(modelClass = TorrentSearchPageViewModel::class.java)

    val collectVm = viewModel(modelClass = TorrentCollectViewModel::class.java, viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val collectSetState by collectVm.torrentSetState.collectAsStateWithLifecycle(persistentSetOf())

    val searchPageState by vm.searchPageState.collectAsStateWithLifecycle()

    val pagerState = rememberPagerState(initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { searchPageState.tabs.size })
    val scope = rememberCoroutineScope()
    if (searchPageState.dialogState.type != SearchPageDialogType.NONE) {
        when (searchPageState.dialogState.type) {
            SearchPageDialogType.OPTION -> TorrentClickOptionDialog(onClicked = {
                scope.launch {
                    when (it) {
                        TorrentClickOption.GET_MAGNET_URL -> vm.updateDialogState()
                        TorrentClickOption.GET_TORRENT_URL -> vm.updateDialogState(isMagnet = false)
                        TorrentClickOption.COLLECT_CLOUD -> vm.collectToCloud(searchPageState.dialogState.data)
                        else -> vm.updateDialogState(null)
                    }
                }

            })

            SearchPageDialogType.ADDRESS -> CopyAddressDialog(
                address = (if (searchPageState.dialogState.isMagnet) searchPageState.dialogState.data?.magnetUrl else searchPageState.dialogState.data?.torrentUrl)
                    ?: ""
            ) {
                scope.launch {
                    vm.updateDialogState(null)
                }
            }

            else -> {}
        }
    }

    LaunchedEffect(key1 = Unit) {
        snapshotFlow { pagerState.currentPage }.collectLatest { tabIndex ->
            scope.launch {
                vm.reloadTabKeywordWhenPageSwitch(tabIndex)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TorrentSearchBar(queryWord = searchPageState.keyword, onSubmit = {
                scope.launch {
                    vm.forceReloadTabKeyword(pagerState.currentPage)
                }
            }, onChange = {
                scope.launch {
                    vm.updateKeyword(it)
                }
            })
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                edgePadding = 0.dp,
                divider = {},
                containerColor = LocalColors.current.Bg1,
                indicator = {
                    Spacer(
                        modifier = Modifier
                            .tabIndicatorOffset(it[pagerState.currentPage])
                            .height(3.dp)
                            .background(LocalColors.current.Brand_pink, RoundedCornerShape(3.dp))
                    )
                },
                modifier = Modifier.wrapContentWidth()
            ) {
                searchPageState.source.forEachIndexed { index, source ->
                    val isSelected = index == pagerState.currentPage
                    Text(text = source.title,
                        color = if (isSelected) LocalColors.current.Brand_pink else LocalColors.current.Text1,
                        fontSize = if (isSelected) 16.sp else 15.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .clickable(indication = null, interactionSource = remember {
                                MutableInteractionSource()
                            }) {
                                scope.launch {
                                    pagerState.scrollToPage(index)
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

            HorizontalPager(state = pagerState) { pagerIndex ->
                val pagerScope = rememberCoroutineScope()
                val pageState = searchPageState.tabs[pagerIndex]
                TorrentSearchTab(tabState = pageState, collectSet = collectSetState, onLoad = {
                    pagerScope.launch {
                        vm.loadMore(src = pageState.src, pagerIndex)
                    }
                }, onCollect = { data, collect ->
                    pagerScope.launch {
                        if (collect) collectVm.collect(data) else collectVm.unCollect(data)
                    }
                }, onClick = { data ->
                    pagerScope.launch {
                        vm.handleTorrentInfoClick(data)
                    }
                })
            }
        }
    }
}


