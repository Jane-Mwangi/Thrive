package design.fiti.thrive.domain.repository

import Resource
import design.fiti.thrive.data.remote.dto.CreateTransactionDto
import design.fiti.thrive.data.remote.dto.UserDto
import design.fiti.thrive.domain.model.DeleteResponse
import design.fiti.thrive.domain.model.UserTransaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun createUserTransaction(
        createTransaction: CreateTransactionDto
    ): Flow<Resource<String>>

    suspend fun deleteUserTransaction(
        transactionId: String
    ): Flow<Resource<DeleteResponse>>

    suspend fun getUserExpense(): Flow<Resource<List<UserTransaction>>>
    suspend fun getUserIncome(): Flow<Resource<List<UserTransaction>>>

}