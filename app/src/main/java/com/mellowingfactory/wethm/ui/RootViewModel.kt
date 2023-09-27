package com.mellowingfactory.wethm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.mellowingfactory.wethm.Repositories
import com.mellowingfactory.wethm.utils.navigateAndClear
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RootViewModel(
    val controller: NavHostController
): ViewModel() {

    private val pref = Repositories.preferences
    private val authService = Repositories.authApiService

    private val stateNavigation = MutableStateFlow(RootNavigationState())

    init {
        recheckAndGetRoute { route ->
            controller.navigateAndClear(route)
        }
    }

    private fun checkState(onReady: () -> Unit){
        viewModelScope.launch {
            stateNavigation.collect {
                if (it.isAllInit()){
                    onReady() //TODO: check thread
                }
            }
        }

        isSignIn { updateStates(
            needLogin = !it
        ) }
        updateStates(
            needShowTerms = isTermsFinished()
        )
        isUserOnboarded { updateStates(
            needShowUserOnboarding = !it
        ) }
        isDeviceOnboarded { updateStates(
            needShowDeviceOnboarding = !it
        ) }
        isAlarmTime { updateStates(
            needShowAlarmTime = !it
        ) }

    }

    fun recheckAndGetRoute(onResult: (String) -> Unit){
        checkState {
            onResult(getActualRoute())
        }
    }

    fun getActualRoute(): String{
        with(stateNavigation.value){
            return if (needLogin == true) RootRoutes.AUTH
            else if (needShowTerms == true) RootRoutes.TERMS
            else if (needShowUserOnboarding == true) RootRoutes.USER_ONBOARDING
            else if (needShowDeviceOnboarding == true) RootRoutes.DEVICE_ONBOARDING
            else if (needShowAlarmTime == true) RootRoutes.ALARM_TIME
            else RootRoutes.SESSION
        }
    }

    fun updateStates(
        needLogin: Boolean? = stateNavigation.value.needLogin,
        needShowTerms: Boolean? = stateNavigation.value.needShowTerms,
        needShowUserOnboarding: Boolean? = stateNavigation.value.needShowUserOnboarding,
        needShowDeviceOnboarding: Boolean? = stateNavigation.value.needShowDeviceOnboarding,
        needShowAlarmTime: Boolean? = stateNavigation.value.needShowAlarmTime
    ){
        stateNavigation.value = RootNavigationState(
            needLogin = needLogin,
            needShowTerms = needShowTerms,
            needShowUserOnboarding = needShowUserOnboarding,
            needShowDeviceOnboarding = needShowDeviceOnboarding,
            needShowAlarmTime = needShowAlarmTime
        )
    }


    private fun isSignIn(onResult: (Boolean) -> Unit){
        authService.isSignIn {
            onResult(it)
        }
    }

    private fun isTermsFinished(): Boolean =
        pref.getBoolean("termsAccepted", false)


    private fun isUserOnboarded(onResult: (Boolean) -> Unit){
        //Todo:  is isUserOnboarded
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                delay(500)
                onResult(false)
            }
        }
    }
    private fun isDeviceOnboarded(onResult: (Boolean) -> Unit){
        //Todo:  is isDeviceOnboarded
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                delay(500)
                onResult(false)
            }
        }
    }

    private fun isAlarmTime(onResult: (Boolean) -> Unit){
        //Todo:  is isAlarmTime
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                delay(500)
                onResult(false)
            }
        }
    }
}