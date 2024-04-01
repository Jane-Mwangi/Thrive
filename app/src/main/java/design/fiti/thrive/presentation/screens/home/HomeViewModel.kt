package design.fiti.thrive.presentation.screens.home

import Resource
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import design.fiti.thrive.data.remote.dto.CreateTransactionDto
import design.fiti.thrive.domain.model.Predictions
import design.fiti.thrive.domain.repository.PredictionsRepository
import design.fiti.thrive.domain.repository.TransactionRepository
import design.fiti.thrive.domain.repository.UserPreferencesRepository
import design.fiti.thrive.presentation.screens.authentication.signup.WhenToNavigate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val predictionsRepository: PredictionsRepository,
    val userPreferencesRepository: UserPreferencesRepository


) : ViewModel() {
    private var _uiState = MutableStateFlow(HomeScreenState())
    val uiState = _uiState.asStateFlow()
    val HomeVmTag = ""

    fun updateUserSelectionOption(transactionType: TransactionType) {
        _uiState.update {
            it.copy(activeUserSelectionOption = transactionType)
        }
    }

    fun getSelectedTransactionType(): String {
        val isTransactionCalledIncome =
            uiState.value.activeUserSelectionOption is TransactionType.Income
        return if (isTransactionCalledIncome) "income" else "expense"
    }

    var CreateTransaction: Job? = null
    fun handleCreateTransaction(nameOfYourTransaction: String, amount: Int) {
        CreateTransaction?.cancel()

        _uiState.update {
            it.copy(
                createIncomeLoadState = WhenToNavigate.Processing
            )
        }
        val isTransactionCalledIncome =
            uiState.value.activeUserSelectionOption is TransactionType.Income
        val newTransaction: CreateTransactionDto = CreateTransactionDto(
            amount = amount,
            name = nameOfYourTransaction,
            transactionType = if (isTransactionCalledIncome) "income" else "expense"
        )

        //call your api with just the new transaction

        CreateTransaction = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = transactionRepository.createUserTransaction(
                        newTransaction
                    )
                    response.collect { apiResponse ->

                        when (apiResponse) {
                            is Resource.Success -> {
                                Log.d(
                                    "Uploading new transaction",
                                    "HandleCreateTransaction ${apiResponse.data}"
                                )

                                _uiState.update {
                                    it.copy(createIncomeLoadState = WhenToNavigate.Go)
                                }

                                Log.d(
                                    "VM Upload new transaction",
                                    "Creating Transaction Success"
                                )

                                Log.d(
                                    "Uploading new transaction",
                                    "HandleCreateTransaction ${apiResponse.data}"
                                )
                            }

                            is Resource.Loading -> {
                                Log.d(
                                    "Uploading new transaction",
                                    "HandleCreateTransaction Loading"
                                )

                                _uiState.update {
                                    it.copy(
                                        createIncomeLoadState = WhenToNavigate.Processing
                                    )
                                }
                                Log.d(
                                    "VM Upload new transaction",
                                    "Creating Transaction Loading"
                                )

                                Log.d(
                                    "Uploading new transaction",
                                    "HandleCreateTransaction ${apiResponse.data}"
                                )
                            }

                            is Resource.Error -> {
                                Log.d(
                                    "Uploading new transaction",
                                    "HandleCreateTransaction Error$$$$$$$$$$$$$ ${apiResponse.message}"
                                )
                                _uiState.update {
                                    it.copy(
                                        createIncomeLoadState = WhenToNavigate.Stopped,
                                        uploadingResponseInfo = apiResponse.message ?: ""
                                    )
                                }
                                Log.d(
                                    "Uploading new transaction",
                                    "HandleCreateTransaction Error$$$$$$$$$$$$$ ${apiResponse.message}"
                                )
                            }

                            else -> {}
                        }

                    }
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            createIncomeLoadState = WhenToNavigate.Stopped,
                        )
                    }
                    Log.d(
                        "Uploading new transaction",
                        "HandleCreateTransaction Error$$$$$$$$$$$$$ ${e.message}${e.localizedMessage}"
                    )
                    e.printStackTrace()
                }
            }
        }

    }


    var GetIncomesJob: Job? = null
    fun getAllMyIncomes() {
        GetIncomesJob?.cancel()
        _uiState.update {
            it.copy(getincomesloadState = WhenToNavigate.Processing)
        }
        GetIncomesJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val apiResult = transactionRepository.getUserIncome()

                    apiResult.collect { currentResult ->
                        when (currentResult) {
                            is Resource.Loading -> {
                                _uiState.update {
                                    it.copy(
                                        getincomesloadState = WhenToNavigate.Processing
                                    )
                                }
                            }

                            is Resource.Error -> {
                                _uiState.update {
                                    it.copy(
                                        getincomesloadState = WhenToNavigate.Stopped
                                    )
                                }
                            }

                            is Resource.Success -> {
                                val incomesList =
                                    currentResult.data?.map { userTransaction -> userTransaction.toIncome() }
                                        ?: emptyList()
                                Log.d("UI STuff", "Success part kwa vm ${currentResult.data}")
                                _uiState.update {
                                    it.copy(
                                        getincomesloadState = WhenToNavigate.Go,
                                        income = incomesList

                                    )
                                }
                            }

                            else -> {}
                        }

                    }
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            getincomesloadState = WhenToNavigate.Stopped
                        )
                    }
                    Log.d(
                        "get new transaction",
                        "get transaction Error$$$$$$$$$$$$$ ${e.message}${e.localizedMessage}"
                    )
                    e.printStackTrace()
                }
            }
        }
    }

    var GetExpensesJob: Job? = null
    fun getAllMyExpenses() {
        GetExpensesJob?.cancel()
        _uiState.update {
            it.copy(getExpensesloadState = WhenToNavigate.Processing)
        }
        GetExpensesJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val apiResult = transactionRepository.getUserExpense()

                    apiResult.collect { currentResult ->
                        when (currentResult) {
                            is Resource.Loading -> {
                                _uiState.update {
                                    it.copy(
                                        getExpensesloadState = WhenToNavigate.Processing
                                    )
                                }
                            }

                            is Resource.Error -> {
                                _uiState.update {
                                    it.copy(
                                        getExpensesloadState = WhenToNavigate.Stopped
                                    )
                                }
                            }

                            is Resource.Success -> {
                                val expensesList =
                                    currentResult.data?.map { userTransaction -> userTransaction.toExpense() }
                                        ?: emptyList()
                                Log.d(
                                    "UI STuff",
                                    "Success part kwa vm for the expense ${currentResult.data}"
                                )
                                _uiState.update {
                                    it.copy(
                                        getExpensesloadState = WhenToNavigate.Go,
                                        expense = expensesList
                                    )
                                }
                            }

                            else -> {}
                        }

                    }
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            getExpensesloadState = WhenToNavigate.Stopped
                        )
                    }
                    Log.d(
                        "get new Expense",
                        "get transaction Error$$$$$$$$$$$$$ ${e.message}${e.localizedMessage}"
                    )
                    e.printStackTrace()
                }
            }
        }
    }

    var getPredictionsJob: Job? = null
    fun getPredictions() {
        getPredictionsJob?.cancel()
        _uiState.update {
            it.copy(predicationloadState = WhenToNavigate.Processing)
        }
        getPredictionsJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val apiResult = predictionsRepository.getUserPredictions()

                    apiResult.collect { currentResult ->
                        when (currentResult) {
                            is Resource.Loading -> {
                                _uiState.update {
                                    it.copy(
                                        predicationloadState = WhenToNavigate.Processing
                                    )
                                }
                            }

                            is Resource.Error -> {
                                _uiState.update {
                                    it.copy(
                                        predicationloadState = WhenToNavigate.Stopped
                                    )
                                }
                            }

                            is Resource.Success -> {
                                val prediction = currentResult.data
                                Log.d(
                                    "UI Predictions STuff",
                                    "Success In Predictions VM ${currentResult.data}"
                                )
                                _uiState.update {
                                    it.copy(
                                        predicationloadState = WhenToNavigate.Go,
                                        predictions = Predictions(
                                            predictedSpendings = prediction?.predictedSpendings
                                                ?: emptyList(),
                                            totalSpent = prediction?.totalSpent ?: 0.0
                                        )
                                    )
                                }

                            }

                            else -> {}
                        }

                    }
                } catch (e: Exception) {
                    _uiState.update {
                        it.copy(
                            predicationloadState = WhenToNavigate.Stopped
                        )
                    }
                    Log.d(
                        "get new Predictions............................",
                        "get Predictions Error%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% ${e.message}${e.localizedMessage}"
                    )
                    e.printStackTrace()
                }
            }
        }
    }

//    fun handleDeleteExpense(expenseId: String) {
//
//        _uiState.update {
//            it.copy(
//                deleteExpenseState = WhenToNavigate.Processing
//            )
//        }
//        viewModelScope.launch {
//
//            withContext(Dispatchers.IO) {
//                try {
//                    val apiResult = expenseRepository.deleteUserExpense(expenseId =expenseId)
//                    apiResult.collect { currentResult ->
//                        when (currentResult) {
//                            is Resource.Loading -> {
//                                _uiState.update {
//                                    it.copy(
//                                        deleteExpenseState = WhenToNavigate.Processing
//                                    )
//                                }
//                            }
//
//                            is Resource.Success -> {
//                                _uiState.update {
//                                    it.copy(
//                                        deleteExpenseState = WhenToNavigate.Go,
//                                        deleteRequestResult = currentResult.data
//                                    )
//                                }
//                            }
//
//                            is Resource.Error -> {
//                                Log.d(
//                                    "Past Reques Gotten ToDelete",
//                                    "...........${currentResult.message}"
//                                )
//                                _uiState.update {
//                                    it.copy(
//                                        deleteExpenseState = WhenToNavigate.Stopped,
//                                        deleteRequestResult = DeleteResponse(
//                                            message = currentResult.message ?: ""
//                                        )
//                                    )
//                                }
//                            }
//                        }
//                    }
//                } catch (e: Exception) {
//                    Log.d(
//                        "Past Reques Gotten ToDelete",
//                        "...........${e.message} ${e.cause} ${e.localizedMessage}"
//                    )
//                    Log.d(
//                        "Past Reques Gotten ToDelete",
//                        "...........user Id SUpposed:::::${uiState.value.userId}"
//                    )
//                    _uiState.update {
//                        it.copy(
//                            deleteExpenseState = WhenToNavigate.Stopped,
//                            deleteRequestResult = DeleteResponse(
//                                message = e.message ?: ""
//                            )
//                        )
//                    }
//                }
//
//            }
//        }
//
//    }


}