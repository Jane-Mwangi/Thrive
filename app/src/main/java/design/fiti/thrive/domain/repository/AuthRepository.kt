package design.fiti.thrive.domain.repository

import Resource
import design.fiti.thrive.domain.model.User
import kotlinx.coroutines.flow.Flow


interface AuthRepository {
    suspend fun loginUser(user: User): Flow<Resource<String>>
    suspend fun signUpUser(user: User): Flow<Resource<String>>
}