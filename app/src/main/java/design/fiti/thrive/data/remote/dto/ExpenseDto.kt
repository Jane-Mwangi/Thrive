package design.fiti.thrive.data.remote.dto

import design.fiti.thrive.data.local.models.ExpenseEntity
import design.fiti.thrive.domain.model.Expense

data class ExpenseDto(
    val _id: String,
    val name: String,
    val amount: String,

) {
    fun toExpense(): Expense = Expense(
        _id,
        name,
        amount,

    )

    fun toExpenseEntity(): ExpenseEntity = ExpenseEntity(
        _id = _id,
        name = name,
        amount = amount,

    )
}

