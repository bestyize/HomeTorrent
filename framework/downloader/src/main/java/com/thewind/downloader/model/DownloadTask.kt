package com.thewind.downloader.model

import com.thewind.downloader.config.systemDownloadDir
import java.io.File

/**
 * @author: read
 * @date: 2023/8/14 上午12:35
 * @description:
 */
class DownloadTask(
    val url: String?,
    val key: String?,
    val type: Int = 0,
    var createTime: Long = 0,
    var finishTime: Long = 0,
    val headers: Map<String, String> = emptyMap()
)


val DownloadTask.fileFullPath: String
    get() = systemDownloadDir + File.separator + key + ".torrent"