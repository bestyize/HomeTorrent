package com.thewind.widget.theme

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.home.baseapp.app.HomeApp
import com.home.baseapp.app.config.dataStore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


/**
 * @author: read
 * @date: 2023/8/26 下午9:08
 * @description:
 */

internal val DATA_STORE_KEY_THEME_ID = intPreferencesKey("data_store_key_theme_id")


object ThemeManager {


    val themeFlow: Flow<ThemeId> = HomeApp.context.dataStore.data.map { pref ->
        val local = pref[DATA_STORE_KEY_THEME_ID] ?: 0
        ThemeId.entries.find { it.value == local } ?: ThemeId.AUTO
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun updateTheme(id: ThemeId) {
        GlobalScope.launch {
            HomeApp.context.dataStore.edit {
                it[DATA_STORE_KEY_THEME_ID] = id.value
            }
        }

    }

}

enum class ThemeId(val value: Int) {
    AUTO(0), DAY(1), NIGHT(2)
}

