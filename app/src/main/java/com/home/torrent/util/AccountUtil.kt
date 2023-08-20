package com.home.torrent.util

/**
 * @author: read
 * @date: 2023/8/20 上午2:43
 * @description:
 */

internal val String?.isValidEmail: Boolean
    get() = this != null && Regex("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+\$").matches(this)

internal val String?.isValidPassword: Boolean
    get() {
        this ?: return false
        // Check length between 6 and 20 characters
        if (length !in 6..20) return false

        // Check if the password contains at least one character, one digit, and common symbols
        val hasCharacter = any { it.isLetter() }
        val hasDigit = any { it.isDigit() }
        val hasCommonSymbol = any { it in "!@#$%^&*()-_=+[]{}|;:'\",.<>?/" }

        return hasCharacter && (hasDigit || hasCommonSymbol)
    }


internal val String?.isValidUsername: Boolean
    get() {
        this ?: return false
        if (length !in 2..10) return false
        val regex = Regex("[0-9a-zA-Z\\p{IsHan}\\p{IsHiragana}\\p{IsKatakana}ー－_@]+")
        return regex.matches(this)
    }


internal fun String?.toIntOrDefault(default: Int): Int {
    runCatching {
        return this?.toInt() ?: default
    }
    return default
}

internal fun String?.toLongOrDefault(default: Long): Long {
    runCatching {
        return this?.toLong() ?: default
    }
    return default
}

internal val String?.validPasswordWithReason: String
    get() {
        this ?: return "密码不能为空"
        // Check length between 6 and 20 characters
        if (length !in 6..20) return "密码长度必须在6到20位之间"

        // Check if the password contains at least one character, one digit, and common symbols
        val hasCharacter = any { it.isLetter() }
        val hasDigit = any { it.isDigit() }
        val hasCommonSymbol = any { it in "!@#$%^&*()-_=+[]{}|;:'\",.<>?/" }

        val isValid = hasCharacter && (hasDigit || hasCommonSymbol)

        if (!isValid) {
            return "密码必须包含字母,至少包含数字或特殊字符的一种或多种"
        }
        return this
    }

internal val String?.validUsernameWithReason: String
    get() {
        this ?: return "用户名不能为空"
        if (length !in 2..10) return "用户名长度必须在2到10位之间"
        val regex = Regex("[0-9a-zA-Z\\p{IsHan}\\p{IsHiragana}\\p{IsKatakana}ー－_@]+")
        if (!regex.matches(this)) {
            return "用户名只能包含数字,字母,汉字,平假名,片假名,下划线,中划线,和@符号"
        }
        return this
    }

