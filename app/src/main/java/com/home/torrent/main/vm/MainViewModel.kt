package com.home.torrent.main.vm

import androidx.lifecycle.ViewModel
import com.home.torrent.main.model.MainTabData
import com.home.torrent.main.service.requestMainTabs
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel : ViewModel() {

    val mainTabsState: MutableStateFlow<List<MainTabData>> = MutableStateFlow(requestMainTabs())


    fun updateMainTabsSelectState(tabIndex: Int) {
        mainTabsState.value = mainTabsState.value.toMutableList().apply {
            forEachIndexed { index, mainTabData ->
                this[index] = mainTabData.copy(isSelected = tabIndex == index)
            }
        }
    }

}