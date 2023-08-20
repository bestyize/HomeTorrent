package com.thewind.account.bean

import androidx.annotation.Keep

@Keep
data class User(
    var uid: Long = -1,
    var userName: String = "",
    var password: String = "",
    var email: String = "",
    var registerTime: Long = -1,
    var lastLoginTime: Long = -1,
    var icon: String = "",
    var level: Int = 0,
)