package com.mellowingfactory.wethm

import com.mellowingfactory.wethm.services.auth.AuthApiService
import com.mellowingfactory.wethm.services.user.UserApiService
import com.mellowingfactory.wethm.utils.Preferences

object Repositories {
    val preferences by lazy { Preferences(WethmApp.appContext) }
    val authApiService by lazy { AuthApiService() }
    val userApiService by lazy { UserApiService() }
}