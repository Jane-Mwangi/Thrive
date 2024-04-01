package design.fiti.thrive.presentation.navigation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import design.fiti.thrive.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NavViewModel @Inject constructor(
    private val savedPreferences: UserPreferencesRepository
) : ViewModel() {
    private var _isSigningInFinished = MutableStateFlow(false)
    val isSigningInFinished = _isSigningInFinished.asStateFlow()
    var loadState: MutableStateFlow<CurrentCondition> = MutableStateFlow(CurrentCondition.Loading)
    var isReadyForHome = MutableStateFlow(false)


    init {
        getPreferences()
        getIsReadyForHomePreference()
    }

    fun updateIsReadyForHomePreference(isReadyForHome: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                savedPreferences.saveReadyForHomePreference(isReadyForHome)
            }
        }
    }

    fun getPreferences() {
        loadState.update { CurrentCondition.Loading }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _isSigningInFinished.update { getSignInStatus() }
                Log.d(
                    "Thrive Back Navigation",
                    "After the update in app nav VM isOnboardingFinished: ${_isSigningInFinished.value} " +
                            "and the function returns ${getSignInStatus()}"
                )
                if (_isSigningInFinished.value) {
                    loadState.update { CurrentCondition.Done }
                } else {
                    loadState.update { CurrentCondition.Stopped }
                }

                Log.d("Thrive Back Navigation", "Load State In ViewModel : ${loadState.value} ")

            }
        }
    }

    fun getIsReadyForHomePreference() {
        loadState.update { CurrentCondition.Loading }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                isReadyForHome.update { getIsReadyForHomeStatus() }
                Log.d(
                    "Thrive Back Navigation",
                    "After the update in app nav VM isOnboardingFinished: ${isReadyForHome.value} " +
                            "and the function returns ${getIsReadyForHomeStatus()}"
                )
                if (isReadyForHome.value) {
                    loadState.update { CurrentCondition.Done }
                } else {
                    loadState.update { CurrentCondition.Stopped }
                }

                Log.d("Thrive Back Navigation", "Load State In ViewModel : ${loadState.value} ")

            }
        }
    }

    private suspend fun getSignInStatus(): Boolean {
        return savedPreferences.authToken.stateIn(viewModelScope).value.isNotEmpty()
    }

    private suspend fun getIsReadyForHomeStatus(): Boolean {

        return savedPreferences.authToken.stateIn(viewModelScope).value.isNotEmpty()
    }
}

sealed class CurrentCondition {
    object Loading : CurrentCondition()
    object Done : CurrentCondition()
    object Stopped : CurrentCondition()
}