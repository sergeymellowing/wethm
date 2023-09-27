package com.mellowingfactory.wethm.services.user

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.rest.RestOptions
import com.amplifyframework.core.Amplify
import com.google.gson.Gson
import com.mellowingfactory.wethm.services.user.entities.CreateUserRequest
import com.mellowingfactory.wethm.services.user.entities.GetUserResponse
import com.mellowingfactory.wethm.services.user.entities.UpdateUserRequest
import com.mellowingfactory.wethm.services.user.entities.User

class UserApiService {

    companion object {
        private const val API_NAME = "DeviceApi"
    }

    val currentUser = mutableStateOf(User())

    init {
        reloadCurrentUserInfo()
    }

    private fun reloadCurrentUserInfo(){
        Amplify.Auth.getCurrentUser(
            { authUser ->
                getUser(authUser.userId){
                    currentUser.value = it?: User(
                        id = authUser.userId
                    )
                    println("Current User: ${currentUser.value}")
                }
            },
            {
                Log.e("Aplify","Failed getCurrentUser: ${it.message}")
            }
        )
    }

    fun getUser(id: String, onComplete: (User?) -> Unit) {
        Log.d("Amplify", id)
        val queryParams = mapOf("id" to id)
        val request = RestOptions.builder()
            .addPath("/user/get")
            .addQueryParameters(queryParams)
            .build()

        Amplify.API.get(API_NAME, request,
            {
                Log.d("Amplify", it.toString())
                val result = it.data.asJSONObject().toString()
                if (it.code.isSuccessful) {
                    val gson = Gson()
                    val userResponse = gson.fromJson(result, GetUserResponse::class.java)
                    Log.i("Amplify", "GET user succeeded: $userResponse")
                    onComplete(userResponse.data)
                } else {
                    val newUser = User(id = id)
                    createUser(newUser){ isSuccess ->
                        if (isSuccess) {
                            onComplete(newUser)
                        } else {
                            onComplete(null)
                        }
                    }
                }

            }, {
                Log.e("Amplify", "GET user api failed.", it)
                onComplete(null)
            })
    }

    fun createUser(user: User, onComplete: (Boolean) -> Unit) {
        val getUserRequest = CreateUserRequest(item = user)
        val gson = Gson()
        val jsonObject = gson.toJson(getUserRequest)
        val body = jsonObject.toString().toByteArray()
        val request = RestOptions.builder()
            .addPath("/user/create")
            .addBody(body)
            .addHeader("Content-Type", "application/json")
            .build()

        try {
            Amplify.API.post(API_NAME, request,
                {
                    val result = it.toString()
                    Log.i("MyAmplifyApp", "CREATE apiNodeUser succeeded: $result")
                    onComplete(true)
                }, {
                    Log.e("MyAmplifyApp", "CREATE apiNodeUser failed.", it)
                    onComplete(false)
                })
        } catch (error: ApiException) {
            Log.e("MyAmplifyApp", "CREATE apiNodeUser exception.", error)
        }
    }

    fun updateUser(id: String, user: User, onComplete: (Boolean) -> Unit) {
        val updateApiNodeUserRequest = UpdateUserRequest(id, user)
        val gson = Gson()
        val jsonObject = gson.toJson(updateApiNodeUserRequest)
        val body = jsonObject.toString().toByteArray()
        println("updateApiNodeUserRequest $updateApiNodeUserRequest")
        println("jsonObject $jsonObject")
        println("body ${String(body, Charsets.UTF_8)}")
        val request = RestOptions.builder()
            .addPath("/user/update")
            .addBody(body)
            .addHeader("Content-Type", "application/json")
            .build()


        Amplify.API.post(API_NAME, request,
            {
                val result = it.data.asJSONObject().toString()
                if (it.code.isSuccessful) {
                    Log.i("Amplify", "UPDATE apiNodeUser succeeded: $result")
                    onComplete(true)
                } else {
                    Log.e("Amplify", "UPDATE apiNodeUser failed: $result")
                    onComplete(false)
                }
            }, {
                Log.e("Amplify", "UPDATE apiNodeUser failed.", it)
                onComplete(false)
            })
    }

}