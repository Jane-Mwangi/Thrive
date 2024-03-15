package design.fiti.thrive.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import design.fiti.thrive.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferencesImplementation @Inject constructor(
    private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences>
) : UserPreferencesRepository {
    private companion object {

        val AUTH_TOKEN = stringPreferencesKey("auth_token")
        val DEVICE_TOKEN = stringPreferencesKey("devuce_token")
        const val TAG = "UserPreferencesRepo"
    }

    override suspend fun saveTokenPreference(authTokenArg: String) {
        Log.d("Nifixie Back Again", "saveTokenPreference: Clicked ")
        Log.d("Nifixie Back Again", "saveTokenPreference: Clicked $authTokenArg ")
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN] = authTokenArg
        }
        Log.d("Nifixie Back Again", "saveTokenPreference: Token Should have been saved... ")
    }

    override suspend fun saveDeviceTokenPreference(deviceTokenArg: String) {
        dataStore.edit { preferences ->
            preferences[DEVICE_TOKEN] = deviceTokenArg
        }
    }

    override suspend fun clearPreferences() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    override val authToken: Flow<String> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading auth token preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[AUTH_TOKEN] ?: ""
        }

    override val deviceToken: Flow<String> = dataStore.data.catch {
        if (it is IOException) {
            Log.e(TAG, "Error reading device token preferences.", it)
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preferences ->
        preferences[DEVICE_TOKEN] ?: ""
    }

    override fun readAuthToken(): Flow<String> {
        return dataStore.data.catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading auth token preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preferences ->
            val currentTokenString = preferences[AUTH_TOKEN] ?: ""
            currentTokenString
        }
    }
}