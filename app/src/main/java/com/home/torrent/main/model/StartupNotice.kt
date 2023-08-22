package com.home.torrent.main.model

data class StartupNotice(
    val show: Boolean = false,
    val title: String? = null,
    val content: String? = null,
    val okTitle: String? = null,
    val cancelTitle: String? = null,
    val okUrl: String? = null,
    val cancelUrl: String? = null,
    val cancelable: Boolean = true
)
