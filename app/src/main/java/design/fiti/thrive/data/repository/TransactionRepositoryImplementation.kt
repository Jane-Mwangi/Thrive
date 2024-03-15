package design.fiti.thrive.data.repository

import Resource
import android.util.Log
import design.fiti.thrive.core.util.httpError
import design.fiti.thrive.core.util.ioError
import design.fiti.thrive.data.local.AppDao
import design.fiti.thrive.data.remote.ThriveApi
import design.fiti.thrive.data.remote.dto.CreateTransactionDto
import design.fiti.thrive.domain.model.DeleteResponse
import design.fiti.thrive.domain.model.UserTransaction
import design.fiti.thrive.domain.repository.TransactionRepository
import design.fiti.thrive.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import retrofit2.HttpException
import java.io.IOException

class TransactionRepositoryImplementation(
    private val api: ThriveApi,
    private val dao: AppDao,
    private val userPreferences: UserPreferencesRepository
):TransactionRepository {

    override  suspend fun createUserTransaction( createTransaction: CreateTransactionDto): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        var authToken: String =
            userPreferences.authToken.stateIn(CoroutineScope(Job() + Dispatchers.IO)).value
        try {
            val response = api.createUserTransaction(createTransaction = createTransaction,authToken=authToken)
            Log.d(
                "Home Vm  upload.......................",
                "(createNewExpense) Its successefully returned: ${response.toString()}"
            )
            emit(Resource.Success(data = "Successfully created your expense"))
        } catch (e: HttpException) {
            Log.d(
                "Home Vm  upload.......................",
                "(createNewExpense) Create Expense Api Error Called on Expense repo ${e.message} ${e.message()} ${
                    e.response()
                } The expense data : ${createTransaction}"
            )
            emit(
                Resource.Error(
                    e.message()
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error(ioError + "::" + e.message))
            Log.d(
                "Home Vm  upload.......................",
                "(CreateTransaction) Create expense Api Error Called on expense repo impl ${e.message} The New expense data : ${createTransaction}"
            )
        }
    }

    override suspend fun deleteUserTransaction(transactionId: String): Flow<Resource<DeleteResponse>> =
        flow {
            emit(Resource.Loading())
            var authToken: String =
                userPreferences.authToken.stateIn(CoroutineScope(Job() + Dispatchers.IO)).value
            try {

                val result =
                    api.deleteUserTransaction(
                        authToken = authToken,
                        transactionId = transactionId,
                    )
                emit(Resource.Success(data = result.toDeleteResponse()))
            } catch (e: HttpException) {

                Log.d(
                    "Home Vm What's Gotten",
                    "Bug in Repo: ${e.response()} ${e.message()} ${e.message} ${e.localizedMessage}"
                )

                emit(Resource.Error("$httpError ${e.message}"))

            } catch (e: IOException) {
                Log.d(
                    "Home Vm What's Gotten",
                    "Bug in Repo:  ${e.message} ${e.localizedMessage}"
                )
                emit(Resource.Error("$ioError ${e.message}"))
            }

        }

    override suspend fun getUserExpense(): Flow<Resource<List<UserTransaction>>> = flow {
        emit(Resource.Loading())

        var cachedExpenses: List<UserTransaction> =
            dao.getAllUserExpense().stateIn(CoroutineScope(Job() + Dispatchers.IO)).value.map {
                it.toUserTransaction()
            }

        var authToken: String =
            userPreferences.authToken.stateIn(CoroutineScope(Job() + Dispatchers.IO)).value
        Log.d("Home Vm", "data in Repo TOKEN stored: ${authToken}")

        emit(Resource.Loading(data = cachedExpenses))

        try {
            val fetchedExpenses = api.getExpense_forTheUser(
                authToken = authToken,
            )
            Log.d("Home Vm", "data in Repo: ${fetchedExpenses}")

            dao.deleteAllUserTransaction()
            fetchedExpenses.forEach() {
                dao.insertUserTransaction(it.toUserTransactionEntity())
            }
        } catch (e: HttpException) {
            Log.d(
                "Home Vm What is fetched",
                "Bug in Repo: ${e.response()} ${e.message()} ${e.message} ${e.localizedMessage}"
            )

            emit(Resource.Error(httpError, cachedExpenses))
        } catch (e: IOException) {
            Log.d(
                "Home Vm What is fetched",
                "Bug in Repo:  ${e.message} ${e.localizedMessage}"
            )

        }

        var newExpenses: List<UserTransaction> =
            dao.getAllUserExpense()
                .stateIn(CoroutineScope(Job() + Dispatchers.IO)).value.map { it.toUserTransaction() }

        emit(Resource.Success(newExpenses))

    }

    override suspend fun getUserIncome(): Flow<Resource<List<UserTransaction>>> = flow {
        emit(Resource.Loading())

        var cachedExpenses: List<UserTransaction> =
            dao.getAllUserIncome().stateIn(CoroutineScope(Job() + Dispatchers.IO)).value.map {
                it.toUserTransaction()
            }

        var authToken: String =
            userPreferences.authToken.stateIn(CoroutineScope(Job() + Dispatchers.IO)).value
        Log.d("Home Vm", "data in Repo TOKEN stored: ${authToken}")

        emit(Resource.Loading(data = cachedExpenses))

        try {
            val fetchedIncomes = api.getIncome_forTheUser(
                authToken = authToken,
            )
            Log.d("Home Vm", "data in Repo: ${fetchedIncomes}")

            dao.deleteAllUserTransaction()
            fetchedIncomes.forEach() {
                dao.insertUserTransaction(it.toUserTransactionEntity())
            }
        } catch (e: HttpException) {
            Log.d(
                "Home Vm What is fetched",
                "Bug in Repo: ${e.response()} ${e.message()} ${e.message} ${e.localizedMessage}"
            )

            emit(Resource.Error(httpError, cachedExpenses))
        } catch (e: IOException) {
            Log.d(
                "Home Vm What is fetched",
                "Bug in Repo:  ${e.message} ${e.localizedMessage}"
            )

        }

        var newIncomes: List<UserTransaction> =
            dao.getAllUserExpense()
                .stateIn(CoroutineScope(Job() + Dispatchers.IO)).value.map { it.toUserTransaction() }

        emit(Resource.Success(newIncomes))

    }



    }
