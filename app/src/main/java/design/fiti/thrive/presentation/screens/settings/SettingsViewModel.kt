package design.fiti.thrive.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import design.fiti.thrive.domain.repository.UserPreferencesRepository
import design.fiti.thrive.presentation.screens.authentication.signup.WhenToNavigate
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesRepository: UserPreferencesRepository
) : ViewModel() {
    private var _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()
    fun handlePrefsCleanUp() {
        _uiState.update {
            it.copy(loadState = WhenToNavigate.Processing)
        }
        viewModelScope.launch {
            val result = async {
                preferencesRepository.clearPreferences()
            }
            result.await().also {
                _uiState.update {
                    it.copy(loadState = WhenToNavigate.Go)
                }
            }
        }
    }
}

data class SettingsUiState(
    val loadState: WhenToNavigate = WhenToNavigate.Stopped
)