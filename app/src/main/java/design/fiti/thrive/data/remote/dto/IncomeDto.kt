package design.fiti.thrive.data.remote.dto

import design.fiti.thrive.data.local.models.IncomeEntity
import design.fiti.thrive.domain.model.Expense

data class IncomeDto(
    val _id: String,
    val name: String,
    val amount: String,

) {
    fun toExpense(): Expense = Expense(
        _id,
        name,
        amount,

    )

    fun toIncomeEntity(): IncomeEntity = IncomeEntity(
        _id = _id,
        name = name,
        amount = amount,

    )
}

