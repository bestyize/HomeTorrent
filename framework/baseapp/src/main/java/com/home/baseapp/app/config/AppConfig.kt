package com.home.baseapp.app.config

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.tencent.mmkv.MMKV
import java.util.Locale

/**
 * @author: read
 * @date: 2023/8/20 上午1:38
 * @description:
 */


val appHost by lazy {
    MMKV.defaultMMKV().decodeString("api_host") ?: "https://thewind.xyz"
}

val isEn by lazy {
    Locale.getDefault().language.equals("en")
}

// At the top level of your kotlin file:
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")