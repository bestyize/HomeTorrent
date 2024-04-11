package com.thewind.community.recommend.page

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thewind.community.card.PosterOption
import com.thewind.community.card.PosterOptionDialog
import com.thewind.community.card.TitlePosterCard
import com.thewind.community.detail.vm.DetailPageViewModel
import com.thewind.community.recommend.model.PosterType
import com.thewind.community.recommend.vm.RecommendPageViewModel
import com.thewind.utils.toDate
import com.thewind.widget.nav.LocalMainNavigation
import com.thewind.widget.theme.LocalColors
import com.thewind.widget.ui.TitleHeader

/**
 * @author: read
 * @date: 2023/8/29 上午1:01
 * @description:
 */

@Composable
@Preview
fun RecommendFeedPage() {
    val vm: RecommendPageViewModel = viewModel(
        modelClass = RecommendPageViewModel::class.java,
        viewModelStoreOwner = LocalContext.current as ComponentActivity
    )
    // https://stackoverflow.com/questions/69002018/why-a-new-viewmodel-is-created-in-each-compose-navigation-route
    val detailPageVm = viewModel(
        modelClass = DetailPageViewModel::class.java,
        viewModelStoreOwner = LocalContext.current as ComponentActivity
    )
    val recommendPageState by vm.recommendPageData.collectAsStateWithLifecycle()

    val router = LocalMainNavigation.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalColors.current.Bg2)
            .statusBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
        ) {
            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
            items(
                recommendPageState.list.size,
                key = { "${recommendPageState.list[it].id}" }) { pos ->
                val data = recommendPageState.list[pos]
                var menuClickState by remember {
                    mutableStateOf(false)
                }
                if (menuClickState && data.type == PosterType.DEFAULT.value) {
                    PosterOptionDialog(onClicked = { option ->
                        when (option) {
                            PosterOption.DELETE -> {
                                vm.deletePoster(data.id)
                            }

                            PosterOption.CANCEL -> {}
                            else -> {}
                        }
                        menuClickState = false

                    })
                }
                TitlePosterCard(title = data.userName ?: "",
                    subTitle = data.date.toDate(),
                    header = data.userHeader,
                    content = data.content ?: "",
                    type = data.type,
                    onCardClick = {
                        detailPageVm.setPoster(data)
                        router.navigate("ht://recommend/detail?postId=${data.id}")
                    },
                    onMenuClick = {
                        menuClickState = true
                    })
                Spacer(modifier = Modifier.height(15.dp))
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
                if (!recommendPageState.loadFinish) {
                    vm.loadPoster(false)
                }
            }
        }
        TitleHeader(
            title = "推荐",
            color = LocalColors.current.Text1,
            backgroundColor = LocalColors.current.Bg1
        )

        Box(modifier = Modifier
            .padding(bottom = 80.dp, end = 15.dp)
            .align(Alignment.BottomEnd)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(100.dp))
            .background(
                color = LocalColors.current.Brand_pink, shape = RoundedCornerShape(100.dp)
            )
            .padding(10.dp)
            .clickable {
                vm.updatePublishPageState(true)
            }) {
            Icon(
                Icons.Filled.Add,
                tint = LocalColors.current.Text_white,
                contentDescription = "",
                modifier = Modifier.size(24.dp)
            )
        }
    }

    if (recommendPageState.publishState) {
        PublishPage {
            vm.updatePublishPageState(false)
        }
    }
}
