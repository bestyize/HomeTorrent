package com.thewind.widget.ui.list.lazy

enum class PageLoadState {
    INIT, // 初始化状态
    FINISH, // 加载完成状态
    ALL_LOADED, //全部加载完状态
    ERROR // 加载失败
}