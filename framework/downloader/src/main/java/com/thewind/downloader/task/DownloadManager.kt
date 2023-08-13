package com.thewind.downloader.task

import com.thewind.downloader.model.DownloadTask
import com.thewind.downloader.model.fileFullPath
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.concurrent.Executors

/**
 * @author: read
 * @date: 2023/8/14 上午12:44
 * @description:
 */
enum class DownloadState(val value: Int) {
    SUCCESS(0), FAILED(1)
}

suspend fun suspendSyncDownload(task: DownloadTask) = withContext(Dispatchers.IO) {
    return@withContext syncDownload(task)
}

fun syncDownload(task: DownloadTask): DownloadState {
    task.url ?: return DownloadState.FAILED
    val file = File(task.fileFullPath)
    if (file.exists()) {
        return DownloadState.SUCCESS
    }
    task.createTime = System.currentTimeMillis()
    val success = HttpDownloader.download(task.url, task.fileFullPath, extra = task.headers)
    if (success) {
        task.finishTime = System.currentTimeMillis()
    }

    return if (success) DownloadState.SUCCESS else DownloadState.FAILED
}

private val tasks = Executors.newFixedThreadPool(5)

fun asyncDownload(task: DownloadTask) {
    tasks.submit {
        syncDownload(task)
    }
}