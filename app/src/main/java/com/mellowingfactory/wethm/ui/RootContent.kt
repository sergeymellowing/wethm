package com.mellowingfactory.wethm.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mellowingfactory.wethm.ui.auth.authGraph
import com.mellowingfactory.wethm.ui.onboardings.user.UserOnboarding
import com.mellowingfactory.wethm.ui.splash.Splash
import com.mellowingfactory.wethm.ui.terms.Terms
import com.mellowingfactory.wethm.ui.today.TodayScreen
import com.mellowingfactory.wethm.utils.safeNavigate
import com.mellowingfactory.wethm.utils.safeNavigateAndClear

@Preview
@Composable
fun RootContent() {
    val controller = rememberNavController()

    val vm = remember { RootViewModel(controller) }
    val scope = rememberCoroutineScope()

    NavHost(
        navController = controller,
        startDestination = RootRoutes.TODAY
    ) {
        composable(route = RootRoutes.SPLASH) { Splash() }

        composable(route = RootRoutes.APP_ONBOARDING) { Text("") }

        authGraph(controller, vm)

        composable(route = RootRoutes.TERMS) {
            Terms {
                vm.updateStates(needShowTerms = false)
                controller.safeNavigateAndClear(scope, vm.getActualRoute())
            }
        }

        composable(route = RootRoutes.USER_ONBOARDING) {
            UserOnboarding {
                vm.updateStates(needShowUserOnboarding = false)
                controller.safeNavigate(scope, vm.getActualRoute())
            }
        }

        composable(route = RootRoutes.DEVICE_ONBOARDING) {
            Text(text = "\n\ndevice onboardind!!")
        }
        composable(route = RootRoutes.ALARM_TIME) {
            Text(text = "\n\nAlarm Time!!")
        }

        composable(route = RootRoutes.TODAY) {
            TodayScreen()
        }
    }
}