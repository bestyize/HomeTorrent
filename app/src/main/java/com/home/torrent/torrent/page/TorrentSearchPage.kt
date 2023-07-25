package com.home.torrent.torrent.page

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.home.torrent.torrent.vm.TorrentSearchViewModel


@Composable
fun TorrentSearchPage() {
    val activity = LocalContext.current.findActivity() ?: return
    val vm = activity.viewModels<TorrentSearchViewModel>()
}


fun Context?.findActivity(): AppCompatActivity? = when (this) {
    is AppCompatActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}