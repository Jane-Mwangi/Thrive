package design.fiti.thrive.data.repository

import android.util.Log
import design.fiti.thrive.core.util.Resource
import design.fiti.thrive.data.remote.ThriveApi
import design.fiti.thrive.domain.model.User
import design.fiti.thrive.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImplementation(private val api: ThriveApi) : AuthRepository {
    override suspend fun loginUser(user: User): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            val token: String = api.login(user.toUserDto())
            emit(Resource.Success(token))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong! ${e.message()}",
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection.${e.message}",
                )
            )
        }

    }

    override suspend fun signUpUser(user: User): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            val outcome: String = api.signUp(user.toUserDto())
            emit(Resource.Success(outcome))
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = "Oops, something went wrong! ${e.message()}",
                )
            )
            Log.d("SignUpViewModel Repo Impl", " Errored ${e.message()}")
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection.${e.message}",
                )
            )
            Log.d("SignUpViewModel Repo Impl", " Errored ${e.message}")
        }

    }
}