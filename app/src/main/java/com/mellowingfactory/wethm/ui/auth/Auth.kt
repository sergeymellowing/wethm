package com.mellowingfactory.wethm.ui.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mellowingfactory.wethm.ui.RootRoutes
import com.mellowingfactory.wethm.ui.RootViewModel
import com.mellowingfactory.wethm.ui.auth.sign_in.SignIn
import com.mellowingfactory.wethm.ui.auth.sign_up.SignUpRoutes
import com.mellowingfactory.wethm.ui.auth.sign_up.signUpGraph
import com.mellowingfactory.wethm.utils.navigateAndClear
import com.mellowingfactory.wethm.utils.safeNavigateAndClear
import kotlinx.coroutines.CoroutineScope


fun NavGraphBuilder.authGraph(
    controller: NavController,
    rootViewModel: RootViewModel
) {

    val goRestorePassword = { controller.navigate(SignUpRoutes.RESTORE_PASSWORD) }
    val goSignUp = { controller.navigate(AuthRoutes.SIGN_UP) }
    val goToLogin = { controller.navigateAndClear(AuthRoutes.SIGN_IN) }
    val onSignIn: (scope: CoroutineScope) -> Unit = { scope ->
        rootViewModel.updateStates( needLogin = false )
        controller.safeNavigateAndClear(scope, rootViewModel.getActualRoute())
    }

    navigation(
        startDestination = AuthRoutes.SIGN_IN,
        route = RootRoutes.AUTH
    ) {
        composable(AuthRoutes.SIGN_IN){
            SignIn(
                goSignUp = goSignUp,
                goRestorePassword = goRestorePassword,
                onSignIn = onSignIn
            )
        }
        signUpGraph(
            controller = controller,
            goToLogin = goToLogin,
            onSignIn = onSignIn
        )
    }
}


//@Composable
//fun Auth(
//    goNext: () -> Unit
//){
//    val controller = rememberNavController()
//    val goRestorePassword = {
//        controller.navigate(
//            AuthRoutes.SIGN_UP +
//                    "?route=${SignUpRoutes.RESTORE_PASSWORD}")
//    }
//    val goSignUp = {
//        controller.navigate(AuthRoutes.SIGN_UP)
//    }
//    val goToLogin = {
//        controller.navigate(AuthRoutes.SIGN_IN)
//    }
//
//    NavHost(
//        navController = controller,
//        startDestination = AuthRoutes.SIGN_IN
//    ){
//        composable(AuthRoutes.SIGN_IN){
//            SignIn(
//                goSignUp = goSignUp,
//                goRestorePassword = goRestorePassword,
//                goNext = goNext
//            )
//        }
//        composable(
//            route = AuthRoutes.SIGN_UP+"?route={route}",
//            arguments = listOf(navArgument("route") { defaultValue = SignUpRoutes.NAME })
//        ){
//            val route = it.arguments?.getString("route")?: SignUpRoutes.NAME
//            SignUp(
//                startDestination = route,
//                goToLogin = goToLogin,
//                goToSession = goNext
//            )
//        }
//
//    }
//}