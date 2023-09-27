package com.mellowingfactory.wethm.ui.auth.sign_up

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.mellowingfactory.wethm.Repositories
import com.mellowingfactory.wethm.services.auth.entities.CodeState
import com.mellowingfactory.wethm.services.auth.entities.ForgetPasswordState
import com.mellowingfactory.wethm.services.auth.entities.SignInState
import com.mellowingfactory.wethm.services.auth.entities.SignUpState
import com.mellowingfactory.wethm.ui.auth.AuthRoutes
import com.mellowingfactory.wethm.utils.ValidPasswordState
import com.mellowingfactory.wethm.utils.isValidEmail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel(
    private val controller: NavController
): ViewModel() {

    val isRestorePassword = mutableStateOf(false)

    init {
        val backStack = controller.currentBackStackEntryFlow
        viewModelScope.launch {
            backStack.collect {
                when(it.destination.route){
                    SignUpRoutes.NAME -> { isRestorePassword.value = false }
                    SignUpRoutes.RESTORE_PASSWORD -> { isRestorePassword.value = true }
                }
                println("Update State isRestorePassord: ${isRestorePassword.value}")
            }
        }
    }

    private val authApi = Repositories.authApiService

    val signUpState = mutableStateOf(SignUpState())
    val confirmPassword = mutableStateOf("")

    val validNames = mutableStateOf(false)
    val validEmail = mutableStateOf(false)
    val validPasswordState = mutableStateOf( ValidPasswordState() )

    val forgetState = mutableStateOf( ForgetPasswordState() )

    val codeSignUpState = mutableStateOf( CodeState() )
    val messageCode = mutableStateOf("")


    fun editEmail(email: String){
        validEmail.value = isValidEmail(email)
        when(isRestorePassword.value){
            true -> forgetState.value = forgetState.value.copy(
                email = email
            )
            false -> {
                signUpState.value = signUpState.value.copy(email = email)
                codeSignUpState.value = codeSignUpState.value.copy(
                    email = email
                )
            }
        }
    }

    fun editNames(
        name: String = signUpState.value.name,
        surname: String = signUpState.value.surname
    ){
        signUpState.value = signUpState.value.copy(
            name = name,
            surname = surname
        )
        validNames.value = name.isNotEmpty() && surname.isNotEmpty()
    }

    fun editPassword(password: String){
        validPasswordState.value = ValidPasswordState().update(password)
        when(isRestorePassword.value){
            true -> editForgetState(
                password = password
            )
            false -> signUpState.value = signUpState.value.copy(
                password = password
            )
        }
    }

    fun editCode(
        code: String
    ) = when(isRestorePassword.value){
        true -> editForgetState(
            code = code
        )
        false -> codeSignUpState.value = codeSignUpState.value.copy(
            code = code
        )
    }

    private fun signUp(){
        authApi.signUp(signUpState.value){ isSuccess: Boolean ->
            if (isSuccess){
                viewModelScope.launch {
                    withContext(Dispatchers.Main){
                        controller.navigate(SignUpRoutes.CODE)
                    }
                }
            }
        }
    }


    private fun editForgetState(
        username: String = forgetState.value.email,
        password: String = forgetState.value.password,
        isEmailForm: Boolean = forgetState.value.isEmailForm,
        code: String = forgetState.value.code
    ){
        forgetState.value = ForgetPasswordState(
            email = username,
            password = password,
            isEmailForm = isEmailForm,
            code = code
        )
    }

    fun resetPassword(){
        println("Forget state"+forgetState.value)
        authApi.resetPassword(forgetState.value) { success, errorMessage ->
            if (success) {
                viewModelScope.launch {
                    withContext(Dispatchers.Main) {
                        controller.navigate(SignUpRoutes.CODE)
                    }
                }
                println("Password reset request sent successfully.")
            } else {
                println("Password reset request failed. Error: $errorMessage")
            }
        }
    }

    private fun confirmPasswordReset(){
        authApi.confirmResetPassword(
            forgetState.value
        ) { isSuccess, errorMessage ->
            if (isSuccess) {
                viewModelScope.launch {
                    withContext(Dispatchers.Main) {
                        controller.navigate(AuthRoutes.SIGN_IN)
                    }
                }
            } else {
                messageCode.value = errorMessage?: "Password reset failed. Please try again later."
            }
        }

    }

    fun confirmCode(
        onSignIn: () -> Unit
    ) = when(isRestorePassword.value){
        true -> controller.navigate(SignUpRoutes.PASSWORD)
        false -> confirmSignUpCode { onSignIn() }
    }

    private fun confirmSignUpCode(
        onSignIn: () -> Unit
    ){
        println("CodeSignState: "+codeSignUpState.value)
        authApi.verifyCode(codeSignUpState.value){ isSuccess, msg ->
            if(isSuccess){
                authApi.login(
                    SignInState(
                        email = codeSignUpState.value.email,
                        username = codeSignUpState.value.email,
                        password = signUpState.value.password
                    )
                ){ isSignIn, loginMessage ->
                    if (isSignIn) {
                        viewModelScope.launch {
                            withContext(Dispatchers.Main) {
                                onSignIn()
                            }
                        }
                    } else{
                        messageCode.value = loginMessage?: ""
                    }
                }
            } else{
                messageCode.value = msg?: ""
            }
        }
    }

    fun enterPassword() = when(isRestorePassword.value){
        true -> confirmPasswordReset()
        false -> signUp()
    }

}