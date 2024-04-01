package design.fiti.thrive.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import design.fiti.thrive.domain.model.Expense

@Entity(tableName = "expense")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val _id: String,
    val name: String,
    val amount: String,

) {
    fun toExpense(): Expense = Expense(
        _id, name, amount
    )
}
