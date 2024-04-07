package com.home.torrent.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thewind.resources.R
import com.thewind.widget.theme.LocalColors

/**
 * @author: read
 * @date: 2023/9/12 上午1:20
 * @description:
 */

@Composable
internal fun TorrentSearchBar(queryWord: String, onSubmit: () -> Unit, onChange: (String) -> Unit) {

    val query = remember {
        mutableStateOf(queryWord)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(LocalColors.current.Bg1)
    ) {
        TextField(
            value = query.value,
            onValueChange = {
                query.value = it
                onChange.invoke(query.value)
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSubmit.invoke()
            }),
            maxLines = 3,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_more_torrent),
                    color = LocalColors.current.Text1
                )
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search),
                    tint = LocalColors.current.Brand_pink,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .clickable {
                            onSubmit.invoke()
                        })
            },
            trailingIcon = {
                Text(text = stringResource(R.string.search),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = LocalColors.current.Brand_pink,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(end = 18.dp)
                        .wrapContentHeight()
                        .clickable(indication = null, interactionSource = remember {
                            MutableInteractionSource()
                        }) {
                            onSubmit.invoke()
                        })
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = LocalColors.current.Text1,
                unfocusedTextColor = LocalColors.current.Text1,
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                focusedContainerColor = LocalColors.current.Bg3,
                unfocusedContainerColor = LocalColors.current.Bg3,
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