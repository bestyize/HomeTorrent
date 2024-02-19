package com.thewind.account.service

import com.google.gson.Gson
import com.home.baseapp.app.config.appHost
import com.thewind.account.AccountManager
import com.thewind.account.bean.LoginResponse
import com.thewind.account.bean.RegisterResponse
import com.thewind.account.bean.SendEmailResponse
import com.thewind.account.util.HttpUtil.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author: read
 * @date: 2023/8/20 上午1:34
 * @description:
 */


object LoginService {
    suspend fun sendVerifyCode(email: String) = withContext(Dispatchers.IO) {
        runCatching {
            val resp = get("$appHost/user/api/send/verify/code?email=$email")
            if (resp.isBlank()) return@withContext SendEmailResponse(-1, "Network Error")
            val response: SendEmailResponse? = Gson().fromJson(resp, SendEmailResponse::class.java)
            response?.let {
                return@withContext it
            }
        }

        return@withContext SendEmailResponse(-1, "Network Error")
    }

    suspend fun register(userName: String, email: String, password: String, verifyCode: String) =
        withContext(Dispatchers.IO) {
            runCatching {
                val resp =
                    get("$appHost/user/api/register?userName=$userName&email=$email&password=$password&verifyCode=$verifyCode")
                val response: RegisterResponse? = Gson().fromJson(resp, RegisterResponse::class.java)
                response?.let {
                    AccountManager.saveUser(it.user)
                    return@withContext it
                }
            }
            return@withContext RegisterResponse(-1, "Network Error")

        }


    suspend fun login(userName: String?, password: String, email: String?) =
        withContext(Dispatchers.IO) {
            runCatching {
                val resp =
                    get("$appHost/user/api/login?userName=${userName ?: ""}&email=${email ?: ""}&password=$password")
                val response: LoginResponse? = Gson().fromJson(resp, LoginResponse::class.java)
                response?.let {
                    AccountManager.saveUser(it.user)
                    return@withContext it
                }
            }
            return@withContext LoginResponse(-1, "Network Error")
        }

    suspend fun modifyPassword(verifyCode: String, email: String, newPassword: String) = withContext(Dispatchers.IO) {
        runCatching {
            val resp = get("$appHost/user/api/modify/password?email=$email&verifyCode=$verifyCode&newPassword=$newPassword")
            val response: LoginResponse? = Gson().fromJson(resp, LoginResponse::class.java)
            response?.let {
                AccountManager.saveUser(it.user)
                return@withContext it
            }
        }
        return@withContext LoginResponse(-1, "Network Error")
    }



}

