package design.fiti.thrive.presentation.screens.home

import design.fiti.thrive.domain.model.DeleteResponse
import design.fiti.thrive.domain.model.Expense
import design.fiti.thrive.domain.model.Income
import design.fiti.thrive.domain.model.Predictions
import design.fiti.thrive.domain.model.UserTransaction
import design.fiti.thrive.presentation.screens.authentication.signup.WhenToNavigate

data class HomeScreenState(
    val activeUserSelectionOption: TransactionType? = null,
    val netBalance: String = "",
    val newIncome: String = "",
    val newTransaction: Int = 0,
    val newTransactionName: String = "",
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
    val transaction: List<UserTransaction> = emptyList(),
    val predictions: Predictions? = null,
    val expense: List<Expense> = emptyList(),
    val loadState: WhenToNavigate = WhenToNavigate.Stopped,
    val predicationloadState: WhenToNavigate = WhenToNavigate.Stopped,
    val getincomesloadState: WhenToNavigate = WhenToNavigate.Stopped,
    val getExpensesloadState: WhenToNavigate = WhenToNavigate.Stopped,
    val createIncomeLoadState: WhenToNavigate = WhenToNavigate.Stopped,
    val deleteLoadState: WhenToNavigate = WhenToNavigate.Stopped,
    val requestLoadState: WhenToNavigate = WhenToNavigate.Stopped,
    val uploadedState: WhenToNavigate = WhenToNavigate.Stopped,
    val uploadingResponseInfo: String = "",
    val deleteRequestResult: DeleteResponse? = null,
    val deleteIncomeState: WhenToNavigate = WhenToNavigate.Stopped,
    val deleteExpenseState: WhenToNavigate = WhenToNavigate.Stopped


)

sealed class TransactionType {
    object Income : TransactionType()
    object Expense : TransactionType()
}
