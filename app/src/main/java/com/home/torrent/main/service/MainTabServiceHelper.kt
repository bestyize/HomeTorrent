package com.home.torrent.main.service

import com.home.torrent.main.model.MainTabData


fun requestMainTabs(): List<MainTabData> {
    return listOf(
        MainTabData(title = "首页", isSelected = true, id = 0),
        MainTabData(title = "社区", isSelected = false, id = 1),
        MainTabData(title = "我的", isSelected = false, id = 3)
    )
}