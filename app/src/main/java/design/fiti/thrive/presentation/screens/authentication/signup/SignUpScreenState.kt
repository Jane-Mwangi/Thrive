package design.fiti.thrive.presentation.screens.authentication.signup



data class SignUpScreenState(
    val email: String = "",
    val emailError: String = "",
    val password: String = "",
    val passwordError: String = "",
    val confirmPassword: String = "",
    val confirmPasswordError: String = "",
    val isLoading: WhenToNavigate = WhenToNavigate.Stopped,
    val apiResult: String? = null
)
