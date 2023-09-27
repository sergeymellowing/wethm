package com.mellowingfactory.wethm.ui.onboardings.user

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mellowingfactory.wethm.Repositories
import com.mellowingfactory.wethm.ui.onboardings.user.entities.UserOnboardingState
import java.util.Calendar

class UserOnboardingViewModel: ViewModel() {

    private val userApi = Repositories.userApiService

    val userState = mutableStateOf(UserOnboardingState())

    private val currentUser = userApi.currentUser

    //Dialogs
    val showBirthYear = mutableStateOf(false)
    val showGender = mutableStateOf(false)
    val showWeight = mutableStateOf(false)
    val showHeight = mutableStateOf(false)

    fun updateUserState(
        birthYear: String = userState.value.birthYear,
        gender: String = userState.value.gender,
        weight: String = userState.value.weight,
        height: String = userState.value.height
    ){
        userState.value = UserOnboardingState(
            birthYear = birthYear,
            gender = gender,
            weight = weight,
            height = height
        )
    }

    fun saveData(
        onUserOnboarded: () -> Unit
    ){
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        currentUser.value = currentUser.value.copy(
            age = currentYear - userState.value.birthYear.toInt(),
            gender = userState.value.gender,
            weight = userState.value.weight.toInt(),
            height = userState.value.height.toInt()
        )
        if (currentUser.value.id != null) {
            userApi.updateUser(
                id = currentUser.value.id?: "",
                user = currentUser.value
            ) {
                if (it) onUserOnboarded()
            }
        }
    }
}