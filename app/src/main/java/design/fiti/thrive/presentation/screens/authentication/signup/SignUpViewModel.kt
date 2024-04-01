package design.fiti.thrive.presentation.screens.authentication.signup

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
//import design.fiti.thrive.core.util.Resource
import design.fiti.thrive.domain.model.User
import design.fiti.thrive.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private var _uiState = MutableStateFlow(
        SignUpScreenState()
    )
    val uiState: StateFlow<SignUpScreenState> = _uiState.asStateFlow()

    fun updateEmailInput(text: String) {
        _uiState.update {
            it.copy(
                email = text
            )
        }
        validateEmail()

    }

    fun updatePasswordInput(text: String) {
        _uiState.update {
            it.copy(
                password = text
            )
        }
        validatePassword()
    }

    fun updateConfirmPasswordInput(text: String) {
        _uiState.update {
            it.copy(
                confirmPassword = text
            )
        }
        validateConfirmPassword()
    }


    fun signUp() {
        _uiState.update {
            it.copy(
                isLoading = WhenToNavigate.Processing
            )
        }
        Log.d(
            "Sign up//////////////////////////////////////////",
            "signup called Email: ${uiState.value.email} Password : ${uiState.value.password}"
        )
        val user = User(email = uiState.value.email, password = uiState.value.password)
        if (validateEmail() && validatePassword() && validateConfirmPassword())
            viewModelScope.launch {
                try {
                    repository.signUpUser(user = user).onEach { kileItarudi ->
                        when (kileItarudi) {
                            is Resource.Success -> {
                                _uiState.value = uiState.value.copy(
                                    apiResult = kileItarudi.data.toString(),
                                    isLoading = WhenToNavigate.Go,
                                    email = "",
                                    password = "",
                                    confirmPassword = ""

                                )
                            }

                            is Resource.Error -> {
                                _uiState.value = uiState.value.copy(
                                    apiResult = kileItarudi.message,
                                    isLoading = WhenToNavigate.Stopped
                                )
                                Log.d("SignUpViewModel", " Errored ${kileItarudi.message}")
                            }

                            is Resource.Loading -> {
                                _uiState.value = uiState.value.copy(
                                    apiResult = kileItarudi.message,
                                    isLoading = WhenToNavigate.Processing
                                )
                            }

                            else -> {}
                        }
                    }.launchIn(this)
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            isLoading = WhenToNavigate.Stopped,
                            apiResult = e.localizedMessage?.toString(),
                        )
                    }
                }

            }
    }

    fun LogIn() {
        _uiState.update {
            it.copy(
                isLoading = WhenToNavigate.Processing
            )
        }
        Log.d(
            "Login Shit",
            " Login called Email: ${uiState.value.email} Password : ${uiState.value.password}"
        )
        val user = User(email = uiState.value.email, password = uiState.value.password)
        if (validateEmail() && validatePassword())
            viewModelScope.launch {
                try {
                    repository.loginUser(user = user).onEach { response ->
                        when (response) {
                            is Resource.Success -> {
                                _uiState.value = uiState.value.copy(
                                    apiResult = response.data.toString(),
                                    isLoading = WhenToNavigate.Go
                                )
                                Log.d("Login Success", " Success ${response.data}")
                            }

                            is Resource.Error -> {
                                _uiState.value = uiState.value.copy(
                                    apiResult = response.data.toString(),
                                    isLoading = WhenToNavigate.Stopped
                                )
                                Log.d("LoginViewModel", " Errored ${response.message}")
                            }

                            is Resource.Loading -> {
                                _uiState.value = uiState.value.copy(
                                    apiResult = response.data.toString(),
                                    isLoading = WhenToNavigate.Processing
                                )
                                Log.d("Login loading", " Loading///////////////////////")
                            }

                            else -> {}
                        }
                    }.launchIn(this)
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            isLoading = WhenToNavigate.Stopped,
                            apiResult = e.localizedMessage?.toString(),
                        )
                    }
                }

            }
    }

    //validate
    fun validateEmail(): Boolean {
        val result: Boolean = Patterns.EMAIL_ADDRESS.matcher(
            uiState.value.email
        ).matches()
        if (result) {
            _uiState.update {
                it.copy(emailError = "Email is valid.")

            }
            return true
        } else {
            _uiState.update {
                it.copy(
                    emailError = "Please enter a valid email",
                    isLoading = WhenToNavigate.Stopped,
                )

            }
            return false
        }
    }

    fun validatePassword(): Boolean {
        //length of characters
//        uiState.value.password.length >= 8

//        uiState.value.password.matches(regex = Regex( ))
        val count_password_length = 8
        if (uiState.value.password.length >= count_password_length) {
            _uiState.update {
                it.copy(passwordError = "Password is valid")
            }
            return true
        } else {
            _uiState.update {
                it.copy(
                    isLoading = WhenToNavigate.Stopped,
                    passwordError = "Password must be at least $count_password_length characters"
                )

            }
            return false
        }
    }

    fun validateConfirmPassword(): Boolean {
        if (uiState.value.confirmPassword == (uiState.value.password)) {
            _uiState.update {
                it.copy(

                    confirmPasswordError = "Password matched"
                )

            }
            return true
        } else {
            _uiState.update {
                it.copy(
                    isLoading = WhenToNavigate.Stopped,
                    confirmPasswordError = "Password MUST match"
                )

            }
            return false
        }
    }


}

sealed class WhenToNavigate() {
    object Stopped : WhenToNavigate()
    object Processing : WhenToNavigate()
    object Go : WhenToNavigate()
}