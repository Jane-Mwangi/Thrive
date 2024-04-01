package design.fiti.thrive.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
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
    private val dataStore: DataStore<Preferences>,
) : UserPreferencesRepository {

    private companion object {
        val SIGN_IN_FINISHED = booleanPreferencesKey("sign_in_finihed")
        val IS_READY_FOR_HOME = booleanPreferencesKey("is_ready_for_home")
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
        val DEVICE_TOKEN = stringPreferencesKey("devuce_token")
        const val TAG = "UserPreferencesRepo"
    }

    override suspend fun savesignInPreference(signInFinished: Boolean) {
        dataStore.edit { preferences ->
            preferences[SIGN_IN_FINISHED] = signInFinished
        }
    }


    override suspend fun saveReadyForHomePreference(isReadyForHome: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_READY_FOR_HOME] = isReadyForHome
        }
    }


    override suspend fun saveTokenPreference(authTokenArg: String) {
        Log.d("Thrive Back Again", "saveTokenPreference: Clicked ")
        Log.d("Thrive Back Again", "saveTokenPreference: Clicked $authTokenArg ")
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN] = authTokenArg
        }
        Log.d("Thrive Back Again", "saveTokenPreference: Token Should have been saved... ")
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

    override val isSignInFinished: Flow<Boolean> = dataStore.data.catch {
        if (it is IOException) {
            Log.e(TAG, "Error reading isSignInFinishhed preferences.", it)
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preferences ->
        preferences[SIGN_IN_FINISHED] ?: false
    }

    override val isReadyForHome: Flow<Boolean> = dataStore.data.catch {
        if (it is IOException) {
            Log.e(TAG, "Error reading isReadyForHome preferences.", it)
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preferences ->
        preferences[IS_READY_FOR_HOME] ?: false
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