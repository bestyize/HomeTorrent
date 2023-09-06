package com.thewind.community.card

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thewind.community.recommend.model.RecommendComment
import com.thewind.community.util.toDate
import com.thewind.widget.theme.LocalColors

/**
 * @author: read
 * @date: 2023/8/28 下午11:25
 * @description:
 */

@Composable
fun CommentCardContainer(
    modifier: Modifier = Modifier,
    dataList: List<RecommendComment> = emptyList(),
    onHeaderClick: (RecommendComment) -> Unit = {},
    onCommentClick: (RecommendComment) -> Unit = {}
) {
    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxSize()
        ) {
            items(dataList.size, key = {
                dataList[it].id
            }) {
                val data = dataList[it]
                CommentCard(
                    comment = data,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable(indication = null, interactionSource = remember {
                            MutableInteractionSource()
                        }, onClick = {
                            onCommentClick.invoke(data)
                        }),
                    onHeaderClick = onHeaderClick
                )
                Spacer(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(LocalColors.current.Bg2)
                        .align(Alignment.BottomCenter)
                )
            }
        }

    }
}


@Composable
private fun CommentCard(
    modifier: Modifier = Modifier,
    comment: RecommendComment,
    onHeaderClick: (RecommendComment) -> Unit = {}
) {
    Row(modifier = modifier) {
        Box(
            modifier = Modifier
                .width((if (comment.isSubComment) 24 else 36).dp)
                .height((if (comment.isSubComment) 24 else 36).dp)
                .background(
                    color = Color.Black, shape = RoundedCornerShape(1000.dp)
                )
                .clickable(indication = null, interactionSource = remember {
                    MutableInteractionSource()
                }, onClick = {
                    onHeaderClick.invoke(comment)
                })
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = comment.userName ?: "",
                fontSize = 13.sp,
                color = LocalColors.current.Text2,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = comment.date.toDate(),
                fontSize = 13.sp,
                color = LocalColors.current.Text2,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = comment.content ?: "",
                fontSize = 14.sp,
                color = LocalColors.current.Text1,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(5.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                comment.subCommentList?.forEach { subComment ->
                    CommentCard(
                        comment = subComment, modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize()
                    )
                }
            }
        }
    }
}


@Composable
@Preview
private fun Container() {
    CommentCardContainer(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(LocalColors.current.Bg1),
        dataList = emptyList()
    )
}