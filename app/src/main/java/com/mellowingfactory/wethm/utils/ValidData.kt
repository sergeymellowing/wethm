package com.mellowingfactory.wethm.utils

fun isValidEmail(email: String): Boolean = emailPattern.find(email) != null

data class ValidPasswordState(
    var isLengthCase: Boolean = false,
    var isUpperCase: Boolean = false,
    var isLowerCase: Boolean = false,
    var isNumberCase: Boolean = false,
    var isSpecialCharacter: Boolean = false
){
    fun update(password: String): ValidPasswordState{
        isLengthCase = password.length in 8..16
        isUpperCase = password.any { it.isUpperCase() }
        isLowerCase = password.any { it.isLowerCase() }
        isNumberCase = password.any { it.isDigit() }
        isSpecialCharacter = specialCharPattern.find(password) != null
        return this
    }

    fun isAllValid(): Boolean = isLengthCase &&
            isUpperCase && isLowerCase &&
            isNumberCase && isSpecialCharacter
}



private val emailPattern by lazy {
    "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
}
private val specialCharPattern by lazy {
    "[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]+".toRegex()
}