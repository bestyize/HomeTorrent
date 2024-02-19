package com.home.torrent.user.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.home.baseapp.app.HomeApp
import com.home.baseapp.app.toast.toast
import com.home.torrent.R
import com.home.torrent.user.model.LoginPageData
import com.home.torrent.user.model.LoginPageStage
import com.home.torrent.user.model.MinePageData
import com.home.torrent.util.isValidEmail
import com.home.torrent.util.isValidPassword
import com.home.torrent.util.isValidUsername
import com.home.torrent.util.validPasswordWithReason
import com.home.torrent.util.validUsernameWithReason
import com.thewind.account.AccountManager
import com.thewind.account.bean.User
import com.thewind.account.service.LoginService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * @author: read
 * @date: 2023/8/20 上午1:34
 * @description:
 */
class UserViewModel : ViewModel() {

    private val _loginPageState: MutableStateFlow<LoginPageData> = MutableStateFlow(LoginPageData())

    val loginPageStage = _loginPageState.asStateFlow()


    private val _minePageState: MutableStateFlow<MinePageData> =
        MutableStateFlow(MinePageData(user = AccountManager.getUser()))

    val minePageState = _minePageState.asStateFlow()


    suspend fun handleLoginClick() {
        when (_loginPageState.value.pageStage) {
            LoginPageStage.LOGIN -> login()
            LoginPageStage.REGISTER -> register()
            LoginPageStage.MODIFY_PASSWORD -> modifyPassword()
        }
    }

    private suspend fun login() {
        val data = _loginPageState.value
        if (data.userName.isBlank() && data.email.isBlank()) {
            toast(HomeApp.context.getString(R.string.must_input_username_or_email))
            return
        }
        if (data.password.isBlank()) {
            toast(HomeApp.context.getString(R.string.input_password))
            return
        }
        LoginService.login(userName = data.userName, email = data.email, password = data.password)
            .let {
                toast(it.message)
                _minePageState.value = _minePageState.value.copy(
                    showLogin = !AccountManager.isLogin(),
                    user = AccountManager.getUser()
                )
            }
    }

    private suspend fun register() {
        val data = _loginPageState.value
        if (!data.userName.isValidUsername) {
            toast(data.userName.validUsernameWithReason)
            return
        }
        if (!data.email.isValidEmail) {
            toast(HomeApp.context.getString(R.string.email_invalid))
            return
        }
        if (!data.password.isValidPassword) {
            toast(data.password.validPasswordWithReason)
            return
        }
        if (data.verifyCode.isBlank()) {
            toast(HomeApp.context.getString(R.string.input_verifycode))
            return
        }
        LoginService.register(
            userName = data.userName,
            email = data.email,
            password = data.password,
            verifyCode = data.verifyCode
        ).let {
            toast(it.message)
            _minePageState.value = _minePageState.value.copy(
                showLogin = !AccountManager.isLogin(),
                user = AccountManager.getUser()
            )
        }
    }

    private suspend fun modifyPassword() {
        val data = _loginPageState.value
        if (data.email.isBlank() || !data.email.isValidEmail) {
            toast(HomeApp.context.getString(R.string.email_invalid))
            return
        }
        if (data.verifyCode.isBlank()) {
            toast(HomeApp.context.getString(R.string.input_verifycode))
            return
        }
        if (data.password.isBlank() || !data.password.isValidPassword) {
            toast(data.password.validPasswordWithReason)
            return
        }
        viewModelScope.launch {
            LoginService.modifyPassword(
                verifyCode = data.verifyCode, email = data.email, newPassword = data.password
            ).let {
                toast(it.message)
                _minePageState.value = _minePageState.value.copy(
                    showLogin = !AccountManager.isLogin(),
                    user = AccountManager.getUser()
                )
            }

        }
    }

    suspend fun sendVerifyCode() {
        val email = _loginPageState.value.email
        if (!email.isValidEmail) {
            toast(HomeApp.context.getString(R.string.email_invalid))
            return
        }
        toast(HomeApp.context.getString(R.string.have_send))
        LoginService.sendVerifyCode(email).let {
            toast(it.message)
        }
    }

    fun logout() {
        viewModelScope.launch {
            AccountManager.loginOut()
            _minePageState.value = _minePageState.value.copy(
                showLogin = !AccountManager.isLogin(),
                user = AccountManager.getUser()
            )
        }
    }


    fun updateUserNameText(userName: String) {
        _loginPageState.value = _loginPageState.value.copy(userName = userName)
    }

    fun updateEmailText(email: String) {
        _loginPageState.value = _loginPageState.value.copy(email = email)
    }

    fun updatePasswordText(password: String) {
        _loginPageState.value = _loginPageState.value.copy(password = password)
    }

    fun updateVerifyCode(verifyCode: String) {
        _loginPageState.value = _loginPageState.value.copy(verifyCode = verifyCode)
    }

    fun updatePageStage(stage: LoginPageStage) {
        _loginPageState.value = _loginPageState.value.copy(pageStage = stage)
    }


    fun openLogin() {
        _minePageState.value = _minePageState.value.copy(showLogin = true)
    }

    fun closeLogin() {
        _minePageState.value = _minePageState.value.copy(showLogin = false)
    }


}