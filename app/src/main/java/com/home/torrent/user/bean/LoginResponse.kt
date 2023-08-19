package com.home.torrent.user.bean

import androidx.annotation.Keep

@Keep
data class LoginResponse(val code: Int = 0, val message: String = "", var user: User? = null)