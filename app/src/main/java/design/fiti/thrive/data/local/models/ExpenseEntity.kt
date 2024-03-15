package design.fiti.thrive.data.local.models

import androidx.room.Entity
import design.fiti.thrive.domain.model.Expense

@Entity(tableName = "expense")
data class ExpenseEntity(
    val _id: String,
    val name: String,
    val amount: String,
    val userIdReference: String
) {
    fun toExpense(): Expense = Expense(
        _id, name, amount, userIdReference
    )
}
