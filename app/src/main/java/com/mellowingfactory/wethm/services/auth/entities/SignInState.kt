package com.mellowingfactory.wethm.services.auth.entities

data class SignInState(
    var email: String = "",
    var username: String = "",
    var password: String = "",
    var isLoginFail: Boolean = false
)