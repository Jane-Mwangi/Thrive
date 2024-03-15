//package design.fiti.thrive.data.repository
//
//import Resource
//import android.util.Log
//import design.fiti.thrive.core.util.httpError
//import design.fiti.thrive.core.util.ioError
//import design.fiti.thrive.data.local.AppDao
//import design.fiti.thrive.data.remote.ThriveApi
//import design.fiti.thrive.data.remote.dto.ExpenseDto
//import design.fiti.thrive.data.remote.dto.UserDto
//import design.fiti.thrive.domain.model.DeleteResponse
//import design.fiti.thrive.domain.model.Expense
//import design.fiti.thrive.domain.repository.ExpenseRepository
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
//class ExpenseRepositoryImplementation(
//    private val api: ThriveApi,
//    private val dao: AppDao,
//    private val userPreferences: UserPreferencesRepository
//) : ExpenseRepository {
//
//    override suspend fun createUserExpense(expense: ExpenseDto): Flow<Resource<String>> = flow {
//        emit(Resource.Loading())
//        var authToken: String =
//            userPreferences.authToken.stateIn(CoroutineScope(Job() + Dispatchers.IO)).value
//        try {
//            val response = api.createUserExpense(expense = expense, authToken = authToken)
//            Log.d(
//                "Home Vm  upload.......................",
//                "(createNewExpense) Its successefully returned: ${response.toString()}"
//            )
//            emit(Resource.Success(data = "Successfully created your expense"))
//        } catch (e: HttpException) {
//            Log.d(
//                "Home Vm  upload.......................",
//                "(createNewExpense) Create Expense Api Error Called on Expense repo ${e.message} ${e.message()} ${
//                    e.response()
//                } The expense data : ${expense}"
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
//                "(CreateExpense) Create expense Api Error Called on expense repo impl ${e.message} The New expense data : ${expense}"
//            )
//        }
//    }
//
//    override suspend fun deleteUserExpense(expenseId: String): Flow<Resource<DeleteResponse>> =
//        flow {
//            emit(Resource.Loading())
//            var authToken: String =
//                userPreferences.authToken.stateIn(CoroutineScope(Job() + Dispatchers.IO)).value
//            try {
//
//                val result =
//                    api.deleteUserExpense(
//                        authToken = authToken,
//                        id = expenseId,
//                    )
//                emit(Resource.Success(data = result.toDeleteResponse()))
//            } catch (e: HttpException) {
//
//                Log.d(
//                    "Home Vm What's Gotten",
//                    "Bug in Repo: ${e.response()} ${e.message()} ${e.message} ${e.localizedMessage}"
//                )
//
//                emit(Resource.Error("${httpError} ${e.message}"))
//
//            } catch (e: IOException) {
//                Log.d(
//                    "Home Vm What's Gotten",
//                    "Bug in Repo:  ${e.message} ${e.localizedMessage}"
//                )
//                emit(Resource.Error("${ioError} ${e.message}"))
//            }
//
//        }
//
//    override suspend fun getUserExpense(userId: String): Flow<Resource<List<Expense>>> = flow {
//        emit(Resource.Loading())
//
//        var cachedExpenses: List<Expense> =
//            dao.getAllExpense().stateIn(CoroutineScope(Job() + Dispatchers.IO)).value.map {
//                it.toExpense()
//            }
//
//        var authToken: String =
//            userPreferences.authToken.stateIn(CoroutineScope(Job() + Dispatchers.IO)).value
//        Log.d("Home Vm", "data in Repo TOKEN stored: ${authToken}")
//
//        emit(Resource.Loading(data = cachedExpenses))
//
//        try {
//            val fetchedExpenses = api.getExpense_forTheUser(
//                authToken = authToken, userId = userId,
//            )
//            Log.d("Home Vm", "data in Repo: ${fetchedExpenses}")
//
//            dao.deleteAllExpense()
//            fetchedExpenses.forEach() {
//                dao.insertExpense(it.toExpenseEntity())
//            }
//        } catch (e: HttpException) {
//            Log.d(
//                "Home Vm What is fetched",
//                "Bug in Repo: ${e.response()} ${e.message()} ${e.message} ${e.localizedMessage}"
//            )
//
//            emit(Resource.Error(httpError, cachedExpenses))
//        } catch (e: IOException) {
//            Log.d(
//                "Home Vm What is fetched",
//                "Bug in Repo:  ${e.message} ${e.localizedMessage}"
//            )
//
//        }
//
//        var newExpenses: List<Expense> =
//            dao.getAllExpense()
//                .stateIn(CoroutineScope(Job() + Dispatchers.IO)).value.map { it.toExpense() }
//
//        emit(Resource.Success(newExpenses))
//
//    }
//
//    override suspend fun getUserDetails(email: String): Flow<Resource<UserDto?>> = flow {
//        emit(Resource.Loading())
//        var authToken: String =
//            userPreferences.authToken.stateIn(CoroutineScope(Job() + Dispatchers.IO)).value
//        Log.d("Home Vm", "data in Repo TOKEN stored GetUserDetails: ${authToken}")
//        try {
//            val userDetails =
//                api.getUserDetails(authToken = authToken, email = email)
//            Log.d("Home Vm", "data about user in Repo Get Details: $userDetails")
//            emit(Resource.Success(userDetails))
//
//        } catch (e: HttpException) {
//            Log.d(
//                "Home Vm",
//                "Bug in Repo GetUserDetails: ${e.response()} ${e.message()} ${e.message} ${e.localizedMessage}"
//            )
//            emit(Resource.Error(httpError))
//
//        } catch (e: IOException) {
//            Log.d(
//                "Home Vm",
//                "Bug in Repo at GetUserDetails:  ${e.message} ${e.localizedMessage}"
//            )
//            emit(Resource.Error(ioError))
//        }
//
//    }
//}