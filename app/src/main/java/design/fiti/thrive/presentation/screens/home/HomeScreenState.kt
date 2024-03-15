package design.fiti.thrive.presentation.screens.home

import design.fiti.thrive.domain.model.DeleteResponse
import design.fiti.thrive.domain.model.Expense
import design.fiti.thrive.domain.model.Income
import design.fiti.thrive.presentation.screens.authentication.signup.WhenToNavigate

data class HomeScreenState(
    val netBalance: String = "",
    val newIncome: String = "",
    val newIncomeError: String = "",
    val expenseName: String = "",
    val incomeName: String = "",
    val incomeAmount: String = "",
    val expenseAmount: String = "",
    val newExpense: String = "",
    val newExpenseError: String = "",
    val userId: String = "",
    val userEmail: String = "",
    val incomeId: String = "",
    val expenseId: String = "",
    val income: List<Income> = emptyList(),
    val expense: List<Expense> = emptyList(),
    val loadState: WhenToNavigate = WhenToNavigate.Stopped,
    val deleteLoadState: WhenToNavigate = WhenToNavigate.Stopped,
    val requestLoadState: WhenToNavigate = WhenToNavigate.Stopped,
    val uploadedState: WhenToNavigate = WhenToNavigate.Stopped,
    val uploadingResponseInfo: String = "",
    val deleteRequestResult: DeleteResponse? = null,
    val deleteIncomeState: WhenToNavigate = WhenToNavigate.Stopped,
    val deleteExpenseState: WhenToNavigate = WhenToNavigate.Stopped,


    )
