package com.home.torrent.torrent.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.torrent.model.TorrentSource
import com.home.torrent.service.requestTorrentSourceList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TorrentSearchViewModel : ViewModel() {

    val sourcesState: MutableStateFlow<List<TorrentSource>> = MutableStateFlow(emptyList())

    fun updateTorrentSource() {
        viewModelScope.launch {
            sourcesState.value = requestTorrentSourceList()
        }
    }


}