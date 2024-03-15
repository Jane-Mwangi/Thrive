//package design.fiti.thrive.domain.repository
//
//import Resource
//import design.fiti.thrive.data.remote.dto.ExpenseDto
//import design.fiti.thrive.data.remote.dto.UserDto
//import design.fiti.thrive.domain.model.DeleteResponse
//import design.fiti.thrive.domain.model.Expense
//import kotlinx.coroutines.flow.Flow
//
//
//interface ExpenseRepository {
//    suspend fun createUserExpense(
//        expense: ExpenseDto
//    ): Flow<Resource<String>>
//
//    suspend fun deleteUserExpense(
//        expenseId: String
//    ): Flow<Resource<DeleteResponse>>
//
//    suspend fun getUserExpense(userId: String): Flow<Resource<List<Expense>>>
//    suspend fun getUserDetails(email: String): Flow<Resource<UserDto?>>
//
//}