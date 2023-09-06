package com.thewind.community.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thewind.community.R
import com.thewind.widget.theme.LocalColors
import com.thewind.widget.ui.ImageTag

/**
 * @author: read
 * @date: 2023/8/28 上午12:58
 * @description:
 */

@Composable
@Preview
fun TitlePosterCard(
    title: String = "标题",
    subTitle: String = "2022-08-29",
    header: String? = null,
    content: String = "",
    onCardClick:() -> Unit = {},
    onMenuClick: () -> Unit = {},
    onShare: () -> Unit = {},
    onComment: () -> Unit = {},
    onLike: () -> Unit = {}
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(LocalColors.current.Bg1, RoundedCornerShape(5.dp)).clickable {
                onCardClick.invoke()
            }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .wrapContentHeight()
        ) {
            HeaderArea(
                title = title,
                subTitle = subTitle,
                modifier = Modifier.fillMaxWidth(),
                onMenuClick = onMenuClick
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = content,
                fontSize = 15.sp,
                color = LocalColors.current.Text1,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
            Spacer(modifier = Modifier.height(12.dp))
            BottomArea(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(),
                onShare = onShare,
                onComment = onComment,
                onLike = onLike
            )
        }
    }
}


@Composable
@Preview
private fun HeaderArea(
    modifier: Modifier = Modifier,
    title: String = "标题",
    subTitle: String = "2022-08-29",
    header: String? = null,
    onMenuClick: () -> Unit = {}
) {
    Box(modifier = modifier) {
        Icon(imageVector = Icons.Default.MoreVert,
            contentDescription = "",
            modifier = Modifier
                .size(16.dp)
                .align(
                    Alignment.TopEnd
                )
                .clickable {
                    onMenuClick.invoke()
                })
        Row(modifier = modifier) {
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .background(
                        color = Color.Black, shape = RoundedCornerShape(1000.dp)
                    )
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = LocalColors.current.Text1,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = subTitle,
                    fontSize = 13.sp,
                    color = LocalColors.current.Text2,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

}


@Composable
@Preview
private fun BottomArea(
    modifier: Modifier = Modifier,
    onShare: () -> Unit = {},
    onComment: () -> Unit = {},
    onLike: () -> Unit = {}
) {
    Row(modifier = modifier) {
        ImageTag(title = stringResource(R.string.share),
            icon = Icons.Default.Share,
            modifier = Modifier
                .wrapContentSize()
                .clickable {
                    onShare.invoke()
                })
        Spacer(modifier = Modifier.width(60.dp))
        ImageTag(title = stringResource(R.string.comment),
            icon = Icons.Default.Create,
            modifier = Modifier
                .wrapContentSize()
                .clickable {
                    onComment.invoke()
                })
        Spacer(modifier = Modifier.width(60.dp))
        ImageTag(title = stringResource(R.string.like),
            icon = Icons.Default.ThumbUp,
            modifier = Modifier
                .wrapContentSize()
                .clickable {
                    onComment.invoke()
                })
    }
}