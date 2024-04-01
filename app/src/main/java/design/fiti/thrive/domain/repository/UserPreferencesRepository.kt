package design.fiti.thrive.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    suspend fun savesignInPreference(signInFinished: Boolean)
    suspend fun saveReadyForHomePreference(isReadyForHome: Boolean)
    suspend fun saveTokenPreference(authToken: String)
    suspend fun clearPreferences()
    suspend fun saveDeviceTokenPreference(deviceToken: String)

    val authToken: Flow<String>
    val deviceToken: Flow<String>
    val isSignInFinished: Flow<Boolean>
    val isReadyForHome: Flow<Boolean>
    fun readAuthToken(): Flow<String>



}