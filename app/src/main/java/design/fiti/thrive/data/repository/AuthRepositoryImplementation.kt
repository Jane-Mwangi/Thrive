package design.fiti.thrive.data.repository

import Resource
import android.util.Log

import design.fiti.thrive.data.remote.ThriveApi
import design.fiti.thrive.domain.model.User
import design.fiti.thrive.domain.repository.AuthRepository
import design.fiti.thrive.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImplementation(
    private val api: ThriveApi,
    private val userPreferences: UserPreferencesRepository
) : AuthRepository {
    override suspend fun loginUser(user: User): Flow<Resource<String>> = flow {
        val tag = "Login Shit"
        emit(Resource.Loading())
        try {
            val token: String = api.login(user.toUserDto())
            Log.d(tag, "Token :.................$token")
            if (token.isNotEmpty()) {
                Log.d(tag, "Token NOT EMPTY:.................$token")
                userPreferences.saveTokenPreference(
                    authToken = token
                )
            }
            Log.d(tag, "Token NOT EMPTY:.................$token")
            emit(Resource.Success(userPreferences.authToken.stateIn(CoroutineScope(Dispatchers.IO)).value))
        } catch (e: HttpException) {
            Log.d(
                tag,
                "Errored :.................Message: ${e.message} Localized : ${e.localizedMessage}"
            )
            emit(
                Resource.Error(
                    message = "Oops, something went wrong! ${e.message()} ${e.localizedMessage}",
                )
            )
        } catch (e: IOException) {
            Log.d(
                tag,
                "Errored :.................Message: ${e.message} Localized : ${e.localizedMessage}"
            )
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection.${e.message} ${e.localizedMessage}",
                )
            )
        }

    }

    override suspend fun signUpUser(user: User): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        Log.d(
            "SignUpViewModel Repo Impl",
            " user data is ${user.toString()} ${user.email + " " + user.password} "
        )
        val errorMessage = "Seems like you already created an account. Try logging in."
        try {
            val outcome = api.signUp(user.toUserDto())

            Log.d(
                "SignUpViewModel Repo Impl",
                " success brought ...........${outcome} "
            )
            emit(Resource.Success(data = outcome))

        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong! $errorMessage",
                )
            )
            Log.d(
                "SignUpViewModel Repo Impl",
                " Errored ${e.message() + e.localizedMessage + e.cause} "
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection.${e.localizedMessage}",
                )
            )
            Log.d("SignUpViewModel Repo Impl", " Errored ${e.message}")
        }

    }
}