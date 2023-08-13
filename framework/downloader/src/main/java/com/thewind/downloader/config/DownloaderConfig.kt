package com.thewind.downloader.config

import android.os.Environment
import android.os.Environment.DIRECTORY_DOWNLOADS
import com.home.baseapp.app.HomeApp
import java.io.File


/**
 * @author: read
 * @date: 2023/8/14 上午12:38
 * @description:
 */

internal val baseDir by lazy { HomeApp.context.filesDir.absolutePath + File.separator + "files" }

internal val cacheDir by lazy { HomeApp.context.filesDir.absolutePath + File.separator + "file_cache"}

internal val systemDownloadDir by lazy { Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).absolutePath  + HomeApp.context.packageName}