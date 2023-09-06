package com.thewind.community.detail

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thewind.community.detail.vm.DetailPageViewModel
import com.thewind.community.recommend.model.RecommendPoster
import com.thewind.widget.theme.AppTheme
import com.thewind.widget.theme.LocalColors
import com.thewind.widget.theme.ThemeId
import com.thewind.widget.theme.ThemeManager

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val posterId = intent.getLongExtra("poster_id", -1)
        val poster: RecommendPoster? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("poster_content", RecommendPoster::class.java)
        } else {
            intent.getParcelableExtra("poster_content")
        }
        setContent {
            val vm = viewModel(modelClass = DetailPageViewModel::class.java)
            if (poster != null) {
                vm.setPoster(poster)
            } else {
                vm.loadPoster(posterId)
            }
            vm.loadComments(posterId)
            val theme =
                ThemeManager.themeFlow.collectAsStateWithLifecycle(initialValue = ThemeId.AUTO)
            val commentsState = vm.commentState.collectAsStateWithLifecycle()
            val isNight = when (theme.value) {
                ThemeId.AUTO -> isSystemInDarkTheme()
                ThemeId.NIGHT -> true
                ThemeId.DAY -> false
            }
            AppTheme(darkTheme = isNight) {
                poster?.let {
                    PosterDetailPage(modifier = Modifier
                        .fillMaxSize()
                        .background(LocalColors.current.Bg2),
                        poster = it,
                        comments = commentsState.value,
                        onPublish = { data, parentId ->
                            vm.publishComment(
                                posterId = posterId,
                                content = data,
                                parentId = parentId
                            )
                        })
                }

            }

        }
    }
}