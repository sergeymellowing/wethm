package com.mellowingfactory.wethm.services.auth

import android.app.Activity
import android.util.Log
import com.amplifyframework.auth.AuthProvider
import com.amplifyframework.auth.AuthUserAttribute
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.mellowingfactory.wethm.services.auth.entities.CodeState
import com.mellowingfactory.wethm.services.auth.entities.SignInState
import com.mellowingfactory.wethm.services.auth.entities.ForgetPasswordState
import com.mellowingfactory.wethm.services.auth.entities.SignUpState

class AuthApiService {
    fun isSignIn(onComplete: (Boolean) -> Unit){
        Amplify.Auth.getCurrentUser(
            {
                onComplete(true)
            },
            {
                onComplete(false)
            }
        )
    }


    fun login(
        state: SignInState,
        onComplete: (Boolean, String?) -> Unit
    ) {
        Amplify.Auth.signIn(
            state.username,
            state.password,
            {
                if (it.isSignedIn) {
                //    syncAuthAndApiUser(state.username) {
                        onComplete(true, it.toString())
                //   }
                }  else {
                    Log.e("wethm", "sign in status: $it")
                    onComplete(true, it.toString())
                }
            },
            {
                val message = it.localizedMessage
                Log.e("wethm", "LogIn Failed: $message")
                onComplete(false, message)
            }
        )
    }

    fun loginWithProvider(
        state: SignInState,
        activity: Activity,
        provider: AuthProvider,
        onComplete: (Boolean, String?) -> Unit
    ) {
        // TODO: set callbacks in AndroidManifest file
        Amplify.Auth.signInWithSocialWebUI(provider, activity, {
            println("state.username ${state.username}")
            println(it)
            if (it.isSignedIn) {
                Log.e("loginWithProvider", "sign in with Provider success: $it")
                Amplify.Auth.getCurrentUser(
                    { state.username = it.userId },
                    { Log.e("Failed to get Auth user", it.toString()) }
                )

                //syncAuthAndApiUser(state.username) {
                    onComplete(true, null)
                //}
            } else {
                Log.e("loginWithProvider", "sign in status: $it")
                onComplete(true, null)
            }
        },
            {  val message = it.localizedMessage
                Log.e("loginWithProvider", message)
                onComplete(false, message)
            }
        )
    }

    fun signUp(state: SignUpState, onComplete: (Boolean) -> Unit) {
        val emailAttribute = AuthUserAttribute(AuthUserAttributeKey.email(), state.email)
        val nameAttribute = AuthUserAttribute(AuthUserAttributeKey.name(), state.name)
        val familyNameAttribute =
            AuthUserAttribute(AuthUserAttributeKey.familyName(), state.surname)
        val authUserAttribute: List<AuthUserAttribute> =
            listOf(emailAttribute, nameAttribute, familyNameAttribute)

        val options = AuthSignUpOptions.builder()
            .userAttributes(authUserAttribute)
            .build()

        println(options)
        Amplify.Auth.signUp(state.email, state.password, options,
            { onComplete(true) },
            {
                onComplete(false)
                Log.e("wethm", "Sign Up Error:", it)
            }
        )
    }

    fun verifyCode(state: CodeState, onComplete: (Boolean, String?) -> Unit) {
        Amplify.Auth.confirmSignUp(
            state.email,
            state.code,
            { onComplete(true, null) },
            { val message = it.localizedMessage
                Log.e("wethm", "Verification Failed: $message")
                onComplete(false, message)
            }
        )
    }

    fun resetPassword(state: ForgetPasswordState, onComplete: (Boolean, String?) -> Unit) {
        Amplify.Auth.resetPassword(
            state.email,
            { result ->
                Log.i("wethm", "Password reset OK: $result")
                onComplete(true, null)
            },
            { error ->
                Log.e("wethm", "Password reset failed", error)
                onComplete(false, error.localizedMessage)
            }
        )
    }

    fun confirmResetPassword(state: ForgetPasswordState, onComplete:(Boolean, String?) -> Unit) {
        println(state.password)
        println(state.email)
        println(state.code)
        Amplify.Auth.confirmResetPassword(state.email,state.password, state.code,
            {
                println("password reset succesfull!!!")
                onComplete(true, null)
            },
            { error ->
                println("password reset failed: ${error.message}")
                onComplete(false, error.message)
            }
        )
    }

}