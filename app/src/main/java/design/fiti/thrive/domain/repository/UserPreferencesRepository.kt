package design.fiti.thrive.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    suspend fun saveTokenPreference(authToken: String)
    suspend fun clearPreferences()
    suspend fun saveDeviceTokenPreference(deviceToken: String)

    val authToken: Flow<String>
    val deviceToken: Flow<String>
    fun readAuthToken(): Flow<String>
}