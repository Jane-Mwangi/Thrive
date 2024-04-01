
package design.fiti.thrive.presentation.navigation

sealed class Screen(
    val route: String
) {
    object Home : Screen(
        route = Routes.HOME.name
    )

    object SignUp : Screen(
        route = Routes.SIGNUP.name
    )

    object SignIn : Screen(
        route = Routes.SIGNIN.name
    )

    object UserTypeFilter : Screen(
        route = Routes.INSIGHTS.name
    )

    object Settings : Screen(
        route = Routes.SETTINGS.name
    )
    object Loading : Screen(
        route = Routes.LOADING.name
    )
    object Orientation : Screen(
        route = Routes.ORIENTATION.name
    )

}


enum class Routes {
    ORIENTATION,HOME,SIGNUP, SIGNIN, SETTINGS, INSIGHTS,LOADING


}