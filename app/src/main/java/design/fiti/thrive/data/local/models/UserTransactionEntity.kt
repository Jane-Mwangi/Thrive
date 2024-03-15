package design.fiti.thrive.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import design.fiti.thrive.domain.model.UserTransaction

@Entity(tableName = "usertransaction")
data class UserTransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val __v: Int,
    val _id: String,
    val amount: Int,
    val createdAt: String,
    val name: String,
    val transactionOwner: String,
    val transactionType: String
) {
    fun toUserTransaction(): UserTransaction =
        UserTransaction(__v, _id, amount, createdAt, name, transactionOwner, transactionType)
}
