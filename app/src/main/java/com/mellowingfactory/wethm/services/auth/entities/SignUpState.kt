package com.mellowingfactory.wethm.services.auth.entities

data class SignUpState(
    var email: String = "",
    var password: String = "",
    var name: String = "",
    var surname: String = "",
    var marketing: Boolean = true,

    var isEmailForm : Boolean = true
)
