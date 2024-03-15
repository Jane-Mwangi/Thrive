//package design.fiti.thrive.data.repository
//
//import Resource
//import android.util.Log
//import design.fiti.thrive.core.util.httpError
//import design.fiti.thrive.core.util.ioError
//import design.fiti.thrive.data.local.AppDao
//import design.fiti.thrive.data.remote.ThriveApi
//import design.fiti.thrive.data.remote.dto.IncomeDto
//import design.fiti.thrive.domain.model.DeleteResponse
//import design.fiti.thrive.domain.model.Income
//import design.fiti.thrive.domain.repository.IncomeRepository
//import design.fiti.thrive.domain.repository.UserPreferencesRepository
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//import kotlinx.coroutines.flow.stateIn
//import retrofit2.HttpException
//import java.io.IOException
//
//class IncomeRepositoryImplementation(
//    private val api: ThriveApi,
//    private val dao: AppDao,
//    private val userPreferences: UserPreferencesRepository
//) : IncomeRepository {
//
//
//    override suspend fun createUserIncome(income: IncomeDto): Flow<Resource<String>> = flow {
//        emit(Resource.Loading())
//        val authToken: String =
//            userPreferences.authToken.stateIn(CoroutineScope(Job() + Dispatchers.IO)).value
//        try {
//            val response = api.createUserIncome(income = income, authToken = authToken)
//            Log.d(
//                "Home Vm  upload.......................",
//                "(createNewIncome) Its successefully returned: ${response.toString()}"
//            )
//            emit(Resource.Success(data = "Successfully created your income"))
//        } catch (e: HttpException) {
//            Log.d(
//                "Home Vm  upload.......................",
//                "(createNewIncome) Create Income Api Error Called on Income repo ${e.message} ${e.message()} ${
//                    e.response()
//                } The income data : ${income}"
//            )
//            emit(
//                Resource.Error(
//                    e.message()
//                )
//            )
//        } catch (e: IOException) {
//            emit(Resource.Error(ioError + "::" + e.message))
//            Log.d(
//                "Home Vm  upload.......................",
//                "(CreateIncome) Create income Api Error Called on income repo impl ${e.message} The New expense data : ${income}"
//            )
//        }
//    }
//
//    override suspend fun deleteUserIncome(incomeId: String): Flow<Resource<DeleteResponse>> =
//        flow {
//            emit(Resource.Loading())
//            val authToken: String =
//                userPreferences.authToken.stateIn(CoroutineScope(Job() + Dispatchers.IO)).value
//            try {
//
//                val result =
//                    api.deleteUserIncome(
//                        authToken = authToken,
//                        id = incomeId
//                    )
//                emit(Resource.Success(data = result.toDeleteResponse()))
//            } catch (e: HttpException) {
//
//                Log.d(
//                    "Home Vm What's Gotten",
//                    "Bug in Repo: ${e.response()} ${e.message()} ${e.message} ${e.localizedMessage}"
//                )
//
//                emit(Resource.Error("$httpError ${e.message}"))
//
//            } catch (e: IOException) {
//                Log.d(
//                    "Home Vm What's Gotten",
//                    "Bug in Repo:  ${e.message} ${e.localizedMessage}"
//                )
//                emit(Resource.Error("$ioError ${e.message}"))
//            }
//
//        }
//
//    override suspend fun getUserIncome(userId: String): Flow<Resource<List<Income>>> = flow {
//        emit(Resource.Loading())
//
//        var cachedIncomes: List<Income> =
//            dao.getAllIncome().stateIn(CoroutineScope(Job() + Dispatchers.IO)).value.map {
//                it.toIncome()
//            }
//
//        var authToken: String =
//            userPreferences.authToken.stateIn(CoroutineScope(Job() + Dispatchers.IO)).value
//        Log.d("Home Vm", "data in Repo TOKEN stored: ${authToken}")
//
//        emit(Resource.Loading(data = cachedIncomes))
//
//        try {
//            val fetchedIncomes = api.getIncome_forTheUser(
//                authToken = authToken, userId = userId,
//            )
//            Log.d("Home Vm", "data in Repo: ${fetchedIncomes}")
//
//            dao.deleteAllIncome()
//            fetchedIncomes.forEach() {
//                dao.insertIncome(it.toIncomeEntity())
//            }
//        } catch (e: HttpException) {
//            Log.d(
//                "Home Vm What is fetched",
//                "Bug in Repo: ${e.response()} ${e.message()} ${e.message} ${e.localizedMessage}"
//            )
//
//            emit(Resource.Error(httpError, cachedIncomes))
//        } catch (e: IOException) {
//            Log.d(
//                "Home Vm What is fetched",
//                "Bug in Repo:  ${e.message} ${e.localizedMessage}"
//            )
//
//        }
//
//        var newIncomes: List<Income> =
//            dao.getAllIncome()
//                .stateIn(CoroutineScope(Job() + Dispatchers.IO)).value.map { it.toIncome() }
//
//        emit(Resource.Success(newIncomes))
//
//    }
//
//
//}