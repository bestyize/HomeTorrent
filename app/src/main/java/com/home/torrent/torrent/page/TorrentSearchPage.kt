package com.home.torrent.torrent.page

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
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
import com.home.torrent.service.requestTorrentSources
import com.home.torrent.torrent.vm.TorrentSearchViewModel
import kotlinx.coroutines.launch


@Composable
@Preview
fun TorrentSearchPage() {
    val vm = viewModel(modelClass = TorrentSearchViewModel::class.java)
    val query = remember {
        mutableStateOf(vm.keywordState.value)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TorrentSearchBar(query, vm)
        TorrentSearchContentArea(query, vm)
    }
}

@Composable
fun TorrentSearchBar(query: MutableState<String>, vm: TorrentSearchViewModel) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        TextField(
            value = query.value,
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
fun TorrentSearchContentArea(query: MutableState<String>, vm: TorrentSearchViewModel) {


    val lastKeyword = remember {
        mutableStateOf("")
    }

    val tabs = remember {
        requestTorrentSources()
    }
    val pageState = rememberPagerState(initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { tabs.size })

    LaunchedEffect(key1 = "pager-torrent") {
        snapshotFlow { pageState.currentPage }.collect { page ->
            Log.i(TAG, "TorrentSearchContentArea, page:$page selected, query = $query")
            vm.updateKeyword(query.value)
        }
    }

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
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .padding(vertical = 2.dp)
                .background(Color.LightGray)
        )
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
        val keyword = vm.keywordState.collectAsStateWithLifecycle()
        HorizontalPager(state = pageState) { pageIndex ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                val fullRefresh = keyword.value != lastKeyword.value
                if (fullRefresh) {
                    dataLists[pageIndex].clear()
                    pageLists[pageIndex].intValue = 1
                }
                TorrentListView(
                    keyword = keyword.value,
                    src = tabs[pageIndex].src,
                    page = pageLists[pageIndex],
                    dataListState = dataLists[pageIndex]
                )
                lastKeyword.value = keyword.value
            }
        }
    }
}


@Composable
fun TorrentListView(
    keyword: String, src: Int, page: MutableState<Int>, dataListState: MutableList<TorrentInfo>
) {
    val vm = viewModel(modelClass = TorrentSearchViewModel::class.java)

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            items(count = dataListState.size) { pos ->
                TorrentSearchItemView(pos, dataListState[pos])
            }
            item {
                val noMoreToast = remember {
                    mutableStateOf(false)
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    if (dataListState.isNotEmpty()) {
                        Text(
                            text = if (noMoreToast.value) "已全部加载" else "正在加载中...",
                            fontSize = 16.sp,
                            color = Color.Red,
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

                if (noMoreToast.value) {
                    Toast.makeText(LocalContext.current, "没有更多了哦", Toast.LENGTH_SHORT).show()
                }
                LaunchedEffect(Unit) {
                    val newData = vm.loadTorrentList(
                        src = src, key = keyword, page = page.value
                    )
                    if (newData.isEmpty()) {
                        noMoreToast.value = true
                        return@LaunchedEffect
                    }
                    noMoreToast.value = false
                    dataListState.addAll(
                        newData
                    )
                    page.value++
                }
            }
        }
    }
}

@Composable
fun TorrentSearchItemView(index: Int, data: TorrentInfo) {
    val vm = viewModel(modelClass = TorrentSearchViewModel::class.java)
    CopyAddressDialog()
    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(Color.White)
        .clickable {
            vm.copyTorrentUrl(data)
        }) {
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
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CopyAddressDialog() {
    val vm = viewModel(modelClass = TorrentSearchViewModel::class.java)
    val copyState = vm.copyTorrentState.collectAsStateWithLifecycle()
    val copyBtnState = remember {
        mutableStateOf(false)
    }
    if (copyBtnState.value) {
        copyBtnState.value = false
        LocalClipboardManager.current.setText(
            AnnotatedString(
                copyState.value?.magnetUrl ?: ""
            )
        )
        vm.copyTorrentUrl(null)
        Toast.makeText(LocalContext.current, "复制成功", Toast.LENGTH_SHORT).show()
    }
    if (copyState.value != null) {
        AlertDialog(onDismissRequest = { vm.copyTorrentUrl(null) }) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.White, RoundedCornerShape(10.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        text = "温馨提示",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 15.dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(1.dp)
                            .padding(vertical = 10.dp)
                            .background(Color.LightGray)
                    )
                }
                item {
                    SelectionContainer {
                        Text(
                            text = copyState.value?.magnetUrl ?: "",
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .wrapContentHeight(),
                            fontSize = 14.sp,
                            color = Color.Blue
                        )
                    }
                }

                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(1.dp)
                            .padding(vertical = 10.dp)
                            .background(Color.Gray)
                    )
                    Text(text = "复制",
                        modifier = Modifier
                            .padding(vertical = 15.dp)
                            .fillMaxWidth(0.8f)
                            .wrapContentHeight()
                            .clickable(indication = null,
                                interactionSource = remember { MutableInteractionSource() }) {
                                copyBtnState.value = true
                            },
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                }

            }
        }
    }

}


private const val TAG = "[Main]TorrentSearchPage"


