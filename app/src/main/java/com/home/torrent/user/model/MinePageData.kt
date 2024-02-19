package com.home.torrent.user.model

import com.thewind.account.AccountManager
import com.thewind.account.bean.User

data class MinePageData(val showLogin: Boolean = !AccountManager.isLogin(), val user: User? = null)
