package com.home.baseapp.app.config

import com.tencent.mmkv.MMKV

/**
 * @author: read
 * @date: 2023/8/20 上午1:38
 * @description:
 */


val appHost by lazy {
    MMKV.defaultMMKV().decodeString("api_host") ?: "https://thewind.xyz"
}