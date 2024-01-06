package design.fiti.thrive.presentation.screens.authentication.signup

import design.fiti.thrive.core.util.Resource

data class SignUpScreenState(
    val email: String = "",
    val emailError: String = "",
    val password: String = "",
    val passwordError: String = "",
    val confirmPassword: String = "",
    val confirmPasswordError: String = "",
    val isLoading: Boolean = true,
    val apiResult: String? = null
)
