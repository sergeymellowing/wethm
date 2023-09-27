package com.mellowingfactory.wethm.ui.auth.sign_in

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.amplifyframework.auth.AuthProvider
import com.mellowingfactory.wethm.Repositories
import com.mellowingfactory.wethm.services.auth.entities.SignInState
import com.mellowingfactory.wethm.utils.Preferences.Companion.AUTO_LOGIN
import com.mellowingfactory.wethm.utils.Preferences.Companion.EMAIL
import com.mellowingfactory.wethm.utils.Preferences.Companion.REMEMBER_EMAIL
import com.mellowingfactory.wethm.utils.ValidPasswordState
import com.mellowingfactory.wethm.utils.findActivity
import com.mellowingfactory.wethm.utils.isValidEmail
import kotlinx.coroutines.CoroutineScope

class SignInViewModel(
    val authSuccessful: () -> Unit
) : ViewModel() {

    private val authService = Repositories.authApiService
    private val preferences = Repositories.preferences

    val signInState = mutableStateOf(SignInState())

    val isRememberMeState = mutableStateOf(false)

    val isAutoSignState = mutableStateOf(false)

    val message = mutableStateOf("")

    val isValidEmail = mutableStateOf(false)

    val validPasswordState = mutableStateOf(ValidPasswordState())

    init {
        loadSavedData()
    }


    fun updateRememberState(isRememberMe: Boolean = false) {
        preferences.setBoolean(REMEMBER_EMAIL, isRememberMe)
        isRememberMeState.value = isRememberMe

    }

    fun updateAutoSignState(isAutoSign: Boolean = false) {
        preferences.setBoolean(AUTO_LOGIN, isAutoSign)
        isAutoSignState.value = isAutoSign
    }

    fun updateLoginState(
        username: String = signInState.value.username,
        password: String = signInState.value.password
    ) {
        signInState.value = signInState.value.copy(
            username = username,
            email = username,
            password = password
        )
        isValidEmail.value = isValidEmail(signInState.value.email)
        validPasswordState.value = ValidPasswordState().update(signInState.value.password)
    }


    fun login() {
        authService.login(signInState.value) { isSucces, msg ->
            message.value = ""
            if (isSucces) {
                message.value = ""
                signInState.value = signInState.value.copy(isLoginFail = false)
                preferences.setString(EMAIL, signInState.value.email)
                authSuccessful()
            } else {
                msg?.let { message.value = it }
            }
        }
    }

    fun loginWithProvider(provider: AuthProvider, context: Context) {
        message.value = ""
        authService.loginWithProvider(signInState.value, context.findActivity(), provider) { isSuccess, msg ->
            if (isSuccess) {
                authSuccessful()
            } else {
                msg?.let { message.value = it }
            }
        }
    }

    private fun loadSavedData() = with(preferences){
        updateRememberState(
            isRememberMe = getBoolean(REMEMBER_EMAIL, false)
        )
        updateAutoSignState(
            isAutoSign = getBoolean(AUTO_LOGIN)
        )

        when(isRememberMeState.value){
            true -> updateLoginState(
                username = getString(EMAIL)
            )
            false -> setString(EMAIL, "")
        }
    }

}