package com.thewind.community.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.home.baseapp.app.HomeApp
import com.thewind.community.R
import com.thewind.widget.theme.LocalColors

/**
 * @author: read
 * @date: 2023/9/4 上午12:44
 * @description:
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
internal fun PosterOptionDialog(
    options: List<PosterOption> = listOf(PosterOption.DELETE, PosterOption.CANCEL),
    onClicked: (PosterOption) -> Unit = {}
) {
    ModalBottomSheet(onDismissRequest = { onClicked.invoke(PosterOption.CANCEL) }) {
        Spacer(modifier = Modifier.height(10.dp))
        options.forEach { option ->
            Text(text = option.value,
                color = LocalColors.current.Text1,
                fontSize = 17.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(0.8f)
                    .wrapContentHeight()
                    .align(Alignment.CenterHorizontally)
                    .clickable(indication = null, interactionSource = remember {
                        MutableInteractionSource()
                    }) {
                        onClicked.invoke(option)
                    })
            Spacer(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.8f)
                    .height(0.5.dp)
                    .background(if (option != PosterOption.CANCEL) LocalColors.current.Bg2 else Color.Transparent)
            )
        }
    }
}


enum class PosterOption(val value: String) {
    DELETE(HomeApp.context.getString(R.string.delete)), COLLECT(HomeApp.context.getString(R.string.collect)), SHARE(
        HomeApp.context.getString(R.string.share)
    ),
    CANCEL(HomeApp.context.getString(R.string.cancel))
}