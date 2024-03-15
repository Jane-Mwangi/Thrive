//package design.fiti.thrive.presentation.screens.home
//
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import dagger.hilt.android.lifecycle.HiltViewModel
//import design.fiti.thrive.data.remote.dto.ExpenseDto
//import design.fiti.thrive.domain.model.DeleteResponse
//import design.fiti.thrive.domain.repository.ExpenseRepository
//import design.fiti.thrive.domain.repository.IncomeRepository
//import design.fiti.thrive.domain.repository.TransactionRepository
//import design.fiti.thrive.presentation.screens.authentication.signup.WhenToNavigate
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.flow.update
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import javax.inject.Inject
//
//@HiltViewModel
//class HomeViewModel @Inject constructor(
//    private val expenseRepository: TransactionRepository,
//
//
//) : ViewModel() {
//    private var _uiState = MutableStateFlow(HomeScreenState())
//    val uiState = _uiState.asStateFlow()
//    val HomeVmTag = ""
//
//
//
//
//
//
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
//
//
//
//
//
//}