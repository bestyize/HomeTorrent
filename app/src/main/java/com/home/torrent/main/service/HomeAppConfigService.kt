package com.home.torrent.main.service

import com.home.baseapp.app.config.appHost
import com.home.torrent.main.model.HomeAppConfig
import com.thewind.network.HttpUtil.get
import com.thewind.utils.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author: read
 * @date: 2023/8/22 下午11:22
 * @description:
 */
object HomeAppConfigService {

    private var _appConfig: HomeAppConfig = HomeAppConfig()

    val appConfig: HomeAppConfig
        get() = _appConfig


    suspend fun requestAppConfig(): HomeAppConfig = withContext(Dispatchers.IO) {
        get("$appHost/app/api/get/config").takeIf { it.isNotBlank() }
            .toObject(HomeAppConfig::class.java)?.let {
                _appConfig = it
                return@withContext it
            }
        return@withContext HomeAppConfig()
    }

}