package com.home.torrent.main.model

import androidx.annotation.Keep

@Keep
data class HomeAppConfig(
    val domain: String = "https://thewind.xyz",
    val domainPublishPage:String = "https://github.com/bestyize/bestyize.github.io/blob/main/domain",
    val newVersionCode: Int = 1,
    val updateUrl: String = "https://thewind.xyz",
    val updateTitle: String = "发现新版本",
    val updateMessage: String = "支持更多能力！",
    val updateForce: Boolean = false,
    val forbidden: Boolean = false,
    val noticeList: List<NoticeOption>? = null
)