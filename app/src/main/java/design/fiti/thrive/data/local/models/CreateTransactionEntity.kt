package design.fiti.thrive.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "createtransaction")

data class CreateTransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Int,
    val name: String,
    val transactionType: String
)
