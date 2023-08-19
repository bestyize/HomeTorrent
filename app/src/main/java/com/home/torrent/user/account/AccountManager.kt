package com.home.torrent.user.account

import com.home.torrent.user.bean.User
import com.home.torrent.util.toJson
import com.home.torrent.util.toObject
import com.tencent.mmkv.MMKV

/**
 * @author: read
 * @date: 2023/8/20 上午2:13
 * @description:
 */
object AccountManager {

    private var _user: User? = null

    fun saveUser(user: User?) {
        user ?: return
        MMKV.defaultMMKV().encode(KEY_USER_INFO, user.toJson())
    }

    fun getUser(): User? {
        if (_user == null) {
            runCatching {
                _user = MMKV.defaultMMKV().decodeString(KEY_USER_INFO).toObject(User::class.java)
            }
        }
        return _user
    }

    fun loginOut() {
        _user = null
        MMKV.defaultMMKV().remove(KEY_USER_INFO)
    }

    fun isLogin(): Boolean {
        return getUser() != null
    }
}


private const val KEY_USER_INFO = "key_user_info"