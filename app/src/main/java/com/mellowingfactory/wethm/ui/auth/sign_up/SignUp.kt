package com.mellowingfactory.wethm.ui.auth.sign_up

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mellowingfactory.wethm.ui.auth.AuthRoutes
import com.mellowingfactory.wethm.ui.auth.sign_up.code.Code
import com.mellowingfactory.wethm.ui.auth.sign_up.entering_name.EnteringName
import com.mellowingfactory.wethm.ui.auth.sign_up.entering_password.EnteringPassword
import com.mellowingfactory.wethm.ui.auth.sign_up.restore_password.RestorePassword
import kotlinx.coroutines.CoroutineScope


fun NavGraphBuilder.signUpGraph(
    controller: NavController,
    goToLogin: () -> Unit,
    onSignIn: (scope: CoroutineScope) -> Unit
) {

    navigation(startDestination = SignUpRoutes.NAME, route = AuthRoutes.SIGN_UP) {

        val vm by lazy { SignUpViewModel(controller) }

        val goEnteringCode = { controller.navigate(SignUpRoutes.CODE) }
        val goEnteringPassword = { controller.navigate(SignUpRoutes.PASSWORD) }
        val goBack: () -> Unit = { controller.navigateUp() }

        composable(SignUpRoutes.NAME){
            EnteringName(
                vm = vm,
                goToLogin = goToLogin,
                goEnteringPassword = goEnteringPassword
            )
        }
        composable(SignUpRoutes.RESTORE_PASSWORD){
            RestorePassword(
                vm = vm,
                goBack = goToLogin,
                goEnteringCode = goEnteringCode
            )
        }
        composable(SignUpRoutes.PASSWORD){
            EnteringPassword(
                vm = vm,
                goBack = goBack,
                goEnteringCode = goEnteringCode,
                goToLogin = goToLogin
            )
        }
        composable(SignUpRoutes.CODE){
            Code(
                vm = vm,
                goBack = goBack,
                onSignIn = onSignIn
            )
        }
    }
}
