package com.mellowingfactory.wethm.utils

import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun NavController.navigateAndClear(route: String){
    navigate(route = route) {
        popUpTo(graph.startDestinationId) { inclusive = true }
    }
}

fun NavController.safeNavigateAndClear(
    scope: CoroutineScope,
    route: String
) = scope.launch{
    withContext(Dispatchers.Main){
        navigateAndClear(route)
    }
}

fun NavController.safeNavigate(
    scope: CoroutineScope,
    route: String
) = scope.launch {
    withContext(Dispatchers.Main){
        navigate(route)
    }
}