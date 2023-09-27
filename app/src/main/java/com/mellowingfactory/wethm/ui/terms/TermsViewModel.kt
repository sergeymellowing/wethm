package com.mellowingfactory.wethm.ui.terms

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mellowingfactory.wethm.Repositories

class TermsViewModel(
    val onReadyTerms: () -> Unit
): ViewModel() {

    private val pref = Repositories.preferences

    val termsState = mutableStateOf(TermsState())

    fun updateTermsState(
        allAccept: Boolean? = null,
        termsAndCondition: Boolean = termsState.value.termsAndCondition,
        privacyPolicy: Boolean = termsState.value.privacyPolicy,
        userDataPolicy: Boolean = termsState.value.userDataPolicy,
        marketingPolicy: Boolean = termsState.value.marketingPolicy
    ){
        termsState.value = termsState.value.copy(
            termsAndCondition = termsAndCondition,
            privacyPolicy = privacyPolicy,
            userDataPolicy = userDataPolicy,
            marketingPolicy = marketingPolicy
        )
        allAccept?.let{
            termsState.value = TermsState().allState(it)
        }
    }

    fun saveTerms(){//TODO: Need send to api
        pref.setBoolean("termsAccepted", true)
        onReadyTerms()
    }
}