package com.thewind.account.bean

import androidx.annotation.Keep

@Keep
data class User(
    val uid: Long = -1,
    val userName: String = "",
    val password: String = "",
    val email: String = "",
    val registerTime: Long = -1,
    val lastLoginTime: Long = -1,
    val icon: String = "",
    val level: Int = 0,
)