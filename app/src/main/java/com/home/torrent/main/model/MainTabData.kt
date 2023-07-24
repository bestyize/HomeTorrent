package com.home.torrent.main.model

data class MainTabData(
    val title: String? = null,
    val id: Int = 0,
    val icon: String? = null,
    val isSelected: Boolean = false,
    val selectedColor: Int = 0xFFFF0000.toInt(),
    val unSelectedColor: Int = 0xFF000000.toInt()
)