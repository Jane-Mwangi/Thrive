package design.fiti.thrive.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import design.fiti.thrive.presentation.screens.authentication.login.SignInScreen
import design.fiti.thrive.presentation.screens.authentication.signup.SignUPScreen
import design.fiti.thrive.presentation.screens.home.BottomNavGraph
import design.fiti.thrive.presentation.screens.home.HomeScreen
import design.fiti.thrive.presentation.screens.orientation.OrientationScreen

@Composable
fun ThriveNavigationGraph() {
    val navController = rememberNavController()

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.SignUpScreen.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Routes.OrientationScreen.name) {
                OrientationScreen(
                    navigateToSignUpScreen = {
                        navController.navigate(route = Routes.SignUpScreen.name)
                    },
                    navigateToSignInScreen = {
                        navController.navigate(route = Routes.SignInScreen.name)
                    },
                )
            }
            composable(route = Routes.SignUpScreen.name) {
                SignUPScreen(
                    navigateToSignInScreen = {
                        navController.navigate(route = Routes.SignInScreen.name)
                    })
            }
            composable(route = Routes.SignInScreen.name) {
                SignInScreen(
                    navigateToHomeScreen = {
                        navController.navigate(route = Routes.HomeScreen.name)
                    })
            }
            composable(route = Routes.HomeScreen.name) {
                BottomNavGraph()

            }
        }

    }

}

sealed class Routes(val name: String) {
    object OrientationScreen : Routes(name = "OrientationScreen")
    object SignUpScreen : Routes(name = "SignUpScreen")
    object HomeScreen : Routes(name = "HomeScreen")
    object SignInScreen : Routes(name = "SignInScreen")


}