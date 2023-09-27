package com.mellowingfactory.wethm.services.auth.entities

data class ForgetPasswordState(
    var email: String = "",
    var password: String = "",
    var isEmailForm: Boolean = false,
    var code: String =""
)
