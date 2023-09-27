package com.mellowingfactory.wethm.ui

data class RootNavigationState(
    var needLogin: Boolean? = null,
    var needShowTerms: Boolean? = null,
    var needShowUserOnboarding: Boolean? = null,
    var needShowDeviceOnboarding: Boolean? = null,
    var needShowAlarmTime: Boolean? = null
){

    fun isAllInit(): Boolean{
        return needLogin != null &&
                needShowTerms != null &&
                needShowUserOnboarding != null &&
                needShowDeviceOnboarding != null &&
                needShowAlarmTime != null
    }
}