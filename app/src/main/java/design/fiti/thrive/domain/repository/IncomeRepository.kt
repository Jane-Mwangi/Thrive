//package design.fiti.thrive.domain.repository
//
//
//
//import Resource
//import design.fiti.thrive.data.remote.dto.IncomeDto
//import design.fiti.thrive.domain.model.DeleteResponse
//import design.fiti.thrive.domain.model.Income
//import kotlinx.coroutines.flow.Flow
//
//interface IncomeRepository {
//
//
//    suspend fun createUserIncome(
//        income: IncomeDto
//    ): Flow<Resource<String>>
//
//    suspend fun deleteUserIncome(
//        incomeId: String
//    ): Flow<Resource<DeleteResponse>>
//
//    suspend fun getUserIncome(userId: String): Flow<Resource<List<Income>>>
//
//
//}