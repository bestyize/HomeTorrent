package com.home.torrent.main.page.splash.service

import com.home.baseapp.app.config.appHost
import com.home.torrent.main.model.HomeAppConfig
import com.home.torrent.util.toObject
import com.thewind.network.HttpUtil.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author: read
 * @date: 2023/8/22 下午11:22
 * @description:
 */
object SplashService {

    suspend fun requestAppConfig(): HomeAppConfig = withContext(Dispatchers.IO) {
        get("$appHost/app/api/get/config").takeIf { it.isNotBlank() }
            .toObject(HomeAppConfig::class.java)?.let {
            return@withContext it
        }
        return@withContext HomeAppConfig()
    }

}