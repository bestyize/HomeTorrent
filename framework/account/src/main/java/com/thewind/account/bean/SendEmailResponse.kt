package com.thewind.account.bean

import androidx.annotation.Keep

@Keep
data class SendEmailResponse(val code: Int, val message: String)
