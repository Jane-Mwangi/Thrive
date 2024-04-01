package design.fiti.thrive.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import design.fiti.thrive.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UpdatePreferencesViewModel @Inject constructor(
    private val userPreferences: UserPreferencesRepository
) : ViewModel() {
    fun updateDeviceTokenPref(deviceToken: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userPreferences.saveDeviceTokenPreference(deviceToken)
            }
        }
    }

    fun updateUserAuthTokenPref(authToken: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userPreferences.saveTokenPreference(authToken)
            }
        }
    }

    var job: Job? = null
    fun getDeviceTokenPref(): String {
        var token = ""
        job = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userPreferences.authToken.collect {
                    token = it
                }
            }
        }
        if (job!!.isCompleted)
            return token
        else
            return "nothing was found"
    }
}