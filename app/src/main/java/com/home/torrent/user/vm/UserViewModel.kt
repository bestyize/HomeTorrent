package com.home.torrent.user.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.baseapp.app.toast.toast
import com.home.torrent.util.isValidEmail
import com.home.torrent.util.isValidPassword
import com.home.torrent.util.isValidUsername
import com.home.torrent.util.validPasswordWithReason
import com.home.torrent.util.validUsernameWithReason
import com.thewind.account.AccountManager
import com.thewind.account.bean.User
import com.thewind.account.service.LoginService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * @author: read
 * @date: 2023/8/20 上午1:34
 * @description:
 */
class UserViewModel : ViewModel() {

    val loginState: MutableStateFlow<User?> = MutableStateFlow(AccountManager.getUser())

    fun login(userName: String? = null, email: String? = null, password: String?) {
        viewModelScope.launch {
            if (userName == null && email == null) {
                toast("用户名或邮箱至少填一个")
                return@launch
            }
            if (password == null) {
                toast("请填写密码")
                return@launch
            }
            LoginService.login(userName = userName, email = email, password = password).let {
                toast(it.message)
                loginState.value = AccountManager.getUser()
            }
        }
    }

    fun register(userName: String?, email: String?, password: String?, verifyCode: Int) {
        viewModelScope.launch {
            if (!userName.isValidUsername) {
                toast(userName.validUsernameWithReason)
                return@launch
            }
            if (!email.isValidEmail) {
                toast("邮箱格式不正确")
                return@launch
            }
            if (!password.isValidPassword) {
                toast(password.validPasswordWithReason)
                return@launch
            }
            if (verifyCode == 0) {
                toast("请填写验证码")
                return@launch
            }
            LoginService.register(
                userName = userName ?: "",
                email = email ?: "",
                password = password ?: "",
                verifyCode = verifyCode
            ).let {
                toast(it.message)
                loginState.value = AccountManager.getUser()
            }
        }
    }

    fun modifyPassword(email: String?, verifyCode: Int, newPassword: String?) {
        if (email == null || !email.isValidEmail) {
            toast("邮箱格式不正确")
            return
        }
        if (verifyCode == 0) {
            toast("请填写验证码")
            return
        }
        if (newPassword == null || !newPassword.isValidPassword) {
            toast(newPassword.validPasswordWithReason)
            return
        }
        viewModelScope.launch {
            LoginService.modifyPassword(
                verifyCode = verifyCode,
                email = email,
                newPassword = newPassword
            ).let {
                toast(it.message)
                loginState.value = AccountManager.getUser()
            }

        }
    }

    fun sendVerifyCode(email: String?) {
        viewModelScope.launch {
            if (email == null || !email.isValidEmail) {
                toast("邮箱格式不正确")
                return@launch
            }
            toast("已发送")
            LoginService.sendVerifyCode(email).let {
                toast(it.message)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            AccountManager.loginOut()
            loginState.value = AccountManager.getUser()
        }
    }

}