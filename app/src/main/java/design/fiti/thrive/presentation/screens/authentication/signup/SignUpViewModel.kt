package design.fiti.thrive.presentation.screens.authentication.signup

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import design.fiti.thrive.core.util.Resource
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
        val user = User(email = uiState.value.email, password = uiState.value.password)
        if (validateEmail() &&
            validatePassword() &&
            validateConfirmPassword()
        )
            viewModelScope.launch {
                repository.signUpUser(user = user).onEach { kileItarudi ->
                    when (kileItarudi) {
                        is Resource.Success -> {
                            _uiState.value = uiState.value.copy(
                                apiResult = kileItarudi.data,
                                isLoading = false
                            )
                        }

                        is Resource.Error -> {
                            _uiState.value = uiState.value.copy(
                                apiResult = kileItarudi.data,
                                isLoading = false
                            )
                            Log.d("SignUpViewModel", " Errored ${kileItarudi.message}")
                        }

                        is Resource.Loading -> {
                            _uiState.value = uiState.value.copy(
                                apiResult = kileItarudi.data,
                                isLoading = true
                            )
                        }
                    }
                }.launchIn(this)
            }
    }

    //validate
    fun validateEmail(): Boolean {
        val result: Boolean = Patterns.EMAIL_ADDRESS.matcher(
            uiState.value.email
        ).matches()
        if (result) {
            _uiState.update {
                it.copy(emailError = "That's a valid email")

            }
            return true
        } else {
            _uiState.update {
                it.copy(emailError = "Please enter a valid email")

            }
            return false
        }
    }

    fun validatePassword(): Boolean {
        //length of characters
//        uiState.value.password.length >= 8

//        uiState.value.password.matches(regex = Regex( ))
        if (uiState.value.password.length >= 8) {
            _uiState.update {
                it.copy(passwordError = "That's a great password")

            }
            return true
        } else {
            _uiState.update {
                it.copy(passwordError = "Password must be at least characters")

            }
            return false
        }
    }

    fun validateConfirmPassword(): Boolean {
        if (uiState.value.confirmPassword == uiState.value.password) {
            _uiState.update {
                it.copy(confirmPasswordError = "Password matched ,yaay😙")

            }
            return true
        } else {
            _uiState.update {
                it.copy(confirmPasswordError = "Password MUST match")

            }
            return false
        }
    }


}