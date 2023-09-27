package com.mellowingfactory.wethm

import android.app.Application
import android.content.Context
import android.util.Log
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.kotlin.core.Amplify

class WethmApp: Application() {

    companion object{
        private var _appContext: Context? = null
        val appContext: Context
            get(){
                return _appContext?: throw Exception("App Context == null")
            }
    }

    override fun onCreate() {
        super.onCreate()
        _appContext = applicationContext
        configureAmplify(applicationContext)
    }

    private fun configureAmplify(context: Context) {
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.configure(context)
            Log.i("wethm", "Configured amplify")
//            fetchCurrentAuthSession { user, isSignedIn ->
//                println(user)
//                println(isSignedIn)
//            }
        } catch (e: Exception) {
            Log.i("wethm", "Amplify configuration failed", e)
        }
    }

}