package design.fiti.thrive.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import design.fiti.thrive.domain.model.Income

@Entity(tableName = "income")
data class IncomeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val _id: String,
    val name: String,
    val amount: String,

) {
    fun toIncome(): Income = Income(
        _id, name, amount
    )

}

