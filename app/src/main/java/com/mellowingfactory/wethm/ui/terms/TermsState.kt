package com.mellowingfactory.wethm.ui.terms

data class TermsState(
    var termsAndCondition: Boolean = false,
    var privacyPolicy: Boolean = false,
    var userDataPolicy: Boolean = false,
    var marketingPolicy: Boolean = false
){
    fun allState(isAccept: Boolean): TermsState{
        termsAndCondition = isAccept
        privacyPolicy = isAccept
        userDataPolicy = isAccept
        marketingPolicy = isAccept
        return this
    }

    fun isAllAccept(): Boolean = termsAndCondition &&
            privacyPolicy && userDataPolicy && marketingPolicy
}
